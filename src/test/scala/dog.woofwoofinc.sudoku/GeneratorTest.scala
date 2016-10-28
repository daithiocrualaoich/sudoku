package dog.woofwoofinc.sudoku

import org.scalatest.{ FlatSpec, Matchers, OptionValues }

class GeneratorTest extends FlatSpec with Matchers with OptionValues with TestBoards {

  "Generator" should "generate an easy puzzle" in {
    val puzzle = Generator.generateEasyPuzzle(graphColouringProblem = Solver(sudoku2060easy).next)

    Difficulty(puzzle.value) should be(Easy)
    Difficulty.isPermitted(puzzle.value) should be(true)
  }

  it should "generate an easy puzzle with <= 27 values" in {
    val puzzle = Generator.generateEasyPuzzle(27, Solver(sudoku2060easy).next)

    puzzle.value.numPlacings should be <= 27
    Difficulty(puzzle.value) should be(Easy)
    Difficulty.isPermitted(puzzle.value) should be(true)
  }

  it should "generate an easy puzzle with <= 30 values" in {
    val puzzle = Generator.generateEasyPuzzle(30, Solver(sudoku2060easy).next)

    puzzle.value.numPlacings should be <= 30
    Difficulty(puzzle.value) should be(Easy)
    Difficulty.isPermitted(puzzle.value) should be(true)
  }

  it should "generate a medium puzzle" in {
    val puzzle = Generator.generateMediumPuzzle(graphColouringProblem = Solver(sudoku2055medium).next)

    Difficulty(puzzle.value) should be(Medium)
    Difficulty.isPermitted(puzzle.value) should be(true)
  }

  it should "generate a hard puzzle" in {
    val puzzle = Generator.generateHardPuzzle(graphColouringProblem = Solver(sudoku2057hard).next)

    Difficulty(puzzle.value) should be(Hard)
    Difficulty.isPermitted(puzzle.value) should be(true)
  }
}
