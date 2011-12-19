package com.gu.sudoku

object `package` {

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
