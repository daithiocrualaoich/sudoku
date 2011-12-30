package com.gu.sudoku

sealed abstract class LatinBlock {
  protected val _values: List[Option[Z_9]]
  def apply(i: Z_9): Option[Z_9] = _values(i.representative - 1)

  lazy val values: Set[Z_9] = (_values flatMap { _.toList }).toSet

  lazy val numValues: Int = _values count { _.isDefined }
  lazy val numDistinctValues: Int = values.size

  lazy val valid: Boolean = numValues == numDistinctValues
  lazy val solved: Boolean = numDistinctValues == 9
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
    ninth: Option[Z_9]) extends LatinBlock {

  protected val _values: List[Option[Z_9]] = List(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

}

object Row {
  val empty = Row(None, None, None, None, None, None, None, None, None)
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
    ninth: Option[Z_9]) extends LatinBlock {

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
    lowerRight: Option[Z_9]) extends LatinBlock {

  protected val _values: List[Option[Z_9]] = List(upperLeft, upperCentre, upperRight, centreLeft, centre, centreRight, lowerLeft, lowerCentre, lowerRight)
}

case class Board(first: Row, second: Row, third: Row, fourth: Row, fifth: Row, sixth: Row, seventh: Row, eighth: Row, ninth: Row) {

  private lazy val rows = List(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)
  private lazy val columns = Z_9.all map { i => new Column(first(i), second(i), third(i), fourth(i), fifth(i), sixth(i), seventh(i), eighth(i), ninth(i)) }
  private lazy val zones = List(
    // Band 1
    new Zone(first(One), first(Two), first(Three), second(One), second(Two), second(Three), third(One), third(Two), third(Three)),
    new Zone(first(Four), first(Five), first(Six), second(Four), second(Five), second(Six), third(Four), third(Five), third(Six)),
    new Zone(first(Seven), first(Eight), first(Nine), second(Seven), second(Eight), second(Nine), third(Seven), third(Eight), third(Nine)),

    // Band 2
    new Zone(fourth(One), fourth(Two), fourth(Three), fifth(One), fifth(Two), fifth(Three), sixth(One), sixth(Two), sixth(Three)),
    new Zone(fourth(Four), fourth(Five), fourth(Six), fifth(Four), fifth(Five), fifth(Six), sixth(Four), sixth(Five), sixth(Six)),
    new Zone(fourth(Seven), fourth(Eight), fourth(Nine), fifth(Seven), fifth(Eight), fifth(Nine), sixth(Seven), sixth(Eight), sixth(Nine)),

    // Band 3
    new Zone(seventh(One), seventh(Two), seventh(Three), eighth(One), eighth(Two), eighth(Three), ninth(One), ninth(Two), ninth(Three)),
    new Zone(seventh(Four), seventh(Five), seventh(Six), eighth(Four), eighth(Five), eighth(Six), ninth(Four), ninth(Five), ninth(Six)),
    new Zone(seventh(Seven), seventh(Eight), seventh(Nine), eighth(Seven), eighth(Eight), eighth(Nine), ninth(Seven), ninth(Eight), ninth(Nine))
  )

  def apply(i: Z_9, j: Z_9): Option[Z_9] = column(i)(j)

  def row(i: Z_9): Row = rows(i.representative - 1)
  def column(i: Z_9): Column = columns(i.representative - 1)
  def zone(i: Z_9): Zone = zones(i.representative - 1)
  def zone(i: Z_9, j: Z_9): Zone = {
    val band = (j.representative - 1) / 3
    val indexWithinBand = (i.representative - 1) / 3

    zone(Z_9(band * 3 + indexWithinBand + 1))
  }

  lazy val valid: Boolean = (rows ++ columns ++ zones) forall { _.valid }
  lazy val solved: Boolean = (rows ++ columns ++ zones) forall { _.solved }
  lazy val numValues: Int = (rows map { _.numValues }).sum

  override def toString = {
    val rowStrings = rows map { row =>
      List(
        row.first map { _.representative } getOrElse "_",
        row.second map { _.representative } getOrElse "_",
        row.third map { _.representative } getOrElse "_",
        "|",
        row.fourth map { _.representative } getOrElse "_",
        row.fifth map { _.representative } getOrElse "_",
        row.sixth map { _.representative } getOrElse "_",
        "|",
        row.seventh map { _.representative } getOrElse "_",
        row.eighth map { _.representative } getOrElse "_",
        row.ninth map { _.representative } getOrElse "_"
      ).mkString(" ", " ", " ")
    }

    val allRows = List(
      rowStrings(0),
      rowStrings(1),
      rowStrings(2),
      "-----------------------",
      rowStrings(3),
      rowStrings(4),
      rowStrings(5),
      "-----------------------",
      rowStrings(6),
      rowStrings(7),
      rowStrings(8)
    )

    allRows.mkString("", "\n", "\n")
  }
}

object Board {

  private val Line = """(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)""".r
  private def toElement(token: String): Option[Z_9] = token.toIntOption map { Z_9(_) }

  // TODO: def apply(graph: GraphColouringProblem): Board

  def apply(board: String): Board = {
    val lines = board.split("\n").toList
    val trimmed = lines map { _.replaceAll("[^0-9_]", "") } filter { _ != "" }

    val rows = trimmed map { line =>
      val Line(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) = line
      Row(
        toElement(first),
        toElement(second),
        toElement(third),
        toElement(fourth),
        toElement(fifth),
        toElement(sixth),
        toElement(seventh),
        toElement(eighth),
        toElement(ninth)
      )
    }

    Board(rows(0), rows(1), rows(2), rows(3), rows(4), rows(5), rows(6), rows(7), rows(8))
  }
}
