package com.gu.sudoku

import scala.util.Random

object `package` {

  type SudokuGraphNode = Node[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraphEdge = Edge[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraph = Graph[(Z_9, Z_9), Set[Z_9]]

  implicit def list2Rotate[A](l: List[A]) = new {
    def rotate(i: Int): List[A] = (l drop i) ++ (l take i)
  }

  implicit def list2Shuffle[A](l: List[A]) = new {
    def shuffle(): List[A] = Random shuffle l
  }

  implicit def iterator2Head[A](it: Iterator[A]) = new {
    lazy val headOption: Option[A] = if (it.hasNext) Some(it.next) else None
    lazy val head: A = headOption.get
  }

  implicit def string2Rotate(s: String) = new {
    def rotate(i: Int): String = (s drop i) ++ (s take i)
    def unrotate(i: Int): String = rotate(s.length - i)
  }

  implicit def string2IntOption(s: String) = new {
    lazy val toIntOption: Option[Int] = try {
      Some(s.toInt)
    } catch {
      case _ => None
    }
  }

  def iterate[T](t: T)(iteration: T => T): T = {
    var previous = t
    var current = iteration(previous)

    while (current != previous) {
      previous = current
      current = iteration(previous)
    }

    current
  }
}
