package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class RowTest extends FlatSpec with ShouldMatchers {

  val emptyRow = Row.empty
  val partialRow = Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullRow = Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidRow = partialRow.copy(sixth = Some(Five))

  "Row" should "return values set in empty row" in {
    emptyRow.values should be(Set())
  }

  it should "return values set in partial row" in {
    partialRow.values should be(Set(One, Four, Five, Eight, Nine))
  }

  it should "return values set in full row" in {
    fullRow.values should be(Z_9.all.toSet)
  }

  it should "return values set in invalid row" in {
    invalidRow.values should be(Set(One, Four, Five, Eight, Nine))
  }

  it should "return valid for empty row" in {
    emptyRow.valid should be(true)
  }

  it should "return valid for partial row" in {
    partialRow.valid should be(true)
  }

  it should "return valid for full row" in {
    fullRow.valid should be(true)
  }

  it should "return invalid for invalid row" in {
    invalidRow.valid should be(false)
  }

  it should "return unsolved for empty row" in {
    emptyRow.solved should be(false)
  }

  it should "return unsolved for partial row" in {
    partialRow.solved should be(false)
  }

  it should "return solved for full row" in {
    fullRow.solved should be(true)
  }

  it should "return unsolved for invalid row" in {
    invalidRow.solved should be(false)
  }

  it should "deindex correctly for empty row" in {
    Z_9.all map { emptyRow(_) } should be(
      List(None, None, None, None, None, None, None, None, None))
  }

  it should "deindex correctly for partial row" in {
    Z_9.all map { partialRow(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
  }

  it should "deindex correctly for full row" in {
    Z_9.all map { fullRow(_) } should be(
      List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
  }

  it should "deindex correctly for invalid row" in {
    Z_9.all map { invalidRow(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }

  it should "have equality test based on immutability" in {
    emptyRow should be(Row.empty)
    partialRow should be(Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullRow should be(Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidRow should be(Row(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }

}

class ColumnTest extends FlatSpec with ShouldMatchers {

  val emptyColumn = Column(None, None, None, None, None, None, None, None, None)
  val partialColumn = Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullColumn = Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidColumn = partialColumn.copy(sixth = Some(Five))

  "Column" should "return values set in empty column" in {
    emptyColumn.values should be(Set())
  }

  it should "return values set in partial column" in {
    partialColumn.values should be(Set(One, Four, Five, Eight, Nine))
  }

  it should "return values set in full column" in {
    fullColumn.values should be(Z_9.all.toSet)
  }

  it should "return values set in invalid column" in {
    invalidColumn.values should be(Set(One, Four, Five, Eight, Nine))
  }

  it should "return valid for empty column" in {
    emptyColumn.valid should be(true)
  }

  it should "return valid for partial column" in {
    partialColumn.valid should be(true)
  }

  it should "return valid for full column" in {
    fullColumn.valid should be(true)
  }

  it should "return invalid for invalid column" in {
    invalidColumn.valid should be(false)
  }

  it should "return unsolved for empty column" in {
    emptyColumn.solved should be(false)
  }

  it should "return unsolved for partial column" in {
    partialColumn.solved should be(false)
  }

  it should "return solved for full column" in {
    fullColumn.solved should be(true)
  }

  it should "return unsolved for invalid column" in {
    invalidColumn.solved should be(false)
  }

  it should "deindex correctly for empty column" in {
    Z_9.all map { emptyColumn(_) } should be(
      List(None, None, None, None, None, None, None, None, None))
  }

  it should "deindex correctly for partial column" in {
    Z_9.all map { partialColumn(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
  }

  it should "deindex correctly for full column" in {
    Z_9.all map { fullColumn(_) } should be(
      List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
  }

  it should "deindex correctly for invalid column" in {
    Z_9.all map { invalidColumn(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }

  it should "have equality test based on immutability" in {
    emptyColumn should be(Column(None, None, None, None, None, None, None, None, None))
    partialColumn should be(Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullColumn should be(Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidColumn should be(Column(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }
}

class ZoneTest extends FlatSpec with ShouldMatchers {

  val emptyZone = Zone(None, None, None, None, None, None, None, None, None)
  val partialZone = Zone(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullZone = Zone(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidZone = partialZone.copy(centreRight = Some(Five))

  "Zone" should "return values set in empty zone" in {
    emptyZone.values should be(Set())
  }

  it should "return values set in partial zone" in {
    partialZone.values should be(Set(One, Four, Five, Eight, Nine))
  }

  it should "return values set in full zone" in {
    fullZone.values should be(Z_9.all.toSet)
  }

  it should "return values set in invalid zone" in {
    invalidZone.values should be(Set(One, Four, Five, Eight, Nine))
  }

  it should "return valid for empty zone" in {
    emptyZone.valid should be(true)
  }

  it should "return valid for partial zone" in {
    partialZone.valid should be(true)
  }

  it should "return valid for full zone" in {
    fullZone.valid should be(true)
  }

  it should "return invalid for invalid zone" in {
    invalidZone.valid should be(false)
  }

  it should "return unsolved for empty zone" in {
    emptyZone.solved should be(false)
  }

  it should "return unsolved for partial zone" in {
    partialZone.solved should be(false)
  }

  it should "return solved for full zone" in {
    fullZone.solved should be(true)
  }

  it should "return unsolved for invalid zone" in {
    invalidZone.solved should be(false)
  }

  it should "deindex correctly for empty zone" in {
    Z_9.all map { emptyZone(_) } should be(
      List(None, None, None, None, None, None, None, None, None))
  }

  it should "deindex correctly for partial zone" in {
    Z_9.all map { partialZone(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
  }

  it should "deindex correctly for full zone" in {
    Z_9.all map { fullZone(_) } should be(
      List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
  }

  it should "deindex correctly for invalid zone" in {
    Z_9.all map { invalidZone(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }

  it should "have equality test based on immutability" in {
    emptyZone should be(Zone(None, None, None, None, None, None, None, None, None))
    partialZone should be(Zone(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullZone should be(Zone(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidZone should be(Zone(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }
}

class BoardTest extends FlatSpec with ShouldMatchers {

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
      Row.empty)
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
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight)))
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
      Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight)))
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
      Row.empty))
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
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
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
      Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))))
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
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
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
      Column(None, None, None, None, None, None, None, None, None)))
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
      Column(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
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
      Column(Some(Nine), Some(Three), Some(Six), Some(One), Some(Four), Some(Seven), Some(Two), Some(Five), Some(Eight))))
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
      Column(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
  }

  it should "return zones in empty board" in {
    Z_9.all map { emptyBoard.zone } should be(List(
      Zone(None, None, None, None, None, None, None, None, None),
      Zone(None, None, None, None, None, None, None, None, None),
      Zone(None, None, None, None, None, None, None, None, None),

      Zone(None, None, None, None, None, None, None, None, None),
      Zone(None, None, None, None, None, None, None, None, None),
      Zone(None, None, None, None, None, None, None, None, None),

      Zone(None, None, None, None, None, None, None, None, None),
      Zone(None, None, None, None, None, None, None, None, None),
      Zone(None, None, None, None, None, None, None, None, None)))
  }

  it should "return zones in partial board" in {
    Z_9.all map { partialBoard.zone } should be(List(
      Zone(Some(One), None, None, None, Some(Four), None, None, None, Some(Seven)),
      Zone(Some(Two), None, None, None, Some(Five), None, None, None, Some(Eight)),
      Zone(Some(Three), None, None, None, Some(Six), None, None, None, Some(Nine)),

      Zone(Some(Two), None, None, None, Some(Five), None, None, None, Some(Eight)),
      Zone(Some(Three), None, None, None, Some(Six), None, None, None, Some(Nine)),
      Zone(Some(One), None, None, None, Some(Four), None, None, None, Some(Seven)),

      Zone(Some(Three), None, None, None, Some(Six), None, None, None, Some(Nine)),
      Zone(Some(One), None, None, None, Some(Four), None, None, None, Some(Seven)),
      Zone(Some(Two), None, None, None, Some(Five), None, None, None, Some(Eight))))
  }

  it should "return zones in full board" in {
    Z_9.all map { fullBoard.zone } should be(List(
      Zone(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
      Zone(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
      Zone(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

      Zone(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
      Zone(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
      Zone(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

      Zone(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
      Zone(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
      Zone(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))))
  }

  it should "return zones in invalid board" in {
    Z_9.all map { invalidBoard.zone } should be(List(
      Zone(Some(One), Some(One), None, None, Some(Four), None, None, None, Some(Seven)),
      Zone(Some(Two), None, None, None, Some(Five), None, None, None, Some(Eight)),
      Zone(Some(Three), None, None, None, Some(Six), None, None, None, Some(Nine)),

      Zone(Some(Two), None, None, None, Some(Five), None, None, None, Some(Eight)),
      Zone(Some(Three), None, None, None, Some(Six), None, None, None, Some(Nine)),
      Zone(Some(One), None, None, None, Some(Four), None, None, None, Some(Seven)),

      Zone(Some(Three), None, None, None, Some(Six), None, None, None, Some(Nine)),
      Zone(Some(One), None, None, None, Some(Four), None, None, None, Some(Seven)),
      Zone(Some(Two), None, None, None, Some(Five), None, None, None, Some(Eight))))
  }

  it should "return zones for row, column indexing" in {
    fullBoard.zone(One, One) should be(fullBoard.zone(One))
    fullBoard.zone(One, Two) should be(fullBoard.zone(One))
    fullBoard.zone(One, Three) should be(fullBoard.zone(One))
    fullBoard.zone(Two, One) should be(fullBoard.zone(One))
    fullBoard.zone(Two, Two) should be(fullBoard.zone(One))
    fullBoard.zone(Two, Three) should be(fullBoard.zone(One))
    fullBoard.zone(Three, One) should be(fullBoard.zone(One))
    fullBoard.zone(Three, Two) should be(fullBoard.zone(One))
    fullBoard.zone(Three, Three) should be(fullBoard.zone(One))

    fullBoard.zone(Four, One) should be(fullBoard.zone(Two))
    fullBoard.zone(Four, Two) should be(fullBoard.zone(Two))
    fullBoard.zone(Four, Three) should be(fullBoard.zone(Two))
    fullBoard.zone(Five, One) should be(fullBoard.zone(Two))
    fullBoard.zone(Five, Two) should be(fullBoard.zone(Two))
    fullBoard.zone(Five, Three) should be(fullBoard.zone(Two))
    fullBoard.zone(Six, One) should be(fullBoard.zone(Two))
    fullBoard.zone(Six, Two) should be(fullBoard.zone(Two))
    fullBoard.zone(Six, Three) should be(fullBoard.zone(Two))

    fullBoard.zone(Seven, One) should be(fullBoard.zone(Three))
    fullBoard.zone(Seven, Two) should be(fullBoard.zone(Three))
    fullBoard.zone(Seven, Three) should be(fullBoard.zone(Three))
    fullBoard.zone(Eight, One) should be(fullBoard.zone(Three))
    fullBoard.zone(Eight, Two) should be(fullBoard.zone(Three))
    fullBoard.zone(Eight, Three) should be(fullBoard.zone(Three))
    fullBoard.zone(Nine, One) should be(fullBoard.zone(Three))
    fullBoard.zone(Nine, Two) should be(fullBoard.zone(Three))
    fullBoard.zone(Nine, Three) should be(fullBoard.zone(Three))

    fullBoard.zone(One, Four) should be(fullBoard.zone(Four))
    fullBoard.zone(One, Five) should be(fullBoard.zone(Four))
    fullBoard.zone(One, Six) should be(fullBoard.zone(Four))
    fullBoard.zone(Two, Four) should be(fullBoard.zone(Four))
    fullBoard.zone(Two, Five) should be(fullBoard.zone(Four))
    fullBoard.zone(Two, Six) should be(fullBoard.zone(Four))
    fullBoard.zone(Three, Four) should be(fullBoard.zone(Four))
    fullBoard.zone(Three, Five) should be(fullBoard.zone(Four))
    fullBoard.zone(Three, Six) should be(fullBoard.zone(Four))

    fullBoard.zone(Four, Four) should be(fullBoard.zone(Five))
    fullBoard.zone(Four, Five) should be(fullBoard.zone(Five))
    fullBoard.zone(Four, Six) should be(fullBoard.zone(Five))
    fullBoard.zone(Five, Four) should be(fullBoard.zone(Five))
    fullBoard.zone(Five, Five) should be(fullBoard.zone(Five))
    fullBoard.zone(Five, Six) should be(fullBoard.zone(Five))
    fullBoard.zone(Six, Four) should be(fullBoard.zone(Five))
    fullBoard.zone(Six, Five) should be(fullBoard.zone(Five))
    fullBoard.zone(Six, Six) should be(fullBoard.zone(Five))

    fullBoard.zone(Seven, Four) should be(fullBoard.zone(Six))
    fullBoard.zone(Seven, Five) should be(fullBoard.zone(Six))
    fullBoard.zone(Seven, Six) should be(fullBoard.zone(Six))
    fullBoard.zone(Eight, Four) should be(fullBoard.zone(Six))
    fullBoard.zone(Eight, Five) should be(fullBoard.zone(Six))
    fullBoard.zone(Eight, Six) should be(fullBoard.zone(Six))
    fullBoard.zone(Nine, Four) should be(fullBoard.zone(Six))
    fullBoard.zone(Nine, Five) should be(fullBoard.zone(Six))
    fullBoard.zone(Nine, Six) should be(fullBoard.zone(Six))

    fullBoard.zone(One, Seven) should be(fullBoard.zone(Seven))
    fullBoard.zone(One, Eight) should be(fullBoard.zone(Seven))
    fullBoard.zone(One, Nine) should be(fullBoard.zone(Seven))
    fullBoard.zone(Two, Seven) should be(fullBoard.zone(Seven))
    fullBoard.zone(Two, Eight) should be(fullBoard.zone(Seven))
    fullBoard.zone(Two, Nine) should be(fullBoard.zone(Seven))
    fullBoard.zone(Three, Seven) should be(fullBoard.zone(Seven))
    fullBoard.zone(Three, Eight) should be(fullBoard.zone(Seven))
    fullBoard.zone(Three, Nine) should be(fullBoard.zone(Seven))

    fullBoard.zone(Four, Seven) should be(fullBoard.zone(Eight))
    fullBoard.zone(Four, Eight) should be(fullBoard.zone(Eight))
    fullBoard.zone(Four, Nine) should be(fullBoard.zone(Eight))
    fullBoard.zone(Five, Seven) should be(fullBoard.zone(Eight))
    fullBoard.zone(Five, Eight) should be(fullBoard.zone(Eight))
    fullBoard.zone(Five, Nine) should be(fullBoard.zone(Eight))
    fullBoard.zone(Six, Seven) should be(fullBoard.zone(Eight))
    fullBoard.zone(Six, Eight) should be(fullBoard.zone(Eight))
    fullBoard.zone(Six, Nine) should be(fullBoard.zone(Eight))

    fullBoard.zone(Seven, Eight) should be(fullBoard.zone(Nine))
    fullBoard.zone(Eight, Eight) should be(fullBoard.zone(Nine))
    fullBoard.zone(Seven, Nine) should be(fullBoard.zone(Nine))
    fullBoard.zone(Eight, Eight) should be(fullBoard.zone(Nine))
    fullBoard.zone(Eight, Eight) should be(fullBoard.zone(Nine))
    fullBoard.zone(Eight, Nine) should be(fullBoard.zone(Nine))
    fullBoard.zone(Nine, Eight) should be(fullBoard.zone(Nine))
    fullBoard.zone(Nine, Eight) should be(fullBoard.zone(Nine))
    fullBoard.zone(Nine, Nine) should be(fullBoard.zone(Nine))
  }

  it should "return valid for empty board" in {
    emptyBoard.valid should be(true)
  }

  it should "return valid for partial board" in {
    partialBoard.valid should be(true)
  }

  it should "return valid for full board" in {
    fullBoard.valid should be(true)
  }

  it should "return invalid for invalid board" in {
    invalidBoard.valid should be(false)
  }

  it should "return unsolved for empty board" in {
    emptyBoard.solved should be(false)
  }

  it should "return unsolved for partial board" in {
    partialBoard.solved should be(false)
  }

  it should "return solved for full board" in {
    fullBoard.solved should be(true)
  }

  it should "return unsolved for invalid board" in {
    invalidBoard.solved should be(false)
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
        Row.empty))

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
        Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))

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
        Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))))

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
        Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
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
        " _ _ _ | _ _ _ | _ _ _ \n")

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
        " _ _ 9 | _ _ 7 | _ _ 8 \n")

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
        " 9 1 2 | 3 4 5 | 6 7 8 \n")

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
        " _ _ 9 | _ _ 7 | _ _ 8 \n")
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
        " _ _ _ | _ _ _ | _ _ _ \n") should be(emptyBoard)

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
        " _ _ 9 | _ _ 7 | _ _ 8 \n") should be(partialBoard)

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
        " 9 1 2 | 3 4 5 | 6 7 8 \n") should be(fullBoard)

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
        " _ _ 9 | _ _ 7 | _ _ 8 \n") should be(invalidBoard)
  }
}
