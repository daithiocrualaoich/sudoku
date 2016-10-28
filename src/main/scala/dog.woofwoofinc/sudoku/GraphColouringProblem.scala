package dog.woofwoofinc.sudoku

case class Node(label: (Z_9, Z_9), candidates: Set[Z_9]) {

  def sameColumn(that: Node): Boolean = this.label._1 == that.label._1
  def sameRow(that: Node): Boolean = this.label._2 == that.label._2
  def sameZone(that: Node): Boolean = {
    (this.label._1.representative - 1) / 3 == (that.label._1.representative - 1) / 3 &&
      (this.label._2.representative - 1) / 3 == (that.label._2.representative - 1) / 3
  }

  def neighbours(that: Node): Boolean = neighbours(that.label)
  def neighbours(label: (Z_9, Z_9)): Boolean = neighbours(label._1, label._2)
  def neighbours(column: Z_9, row: Z_9): Boolean = {
    // No self edges
    !(this.label._1 == column && this.label._2 == row) &&
      (this.label._1 == column || this.label._2 == row || (
        (this.label._1.representative - 1) / 3 == (column.representative - 1) / 3 &&
        (this.label._2.representative - 1) / 3 == (row.representative - 1) / 3
      ))
  }

  lazy val neighbourLabels: Set[(Z_9, Z_9)] = (Z_9.set cross Z_9.set) filter { neighbours }

  lazy val index: Int = Node.index(label)
}

object Node {
  def index(label: (Z_9, Z_9)): Int = index(label._1, label._2)
  def index(column: Z_9, row: Z_9): Int =
    column.representative + (row.representative - 1) * 9 - 1
}

case class Graph(nodes: Set[Node]) {
  // Edge relationships are inferred from labels

  private lazy val nodeLookup = (nodes.toList sortBy { _.index }).toArray

  def apply(column: Z_9, row: Z_9): Node = apply((column, row))
  def apply(label: (Z_9, Z_9)): Node = nodeLookup(Node index label)

  def replace(node: Node, replacement: Node): Graph = Graph(nodes - node + replacement)

  def rowOf(label: (Z_9, Z_9)): Set[Node] = rowOf(apply(label))
  def rowOf(column: Z_9, row: Z_9): Set[Node] = rowOf(apply(column, row))
  def rowOf(node: Node): Set[Node] = {
    val indexOffset = (node.index / 9) * 9
    (0 to 8).toSet map { column: Int => column + indexOffset } map { nodeLookup }
  }

  def columnOf(label: (Z_9, Z_9)): Set[Node] = columnOf(apply(label))
  def columnOf(column: Z_9, row: Z_9): Set[Node] = columnOf(apply(column, row))
  def columnOf(node: Node): Set[Node] = {
    val indexOffset = node.index % 9
    (0 to 8).toSet map { row: Int => row * 9 + indexOffset } map { nodeLookup }
  }

  def zoneOf(label: (Z_9, Z_9)): Set[Node] = zoneOf(apply(label))
  def zoneOf(column: Z_9, row: Z_9): Set[Node] = zoneOf(apply(column, row))
  def zoneOf(node: Node): Set[Node] = {
    // TODO: Implement using indices only
    nodes filter { node sameZone _ }
  }

  def constrain(label: (Z_9, Z_9), element: Z_9): Graph =
    constrain(label._1, label._2, Set(element))

  def constrain(col: Z_9, row: Z_9, element: Z_9): Graph =
    constrain(col, row, Set(element))

  def constrain(label: (Z_9, Z_9), elements: Set[Z_9]): Graph =
    constrain(label._1, label._2, elements)

  def constrain(column: Z_9, row: Z_9, elements: Set[Z_9]): Graph = {
    val oldNode: Node = apply(column, row)
    val newCandidates = oldNode.candidates intersect elements

    (oldNode.candidates == newCandidates) match {
      case true => this

      case false =>
        replace(oldNode, oldNode.copy(candidates = newCandidates))
    }
  }

  def constrainNeighbours(label: (Z_9, Z_9), elements: Set[Z_9]): Graph =
    constrainNeighbours(label._1, label._2, elements)

  def constrainNeighbours(column: Z_9, row: Z_9, elements: Set[Z_9]): Graph = {
    val node = apply(column, row)

    var _graph = this
    node.neighbourLabels foreach { neighbourLabel =>
      _graph = _graph.constrain(neighbourLabel, elements)
    }

    _graph
  }

  lazy val zeroElementPlacings: Set[Node] = nodes filter { _.candidates.isEmpty }

  lazy val oneElementPlacings: Set[Node] = nodes filter { _.candidates.size == 1 }
  lazy val twoElementPlacings: Set[Node] = nodes filter { _.candidates.size == 2 }
  lazy val threeElementPlacings: Set[Node] = nodes filter { _.candidates.size == 3 }
}

object GraphColouringProblem {

  def apply(board: Board): GraphColouringProblem = {
    var _graph = unconstrained.graph

    for (column <- Z_9.set) {
      for (row <- Z_9.set) {
        board(column, row) foreach { element =>
          _graph = _graph.constrain(column, row, element)
        }
      }
    }

    new GraphColouringProblem {
      protected val graph = _graph
    }
  }

