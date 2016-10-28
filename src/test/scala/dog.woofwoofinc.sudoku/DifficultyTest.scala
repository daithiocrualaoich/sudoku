package dog.woofwoofinc.sudoku

import org.scalatest.{ FlatSpec, Matchers }

class DifficultyTest extends FlatSpec with Matchers with TestBoards {

  "Difficulty" should "determine easy boards" in {
    Difficulty(sudoku2054easy) should be(Easy)
    Difficulty(sudoku2055medium) should not be (Easy)
    Difficulty(sudoku2056medium) should not be (Easy)
    Difficulty(sudoku2057hard) should not be (Easy)
    Difficulty(sudoku2058hard) should not be (Easy)
    Difficulty(sudoku2059hard) should not be (Easy)
    Difficulty(sudoku2060easy) should be(Easy)
    Difficulty(sudoku2061medium) should not be (Easy)
    Difficulty(sudoku2062medium) should not be (Easy)
    Difficulty(sudoku2063hard) should not be (Easy)
    Difficulty(sudoku2064hard) should not be (Easy)
    Difficulty(sudoku2065hard) should not be (Easy)
    Difficulty(sudoku2066easy) should be(Easy)
    Difficulty(sudoku2067medium) should not be (Easy)
  }

  it should "determine medium boards" in {
    Difficulty(sudoku2054easy) should not be (Medium)
    Difficulty(sudoku2055medium) should be(Medium)
    Difficulty(sudoku2056medium) should be(Medium)
    Difficulty(sudoku2057hard) should not be (Medium)
    Difficulty(sudoku2058hard) should not be (Medium)
    Difficulty(sudoku2059hard) should not be (Medium)
    Difficulty(sudoku2060easy) should not be (Medium)
    Difficulty(sudoku2061medium) should be(Medium)
    Difficulty(sudoku2062medium) should be(Medium)
    Difficulty(sudoku2063hard) should not be (Medium)
    Difficulty(sudoku2064hard) should not be (Medium)
    Difficulty(sudoku2065hard) should not be (Medium)
    Difficulty(sudoku2066easy) should not be (Medium)
    Difficulty(sudoku2067medium) should be(Medium)
  }

  it should "determine hard boards" in {
    Difficulty(sudoku2054easy) should not be (Hard)
    Difficulty(sudoku2055medium) should not be (Hard)
    Difficulty(sudoku2056medium) should not be (Hard)
    Difficulty(sudoku2057hard) should be(Hard)
    Difficulty(sudoku2058hard) should be(Hard)
    Difficulty(sudoku2059hard) should be(Hard)
    Difficulty(sudoku2060easy) should not be (Hard)
    Difficulty(sudoku2061medium) should not be (Hard)
    Difficulty(sudoku2062medium) should not be (Hard)
    Difficulty(sudoku2063hard) should be(Hard)
    Difficulty(sudoku2064hard) should be(Hard)
    Difficulty(sudoku2065hard) should be(Hard)
    Difficulty(sudoku2066easy) should not be (Hard)
    Difficulty(sudoku2067medium) should not be (Hard)
  }

  it should "determine permitted boards" in {
    Difficulty.isPermitted(sudoku2054easy) should be(true)
    Difficulty.isPermitted(sudoku2055medium) should be(true)
    Difficulty.isPermitted(sudoku2056medium) should be(true)
    Difficulty.isPermitted(sudoku2057hard) should be(true)
    Difficulty.isPermitted(sudoku2058hard) should be(true)
    Difficulty.isPermitted(sudoku2059hard) should be(true)
    Difficulty.isPermitted(sudoku2060easy) should be(true)
    Difficulty.isPermitted(sudoku2061medium) should be(true)
    Difficulty.isPermitted(sudoku2062medium) should be(true)
    Difficulty.isPermitted(sudoku2063hard) should be(true)
    Difficulty.isPermitted(sudoku2064hard) should be(true)
    Difficulty.isPermitted(sudoku2065hard) should be(true)
    Difficulty.isPermitted(sudoku2066easy) should be(true)
    Difficulty.isPermitted(sudoku2067medium) should be(true)
  }

  it should "determine unpermitted boards" in {
    val notPermitted = Board("""
       1 2 3 | _ _ _ | _ _ _
       4 5 6 | _ _ _ | _ _ _
       7 8 9 | _ _ _ | _ _ _
      -----------------------
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
      -----------------------
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
    """).toGraphColouringProblem

    Difficulty.isPermitted(notPermitted) should be(false)
  }
}