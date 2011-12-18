package com.gu.sudoku

class Row(
  val first: Option[Z_9],
  val second: Option[Z_9],
  val third: Option[Z_9],
  val fourth: Option[Z_9],
  val fifth: Option[Z_9],
  val sixth: Option[Z_9],
  val seventh: Option[Z_9],
  val eighth: Option[Z_9],
  val ninth: Option[Z_9]) {

  lazy val values: Set[Z_9] = (first.toList ++
    second.toList ++
    third.toList ++
    fourth.toList ++
    fifth.toList ++
    sixth.toList ++
    seventh.toList ++
    eighth.toList ++
    ninth.toList).toSet

  lazy val valid: Boolean = true
}

