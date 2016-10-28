package dog.woofwoofinc.sudoku

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
    case _: Exception => None
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

  implicit class RichList[A](val l: List[A]) extends AnyVal {
    def rotate(i: Int): List[A] = (l drop i) ++ (l take i)
    def unrotate(i: Int): List[A] = rotate(l.length - i)

    def shuffled(): List[A] = Random shuffle l

    def cross[B](it: Iterable[B]): List[(A, B)] =
      l flatMap { a => it map { b => (a, b) } }
  }

  implicit class RichSet[A](val s: Set[A]) extends AnyVal {
    def cross[B](it: Iterable[B]): Set[(A, B)] =
      s flatMap { a => it map { b => (a, b) } }
  }

  implicit class RichIterator[A](val it: Iterator[A]) extends AnyVal {
    def shuffled(): Iterator[A] = it.toList.shuffled.toIterator

    def headOption: Option[A] = if (it.hasNext) Some(it.next) else None
    def head: A = headOption.get
  }

  implicit class RichString(val s: String) extends AnyVal {
    def toIntOption: Option[Int] = quietly { s.toInt }
  }
}
