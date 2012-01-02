package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class GeneratorTest extends FlatSpec with ShouldMatchers with TestBoards {

  "Generator" should "generate an easy puzzle" in {
    val puzzle = Generator.generateEasyPuzzle(graphColouringProblem = Solver(sudoku2060easy).next)

    puzzle should be('defined)

    Difficulty(puzzle.get) should be(Easy)
    Difficulty.isPermitted(puzzle.get) should be(true)
  }

  it should "generate an easy puzzle with <= 27 values" in {
    val puzzle = Generator.generateEasyPuzzle(27, Solver(sudoku2060easy).next)

    puzzle should be('defined)

    puzzle should be('defined)

    puzzle.get.numPlacings should be <= 27
    Difficulty(puzzle.get) should be(Easy)
    Difficulty.isPermitted(puzzle.get) should be(true)
  }

  it should "generate an easy puzzle with <= 30 values" in {
    val puzzle = Generator.generateEasyPuzzle(30, Solver(sudoku2060easy).next)

    puzzle should be('defined)

    puzzle should be('defined)

    puzzle.get.numPlacings should be <= 30
    Difficulty(puzzle.get) should be(Easy)
    Difficulty.isPermitted(puzzle.get) should be(true)
  }

  it should "generate a medium puzzle" in {
    val puzzle = Generator.generateMediumPuzzle(graphColouringProblem = Solver(sudoku2055medium).next)

    puzzle should be('defined)

    Difficulty(puzzle.get) should be(Medium)
    Difficulty.isPermitted(puzzle.get) should be(true)
  }

  it should "generate a hard puzzle" in {
    val puzzle = Generator.generateHardPuzzle(graphColouringProblem = Solver(sudoku2057hard).next)

    puzzle should be('defined)

    Difficulty(puzzle.get) should be(Hard)
    Difficulty.isPermitted(puzzle.get) should be(true)
  }

}
