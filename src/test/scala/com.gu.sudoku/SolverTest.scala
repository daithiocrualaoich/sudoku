package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class SolverTest extends FlatSpec with ShouldMatchers with TestBoards {

  "Solver" should "solve sudoku2054easy" in {
    val solution = Solver.solve(sudoku2054easy)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2054easy_solution)
  }

  it should "solve sudoku2055medium" in {
    val solution = Solver.solve(sudoku2055medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2055medium_solution)
  }

  it should "solve sudoku2056medium" in {
    val solution = Solver.solve(sudoku2056medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2056medium_solution)
  }

  it should "solve sudoku2057hard" in {
    val solution = Solver.solve(sudoku2057hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2057hard_solution)
  }

  it should "solve sudoku2058hard" in {
    val solution = Solver.solve(sudoku2058hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2058hard_solution)
  }

  it should "solve sudoku2059hard" in {
    val solution = Solver.solve(sudoku2059hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2059hard_solution)
  }

  it should "solve sudoku2060easy" in {
    val solution = Solver.solve(sudoku2060easy)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2060easy_solution)
  }

  it should "solve sudoku2061medium" in {
    val solution = Solver.solve(sudoku2061medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2061medium_solution)
  }

  it should "solve sudoku2062medium" in {
    val solution = Solver.solve(sudoku2062medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2062medium_solution)
  }

  it should "solve sudoku2063hard" in {
    val solution = Solver.solve(sudoku2063hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2063hard_solution)
  }

  it should "solve sudoku2064hard" in {
    val solution = Solver.solve(sudoku2064hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2064hard_solution)
  }

  it should "solve sudoku2065hard" in {
    val solution = Solver.solve(sudoku2065hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2065hard_solution)
  }

  it should "solve sudoku2066easy" in {
    val solution = Solver.solve(sudoku2066easy)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2066easy_solution)
  }

  it should "solve sudoku2067medium" in {
    val solution = Solver.solve(sudoku2067medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(sudoku2067medium_solution)
  }

}
