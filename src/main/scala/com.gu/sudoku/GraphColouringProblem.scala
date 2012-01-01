package com.gu.sudoku

import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge.UnDiEdge
import scalax.collection.immutable.{ TinyGraphImpl => Graph }

case class Node(label: (Z_9, Z_9), candidates: Set[Z_9]) {
  // TODO: Use in GraphColouringProblem to replace getNode calls
  def neighbours(that: Node): Boolean = neighbours(that.label)
  def neighbours(label: (Z_9, Z_9)): Boolean = neighbours(label._1, label._2)

  def neighbours(column: Z_9, row: Z_9): Boolean = {
    this.label._1 == column || this.label._2 == row || (
      (this.label._1.representative - 1) / 3 == (column.representative - 1) / 3 &&
      (this.label._2.representative - 1) / 3 == (row.representative - 1) / 3)
  }

  lazy val index: Int = label._1.representative + (label._2.representative - 1) * 9
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

      val edges = labels flatMap { label =>
        val (column, row) = label

        val node = Node(label, Z_9.all.toSet)

        val columnEdges = Z_9.all.toSet[Z_9] map { i =>
          node ~ Node((column, i), Z_9.all.toSet)
        }

        val rowEdges = Z_9.all.toSet[Z_9] map { i =>
          node ~ Node((i, row), Z_9.all.toSet)
        }

        val zoneColOrigin = (column.representative - 1) / 3 * 3 + 1
        val zoneRowOrigin = (row.representative - 1) / 3 * 3 + 1

        val zoneEdges = Z_9.all.toSet[Z_9] map { i =>
          node ~ Node((
            Z_9(zoneColOrigin + (i.representative - 1) % 3),
            Z_9(zoneRowOrigin + (i.representative - 1) / 3)), Z_9.all.toSet)
        }

        // Remove self edges
        columnEdges ++ rowEdges ++ zoneEdges - (node ~ node)
      }

      Graph.from(edges)
    }
  }
}

trait GraphColouringProblem { self =>

  val Graph = scalax.collection.immutable.TinyGraphImpl

  protected val graph: Graph[Node, UnDiEdge]

  def apply(column: Z_9, row: Z_9): Node = getNode(column, row)

  private def getNode(column: Z_9, row: Z_9): graph.NodeT = getNode((column, row))
  private def getNode(label: (Z_9, Z_9)): graph.NodeT = (graph.nodes filter { _.label == label }).head

  private def addConstraintToNode(label: (Z_9, Z_9), element: Z_9): GraphColouringProblem =
    addConstraintsToNode(label._1, label._2, Set(element))

  private def addConstraintToNode(col: Z_9, row: Z_9, element: Z_9): GraphColouringProblem =
    addConstraintsToNode(col, row, Set(element))

  private def addConstraintsToNode(label: (Z_9, Z_9), elements: Set[Z_9]): GraphColouringProblem =
    addConstraintsToNode(label._1, label._2, elements)

  private def addConstraintsToNeighbours(label: (Z_9, Z_9), elements: Set[Z_9]): GraphColouringProblem =
    addConstraintsToNeighbours(label._1, label._2, elements)

  private def addConstraintsToNode(column: Z_9, row: Z_9, elements: Set[Z_9]): GraphColouringProblem = {
    val oldNode: Node = getNode(column, row)
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
    val neighbours = getNode(column, row) ~|

    var _graphColouringProblem = this
    neighbours foreach { neighbour =>
      _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbour.label, elements)
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

  private def columnOf(node: Node): Set[Node] = {
    val innerNode = getNode(node.label)
    val neighbours = (innerNode ~|)

    val innerNodeColumn = (neighbours + innerNode) filter { _.label._1 == node.label._1 }
    innerNodeColumn.toSet map { node: graph.NodeT => node.value }
  }

  private def rowOf(node: Node): Set[Node] = {
    val innerNode = getNode(node.label)
    val neighbours = (innerNode ~|)

    val innerNodeRow = (neighbours + innerNode) filter { _.label._2 == node.label._2 }
    innerNodeRow.toSet map { node: graph.NodeT => node.value }
  }

  private def zoneOf(node: Node): Set[Node] = {
    val innerNode = getNode(node.label)
    val neighbours = (innerNode ~|)

    val innerNodeZone = (neighbours + innerNode).toSet filter { other =>
      (node.label._1.representative - 1) / 3 == (other.label._1.representative - 1) / 3 &&
        (node.label._2.representative - 1) / 3 == (other.label._2.representative - 1) / 3
    }
    innerNodeZone.toSet map { node: graph.NodeT => node.value }
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

  def singleEliminateByRowSinglePlacements(): GraphColouringProblem = {
    // For each row, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (row <- Z_9.all) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(
        _graphColouringProblem.rowOf(
          getNode(One, row)))
    }

    _graphColouringProblem
  }

  def singleEliminateByColumnSinglePlacements(): GraphColouringProblem = {
    // For each column, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (column <- Z_9.all) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(
        _graphColouringProblem.columnOf(
          getNode(column, One)))
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
        val node = getNode(zoneUpperLeftCol, zoneUpperLeftRow)

        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(
          _graphColouringProblem.zoneOf(
            node))
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

