package com.gu.sudoku

sealed abstract class Z_9IndexedBlock {
  protected val _values: List[Option[Z_9]]
  def apply(i: Z_9): Option[Z_9] = _values.rotate(8)(i.toInt % 9)
  //  def apply(i: Z_9): Option[Z_9] = i match {
  //    case Nine => _values.last
  //    case _ => _values(i.toInt - 1)
  //  }

  private lazy val _valuesList: List[Z_9] = _values flatMap { _.toList }
  lazy val values: Set[Z_9] = _valuesList.toSet
  lazy val valid: Boolean = _valuesList.distinct == _valuesList
  lazy val solved: Boolean = valid && values.size == 9
}

case class Row(
    first: Option[Z_9],
    second: Option[Z_9],
    third: Option[Z_9],
    fourth: Option[Z_9],
    fifth: Option[Z_9],
    sixth: Option[Z_9],
    seventh: Option[Z_9],
    eighth: Option[Z_9],
    ninth: Option[Z_9]) extends Z_9IndexedBlock {

  protected val _values: List[Option[Z_9]] = List(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

}

case class Column(
    first: Option[Z_9],
    second: Option[Z_9],
    third: Option[Z_9],
    fourth: Option[Z_9],
    fifth: Option[Z_9],
    sixth: Option[Z_9],
    seventh: Option[Z_9],
    eighth: Option[Z_9],
    ninth: Option[Z_9]) extends Z_9IndexedBlock {

  protected val _values: List[Option[Z_9]] = List(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

}

case class Zone(
    upperLeft: Option[Z_9],
    upperCentre: Option[Z_9],
    upperRight: Option[Z_9],
    centreLeft: Option[Z_9],
    centre: Option[Z_9],
    centreRight: Option[Z_9],
    lowerLeft: Option[Z_9],
    lowerCentre: Option[Z_9],
    lowerRight: Option[Z_9]) extends Z_9IndexedBlock {

  protected val _values: List[Option[Z_9]] = List(upperLeft, upperCentre, upperRight, centreLeft, centre, centreRight, lowerLeft, lowerCentre, lowerRight)
}



