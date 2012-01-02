package com.gu.sudoku

case class Node(label: (Z_9, Z_9), candidates: Set[Z_9]) {
  def neighbours(that: Node): Boolean = neighbours(that.label)
  def neighbours(label: (Z_9, Z_9)): Boolean = neighbours(label._1, label._2)

  def neighbours(column: Z_9, row: Z_9): Boolean = {
    // No self edges
    !(this.label._1 == column && this.label._2 == row) &&
      (this.label._1 == column || this.label._2 == row || (
        (this.label._1.representative - 1) / 3 == (column.representative - 1) / 3 &&
        (this.label._2.representative - 1) / 3 == (row.representative - 1) / 3)
      )
  }

  def sameColumn(that: Node): Boolean = this.label._1 == that.label._1
  def sameRow(that: Node): Boolean = this.label._2 == that.label._2
  def sameZone(that: Node): Boolean = {
    (this.label._1.representative - 1) / 3 == (that.label._1.representative - 1) / 3 &&
      (this.label._2.representative - 1) / 3 == (that.label._2.representative - 1) / 3
  }

  lazy val neighbourLabels: Set[(Z_9, Z_9)] = (Z_9.all.toSet cross Z_9.all) filter { neighbours }

  lazy val index: Int = label._1.representative + (label._2.representative - 1) * 9
}

case class Graph(nodes: Set[Node]) {
  // Edge relationships are inferred from labels

  private lazy val nodeLookup: Map[(Z_9, Z_9), Node] = nodes toMapWithKeys { _.label }

  def apply(column: Z_9, row: Z_9): Node = apply((column, row))
  def apply(label: (Z_9, Z_9)): Node = nodeLookup(label)

  def replace(node: Node, replacement: Node): Graph = Graph(nodes - node + replacement)

  def rowOf(label: (Z_9, Z_9)): Set[Node] = rowOf(apply(label))
  def rowOf(column: Z_9, row: Z_9): Set[Node] = rowOf(apply(column, row))
  def rowOf(node: Node): Set[Node] = nodes filter { node sameRow _ }

  def columnOf(label: (Z_9, Z_9)): Set[Node] = columnOf(apply(label))
  def columnOf(column: Z_9, row: Z_9): Set[Node] = columnOf(apply(column, row))
  def columnOf(node: Node): Set[Node] = nodes filter { node sameColumn _ }

  def zoneOf(label: (Z_9, Z_9)): Set[Node] = zoneOf(apply(label))
  def zoneOf(column: Z_9, row: Z_9): Set[Node] = zoneOf(apply(column, row))
  def zoneOf(node: Node): Set[Node] = nodes filter { node sameZone _ }
}

object GraphColouringProblem {

  def apply(board: Board): GraphColouringProblem = {
    var graph = unconstrained

    for (column <- Z_9.all) {
      for (row <- Z_9.all) {
        board(column, row) foreach { element =>
          graph = graph.addConstraintToNode(column, row, element)
        }
      }
    }

    graph
  }

  lazy val unconstrained = new GraphColouringProblem {
    protected val graph = {
      val labels = (Z_9.all cross Z_9.all).toSet[(Z_9, Z_9)]
      val nodes = labels map {
        label => Node(label, Z_9.all.toSet)
      }

      Graph(nodes)
    }
  }
}

trait GraphColouringProblem { self =>

  protected val graph: Graph

  def apply(column: Z_9, row: Z_9): Node = graph(column, row)

  private def addConstraintToNode(label: (Z_9, Z_9), element: Z_9): GraphColouringProblem =
    addConstraintsToNode(label._1, label._2, Set(element))

  private def addConstraintToNode(col: Z_9, row: Z_9, element: Z_9): GraphColouringProblem =
    addConstraintsToNode(col, row, Set(element))

  private def addConstraintsToNode(label: (Z_9, Z_9), elements: Set[Z_9]): GraphColouringProblem =
    addConstraintsToNode(label._1, label._2, elements)

