package dog.woofwoofinc.sudoku

import org.scalatest.{ FlatSpec, Matchers }

class RowTest extends FlatSpec with Matchers {

  val emptyRow = Row.empty
  val partialRow = Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullRow = Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidRow = partialRow.copy(sixth = Some(Five))

  "Row" should "deindex correctly for empty row" in {
    Z_9.all map { emptyRow(_) } should be(
      List(None, None, None, None, None, None, None, None, None)
    )
  }

  it should "deindex correctly for partial row" in {
    Z_9.all map { partialRow(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
    )
  }

  it should "deindex correctly for full row" in {
    Z_9.all map { fullRow(_) } should be(
      List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
    )
  }

  it should "deindex correctly for invalid row" in {
    Z_9.all map { invalidRow(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))
    )
  }

  it should "have equality test based on immutability" in {
    emptyRow should be(Row.empty)
    partialRow should be(Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullRow should be(Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidRow should be(Row(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }

}

class ColumnTest extends FlatSpec with Matchers {

  val emptyColumn = Column(None, None, None, None, None, None, None, None, None)
  val partialColumn = Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullColumn = Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidColumn = partialColumn.copy(sixth = Some(Five))

  "Column" should "deindex correctly for empty column" in {
    Z_9.all map { emptyColumn(_) } should be(
      List(None, None, None, None, None, None, None, None, None)
    )
  }

  it should "deindex correctly for partial column" in {
    Z_9.all map { partialColumn(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
    )
  }

  it should "deindex correctly for full column" in {
    Z_9.all map { fullColumn(_) } should be(
      List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
    )
  }

  it should "deindex correctly for invalid column" in {
    Z_9.all map { invalidColumn(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))
    )
  }

  it should "have equality test based on immutability" in {
    emptyColumn should be(Column(None, None, None, None, None, None, None, None, None))
    partialColumn should be(Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullColumn should be(Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidColumn should be(Column(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }
}

class BoardTest extends FlatSpec with Matchers {

  val emptyBoard = {
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    //-----------------------
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    //-----------------------
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _

    Board(
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty
    )
  }

  val partialBoard = {
    // 1 _ _ | 2 _ _ | 3 _ _
    // _ 4 _ | _ 5 _ | _ 6 _
    // _ _ 7 | _ _ 8 | _ _ 9
    //-----------------------
    // 2 _ _ | 3 _ _ | 1 _ _
    // _ 5 _ | _ 6 _ | _ 4 _
    // _ _ 8 | _ _ 9 | _ _ 7
    //-----------------------
    // 3 _ _ | 1 _ _ | 2 _ _
    // _ 6 _ | _ 4 _ | _ 5 _
    // _ _ 9 | _ _ 7 | _ _ 8

    Board(
      Row(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
      Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
      Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

      Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
      Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
      Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

      Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
      Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
    )
  }

  val fullBoard = {
    // 1 2 3 | 4 5 6 | 7 8 9
    // 4 5 6 | 7 8 9 | 1 2 3
    // 7 8 9 | 1 2 3 | 4 5 6
    //-----------------------
    // 2 3 4 | 5 6 7 | 8 9 1
    // 5 6 7 | 8 9 1 | 2 3 4
    // 8 9 1 | 2 3 4 | 5 6 7
    //-----------------------
    // 3 4 5 | 6 7 8 | 9 1 2
    // 6 7 8 | 9 1 2 | 3 4 5
    // 9 1 2 | 3 4 5 | 6 7 8
    Board(
      Row(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
      Row(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
      Row(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

      Row(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
      Row(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
      Row(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

      Row(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
      Row(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
      Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))
    )
  }

  val invalidBoard = {
    // 1 1 _ | 2 _ _ | 3 _ _
    // _ 4 _ | _ 5 _ | _ 6 _
    // _ _ 7 | _ _ 8 | _ _ 9
    //-----------------------
    // 2 _ _ | 3 _ _ | 1 _ _
    // _ 5 _ | _ 6 _ | _ 4 _
    // _ _ 8 | _ _ 9 | _ _ 7
    //-----------------------
    // 3 _ _ | 1 _ _ | 2 _ _
    // _ 6 _ | _ 4 _ | _ 5 _
    // _ _ 9 | _ _ 7 | _ _ 8
    partialBoard.copy(first = partialBoard.first.copy(second = Some(One)))
  }

  "Board" should "have apply notation" in {
    emptyBoard(One, Seven) should be(None)
    emptyBoard(Three, Three) should be(None)
    emptyBoard(Eight, Two) should be(None)

    partialBoard(One, Seven) should be(Some(Three))
    partialBoard(Three, Three) should be(Some(Seven))
    partialBoard(Eight, Two) should be(Some(Six))

    fullBoard(One, Seven) should be(Some(Three))
    fullBoard(Three, Three) should be(Some(Nine))
    fullBoard(Eight, Two) should be(Some(Two))

    invalidBoard(One, Seven) should be(Some(Three))
    invalidBoard(Three, Three) should be(Some(Seven))
    invalidBoard(Eight, Two) should be(Some(Six))
  }

  it should "return rows in empty board" in {
    Z_9.all map { emptyBoard.row } should be(List(
      Row.empty,
      Row.empty,
      Row.empty,

      Row.empty,
      Row.empty,
      Row.empty,

      Row.empty,
      Row.empty,
      Row.empty
    ))
  }

  it should "return rows in partial board" in {
    Z_9.all map { partialBoard.row } should be(List(
      Row(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
      Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
      Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

      Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
      Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
      Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

      Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
      Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
    ))
  }

  it should "return rows in full board" in {
    Z_9.all map { fullBoard.row } should be(List(
      Row(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
      Row(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
      Row(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

      Row(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
      Row(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
      Row(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

      Row(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
      Row(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
      Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))
    ))
  }

  it should "return rows in invalid board" in {
    Z_9.all map { invalidBoard.row } should be(List(
      Row(Some(One), Some(One), None, Some(Two), None, None, Some(Three), None, None),
      Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
      Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

      Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
      Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
      Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

      Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
      Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
    ))
  }

  it should "return columns in empty board" in {
    Z_9.all map { emptyBoard.column } should be(List(
      Column(None, None, None, None, None, None, None, None, None),
      Column(None, None, None, None, None, None, None, None, None),
      Column(None, None, None, None, None, None, None, None, None),

      Column(None, None, None, None, None, None, None, None, None),
      Column(None, None, None, None, None, None, None, None, None),
      Column(None, None, None, None, None, None, None, None, None),

      Column(None, None, None, None, None, None, None, None, None),
      Column(None, None, None, None, None, None, None, None, None),
      Column(None, None, None, None, None, None, None, None, None)
    ))
  }

  it should "return columns in partial board" in {
    Z_9.all map { partialBoard.column } should be(List(
      Column(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
      Column(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
      Column(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

      Column(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
      Column(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
      Column(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

      Column(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
      Column(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
      Column(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
    ))
  }

  it should "return columns in full board" in {
    Z_9.all map { fullBoard.column } should be(List(
      Column(Some(One), Some(Four), Some(Seven), Some(Two), Some(Five), Some(Eight), Some(Three), Some(Six), Some(Nine)),
      Column(Some(Two), Some(Five), Some(Eight), Some(Three), Some(Six), Some(Nine), Some(Four), Some(Seven), Some(One)),
      Column(Some(Three), Some(Six), Some(Nine), Some(Four), Some(Seven), Some(One), Some(Five), Some(Eight), Some(Two)),

      Column(Some(Four), Some(Seven), Some(One), Some(Five), Some(Eight), Some(Two), Some(Six), Some(Nine), Some(Three)),
      Column(Some(Five), Some(Eight), Some(Two), Some(Six), Some(Nine), Some(Three), Some(Seven), Some(One), Some(Four)),
      Column(Some(Six), Some(Nine), Some(Three), Some(Seven), Some(One), Some(Four), Some(Eight), Some(Two), Some(Five)),

      Column(Some(Seven), Some(One), Some(Four), Some(Eight), Some(Two), Some(Five), Some(Nine), Some(Three), Some(Six)),
      Column(Some(Eight), Some(Two), Some(Five), Some(Nine), Some(Three), Some(Six), Some(One), Some(Four), Some(Seven)),
      Column(Some(Nine), Some(Three), Some(Six), Some(One), Some(Four), Some(Seven), Some(Two), Some(Five), Some(Eight))
    ))
  }

  it should "return columns in invalid board" in {
    Z_9.all map { invalidBoard.column } should be(List(
      Column(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
      Column(Some(One), Some(Four), None, None, Some(Five), None, None, Some(Six), None),
      Column(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

      Column(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
      Column(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
      Column(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

      Column(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
      Column(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
      Column(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
    ))
  }

  it should "have equality test based on immutability" in {
    emptyBoard should be(
      Board(
        Row.empty,
        Row.empty,
        Row.empty,

        Row.empty,
        Row.empty,
        Row.empty,

        Row.empty,
        Row.empty,
        Row.empty
      )
    )

    partialBoard should be(
      Board(
        Row(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
        Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
        Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

        Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
        Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
        Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

        Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
        Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
        Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
      )
    )

    fullBoard should be(
      Board(
        Row(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
        Row(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
        Row(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

        Row(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
        Row(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
        Row(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

        Row(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
        Row(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
        Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))
      )
    )

    invalidBoard should be(
      Board(
        Row(Some(One), Some(One), None, Some(Two), None, None, Some(Three), None, None),
        Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
        Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

        Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
        Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
        Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

        Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
        Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
        Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
      )
    )
  }

  it should "toString boards as grids" in {
    emptyBoard.toString should be(
      "" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        "-----------------------\n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        "-----------------------\n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n"
    )

    partialBoard.toString should be(
      "" +
        " 1 _ _ | 2 _ _ | 3 _ _ \n" +
        " _ 4 _ | _ 5 _ | _ 6 _ \n" +
        " _ _ 7 | _ _ 8 | _ _ 9 \n" +
        "-----------------------\n" +
        " 2 _ _ | 3 _ _ | 1 _ _ \n" +
        " _ 5 _ | _ 6 _ | _ 4 _ \n" +
        " _ _ 8 | _ _ 9 | _ _ 7 \n" +
        "-----------------------\n" +
        " 3 _ _ | 1 _ _ | 2 _ _ \n" +
        " _ 6 _ | _ 4 _ | _ 5 _ \n" +
        " _ _ 9 | _ _ 7 | _ _ 8 \n"
    )

    fullBoard.toString should be(
      "" +
        " 1 2 3 | 4 5 6 | 7 8 9 \n" +
        " 4 5 6 | 7 8 9 | 1 2 3 \n" +
        " 7 8 9 | 1 2 3 | 4 5 6 \n" +
        "-----------------------\n" +
        " 2 3 4 | 5 6 7 | 8 9 1 \n" +
        " 5 6 7 | 8 9 1 | 2 3 4 \n" +
        " 8 9 1 | 2 3 4 | 5 6 7 \n" +
        "-----------------------\n" +
        " 3 4 5 | 6 7 8 | 9 1 2 \n" +
        " 6 7 8 | 9 1 2 | 3 4 5 \n" +
        " 9 1 2 | 3 4 5 | 6 7 8 \n"
    )

    invalidBoard.toString should be(
      "" +
        " 1 1 _ | 2 _ _ | 3 _ _ \n" +
        " _ 4 _ | _ 5 _ | _ 6 _ \n" +
        " _ _ 7 | _ _ 8 | _ _ 9 \n" +
        "-----------------------\n" +
        " 2 _ _ | 3 _ _ | 1 _ _ \n" +
        " _ 5 _ | _ 6 _ | _ 4 _ \n" +
        " _ _ 8 | _ _ 9 | _ _ 7 \n" +
        "-----------------------\n" +
        " 3 _ _ | 1 _ _ | 2 _ _ \n" +
        " _ 6 _ | _ 4 _ | _ 5 _ \n" +
        " _ _ 9 | _ _ 7 | _ _ 8 \n"
    )
  }

  it should "construct boards from grid Strings" in {
    Board(
      "" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        "-----------------------\n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        "-----------------------\n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n" +
        " _ _ _ | _ _ _ | _ _ _ \n"
    ) should be(emptyBoard)

    Board(
      "" +
        " 1 _ _ | 2 _ _ | 3 _ _ \n" +
        " _ 4 _ | _ 5 _ | _ 6 _ \n" +
        " _ _ 7 | _ _ 8 | _ _ 9 \n" +
        "-----------------------\n" +
        " 2 _ _ | 3 _ _ | 1 _ _ \n" +
        " _ 5 _ | _ 6 _ | _ 4 _ \n" +
        " _ _ 8 | _ _ 9 | _ _ 7 \n" +
        "-----------------------\n" +
        " 3 _ _ | 1 _ _ | 2 _ _ \n" +
        " _ 6 _ | _ 4 _ | _ 5 _ \n" +
        " _ _ 9 | _ _ 7 | _ _ 8 \n"
    ) should be(partialBoard)

    Board(
      "" +
        " 1 2 3 | 4 5 6 | 7 8 9 \n" +
        " 4 5 6 | 7 8 9 | 1 2 3 \n" +
        " 7 8 9 | 1 2 3 | 4 5 6 \n" +
        "-----------------------\n" +
        " 2 3 4 | 5 6 7 | 8 9 1 \n" +
        " 5 6 7 | 8 9 1 | 2 3 4 \n" +
        " 8 9 1 | 2 3 4 | 5 6 7 \n" +
        "-----------------------\n" +
        " 3 4 5 | 6 7 8 | 9 1 2 \n" +
        " 6 7 8 | 9 1 2 | 3 4 5 \n" +
        " 9 1 2 | 3 4 5 | 6 7 8 \n"
    ) should be(fullBoard)

    Board(
      "" +
        " 1 1 _ | 2 _ _ | 3 _ _ \n" +
        " _ 4 _ | _ 5 _ | _ 6 _ \n" +
        " _ _ 7 | _ _ 8 | _ _ 9 \n" +
        "-----------------------\n" +
        " 2 _ _ | 3 _ _ | 1 _ _ \n" +
        " _ 5 _ | _ 6 _ | _ 4 _ \n" +
        " _ _ 8 | _ _ 9 | _ _ 7 \n" +
        "-----------------------\n" +
        " 3 _ _ | 1 _ _ | 2 _ _ \n" +
        " _ 6 _ | _ 4 _ | _ 5 _ \n" +
        " _ _ 9 | _ _ 7 | _ _ 8 \n"
    ) should be(invalidBoard)
  }
}
