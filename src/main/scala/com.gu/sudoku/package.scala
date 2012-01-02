package com.gu.sudoku

import scala.collection.generic.CanBuildFrom
import scala.util.Random

object `package` {

  val SingletonSet = new {
    def unapply[A](s: Set[A]): Option[A] = if (s.size == 1) s.headOption else None
  }

  val EmptySet = new {
    def unapply[A](s: Set[A]): Boolean = s.isEmpty
  }

  def quietly[T](action: => T): Option[T] = try {
    Some(action)
  } catch {
    case _ => None
  }

  def iterate[T](initial: T)(next: T => T): T = {
    var previous = initial
    var current = next(previous)

    while (current != previous) {
      previous = current
      current = next(previous)
    }

    current
  }

  implicit def list2Rotate[A](l: List[A]) = new {
    def rotate(i: Int): List[A] = (l drop i) ++ (l take i)
    def unrotate(i: Int): List[A] = rotate(l.length - i)
  }

  implicit def list2Shuffled[A](l: List[A]) = new {
    def shuffled(): List[A] = Random shuffle l
  }

  implicit def list2Cross[A](l: List[A]) = new {
    def cross[B](it: Iterable[B]): List[(A, B)] =
      l flatMap { a => it map { b => (a, b) } }
  }

  implicit def set2Cross[A](s: Set[A]) = new {
    def cross[B](it: Iterable[B]): Set[(A, B)] =
      s flatMap { a => it map { b => (a, b) } }
  }

  implicit def set2IsNonEmpty[A](s: Set[A]) = new {
    lazy val isNonEmpty: Boolean = !s.isEmpty
  }

  implicit def iterator2Shuffled[A](it: Iterator[A]) = new {
    def shuffled(): Iterator[A] = it.toList.shuffled.toIterator
  }

  implicit def iterator2Head[A](it: Iterator[A]) = new {
    lazy val headOption: Option[A] = if (it.hasNext) Some(it.next) else None
    lazy val head: A = headOption.get
  }

  implicit def string2IntOption(s: String) = new {
    lazy val toIntOption: Option[Int] = quietly { s.toInt }
  }

}
