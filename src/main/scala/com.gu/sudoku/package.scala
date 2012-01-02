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

  def iterate[T](t: T)(iteration: T => T): T = {
    var previous = t
    var current = iteration(previous)

    while (current != previous) {
      previous = current
      current = iteration(previous)
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

  implicit def list2Cross[A](esses: List[A]) = new {
    def cross[B](ts: Iterable[B]): List[(A, B)] =
      esses flatMap { s => ts map { t => (s, t) } }
  }

  implicit def set2Cross[A](esses: Set[A]) = new {
    def cross[B](ts: Iterable[B]): Set[(A, B)] =
      esses flatMap { s => ts map { t => (s, t) } }
  }

  implicit def set2ToMapWithKeys[T](tees: Set[T]) = new {
    def toMapWithKeys[S](f: T => S): Map[S, T] = (tees map { tee => (f(tee), tee) }).toMap
  }

  implicit def set2NonEmpty[A](esses: Set[A]) = new {
    lazy val nonEmpty: Boolean = esses.size != 0
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
