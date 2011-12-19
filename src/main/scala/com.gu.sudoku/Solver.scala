package com.gu.sudoku

object Solver {

  type SudokuGraphNode = Node[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraphEdge = Edge[(Z_9, Z_9), Set[Z_9]]

  type SudokuGraph = Graph[(Z_9, Z_9), Set[Z_9]]

  def graph(board: Board): SudokuGraph = {
    def candidates(i: Z_9, j: Z_9): Set[Z_9] = {
      board(i, j) map { Set(_) } getOrElse {
        Z_9.all.toSet -- (board.row(j).values ++ board.column(i).values ++ board.zone(i, j).values)
      }
    }

    var g = new SudokuGraph

    // Nodes
    for (i <- Z_9.all) {
      for (j <- Z_9.all) {
        g = g.addNode((i, j), candidates(i, j))
      }
    }

    // Edges
    for (i <- Z_9.all) {
      for (j <- Z_9.all) {

        // row neighbours
        (Z_9.all filter { _ > i }) foreach { k =>
          g = g.addEdge((i, j), (k, j))
        }

        // column neighbours
        (Z_9.all filter { _ > j }) foreach { k =>
          g = g.addEdge((i, j), (i, k))
        }

        // zone neighbours
        val zoneUpperLeftCol = (i.representative - 1) / 3 * 3 + 1
        val zoneUpperLeftRow = (j.representative - 1) / 3 * 3 + 1
        for (row <- 0 to 2) {
          for (col <- 0 to 2) {
            val rowZ_9 = Z_9.fromInt(zoneUpperLeftRow + row)
            val colZ_9 = Z_9.fromInt(zoneUpperLeftCol + col)

            if (rowZ_9 > j || (rowZ_9 == j && colZ_9 > i)) {
              g = g.addEdge((i, j), (colZ_9, rowZ_9))
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

    Board(rows(One), rows(Two), rows(Three), rows(Four), rows(Five), rows(Six), rows(Seven), rows(Eight), rows(Nine))
  }

  def iterateEliminateByRowColumnAndZoneElements(puzzle: SudokuGraph): SudokuGraph = {
    iterate(puzzle) { eliminateByRowColumnAndZoneElements }
  }

  def eliminateByRowColumnAndZoneElements(puzzle: SudokuGraph): SudokuGraph = {
    // The process of computing candidate sets for node values during
    // graph construction performs the exclusion of elements in the 
    // same row, column or zone. Conversion back to Board is all that 
    // is required for this reduction.
    graph(board(puzzle))
  }

  def iterateOnlyPossiblePlacing(puzzle: SudokuGraph): SudokuGraph = {
    iterate(puzzle) { eliminateByOnlyPossiblePlacing }
  }

  def eliminateByOnlyPossiblePlacing(puzzle: SudokuGraph): SudokuGraph = {
    // For each exclusion area, place elements that have only one possible placing
    var g = puzzle

    def eliminateBy(puzzle: SudokuGraph, nodes: List[SudokuGraphNode]): SudokuGraph = {
      var g = puzzle

      val candidateSets: List[(SudokuGraphNode, List[Z_9])] = nodes map { node => (node, node.value.toList) }

      val reverseIndexCandidateSets: List[(Z_9, SudokuGraphNode)] = candidateSets flatMap {
        case (node, candidates) => candidates map { candidate => (candidate, node) }
      }
      val reverseIndexCandidateSetsLookup: Map[Z_9, List[(Z_9, SudokuGraphNode)]] = reverseIndexCandidateSets groupBy {
        case (candidate, _) => candidate
      }

      val singlePlacements: List[(SudokuGraphNode, Z_9)] = reverseIndexCandidateSetsLookup.toList collect {
        case (candidate, head :: Nil) => (head._2, candidate)
      }
      val newSinglePlacements: List[(SudokuGraphNode, Z_9)] = singlePlacements filter {
        case (node, _) => node.value.size != 1
      }

      newSinglePlacements foreach {
        case (node, newValue) =>
          val neighbours: Set[SudokuGraphNode] = g.neighbours(node) -- Set(node)
          val neighboursToUpdate = neighbours filter { _.value contains newValue }

          neighboursToUpdate foreach { neighbour =>
            g = g.updateNode(neighbour.label, neighbour.value -- Set(newValue))
          }

          g = g.updateNode(node.label, Set(newValue))
      }

      g
    }

    // Eliminate by rows
    for (row <- Z_9.all) {
      val nodes: List[SudokuGraphNode] = Z_9.all map { col => g.getNode(col, row).get }
      g = eliminateBy(g, nodes)
    }

    // Eliminate by columns
    for (col <- Z_9.all) {
      val nodes: List[SudokuGraphNode] = Z_9.all map { row => g.getNode(col, row).get }
      g = eliminateBy(g, nodes)
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

        val nodes: List[SudokuGraphNode] = indices.toList map { case (col, row) => g.getNode(col, row).get }
        g = eliminateBy(g, nodes)
      }
    }

    g
  }

  def iterateExploitTwoElementCoverings(puzzle: SudokuGraph): SudokuGraph = {
    iterate(puzzle) { exploitTwoElementCoverings }
  }

  def exploitTwoElementCoverings(puzzle: SudokuGraph): SudokuGraph = {
    // Any pair of nodes which have the same two possible candidate values
    // excludes any node which is a neighbour of both from having either
    // of these values.    
    var g = puzzle

    val nodesWithTwoElementCandidateSets: List[SudokuGraphNode] = g.nodes.toList filter { _.value.size == 2 }
    val partitionedByCandidateSets: Map[Set[Z_9], List[SudokuGraphNode]] = nodesWithTwoElementCandidateSets groupBy { _.value }
    val nonSingletonPartitions: List[List[SudokuGraphNode]] = (partitionedByCandidateSets.values filter { _.size > 1 }).toList

    val twoElementCoverings: List[(SudokuGraphNode, SudokuGraphNode)] = nonSingletonPartitions flatMap { partition =>
      val crossProduct = partition flatMap { x => partition map { y => (x, y) } }
      crossProduct filter { case (n1, n2) => g.neighbours(n1) contains n2 }
    }

    twoElementCoverings foreach {
      case (n1, n2) =>
        val coveringElements: Set[Z_9] = n1.value ++ n2.value
        val commonNeighbours: Set[SudokuGraphNode] = (g.neighbours(n1) intersect g.neighbours(n2)) -- Set(n1, n2)
        val neighboursToUpdate = commonNeighbours filter { _.value.intersect(coveringElements).size > 0 }

        neighboursToUpdate foreach { node =>
          g = g.updateNode(node.label, node.value -- coveringElements)
        }
    }

    g
  }

  def solveByIterateEliminateByRowColumnAndZoneElements(puzzle: SudokuGraph): Option[SudokuGraph] = {
    Some(iterateEliminateByRowColumnAndZoneElements(puzzle)) filter { graph => board(graph).valid }
  }

  def solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacing(puzzle: SudokuGraph): Option[SudokuGraph] = {
    val solution = iterate(puzzle) {
      (iterateEliminateByRowColumnAndZoneElements _) andThen iterateOnlyPossiblePlacing
    }

    Some(solution) filter { graph => board(graph).valid }
  }

  def solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacingAndExploitTwoElementCoverings(puzzle: SudokuGraph): Option[SudokuGraph] = {
    val solution = iterate(puzzle) {
      (iterateEliminateByRowColumnAndZoneElements _) andThen iterateOnlyPossiblePlacing andThen iterateExploitTwoElementCoverings
    }

    Some(solution) filter { graph => board(graph).valid }
  }

  def solve(puzzle: Board): Option[Board] = {
    //    val solution = solveByIterateEliminateByRowColumnAndZoneElements(graph(puzzle))
    val solution = solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacing(graph(puzzle))
    //    val solution = solveByIterateEliminateByRowColumnAndZoneElementsAndOnlyPossiblePlacingAndExploitTwoElementCoverings(graph(puzzle))

    solution map { board(_) } filter { _.valid }
  }

}