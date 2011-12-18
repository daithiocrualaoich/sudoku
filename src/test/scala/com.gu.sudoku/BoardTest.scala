package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class BoardTest extends FlatSpec with ShouldMatchers {

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

}

