package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class Z_9Test extends FlatSpec with ShouldMatchers {

  "Z_9" should "create elements of Z_9 from Int" in {
    Z_9(1) should be(One)
    Z_9(2) should be(Two)
    Z_9(3) should be(Three)
    Z_9(4) should be(Four)
    Z_9(5) should be(Five)
    Z_9(6) should be(Six)
    Z_9(7) should be(Seven)
    Z_9(8) should be(Eight)
    Z_9(9) should be(Nine)
  }

  it should "create elements of Z_9 from Int outside the [1-9] interval" in {
    Z_9(0) should be(Nine)
    Z_9(10) should be(One)
    Z_9(11) should be(Two)
    Z_9(12) should be(Three)
    Z_9(13) should be(Four)
    Z_9(14) should be(Five)
    Z_9(15) should be(Six)
    Z_9(16) should be(Seven)
    Z_9(17) should be(Eight)
    Z_9(18) should be(Nine)
  }

  it should "convert to Int" in {
    for (i <- 1 to 9) {
      Z_9(i).representative should be(i)
    }
  }
}

