package com.gu.sudoku

object Sudoku {

  type SudokuGraph = Graph[(Z_9, Z_9), Set[Z_9]]

  def graph(board: Board): SudokuGraph = {
    val g = new SudokuGraph
    def candidates(i: Z_9, j: Z_9): Set[Z_9] = {
      board(i, j) map { Set(_) } getOrElse {
        Z_9.all.toSet -- (board.row(j).values ++ board.column(i).values ++ board.zone(i, j).values)
      }
    }

    for (i <- Z_9.all) {
      for (j <- Z_9.all) {
        g.addNode((i, j), candidates(i, j))
      }
    }

    for (i <- Z_9.all) {
      for (j <- Z_9.all) {

        // row neighbours
        (Z_9.all filter { _ > i }) foreach { k =>
          g.addEdge((i, j), (k, j))
        }

        // column neighbours
        (Z_9.all filter { _ > j }) foreach { k =>
          g.addEdge((i, j), (i, k))
        }

        // zone neighbours
        val zoneUpperLeftCol = (i.representative - 1) / 3 * 3 + 1
        val zoneUpperLeftRow = (j.representative - 1) / 3 * 3 + 1
        for (row <- 0 to 2) {
          for (col <- 0 to 2) {
            val rowZ_9 = Z_9.fromInt(zoneUpperLeftRow + row)
            val colZ_9 = Z_9.fromInt(zoneUpperLeftCol + col)

            if (rowZ_9 > i || (rowZ_9 == i && colZ_9 > j)) {
              g.addEdge((i, j), (colZ_9, rowZ_9))
            }

          }
        }
      }
    }

    g
  }

  def board(graph: SudokuGraph): Board = {
    def boardValue(i: Z_9, j: Z_9) = {
      graph.getNode((i, j)).get.value.toList match {
        case head :: Nil => Some(head)
        case _ => None
      }
    }

    val rows = (Z_9.all map { j =>
      (j, Row(
        boardValue(One, j),
        boardValue(Two, j),
        boardValue(Three, j),
        boardValue(Four, j),
        boardValue(Five, j),
        boardValue(Six, j),
        boardValue(Seven, j),
        boardValue(Eight, j),
        boardValue(Nine, j)
      ))
    }).toMap

    Board(
      rows(One),
      rows(Two),
      rows(Three),
      rows(Four),
      rows(Five),
      rows(Six),
      rows(Seven),
      rows(Eight),
      rows(Nine)
    )
  }

  def iterateEliminateByRowColumnAndZoneElements(puzzle: SudokuGraph): SudokuGraph = {
    var previous = puzzle
    var current = eliminateByRowColumnAndZoneElements(previous)

    while (board(current) != board(previous)) {
      previous = current
      current = eliminateByRowColumnAndZoneElements(previous)
    }

    current
  }

  def eliminateByRowColumnAndZoneElements(puzzle: SudokuGraph): SudokuGraph = {
    // The process of computing candidate sets for node values during
    // graph construction performs the exclusion of elements in the 
    // same row, column or zone. Conversion back to Board is all that 
    // is required for this reduction.
    graph(board(puzzle))
  }

  def iterateOnlyPossiblePlacing(puzzle: SudokuGraph): SudokuGraph = {
    var previous = puzzle
    var current = eliminateByOnlyPossiblePlacing(previous)

    while (current._1) {
      previous = current._2
      current = eliminateByOnlyPossiblePlacing(previous)
    }

    current._2
  }

  def eliminateByOnlyPossiblePlacing(puzzle: SudokuGraph): (Boolean, SudokuGraph) = {
    // For each exclusion area, place elements that have only one possible placing
    val g = puzzle.copy()
    var changed = false

    def eliminateBy(nodes: List[g.Node]): Boolean = {
      var updated = false
      val candidateSets: List[(g.Node, List[Z_9])] = nodes map { node => (node, node.value.toList) }
      val reverseIndexCandidateSets: List[(Z_9, g.Node)] = candidateSets flatMap {
        case (node, candidates) => candidates map { candidate => (candidate, node) }
      }

      val reverseIndexCandidateSetsLookup: Map[Z_9, List[(Z_9, g.Node)]] = reverseIndexCandidateSets groupBy {
        case (candidate, _) => candidate
      }

      val singlePlacements: List[(g.Node, Z_9)] = reverseIndexCandidateSetsLookup.toList collect {
        case (candidate, head :: Nil) => (head._2, candidate)
      }
      val newSinglePlacements: List[(g.Node, Z_9)] = singlePlacements filter {
        case (node, _) => node.value.size != 1
      }

      newSinglePlacements foreach {
        case (node, newValue) =>
          val neighbours: Set[g.Node] = node.neighbours -- Set(node)
          val neighboursToUpdate = neighbours filter { _.value contains newValue }

          neighboursToUpdate foreach { neighbour =>
            g.updateNode(neighbour.label, neighbour.value -- Set(newValue))
            updated = true
          }

          g.updateNode(node.label, Set(newValue))
      }

      updated
    }

    // Eliminate by rows
    for (row <- Z_9.all) {
      val nodes: List[g.Node] = Z_9.all map { col => g.getNode(col, row).get }

      val updated = eliminateBy(nodes)
      if (updated) {
        changed = true
      }
    }

    // Eliminate by columns
    for (col <- Z_9.all) {
      val nodes: List[g.Node] = Z_9.all map { row => g.getNode(col, row).get }

      val updated = eliminateBy(nodes)
      if (updated) {
        changed = true
      }
    }

    // Eliminate by zones
    for (band <- (0 to 2)) {
      for (indexWithinBand <- (0 to 2)) {
        val zoneUpperLeftCol = band * 3 + 1
        val zoneUpperLeftRow = indexWithinBand * 3 + 1

        val indices = (0 to 2) flatMap { row =>
          (0 to 2) map { col =>
            (Z_9.fromInt(zoneUpperLeftCol + col), Z_9.fromInt(zoneUpperLeftRow + row))
          }
        }

        val nodes: List[g.Node] = indices.toList map { case (col, row) => g.getNode(col, row).get }

        val updated = eliminateBy(nodes)
        if (updated) {
          changed = true
        }
      }
    }

    (changed, g)
  }

