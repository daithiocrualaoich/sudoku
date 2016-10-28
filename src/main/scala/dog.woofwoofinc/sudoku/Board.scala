package dog.woofwoofinc.sudoku

case class Row(
    first: Option[Z_9], second: Option[Z_9], third: Option[Z_9],
    fourth: Option[Z_9], fifth: Option[Z_9], sixth: Option[Z_9],
    seventh: Option[Z_9], eighth: Option[Z_9], ninth: Option[Z_9]
) {

  private val values = Array(
    first, second, third,
    fourth, fifth, sixth,
    seventh, eighth, ninth
  )

  def apply(i: Z_9): Option[Z_9] = values(i.representative - 1)
}

object Row {
  val empty = Row(None, None, None, None, None, None, None, None, None)
}

case class Column(
    first: Option[Z_9], second: Option[Z_9], third: Option[Z_9],
    fourth: Option[Z_9], fifth: Option[Z_9], sixth: Option[Z_9],
    seventh: Option[Z_9], eighth: Option[Z_9], ninth: Option[Z_9]
) {

  private val values = Array(
    first, second, third,
    fourth, fifth, sixth,
    seventh, eighth, ninth
  )

  def apply(i: Z_9): Option[Z_9] = values(i.representative - 1)
}

object Column {
  val empty = Column(None, None, None, None, None, None, None, None, None)
}

case class Board(
    first: Row, second: Row, third: Row,
    fourth: Row, fifth: Row, sixth: Row,
    seventh: Row, eighth: Row, ninth: Row
) {

  private lazy val rows = List(
    first, second, third,
    fourth, fifth, sixth,
    seventh, eighth, ninth
  )

  private lazy val columns = Z_9.all map { i =>
    new Column(
      first(i), second(i), third(i),
      fourth(i), fifth(i), sixth(i),
      seventh(i), eighth(i), ninth(i)
    )
  }

  def apply(i: Z_9, j: Z_9): Option[Z_9] = column(i)(j)

  def row(i: Z_9): Row = rows(i.representative - 1)
  def column(i: Z_9): Column = columns(i.representative - 1)

  lazy val toGraphColouringProblem: GraphColouringProblem = GraphColouringProblem(this)

  override def toString = {
    def element(value: Option[Z_9]): String = value map { _.representative.toString } getOrElse "_"

    val rowStrings = rows map { row =>
      List(
        element(row.first), element(row.second), element(row.third),
        "|",
        element(row.fourth), element(row.fifth), element(row.sixth),
        "|",
        element(row.seventh), element(row.eighth), element(row.ninth)
      ).mkString(" ", " ", " ")
    }

    val allRows = List(
      rowStrings(0), rowStrings(1), rowStrings(2),
      "-----------------------",
      rowStrings(3), rowStrings(4), rowStrings(5),
      "-----------------------",
      rowStrings(6), rowStrings(7), rowStrings(8)
    )

    allRows.mkString("", "\n", "\n")
  }
}

object Board {

  private val Line = """(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)""".r
  private def toElement(token: String): Option[Z_9] = token.toIntOption map { Z_9(_) }

  def apply(board: String): Board = {
    val lines = board.split("\n").toList
    val trimmed = lines map { _.replaceAll("[^0-9_]", "") } filter { _ != "" }

    val rows = trimmed map { line =>
      val Line(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) = line
      Row(
        toElement(first), toElement(second), toElement(third),
        toElement(fourth), toElement(fifth), toElement(sixth),
        toElement(seventh), toElement(eighth), toElement(ninth)
      )
    }

    Board(
      rows(0), rows(1), rows(2),
      rows(3), rows(4), rows(5),
      rows(6), rows(7), rows(8)
    )
  }

  def apply(graphColouringProblem: GraphColouringProblem): Board = {
    def boardValue(i: Z_9, j: Z_9) = SingletonSet.unapply(graphColouringProblem(i, j).candidates)

    val rows = (Z_9.all map { j =>
      (j, Row(
        boardValue(One, j), boardValue(Two, j), boardValue(Three, j),
        boardValue(Four, j), boardValue(Five, j), boardValue(Six, j),
        boardValue(Seven, j), boardValue(Eight, j), boardValue(Nine, j)
      ))
    }).toMap

    Board(
      rows(One), rows(Two), rows(Three),
      rows(Four), rows(Five), rows(Six),
      rows(Seven), rows(Eight), rows(Nine)
    )
  }
}
