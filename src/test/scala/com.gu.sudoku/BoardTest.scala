package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class RowTest extends FlatSpec with ShouldMatchers {

  val emptyRow = new Row(None, None, None, None, None, None, None, None, None)
  val partialRow = new Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullRow = new Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidRow = new Row(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))

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
    emptyRow should be(new Row(None, None, None, None, None, None, None, None, None))
    partialRow should be(new Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullRow should be(new Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidRow should be(new Row(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }

}

class ColumnTest extends FlatSpec with ShouldMatchers {

  val emptyColumn = new Column(None, None, None, None, None, None, None, None, None)
  val partialColumn = new Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullColumn = new Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidColumn = new Column(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))

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
    emptyColumn should be(new Column(None, None, None, None, None, None, None, None, None))
    partialColumn should be(new Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullColumn should be(new Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidColumn should be(new Column(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }
}

class ZoneTest extends FlatSpec with ShouldMatchers {

  val emptyZone = new Zone(None, None, None, None, None, None, None, None, None)
  val partialZone = new Zone(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullZone = new Zone(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidZone = new Zone(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))

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
      List(None, None, None, None, None, None, None, None, None)
    )
  }

  it should "deindex correctly for partial zone" in {
    Z_9.all map { partialZone(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
    )
  }

  it should "deindex correctly for full zone" in {
    Z_9.all map { fullZone(_) } should be(
      List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
    )
  }

  it should "deindex correctly for invalid zone" in {
    Z_9.all map { invalidZone(_) } should be(
      List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))
    )
  }

  it should "have equality test based on immutability" in {
    emptyZone should be(new Zone(None, None, None, None, None, None, None, None, None))
    partialZone should be(new Zone(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    fullZone should be(new Zone(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    invalidZone should be(new Zone(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
  }
}