  def iterateExploitTwoElementCoverings(puzzle: SudokuGraph): SudokuGraph = {
    var previous = puzzle
    var current = exploitTwoElementCoverings(previous)

    while (current._1) {
      previous = current._2
      current = exploitTwoElementCoverings(previous)
    }

    current._2
  }

  def exploitTwoElementCoverings(puzzle: SudokuGraph): (Boolean, SudokuGraph) = {
    // Any pair of nodes which have the same two possible candidate values
    // excludes any node which is a neighbour of both from having either
    // of these values.    
    val g: Graph[(Z_9, Z_9), Set[Z_9]] = puzzle.copy()
    var changed = false

    val nodesWithTwoElementCandidateSets = g.getNodes filter { _.value.size == 2 }
    val partitionedByCandidateSets: Map[Set[Z_9], List[g.Node]] = nodesWithTwoElementCandidateSets groupBy { _.value }
    val nonSingletonPartitions: List[List[g.Node]] = (partitionedByCandidateSets.values filter { _.size > 1 }).toList

    val twoElementCoverings: List[(g.Node, g.Node)] = nonSingletonPartitions flatMap { partition =>
      val crossProduct = partition flatMap { x => partition map { y => (x, y) } }
      crossProduct filter { case (n1, n2) => n1.neighbours contains n2 }
    }

    twoElementCoverings foreach {
      case (n1, n2) =>
        val coveringElements: Set[Z_9] = n1.value ++ n2.value
        val commonNeighbours: Set[g.Node] = (n1.neighbours intersect n2.neighbours) -- Set(n1, n2)
        val neighboursToUpdate = commonNeighbours filter { _.value.intersect(coveringElements).size > 0 }

        neighboursToUpdate foreach { node =>
          g.updateNode(node.label, node.value -- coveringElements)
          changed = true
        }
    }

    (changed, g)
  }

  def solveByIterateEliminateByRowColumnAndZoneElements(puzzle: SudokuGraph): Option[SudokuGraph] = {
    Some(iterateEliminateByRowColumnAndZoneElements(puzzle)) filter { graph => board(graph).valid }
  }

  def solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacing(puzzle: SudokuGraph): Option[SudokuGraph] = {
    var previous = puzzle
    var current = iterateOnlyPossiblePlacing(iterateEliminateByRowColumnAndZoneElements(previous))

    while (board(current) != board(previous)) {
      previous = current
      current = iterateOnlyPossiblePlacing(iterateEliminateByRowColumnAndZoneElements(previous))
    }

    Some(current) filter { graph => board(graph).valid }
  }

  def solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacingAndExploitTwoElementCoverings(puzzle: SudokuGraph): Option[SudokuGraph] = {
    var previous = puzzle
    var current = iterateExploitTwoElementCoverings(iterateOnlyPossiblePlacing(iterateEliminateByRowColumnAndZoneElements(previous)))

    while (board(current) != board(previous)) {
      previous = current
      current = iterateExploitTwoElementCoverings(iterateOnlyPossiblePlacing(iterateEliminateByRowColumnAndZoneElements(previous)))
    }

    Some(current) filter { graph => board(graph).valid }
  }

  def solve(puzzle: Board): Option[Board] = {
    //    val solution = solveByIterateEliminateByRowColumnAndZoneElements(graph(puzzle))
    val solution = solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacing(graph(puzzle))
    //    val solution = solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacingAndExploitTwoElementCoverings(graph(puzzle))

    solution map { board(_) } filter { _.valid }
  }

}