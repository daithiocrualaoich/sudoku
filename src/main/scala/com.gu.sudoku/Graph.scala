package com.gu.sudoku

case class Node[T, S](label: T, value: S)

case class Edge[T, S](from: Node[T, S], to: Node[T, S]) {
  lazy val nodes = Set(from, to)
  def incidentOn(node: Node[T, S]): Boolean = nodes contains node
}

case class Graph[T, S](
    nodes: Set[Node[T, S]] = Set.empty[Node[T, S]],
    edges: Set[Edge[T, S]] = Set.empty[Edge[T, S]]) {

  def neighbours(node: Node[T, S]): Set[Node[T, S]] = (edges filter { _ incidentOn node } flatMap { _.nodes }) -- Set(node)

  def addNode(label: T, value: S): Graph[T, S] = Graph(nodes + Node(label, value), edges)
  def getNode(label: T): Option[Node[T, S]] = nodes find { _.label == label }
  def updateNode(label: T, value: S): Graph[T, S] = getNode(label) match {
    case Some(oldNode) =>
      // Graph needs a bit of surgery in this case
      val newNode = Node(label, value)

      val oldEdges = edges filter { _ incidentOn oldNode }
      val newEdges = oldEdges map { edge =>
        // Meh... graph is undirected
        val target = (edge.nodes - oldNode).head
        Edge(newNode, target)
      }

      Graph(nodes - oldNode + newNode, edges -- oldEdges ++ newEdges)

    case None =>
      addNode(label, value)
  }

  def addEdge(n1: T, n2: T): Graph[T, S] = {
    // Does not add when nodes not present.
    (getNode(n1), getNode(n2)) match {
      case (Some(from), Some(to)) => Graph(nodes, edges + Edge(from, to))
      case _ => this
    }
  }
}
