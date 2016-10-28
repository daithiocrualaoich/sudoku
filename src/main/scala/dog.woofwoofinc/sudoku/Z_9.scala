package dog.woofwoofinc.sudoku

sealed abstract class Z_9(val representative: Int) {
  override def toString(): String = "[%d]" format representative
}

object Z_9 {
  implicit val ordering: Ordering[Z_9] = Ordering[Int] on { _.representative }
  val all = List(One, Two, Three, Four, Five, Six, Seven, Eight, Nine)
  val set = all.toSet

  def apply(i: Int): Z_9 = all.rotate(8)(i % 9)
}

object One extends Z_9(1)
object Two extends Z_9(2)
object Three extends Z_9(3)
object Four extends Z_9(4)
object Five extends Z_9(5)
object Six extends Z_9(6)
object Seven extends Z_9(7)
object Eight extends Z_9(8)
object Nine extends Z_9(9)
