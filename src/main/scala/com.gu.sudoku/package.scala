package com.gu.sudoku

object `package` {

  type SudokuGraphNode = Node[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraphEdge = Edge[(Z_9, Z_9), Set[Z_9]]
  type SudokuGraph = Graph[(Z_9, Z_9), Set[Z_9]]

  implicit def list2Rotate[A](l: List[A]) = new {
    def rotate(i: Int): List[A] = (l drop i) ++ (l take i)
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
