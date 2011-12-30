package com.gu.sudoku

object GraphColouringProblem {

  def apply(board: Board): GraphColouringProblem = {
    var graph = unconstrained

    for (col <- Z_9.all) {
      for (row <- Z_9.all) {
        board(col, row) foreach { element =>
          graph = graph.addConstraintToNode(col, row, element)
        }
      }
    }

    graph
  }

  lazy val unconstrained = new GraphColouringProblem {
    protected val graph = {
      var _graph = new SudokuGraph

      // Nodes
      for (col <- Z_9.all) {
        for (row <- Z_9.all) {
          _graph = _graph.addNode((col, row), Z_9.all.toSet)
        }
      }

      // Edges
      for (col <- Z_9.all) {
        for (row <- Z_9.all) {
          val zoneColOrigin = (col.representative - 1) / 3 * 3 + 1
          val zoneRowOrigin = (row.representative - 1) / 3 * 3 + 1

          for (i <- Z_9.all) {
            _graph = _graph.addEdge((col, row), (i, row)).
              addEdge((col, row), (col, i)).
              addEdge((col, row), (
                Z_9(zoneColOrigin + (i.representative - 1) % 3),
                Z_9(zoneRowOrigin + (i.representative - 1) / 3)
              )
              )
          }

          // Fixup: Remove self-edges
          _graph = _graph.removeEdge((col, row), (col, row))
        }
      }

      _graph
    }
  }
}

trait GraphColouringProblem { self =>

  type SudokuGraphNode = Node[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraphEdge = Edge[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraph = Graph[(Z_9, Z_9), Set[Z_9]]

  protected val graph: SudokuGraph

  lazy val candidateSetsSizeSum: Int = (graph.nodes.toList map { _.value.size }).sum

  def apply(col: Z_9, row: Z_9): Set[Z_9] = graph.getNode((col, row)).get.value

  private def addConstraintToNode(label: (Z_9, Z_9), element: Z_9): GraphColouringProblem =
    addConstraintsToNode(label._1, label._2, Set(element))

  private def addConstraintToNode(col: Z_9, row: Z_9, element: Z_9): GraphColouringProblem =
    addConstraintsToNode(col, row, Set(element))

  private def addConstraintsToNode(label: (Z_9, Z_9), elements: Set[Z_9]): GraphColouringProblem =
    addConstraintsToNode(label._1, label._2, elements)

  private def addConstraintsToNeighbours(label: (Z_9, Z_9), elements: Set[Z_9]): GraphColouringProblem =
    addConstraintsToNeighbours(label._1, label._2, elements)

  private def addConstraintsToNode(col: Z_9, row: Z_9, elements: Set[Z_9]): GraphColouringProblem = {
    new GraphColouringProblem {
      protected val graph = self.graph.updateNodeValue(
        (col, row), self.graph.getNode((col, row)).get.value intersect elements
      )
    }
  }

  private def addConstraintsToNeighbours(col: Z_9, row: Z_9, elements: Set[Z_9]): GraphColouringProblem = {
    var _graph = graph
    graph.neighbours((col, row)) foreach { neighbour =>
      _graph = _graph.updateNodeValue(neighbour.label, neighbour.value intersect elements)
    }

    new GraphColouringProblem {
      protected val graph = _graph
    }
  }

  def singleEliminateByLatinBlockExclusion(): GraphColouringProblem = {
    // For each placement, place elements that have only one possible 
    // candidate and exclude value from neighbour candidate sets.

    var _graphColouringProblem = this

    _graphColouringProblem.graph.nodes filter { _.value.size == 1 } foreach { singleValueNode =>
      _graphColouringProblem = _graphColouringProblem.addConstraintsToNeighbours(
        singleValueNode.label,
        Z_9.all.toSet -- singleValueNode.value
      )
    }

    _graphColouringProblem
  }

  def eliminateByLatinBlockExclusion(): GraphColouringProblem = {
    // For each placement, place elements that have only one possible 
    // candidate and exclude value from neighbour candidate sets.

    iterate(this) { _.singleEliminateByLatinBlockExclusion() }
  }

  private def rowOf(node: SudokuGraphNode): Set[SudokuGraphNode] = {
    (graph.neighbours(node.label) + node) filter { _.label._2 == node.label._2 }
  }

  private def columnOf(node: SudokuGraphNode): Set[SudokuGraphNode] = {
    (graph.neighbours(node.label) + node) filter { _.label._1 == node.label._1 }
  }

  private def zoneOf(node: SudokuGraphNode): Set[SudokuGraphNode] = {
    (graph.neighbours(node.label) + node) filter { other =>
      (node.label._1.representative - 1) / 3 == (other.label._1.representative - 1) / 3 &&
        (node.label._2.representative - 1) / 3 == (other.label._2.representative - 1) / 3
    }
  }

  private def placementsFor(nodes: Set[SudokuGraphNode]): Map[Z_9, Set[SudokuGraphNode]] = {
    val placements: Set[(Z_9, SudokuGraphNode)] = nodes flatMap {
      node => node.value map { value => (value, node) }
    }

    val placementGroups: Map[Z_9, Set[(Z_9, SudokuGraphNode)]] = placements groupBy {
      case (candidate, _) => candidate
    }

    placementGroups mapValues {
      _ map { case (_, node) => node }
    }
  }

  private def eliminateBySinglePlacementsIn(nodes: Set[SudokuGraphNode]): GraphColouringProblem = {
    val singlePlacements: Map[Z_9, SudokuGraphNode] = placementsFor(nodes) collect {
      case (value, SingletonSet(node)) => value -> node
    }

    val newSinglePlacements: Map[Z_9, SudokuGraphNode] = singlePlacements filter {
      case (_, node) => node.value.size != 1
    }

    var graphColouringProblem = this
    newSinglePlacements foreach {
      case (value, node) =>
        graphColouringProblem = graphColouringProblem.addConstraintToNode(node.label, value)
    }

    graphColouringProblem
  }

  def singleEliminateByRowSinglePlacements(): GraphColouringProblem = {
    // For each row, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (row <- Z_9.all) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(rowOf(graph.getNode(One, row).get))
    }

    _graphColouringProblem
  }

  def singleEliminateByColumnSinglePlacements(): GraphColouringProblem = {
    // For each column, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (col <- Z_9.all) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(columnOf(graph.getNode(col, One).get))
    }

    _graphColouringProblem
  }

