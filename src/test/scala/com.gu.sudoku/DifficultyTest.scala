package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class DifficultyTest extends FlatSpec with ShouldMatchers with TestBoards {

  "Difficulty" should "determine easy boards" in {
    Difficulty.isEasy(sudoku2054easy) should be(true)
    Difficulty.isEasy(sudoku2055medium) should be(false)
    Difficulty.isEasy(sudoku2056medium) should be(false)
    Difficulty.isEasy(sudoku2057hard) should be(false)
    Difficulty.isEasy(sudoku2058hard) should be(false)
    Difficulty.isEasy(sudoku2059hard) should be(false)
    Difficulty.isEasy(sudoku2060easy) should be(true)
    Difficulty.isEasy(sudoku2061medium) should be(false)
    Difficulty.isEasy(sudoku2062medium) should be(false)
    Difficulty.isEasy(sudoku2063hard) should be(false)
    Difficulty.isEasy(sudoku2064hard) should be(false)
    Difficulty.isEasy(sudoku2065hard) should be(false)
    Difficulty.isEasy(sudoku2066easy) should be(true)
    Difficulty.isEasy(sudoku2067medium) should be(false)
  }

  it should "determine medium boards" in {
    Difficulty.isMedium(sudoku2054easy) should be(false)
    Difficulty.isMedium(sudoku2055medium) should be(true)
    Difficulty.isMedium(sudoku2056medium) should be(true)
    Difficulty.isMedium(sudoku2057hard) should be(false)
    Difficulty.isMedium(sudoku2058hard) should be(false)
    Difficulty.isMedium(sudoku2059hard) should be(false)
    Difficulty.isMedium(sudoku2060easy) should be(false)
    Difficulty.isMedium(sudoku2061medium) should be(true)
    Difficulty.isMedium(sudoku2062medium) should be(true)
    Difficulty.isMedium(sudoku2063hard) should be(false)
    Difficulty.isMedium(sudoku2064hard) should be(false)
    Difficulty.isMedium(sudoku2065hard) should be(false)
    Difficulty.isMedium(sudoku2066easy) should be(false)
    Difficulty.isMedium(sudoku2067medium) should be(true)
  }

  it should "determine hard boards" in {
    Difficulty.isHard(sudoku2054easy) should be(false)
    Difficulty.isHard(sudoku2055medium) should be(false)
    Difficulty.isHard(sudoku2056medium) should be(false)
    Difficulty.isHard(sudoku2057hard) should be(true)
    Difficulty.isHard(sudoku2058hard) should be(true)
    Difficulty.isHard(sudoku2059hard) should be(true)
    Difficulty.isHard(sudoku2060easy) should be(false)
    Difficulty.isHard(sudoku2061medium) should be(false)
    Difficulty.isHard(sudoku2062medium) should be(false)
    Difficulty.isHard(sudoku2063hard) should be(true)
    Difficulty.isHard(sudoku2064hard) should be(true)
    Difficulty.isHard(sudoku2065hard) should be(true)
    Difficulty.isHard(sudoku2066easy) should be(false)
    Difficulty.isHard(sudoku2067medium) should be(false)
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
      """)

    Difficulty.isPermitted(notPermitted) should be(false)
  }

}

class EasyTest extends FlatSpec with ShouldMatchers with TestBoards {

  def unextract(board: Board): Boolean = board match {
    case Easy() => true
    case _ => false
  }

  "Easy" should "unextract" in {
    unextract(sudoku2054easy) should be(true)
    unextract(sudoku2055medium) should be(false)
    unextract(sudoku2056medium) should be(false)
    unextract(sudoku2057hard) should be(false)
    unextract(sudoku2058hard) should be(false)
    unextract(sudoku2059hard) should be(false)
    unextract(sudoku2060easy) should be(true)
    unextract(sudoku2061medium) should be(false)
    unextract(sudoku2062medium) should be(false)
    unextract(sudoku2063hard) should be(false)
    unextract(sudoku2064hard) should be(false)
    unextract(sudoku2065hard) should be(false)
    unextract(sudoku2066easy) should be(true)
    unextract(sudoku2067medium) should be(false)
  }

}

class MediumTest extends FlatSpec with ShouldMatchers with TestBoards {

  def unextract(board: Board): Boolean = board match {
    case Medium() => true
    case _ => false
  }

  "Medium" should "unextract" in {
    unextract(sudoku2054easy) should be(false)
    unextract(sudoku2055medium) should be(true)
    unextract(sudoku2056medium) should be(true)
    unextract(sudoku2057hard) should be(false)
    unextract(sudoku2058hard) should be(false)
    unextract(sudoku2059hard) should be(false)
    unextract(sudoku2060easy) should be(false)
    unextract(sudoku2061medium) should be(true)
    unextract(sudoku2062medium) should be(true)
    unextract(sudoku2063hard) should be(false)
    unextract(sudoku2064hard) should be(false)
    unextract(sudoku2065hard) should be(false)
    unextract(sudoku2066easy) should be(false)
    unextract(sudoku2067medium) should be(true)
  }

}

class HardTest extends FlatSpec with ShouldMatchers with TestBoards {

  def unextract(board: Board): Boolean = board match {
    case Hard() => true
    case _ => false
  }

  "Hard" should "unextract" in {
    unextract(sudoku2054easy) should be(false)
    unextract(sudoku2055medium) should be(false)
    unextract(sudoku2056medium) should be(false)
    unextract(sudoku2057hard) should be(true)
    unextract(sudoku2058hard) should be(true)
    unextract(sudoku2059hard) should be(true)
    unextract(sudoku2060easy) should be(false)
    unextract(sudoku2061medium) should be(false)
    unextract(sudoku2062medium) should be(false)
    unextract(sudoku2063hard) should be(true)
    unextract(sudoku2064hard) should be(true)
    unextract(sudoku2065hard) should be(true)
    unextract(sudoku2066easy) should be(false)
    unextract(sudoku2067medium) should be(false)
  }
}

class PermittedTest extends FlatSpec with ShouldMatchers with TestBoards {

  def unextract(board: Board): Boolean = board match {
    case Permitted() => true
    case _ => false
  }

  "Permitted" should "unextract" in {
    unextract(sudoku2054easy) should be(true)
    unextract(sudoku2055medium) should be(true)
    unextract(sudoku2056medium) should be(true)
    unextract(sudoku2057hard) should be(true)
    unextract(sudoku2058hard) should be(true)
    unextract(sudoku2059hard) should be(true)
    unextract(sudoku2060easy) should be(true)
    unextract(sudoku2061medium) should be(true)
    unextract(sudoku2062medium) should be(true)
    unextract(sudoku2063hard) should be(true)
    unextract(sudoku2064hard) should be(true)
    unextract(sudoku2065hard) should be(true)
    unextract(sudoku2066easy) should be(true)
    unextract(sudoku2067medium) should be(true)

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
      """)

    unextract(notPermitted) should be(false)
  }

}
