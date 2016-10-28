package dog.woofwoofinc.sudoku

import org.scalatest.{ FlatSpec, Matchers, OptionValues }

class SolverTest extends FlatSpec with Matchers with OptionValues with TestBoards {

  "Solver" should "solve sudoku2054easy" in {
    val solution = Solver(sudoku2054easy).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2054easy_solution)
  }

  it should "solve sudoku2055medium" in {
    val solution = Solver(sudoku2055medium).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2055medium_solution)
  }

  it should "solve sudoku2056medium" in {
    val solution = Solver(sudoku2056medium).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2056medium_solution)
  }

  it should "solve sudoku2057hard" in {
    val solution = Solver(sudoku2057hard).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2057hard_solution)
  }

  it should "solve sudoku2058hard" in {
    val solution = Solver(sudoku2058hard).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2058hard_solution)
  }

  it should "solve sudoku2059hard" in {
    val solution = Solver(sudoku2059hard).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2059hard_solution)
  }

  it should "solve sudoku2060easy" in {
    val solution = Solver(sudoku2060easy).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2060easy_solution)
  }

  it should "solve sudoku2061medium" in {
    val solution = Solver(sudoku2061medium).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2061medium_solution)
  }

  it should "solve sudoku2062medium" in {
    val solution = Solver(sudoku2062medium).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2062medium_solution)
  }

  it should "solve sudoku2063hard" in {
    val solution = Solver(sudoku2063hard).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2063hard_solution)
  }

  it should "solve sudoku2064hard" in {
    val solution = Solver(sudoku2064hard).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2064hard_solution)
  }

  it should "solve sudoku2065hard" in {
    val solution = Solver(sudoku2065hard).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2065hard_solution)
  }

  it should "solve sudoku2066easy" in {
    val solution = Solver(sudoku2066easy).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2066easy_solution)
  }

  it should "solve sudoku2067medium" in {
    val solution = Solver(sudoku2067medium).headOption

    solution.value should be('valid)
    solution.value should be('solved)

    solution.value should be(sudoku2067medium_solution)
  }

  it should "determine unique solutions" in {
    Solver(sudoku2054easy).drop(1).headOption should not be 'defined
    Solver(sudoku2055medium).drop(1).headOption should not be 'defined
    Solver(sudoku2056medium).drop(1).headOption should not be 'defined
    Solver(sudoku2057hard).drop(1).headOption should not be 'defined
    Solver(sudoku2058hard).drop(1).headOption should not be 'defined
    Solver(sudoku2059hard).drop(1).headOption should not be 'defined
    Solver(sudoku2060easy).drop(1).headOption should not be 'defined
    Solver(sudoku2061medium).drop(1).headOption should not be 'defined
    Solver(sudoku2062medium).drop(1).headOption should not be 'defined
    Solver(sudoku2063hard).drop(1).headOption should not be 'defined
    Solver(sudoku2064hard).drop(1).headOption should not be 'defined
    Solver(sudoku2065hard).drop(1).headOption should not be 'defined
    Solver(sudoku2066easy).drop(1).headOption should not be 'defined
    Solver(sudoku2067medium).drop(1).headOption should not be 'defined
  }

  it should "determine multiple solutions" in {
    val underconstrained = Board("""
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

    (Solver(underconstrained) take 10).size should be(10)
  }

}