  def singleEliminateByZoneSinglePlacements(): GraphColouringProblem = {
    // For each zone, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (band <- (0 to 2)) {
      for (indexWithinBand <- (0 to 2)) {
        val zoneUpperLeftCol = Z_9(band * 3 + 1)
        val zoneUpperLeftRow = Z_9(indexWithinBand * 3 + 1)
        val node = graph.getNode(zoneUpperLeftCol, zoneUpperLeftRow).get

        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(zoneOf(node))
      }
    }

    _graphColouringProblem
  }

  def eliminateByLatinBlockSinglePlacements(): GraphColouringProblem = {
    // For each latin block, place elements that have only one possible placing

    iterate(this) {
      _.singleEliminateByRowSinglePlacements().
        singleEliminateByColumnSinglePlacements().
        singleEliminateByZoneSinglePlacements()
    }
  }

  private def eliminateBySinglePlacementSetsIn(nodes: Set[SudokuGraphNode]): GraphColouringProblem = {
    val singlePlacementSets: Map[Z_9, Set[SudokuGraphNode]] = placementsFor(nodes) filter {
      case (value, nodes) => nodes.size == 2 || nodes.size == 3
    }

    var graphColouringProblem = this
    singlePlacementSets foreach {
      case (value, nodes) =>
        val commonNeighbours = nodes map { node => graph.neighbours(node.label) } reduce { _ intersect _ }
        commonNeighbours foreach { neighbour =>
          graphColouringProblem = graphColouringProblem.addConstraintsToNode(neighbour.label, Z_9.all.toSet - value)
        }
    }

    graphColouringProblem
  }

  def eliminateByLatinBlockSinglePlacementSets(): GraphColouringProblem = {
    // For each latin block, values that can only be placed in two or three
    // places exclude common neighbours from having that value

    iterate(this) { graphColouringProblem =>
      var _graphColouringProblem = graphColouringProblem

      // Eliminate by rows
      for (row <- Z_9.all) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(rowOf(graph.getNode(One, row).get))
      }

      // Eliminate by columns
      for (col <- Z_9.all) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(columnOf(graph.getNode(col, One).get))
      }

      // Eliminate by zones
      for (band <- (0 to 2)) {
        for (indexWithinBand <- (0 to 2)) {
          val zoneUpperLeftCol = Z_9(band * 3 + 1)
          val zoneUpperLeftRow = Z_9(indexWithinBand * 3 + 1)
          val node = graph.getNode(zoneUpperLeftCol, zoneUpperLeftRow).get

          _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(zoneOf(node))
        }
      }

      _graphColouringProblem
    }
  }

  def eliminateByTwoElementCoverings(): GraphColouringProblem = {
    // Any pair of connected nodes which have the same two possible candidate
    // values excludes any node which is a neighbour of both from having either
    // of these values.

    iterate(this) { graphColouringProblem =>
      var _graphColouringProblem = graphColouringProblem

      val nodesWithTwoElementCandidateSets: Set[SudokuGraphNode] = _graphColouringProblem.graph.nodes filter { _.value.size == 2 }
      val partitionedByCandidateSets: Map[Set[Z_9], Set[SudokuGraphNode]] = nodesWithTwoElementCandidateSets groupBy { _.value }
      val nonSingletonPartitions: Set[Set[SudokuGraphNode]] = (partitionedByCandidateSets.values filter { _.size > 1 }).toSet

      val twoElementCoverings: Set[(SudokuGraphNode, SudokuGraphNode)] = nonSingletonPartitions flatMap { partition =>
        partition cross partition
      } filter {
        case (n1, n2) => n1 != n2
      }

      val connectedTwoElementCoverings = twoElementCoverings filter {
        case (n1, n2) => _graphColouringProblem.graph.neighbours(n1.label) contains n2
      }

      connectedTwoElementCoverings foreach {
        case (n1, n2) =>
          val coveringElements: Set[Z_9] = n1.value ++ n2.value
          val commonNeighbours: Set[SudokuGraphNode] = (_graphColouringProblem.graph.neighbours(n1.label) intersect _graphColouringProblem.graph.neighbours(n2.label)) -- Set(n1, n2)
          val neighboursToUpdate = commonNeighbours filter { _.value.intersect(coveringElements).size > 0 }

          neighboursToUpdate foreach { neighbour =>
            _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbour.label, Z_9.all.toSet -- coveringElements)
          }
      }

      _graphColouringProblem
    }
  }

  def eliminateByThreeElementCoverings(): GraphColouringProblem = {
    // Any triple of completely connected nodes which have only the three possible candidate
    // values excludes any node which is a neighbour of all from having any of these values.

    iterate(this) { graphColouringProblem =>
      var _graphColouringProblem = graphColouringProblem

      val nodesWithTwoOrThreeElementCandidateSets: Set[SudokuGraphNode] = _graphColouringProblem.graph.nodes filter { node => node.value.size == 2 || node.value.size == 3 }

      // Expand out two element candidate sets with all possibles
      val nodesWithThreeElementCandidateSets: Set[SudokuGraphNode] = nodesWithTwoOrThreeElementCandidateSets flatMap { node =>
        node.value.size match {
          case 3 => List(node)
          case 2 => (Z_9.all.toSet -- node.value) map { padding => Node[(Z_9, Z_9), Set[Z_9]](node.label, node.value + padding) }
        }
      }

      val partitionedByCandidateSets: Map[Set[Z_9], Set[SudokuGraphNode]] = nodesWithThreeElementCandidateSets groupBy { _.value }
      val potentialPartitions: Set[Set[SudokuGraphNode]] = partitionedByCandidateSets.values.toSet filter { _.size >= 3 }

      val threeElementCoverings: Set[(SudokuGraphNode, SudokuGraphNode, SudokuGraphNode)] = potentialPartitions flatMap { partition =>
        (partition cross partition cross partition) map { case ((n1, n2), n3) => (n1, n2, n3) }
      } filter {
        case (n1, n2, n3) => Set(n1, n2, n3).size == 3
      }

      val connectedThreeElementCoverings = threeElementCoverings filter {
        case (n1, n2, n3) =>
          (Set(n2, n3) subsetOf _graphColouringProblem.graph.neighbours(n1.label)) &&
            (Set(n1, n3) subsetOf _graphColouringProblem.graph.neighbours(n2.label))
      }

      connectedThreeElementCoverings foreach {
        case (n1, n2, n3) =>
          val coveringElements: Set[Z_9] = n1.value ++ n2.value ++ n3.value
          val commonNeighbours: Set[SudokuGraphNode] = (
            _graphColouringProblem.graph.neighbours(n1.label) intersect
            _graphColouringProblem.graph.neighbours(n2.label) intersect
            _graphColouringProblem.graph.neighbours(n3.label)
          ) -- Set(n1, n2, n3)
          val neighboursToUpdate = commonNeighbours filter { _.value.intersect(coveringElements).size > 0 }

          neighboursToUpdate foreach { neighbour =>
            _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbour.label, Z_9.all.toSet -- coveringElements)
          }
      }

      _graphColouringProblem
    }
  }

  def reduceBySearch(): Iterator[GraphColouringProblem] = {
    // Pick the node with smallest candidate set size > 1 and most constrained neighbours and try alternatives
    val nodes: Map[Int, List[SudokuGraphNode]] = graph.nodes.toList groupBy { _.value.size }

    val candidateNodes = nodes.toList filter { case (size, _) => size > 1 }

    candidateNodes match {
      case Nil => Iterator(this)
      case _ =>
        val nodesWithSmallestCandidateSetSize = (candidateNodes sortBy { case (size, _) => size }).head._2

        val nodesWithNeighboursCandidateSetSum = nodesWithSmallestCandidateSetSize map { node =>
          ((graph.neighbours(node.label).toList map { _.value.size }).sum, node)
        }

        val node = (nodesWithNeighboursCandidateSetSum sortBy { case (size, _) => size }).head._2

        node.value.toIterator map { value =>
          new GraphColouringProblem { protected val graph = self.graph.updateNodeValue(node.label, Set(value)) }
        }
    }
  }

  lazy val valid = toBoard.valid
  lazy val solved = toBoard.solved

  lazy val toBoard: Board = {
    def boardValue(i: Z_9, j: Z_9) = SingletonSet.unapply(this(i, j))

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
        boardValue(Nine, j)))
    }).toMap

    Board(rows(One), rows(Two), rows(Three), rows(Four), rows(Five), rows(Six), rows(Seven), rows(Eight), rows(Nine))
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[GraphColouringProblem]
  override def hashCode(): Int = graph.hashCode()
  override def equals(other: Any): Boolean = other.isInstanceOf[GraphColouringProblem] match {
    case true =>
      val that = other.asInstanceOf[GraphColouringProblem]
      (that canEqual this) && (this.graph == that.graph)

    case _ => false
  }
}
