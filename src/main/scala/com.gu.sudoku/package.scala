package com.gu.sudoku

object `package` {

  implicit def list2Rotate[A](l: List[A]) = new {
    def rotate(i: Int): List[A] = l.drop(i) ++ l.take(i)
  }
}
