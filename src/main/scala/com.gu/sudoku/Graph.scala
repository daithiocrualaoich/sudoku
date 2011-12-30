package com.gu.sudoku

// TODO: Replace this mess with scalax.collection.Graph

case class Node[T, S](label: T, value: S)

case class Edge[T, S](u: Node[T, S], v: Node[T, S]) {
  lazy val nodes = Set(u, v)

  def incidentOn(node: Node[T, S]): Boolean = nodes contains node

  def canEqual(other: Any): Boolean = other.isInstanceOf[Edge[T, S]]
  override def hashCode(): Int = nodes.hashCode()
  override def equals(other: Any): Boolean = other.isInstanceOf[Edge[T, S]] match {
    case true =>
      val that = other.asInstanceOf[Edge[T, S]]
      (that canEqual this) && (this.nodes == that.nodes)

    case _ => false
  }
}

case class Graph[T, S](
    nodes: Set[Node[T, S]] = Set.empty[Node[T, S]],
    edges: Set[Edge[T, S]] = Set.empty[Edge[T, S]]) {

  def neighbours(label: T): Set[Node[T, S]] = {
    val node = getNode(label).toSet[Node[T, S]]
    val incidentEdges = node flatMap { n => edges filter { _ incidentOn n } }

    (incidentEdges flatMap { _.nodes }) -- node
  }

  def addNode(label: T, value: S): Graph[T, S] = Graph(nodes + Node(label, value), edges)
  def getNode(label: T): Option[Node[T, S]] = nodes find { _.label == label }
  def updateNodeValue(label: T, value: S): Graph[T, S] = getNode(label) match {
    case Some(oldNode) =>
      // Graph needs a bit of surgery in this case
      val newNode = Node(label, value)

      val oldEdges = edges filter { _ incidentOn oldNode }
      val newEdges = oldEdges map { edge =>
        // Meh... edges are undirected
        val target = (edge.nodes - oldNode).head
        Edge(newNode, target)
      }

      Graph(nodes - oldNode + newNode, edges -- oldEdges ++ newEdges)

    case None =>
      addNode(label, value)
  }

  def addEdge(n1: T, n2: T): Graph[T, S] = {
    // Does not add when nodes are not present.
    (getNode(n1), getNode(n2)) match {
      case (Some(u), Some(v)) => Graph(nodes, edges + Edge(u, v))
      case _ => this
    }
  }

  def removeEdge(n1: T, n2: T): Graph[T, S] = {
    // Does not remove when nodes or edge is not present.
    (getNode(n1), getNode(n2)) match {
      case (Some(u), Some(v)) => Graph(nodes, edges - Edge(u, v))
      case _ => this
    }
  }
}