  lazy val unconstrained = new GraphColouringProblem {
    protected val graph = {
      val labels: Set[(Z_9, Z_9)] = (Z_9.set cross Z_9.set)
      val nodes = labels map {
        label => Node(label, Z_9.set)
      }

      Graph(nodes)
    }
  }
}

trait GraphColouringProblem { self =>

  protected val graph: Graph

  def apply(column: Z_9, row: Z_9): Node = graph(column, row)

  def singleEliminateByLatinBlockExclusion(): GraphColouringProblem = {
    // For each placement, place elements that have only one possible 
    // candidate and exclude value from neighbour candidate sets.

    var _graph = graph
    _graph.oneElementPlacings foreach { singleValueNode =>
      _graph = _graph.constrainNeighbours(
        singleValueNode.label,
        Z_9.set -- singleValueNode.candidates
      )
    }

    new GraphColouringProblem {
      protected val graph = _graph
    }
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

    var _graph = graph
    newSinglePlacements foreach {
      case (value, node) =>
        _graph = _graph.constrain(node.label, value)
    }

    new GraphColouringProblem {
      protected val graph = _graph
    }
  }

  def singleEliminateByLatinBlockSingleRowPlacements(): GraphColouringProblem = {
    // For each row, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (row <- Z_9.set) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(
        _graphColouringProblem.graph.rowOf(One, row)
      )
    }

    _graphColouringProblem
  }

  def singleEliminateByLatinBlockSingleColumnPlacements(): GraphColouringProblem = {
    // For each column, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (column <- Z_9.set) {
      _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementsIn(
        _graphColouringProblem.graph.columnOf(column, One)
      )
    }

    _graphColouringProblem
  }

  def singleEliminateByLatinBlockSingleZonePlacements(): GraphColouringProblem = {
    // For each zone, place elements that have only one possible placing

    var _graphColouringProblem = this
    for (band <- 0 to 2) {
      for (indexWithinBand <- 0 to 2) {
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

    var _graph = graph
    singlePlacementSets foreach {
      case (value, nodes) =>
        val commonNeighbours = nodes map { node => graph(node.label).neighbourLabels } reduce { _ intersect _ }
        commonNeighbours foreach { neighbourLabel =>
          _graph = _graph.constrain(neighbourLabel, Z_9.set - value)
        }
    }

    new GraphColouringProblem {
      protected val graph = _graph
    }
  }

  def eliminateByLatinBlockSinglePlacementSets(): GraphColouringProblem = {
    // For each latin block, values that can only be placed in two or three
    // places exclude common neighbours from having that value

    iterate(this) { graphColouringProblem =>
      var _graphColouringProblem = graphColouringProblem

      // Eliminate by rows
      for (row <- Z_9.set) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(
          _graphColouringProblem.graph.rowOf(One, row)
        )
      }

      // Eliminate by columns
      for (column <- Z_9.set) {
        _graphColouringProblem = _graphColouringProblem.eliminateBySinglePlacementSetsIn(
          _graphColouringProblem.graph.columnOf(column, One)
        )
      }

      // Eliminate by zones
      for (band <- 0 to 2) {
        for (indexWithinBand <- 0 to 2) {
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
      var _graph = graphColouringProblem.graph

      val nodesWithTwoElementCandidateSets: Set[Node] = _graph.twoElementPlacings
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
            _graph = _graph.constrain(neighbourLabel, Z_9.set -- coveringElements)
          }
      }

      new GraphColouringProblem {
        protected val graph = _graph
      }
    }
  }

  def eliminateByThreeElementCoverings(): GraphColouringProblem = {
    // Any triple of completely connected nodes which have only the three possible candidate
    // values excludes any node which is a neighbour of all from having any of these values.

    iterate(this) { graphColouringProblem =>
      var _graph = graphColouringProblem.graph

      val nodesWithTwoOrThreeElementCandidateSets: Set[Node] =
        _graph.twoElementPlacings ++ _graph.threeElementPlacings

      // Expand out two element candidate sets with all possibles
      val nodesWithThreeElementCandidateSets: Set[Node] = nodesWithTwoOrThreeElementCandidateSets flatMap { node =>
        node.candidates.size match {
          case 3 => List(node)
          case 2 => (Z_9.set -- node.candidates) map { padding => node.copy(candidates = node.candidates + padding) }
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
            _graph = _graph.constrain(neighbourLabel, Z_9.set -- coveringElements)
          }
      }

      new GraphColouringProblem {
        protected val graph = _graph
      }
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

    val nodes: Set[Node] = graph.oneElementPlacings

    nodes match {
      case EmptySet() => Iterator(this)
      case _ =>
        nodes.toIterator map { node =>
          val newNode = node.copy(candidates = Z_9.set)
          new GraphColouringProblem {
            protected val graph = self.graph.replace(node, newNode)
          }
        }
    }
  }

  lazy val candidateSetsSizeSum: Int = (graph.nodes.toList map { _.candidates.size }).sum
  lazy val numPlacings = graph.oneElementPlacings.size
  lazy val solved = valid && numPlacings == 81

  lazy val valid = {
    val latinPropertyViolations = (graph.oneElementPlacings cross graph.oneElementPlacings) filter {
      case (n1, n2) => n1.index < n2.index
    } filter {
      case (n1, n2) => n1.candidates == n2.candidates
    } filter {
      case (n1, n2) => n1 neighbours n2
    }

    graph.zeroElementPlacings.isEmpty && latinPropertyViolations.isEmpty
  }

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
