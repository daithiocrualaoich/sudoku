package com.gu.sudoku

import scala.collection.mutable.{ Map => MutableMap, Set => MutableSet }

class Graph[T, S] {
  case class Edge(n1: Node, n2: Node) {
    lazy val nodes = Set(n1, n2)
    override def toString = "%s ---> %s".format(n1, n2)
  }

  case class Node(label: T, value: S) {
    def neighbors: Set[Node] = (edges filter { _.nodes contains this } flatMap { _.nodes }).toSet - this
    override def toString = "%s(%s)".format(label, value)
  }

  // TODO: Try with immutable datastructures
  private val nodes: MutableMap[T, Node] = MutableMap()
  private val edges: MutableSet[Edge] = MutableSet()

  def addNode(label: T, value: S): Node = {
    val n = new Node(label, value)
    nodes += (label -> n)

    n
  }

  def getNodes: Map[T, Node] = nodes.toMap

  def addEdge(n1: T, n2: T) {
    edges += Edge(nodes(n1), nodes(n2))
  }

  //  def updateNode(label: T, value: S) {
  //    (nodes get label) match {
  //      case Some(oldNode) =>
  //        val newNode = addNode(label, value)
  //
  //        edges filter { _.nodes contains oldNode } foreach { edge =>
  //          edges remove edge
  //          (edge.nodes - oldNode) foreach { neighbour =>
  //            addEdge(newNode, neighbour)
  //          }
  //        }
  //
  //      case None => addNode(label, value)
  //    }
  //  }

  override def toString = edges.mkString("\n")
}