  private def addConstraintsToNeighbours(label: (Z_9, Z_9), elements: Set[Z_9]): GraphColouringProblem =
    addConstraintsToNeighbours(label._1, label._2, elements)

  private def addConstraintsToNode(column: Z_9, row: Z_9, elements: Set[Z_9]): GraphColouringProblem = {
    val oldNode: Node = graph(column, row)
    val newCandidates = oldNode.candidates intersect elements

    (oldNode.candidates == newCandidates) match {
      case true => this

      case false =>
        val newNode: Node = oldNode.copy(candidates = newCandidates)

        new GraphColouringProblem {
          protected val graph = self.graph.replace(oldNode, newNode)
        }
    }
  }

  private def addConstraintsToNeighbours(column: Z_9, row: Z_9, elements: Set[Z_9]): GraphColouringProblem = {
    val node = graph(column, row)

    var _graphColouringProblem = this
    node.neighbourLabels foreach { neighbourLabel =>
      _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbourLabel, elements)
    }

    _graphColouringProblem
  }

  def singleEliminateByLatinBlockExclusion(): GraphColouringProblem = {
    // For each placement, place elements that have only one possible 
    // candidate and exclude value from neighbour candidate sets.

    var _graphColouringProblem = this
    _graphColouringProblem.oneElementPlacings foreach { singleValueNode =>
      _graphColouringProblem = _graphColouringProblem.addConstraintsToNeighbours(
        singleValueNode.label,
        Z_9.all.toSet -- singleValueNode.candidates)
    }

    _graphColouringProblem
  }

  def eliminateByLatinBlockExclusion(): GraphColouringProblem = {
    // For each placement, place elements that have only one possible 
    // candidate and exclude value from neighbour candidate sets.

    iterate(this) { _.singleEliminateByLatinBlockExclusion() }
  }

  private def placementsFor(nodes: Set[Node]): Map[Z_9, Set[Node]] = {
    val placements: Set[(Z_9, Node)] = nodes flatMap {
      node => node.candidates map { value => (value, node) }
    }

    val placementGroups: Map[Z_9, Set[(Z_9, Node)]] = placements groupBy {
      case (candidate, _) => candidate
    }

    placementGroups mapValues {
      _ map { case (_, node) => node }
    }
  }

  private def eliminateBySinglePlacementsIn(nodes: Set[Node]): GraphColouringProblem = {
    val singlePlacements: Map[Z_9, Node] = placementsFor(nodes) collect {
      case (value, SingletonSet(node)) => value -> node
    }

    val newSinglePlacements: Map[Z_9, Node] = singlePlacements filter {
      case (_, node) => node.candidates.size != 1
    }

    var graphColouringProblem = this
    newSinglePlacements foreach {
      case (value, node) =>
        graphColouringProblem = graphColouringProblem.addConstraintToNode(node.label, value)
    }

    graphColouringProblem
  }

  def singleEliminateByLatinBlockSingleRowPlacements(): GraphColouringProblem = {
    // For each row, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (row <- Z_9.all) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(_graphColouringProblem.graph.rowOf(One, row))
    }

    _graphColouringProblem
  }

  def singleEliminateByLatinBlockSingleColumnPlacements(): GraphColouringProblem = {
    // For each column, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (column <- Z_9.all) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(_graphColouringProblem.graph.columnOf(column, One))
    }

    _graphColouringProblem
  }

  def singleEliminateByLatinBlockSingleZonePlacements(): GraphColouringProblem = {
    // For each zone, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (band <- (0 to 2)) {
      for (indexWithinBand <- (0 to 2)) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(
          _graphColouringProblem.graph.zoneOf(
            Z_9(band * 3 + 1), Z_9(indexWithinBand * 3 + 1)
          )
        )
      }
    }

    _graphColouringProblem
  }

  def singleEliminateByLatinBlockSinglePlacements(): GraphColouringProblem = {
    // For each latin block, place elements that have only one possible placing

    this.singleEliminateByLatinBlockSingleRowPlacements().
      singleEliminateByLatinBlockSingleColumnPlacements().
      singleEliminateByLatinBlockSingleZonePlacements()
  }

  def eliminateByLatinBlockSinglePlacements(): GraphColouringProblem = {
    // For each latin block, place elements that have only one possible placing

    iterate(this) {
      _.singleEliminateByLatinBlockSinglePlacements()
    }
  }

  private def eliminateBySinglePlacementSetsIn(nodes: Set[Node]): GraphColouringProblem = {
    val singlePlacementSets: Map[Z_9, Set[Node]] = placementsFor(nodes) filter {
      case (value, nodes) => nodes.size == 2 || nodes.size == 3
    }

    var _graphColouringProblem = this
    singlePlacementSets foreach {
      case (value, nodes) =>
        val commonNeighbours = nodes map { node => graph(node.label).neighbourLabels } reduce { _ intersect _ }
        commonNeighbours foreach { neighbourLabel =>
          _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbourLabel, Z_9.all.toSet - value)
        }
    }

    _graphColouringProblem
  }

  def eliminateByLatinBlockSinglePlacementSets(): GraphColouringProblem = {
    // For each latin block, values that can only be placed in two or three
    // places exclude common neighbours from having that value

    iterate(this) { graphColouringProblem =>
      var _graphColouringProblem = graphColouringProblem

      // Eliminate by rows
      for (row <- Z_9.all) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(
          _graphColouringProblem.graph.rowOf(One, row)
        )
      }

      // Eliminate by columns
      for (column <- Z_9.all) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(
          _graphColouringProblem.graph.columnOf(column, One)
        )
      }

      // Eliminate by zones
      for (band <- (0 to 2)) {
        for (indexWithinBand <- (0 to 2)) {
          _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(
            _graphColouringProblem.graph.zoneOf(Z_9(band * 3 + 1), Z_9(indexWithinBand * 3 + 1))
          )
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

      val nodesWithTwoElementCandidateSets: Set[Node] = twoElementPlacings
      val partitionedByCandidateSets: Map[Set[Z_9], Set[Node]] = nodesWithTwoElementCandidateSets groupBy { _.candidates }
      val nonSingletonPartitions: Set[Set[Node]] = (partitionedByCandidateSets.values filter { _.size > 1 }).toSet

      val twoElementCoverings: Set[(Node, Node)] = nonSingletonPartitions flatMap { partition =>
        partition cross partition
      } filter {
        case (n1, n2) => n1.index < n2.index
      }

      val connectedTwoElementCoverings = twoElementCoverings filter {
        case (n1, n2) => n1 neighbours n2
      }

      connectedTwoElementCoverings foreach {
        case (n1, n2) =>
          val coveringElements: Set[Z_9] = n1.candidates ++ n2.candidates
          val commonNeighbours: Set[(Z_9, Z_9)] = n1.neighbourLabels intersect n2.neighbourLabels

          commonNeighbours foreach { neighbourLabel =>
            _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbourLabel, Z_9.all.toSet -- coveringElements)
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

      val nodesWithTwoOrThreeElementCandidateSets: Set[Node] =
        _graphColouringProblem.twoElementPlacings ++ _graphColouringProblem.threeElementPlacings

      // Expand out two element candidate sets with all possibles
      val nodesWithThreeElementCandidateSets: Set[Node] = nodesWithTwoOrThreeElementCandidateSets flatMap { node =>
        node.candidates.size match {
          case 3 => List(node)
          case 2 => (Z_9.all.toSet -- node.candidates) map { padding => Node(node.label, node.candidates + padding) }
        }
      }

      val partitionedByCandidateSets: Map[Set[Z_9], Set[Node]] = nodesWithThreeElementCandidateSets groupBy { _.candidates }
      val potentialPartitions: Set[Set[Node]] = partitionedByCandidateSets.values.toSet filter { _.size >= 3 }

      val threeElementCoverings: Set[(Node, Node, Node)] = potentialPartitions flatMap { partition =>
        (partition cross partition cross partition) map { case ((n1, n2), n3) => (n1, n2, n3) }
      } filter {
        case (n1, n2, n3) => n1.index < n2.index && n2.index < n3.index
      }

      val connectedThreeElementCoverings = threeElementCoverings filter {
        case (n1, n2, n3) =>
          (n1 neighbours n2) && (n1 neighbours n3) && (n2 neighbours n3)
      }

      connectedThreeElementCoverings foreach {
        case (n1, n2, n3) =>
          val coveringElements: Set[Z_9] = n1.candidates ++ n2.candidates ++ n3.candidates
          val commonNeighbours: Set[(Z_9, Z_9)] = n1.neighbourLabels filter { n2.neighbours } filter { n3.neighbours }

          commonNeighbours foreach { neighbourLabel =>
            _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbourLabel, Z_9.all.toSet -- coveringElements)
          }
      }

      _graphColouringProblem
    }
  }

  def reduceBySearch(): Iterator[GraphColouringProblem] = {
    // Pick the node with smallest candidate set size > 1 and 
    // most constrained neighbours and try alternatives

    val nodes: Map[Int, List[Node]] = graph.nodes.toList groupBy { _.candidates.size }
    val candidateNodes = nodes.toList filter { case (size, _) => size > 1 }

    candidateNodes match {
      case Nil => Iterator(this)
      case _ =>
        val nodesWithSmallestCandidateSetSize = (candidateNodes sortBy { case (size, _) => size }).head._2

        val nodesWithNeighboursCandidateSetSum = nodesWithSmallestCandidateSetSize map { node =>
          ((node.neighbourLabels.toList map { label => graph(label).candidates.size }).sum, node)
        }

        val node = (nodesWithNeighboursCandidateSetSum sortBy { case (size, _) => size }).head._2

        node.candidates.toIterator map { value =>
          val newNode = node.copy(candidates = Set(value))
          new GraphColouringProblem {
            protected val graph = self.graph.replace(node, newNode)
          }
        }
    }
  }

  def expandBySearch(): Iterator[GraphColouringProblem] = {
    // Pick any node with candidate set size 1 and add graph to iterator without that placing

    val nodes: Set[Node] = oneElementPlacings

    nodes match {
      case EmptySet() => Iterator(this)
      case _ =>
        nodes.toIterator map { node =>
          val newNode = node.copy(candidates = Z_9.all.toSet)
          new GraphColouringProblem {
            protected val graph = self.graph.replace(node, newNode)
          }
        }
    }
  }

  lazy val candidateSetsSizeSum: Int = (graph.nodes.toList map { _.candidates.size }).sum

  lazy val oneElementPlacings: Set[Node] = graph.nodes filter { _.candidates.size == 1 }
  lazy val twoElementPlacings: Set[Node] = graph.nodes filter { _.candidates.size == 2 }
  lazy val threeElementPlacings: Set[Node] = graph.nodes filter { _.candidates.size == 3 }

  lazy val numPlacings = oneElementPlacings.size

  lazy val valid = toBoard.valid
  lazy val solved = toBoard.solved

  lazy val toBoard: Board = Board(this)

  def canEqual(other: Any): Boolean = other.isInstanceOf[GraphColouringProblem]
  override def hashCode(): Int = graph.hashCode()
  override def equals(other: Any): Boolean = other.isInstanceOf[GraphColouringProblem] match {
    case true =>
      val that = other.asInstanceOf[GraphColouringProblem]
      (that canEqual this) && (this.graph == that.graph)

    case _ => false
  }
}