  private def eliminateBySinglePlacementSetsIn(nodes: Set[Node]): GraphColouringProblem = {
    val singlePlacementSets: Map[Z_9, Set[Node]] = placementsFor(nodes) filter {
      case (value, nodes) => nodes.size == 2 || nodes.size == 3
    }

    var _graphColouringProblem = this
    singlePlacementSets foreach {
      case (value, nodes) =>
        val commonNeighbours = nodes map { node => getNode(node.label) ~| } reduce { _ intersect _ }
        commonNeighbours foreach { neighbour =>
          _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbour.label, Z_9.all.toSet - value)
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
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(rowOf(getNode(One, row)))
      }

      // Eliminate by columns
      for (col <- Z_9.all) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(columnOf(getNode(col, One)))
      }

      // Eliminate by zones
      for (band <- (0 to 2)) {
        for (indexWithinBand <- (0 to 2)) {
          val zoneUpperLeftCol = Z_9(band * 3 + 1)
          val zoneUpperLeftRow = Z_9(indexWithinBand * 3 + 1)
          val node = getNode(zoneUpperLeftCol, zoneUpperLeftRow)

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

      val nodesWithTwoElementCandidateSets: Set[Node] = twoElementPlacings
      val partitionedByCandidateSets: Map[Set[Z_9], Set[Node]] = nodesWithTwoElementCandidateSets groupBy { _.candidates }
      val nonSingletonPartitions: Set[Set[Node]] = (partitionedByCandidateSets.values filter { _.size > 1 }).toSet

      val twoElementCoverings: Set[(Node, Node)] = nonSingletonPartitions flatMap { partition =>
        partition cross partition
      } filter {
        case (n1, n2) => n1.index < n2.index
      }

      val connectedTwoElementCoverings = twoElementCoverings filter {
        case (n1, n2) => (getNode(n1.label) ~|) contains getNode(n2.label)
      }

      connectedTwoElementCoverings foreach {
        case (n1, n2) =>
          val coveringElements: Set[Z_9] = n1.candidates ++ n2.candidates
          val innerN1 = getNode(n1.label)
          val innerN2 = getNode(n2.label)
          val commonNeighbours: Set[graph.NodeT] = (innerN1 ~|).toSet intersect (innerN2 ~|).toSet -- Set(innerN1, innerN2)
          val neighboursToUpdate = commonNeighbours filter { _.candidates.intersect(coveringElements).size > 0 }

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
          val innerN1 = _graphColouringProblem.getNode(n1.label)
          val innerN2 = _graphColouringProblem.getNode(n2.label)
          val innerN3 = _graphColouringProblem.getNode(n3.label)

          (Set(innerN2, innerN3) subsetOf (innerN1 ~|).toSet) && (Set(innerN1, innerN3) subsetOf (innerN2 ~|).toSet)
      }

      connectedThreeElementCoverings foreach {
        case (n1, n2, n3) =>
          val coveringElements: Set[Z_9] = n1.candidates ++ n2.candidates ++ n3.candidates

          val commonNeighbours: Set[(Z_9, Z_9)] = ((Z_9.all.toSet cross Z_9.all) filter { label: (Z_9, Z_9) =>
            n1.neighbours(label) && n2.neighbours(label) && n3.neighbours(label)
          }) -- Set(n1.label, n2.label, n3.label)

          commonNeighbours foreach { neighbour =>
            _graphColouringProblem = _graphColouringProblem.addConstraintsToNode(neighbour, Z_9.all.toSet -- coveringElements)
          }
      }

      _graphColouringProblem
    }
  }

  def reduceBySearch(): Iterator[GraphColouringProblem] = {
    // Pick the node with smallest candidate set size > 1 and 
    // most constrained neighbours and try alternatives

    val nodes: Map[Int, List[Node]] = graph.nodes.toList map { _.value } groupBy { _.candidates.size }

    val candidateNodes = nodes.toList filter { case (size, _) => size > 1 }

    candidateNodes match {
      case Nil => Iterator(this)
      case _ =>
        val nodesWithSmallestCandidateSetSize = (candidateNodes sortBy { case (size, _) => size }).head._2

        val nodesWithNeighboursCandidateSetSum = nodesWithSmallestCandidateSetSize map { node =>
          (((getNode(node.label) ~|).toList map { _.candidates.size }).sum, node)
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

  lazy val oneElementPlacings: Set[Node] = {
    val innerNodes = graph.nodes filter { _.candidates.size == 1 }
    innerNodes.toSet map { nodeT: graph.NodeT => nodeT.value }
  }

  lazy val twoElementPlacings: Set[Node] = {
    val innerNodes = graph.nodes filter { _.candidates.size == 2 }
    innerNodes.toSet map { nodeT: graph.NodeT => nodeT.value }
  }

  lazy val threeElementPlacings: Set[Node] = {
    val innerNodes = graph.nodes filter { _.candidates.size == 3 }
    innerNodes.toSet map { nodeT: graph.NodeT => nodeT.value }
  }

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
