package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class GeneratorTest extends FlatSpec with ShouldMatchers {

  "Generator" should "generate a solved board" in {
    val board = Generator.generateSolvedBoard()

    board.numValues should be(81)
  }

  it should "generate a solvable puzzle" in {
    val puzzle = Generator.generatePuzzleBoards().headOption

    puzzle should be('defined)

    Difficulty.isPermitted(puzzle.get) should be(true)
  }

  it should "generate an easy puzzle" in {
    val puzzle = (Generator.generatePuzzleBoards() filter { Difficulty.isEasy }).headOption

    puzzle should be('defined)

    Difficulty.isPermitted(puzzle.get) should be(true)

    Difficulty.isEasy(puzzle.get) should be(true)
    Difficulty.isMedium(puzzle.get) should be(false)
    Difficulty.isHard(puzzle.get) should be(false)
  }

  //  it should "generate an easy puzzle with <= 26 values" in {
  //    val puzzle = (Generator.generatePuzzleBoards() filter { _.numValues <= 26 } filter { Difficulty.isEasy }).headOption
  //
  //    puzzle should be('defined)
  //
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //
  //    Difficulty.isEasy(puzzle.get) should be(true)
  //    Difficulty.isMedium(puzzle.get) should be(false)
  //    Difficulty.isHard(puzzle.get) should be(false)
  //  }

  //  it should "generate an easy puzzle with <= 30 values" in {
  //    val puzzle = (Generator.generatePuzzleBoards() filter { _.numValues <= 30 } filter { Difficulty.isEasy }).headOption
  //
  //    puzzle should be('defined)
  //
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //
  //    Difficulty.isEasy(puzzle.get) should be(true)
  //    Difficulty.isMedium(puzzle.get) should be(false)
  //    Difficulty.isHard(puzzle.get) should be(false)
  //  }

  //  it should "generate a medium puzzle" in {
  //    val puzzle = (Generator.generatePuzzleBoards() filter { Difficulty.isMedium }).headOption
  //
  //    puzzle should be('defined)
  //
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //
  //    Difficulty.isEasy(puzzle.get) should be(false)
  //    Difficulty.isMedium(puzzle.get) should be(true)
  //    Difficulty.isHard(puzzle.get) should be(false)
  //  }
  //
  //  it should "generate a hard puzzle" in {
  //    val puzzle = (Generator.generatePuzzleBoards() filter { Difficulty.isHard }).headOption
  //
  //    puzzle should be('defined)
  //
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //
  //    Difficulty.isEasy(puzzle.get) should be(false)
  //    Difficulty.isMedium(puzzle.get) should be(false)
  //    Difficulty.isHard(puzzle.get) should be(true)
  //  }

}
