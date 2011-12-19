package com.gu.sudoku

import scala.collection.mutable.{ Map => MutableMap, Set => MutableSet }

class Graph[T, S] {
  case class Edge(from: Node, to: Node) {
    lazy val nodes = Set(from, to)
    override def toString = "%s ---> %s".format(from, to)
  }

  case class Node(label: T, value: S) {
    def neighbours: Set[Node] = (edges filter { _.nodes contains this } flatMap { _.nodes }).toSet - this
    override def toString = "%s(%s)".format(label, value)
  }

  // TODO: Make Graph immutable
  private val nodes: MutableMap[T, Node] = MutableMap()
  private val edges: MutableSet[Edge] = MutableSet()

  def addNode(label: T, value: S): Node = {
    val n = new Node(label, value)
    nodes += (label -> n)

    n
  }

  def getNodes: List[Node] = nodes.values.toList
  def getNode(label: T): Option[Node] = nodes.get(label)

  def addEdge(n1: T, n2: T) {
    edges += Edge(nodes(n1), nodes(n2))
  }

  def getEdges: Set[Edge] = edges.toSet

  def copy(): Graph[T, S] = {
    val g = new Graph[T, S]

    nodes foreach {
      case (_, node) =>
        g.addNode(node.label, node.value)
    }

    edges foreach { edge =>
      g.addEdge(edge.from.label, edge.to.label)
    }

    g
  }

  def updateNode(label: T, value: S) {
    (nodes get label) match {
      case Some(oldNode) =>
        val newNode = addNode(label, value)

        edges filter { _.nodes contains oldNode } foreach { edge =>
          edges remove edge
          (edge.nodes - oldNode) foreach { neighbour =>
            addEdge(newNode.label, neighbour.label)
          }
        }

      case None => addNode(label, value)
    }
  }

  override def toString = edges.mkString("\n")
}
