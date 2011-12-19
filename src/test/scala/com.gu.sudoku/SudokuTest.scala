package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class SudokuTest extends FlatSpec with ShouldMatchers {

  val sudoku2054easy = {
    // _ 2 7 | 5 _ _ | _ _ _
    // 4 _ _ | 6 _ 1 | 2 3 _
    // 8 _ _ | _ _ _ | _ 9 _
    //-----------------------
    // 9 3 _ | 7 _ _ | _ 2 _
    // _ _ _ | _ 6 _ | _ _ _
    // _ 6 _ | _ _ 5 | _ 1 4
    //-----------------------
    // _ 4 _ | _ _ _ | _ _ 8
    // _ 5 3 | 8 _ 9 | _ _ 2
    // _ _ _ | _ _ 7 | 6 4 _
    Board(
      Row(None, Some(Two), Some(Seven), Some(Five), None, None, None, None, None),
      Row(Some(Four), None, None, Some(Six), None, Some(One), Some(Two), Some(Three), None),
      Row(Some(Eight), None, None, None, None, None, None, Some(Nine), None),

      Row(Some(Nine), Some(Three), None, Some(Seven), None, None, None, Some(Two), None),
      Row(None, None, None, None, Some(Six), None, None, None, None),
      Row(None, Some(Six), None, None, None, Some(Five), None, Some(One), Some(Four)),

      Row(None, Some(Four), None, None, None, None, None, None, Some(Eight)),
      Row(None, Some(Five), Some(Three), Some(Eight), None, Some(Nine), None, None, Some(Two)),
      Row(None, None, None, None, None, Some(Seven), Some(Six), Some(Four), None)
    )
  }

  val sudoku2055medium = {
    // _ _ _ | _ _ 8 | 9 _ _
    // _ _ 5 | 9 _ _ | _ 8 _
    // _ 3 _ | _ 4 _ | _ 2 _
    //-----------------------
    // _ 1 _ | _ 2 _ | _ _ 6
    // 2 _ _ | _ _ _ | _ _ 5
    // 7 _ _ | _ 3 _ | _ 4 _
    //-----------------------
    // _ 4 _ | _ 8 _ | _ 5 _
    // _ 5 _ | _ _ 2 | 7 _ _
    // _ _ 9 | 6 _ _ | _ _ _
    Board(
      Row(None, None, None, None, None, Some(Eight), Some(Nine), None, None),
      Row(None, None, Some(Five), Some(Nine), None, None, None, Some(Eight), None),
      Row(None, Some(Three), None, None, Some(Four), None, None, Some(Two), None),

      Row(None, Some(One), None, None, Some(Two), None, None, None, Some(Six)),
      Row(Some(Two), None, None, None, None, None, None, None, Some(Five)),
      Row(Some(Seven), None, None, None, Some(Three), None, None, Some(Four), None),

      Row(None, Some(Four), None, None, Some(Eight), None, None, Some(Five), None),
      Row(None, Some(Five), None, None, None, Some(Two), Some(Seven), None, None),
      Row(None, None, Some(Nine), Some(Six), None, None, None, None, None)
    )
  }

  val sudoku2056medium = {
    // _ 2 7 | _ _ _ | _ 6 _
    // 9 _ _ | _ _ _ | _ _ 5
    // 3 _ _ | _ 7 2 | _ _ _
    //-----------------------
    // _ 9 _ | 1 _ _ | 6 _ _
    // _ 7 _ | _ _ _ | _ 8 _
    // _ _ 1 | _ _ 8 | _ 7 _
    //-----------------------
    // _ _ _ | 5 4 _ | _ _ 6
    // 2 _ _ | _ _ _ | _ _ 4
    // _ 6 _ | _ _ _ | 3 1 _
    Board(
      Row(None, Some(Two), Some(Seven), None, None, None, None, Some(Six), None),
      Row(Some(Nine), None, None, None, None, None, None, None, Some(Five)),
      Row(Some(Three), None, None, None, Some(Seven), Some(Two), None, None, None),

      Row(None, Some(Nine), None, Some(One), None, None, Some(Six), None, None),
      Row(None, Some(Seven), None, None, None, None, None, Some(Eight), None),
      Row(None, None, Some(One), None, None, Some(Eight), None, Some(Seven), None),

      Row(None, None, None, Some(Five), Some(Four), None, None, None, Some(Six)),
      Row(Some(Two), None, None, None, None, None, None, None, Some(Four)),
      Row(None, Some(Six), None, None, None, None, Some(Three), Some(One), None)
    )
  }

  val sudoku2057hard = {
    // _ _ 9 | _ _ _ | 7 _ _
    // _ 5 _ | _ _ _ | _ 2 _
    // 3 _ _ | 7 _ 4 | _ _ 8
    //-----------------------
    // _ _ 7 | _ 5 _ | 1 _ _
    // _ _ _ | 1 _ 7 | _ _ _
    // _ _ 2 | _ 8 _ | 6 _ _
    //-----------------------
    // 4 _ _ | 9 _ 3 | _ _ 1
    // _ 3 _ | _ _ _ | _ 6 _
    // _ _ 6 | _ _ _ | 8 _ _
    Board(
      Row(None, None, Some(Nine), None, None, None, Some(Seven), None, None),
      Row(None, Some(Five), None, None, None, None, None, Some(Two), None),
      Row(Some(Three), None, None, Some(Seven), None, Some(Four), None, None, Some(Eight)),

      Row(None, None, Some(Seven), None, Some(Five), None, Some(One), None, None),
      Row(None, None, None, Some(One), None, Some(Seven), None, None, None),
      Row(None, None, Some(Two), None, Some(Eight), None, Some(Six), None, None),

      Row(Some(Four), None, None, Some(Nine), None, Some(Three), None, None, Some(One)),
      Row(None, Some(Three), None, None, None, None, None, Some(Six), None),
      Row(None, None, Some(Six), None, None, None, Some(Eight), None, None)
    )
  }

  val sudoku2058hard = {
    // _ 8 4 | _ _ _ | _ 6 _
    // 7 _ _ | 5 _ _ | _ _ 8
    // _ _ _ | 9 _ _ | _ _ 2
    //-----------------------
    // _ _ _ | 7 _ 9 | 1 8 _
    // _ _ _ | _ _ _ | _ _ _
    // _ 6 2 | 4 _ 5 | _ _ _
    //-----------------------
    // 4 _ _ | _ _ 3 | _ _ _
    // 8 _ _ | _ _ 7 | _ _ 1
    // _ 3 _ | _ _ _ | 4 9 _
    Board(
      Row(None, Some(Eight), Some(Four), None, None, None, None, Some(Six), None),
      Row(Some(Seven), None, None, Some(Five), None, None, None, None, Some(Eight)),
      Row(None, None, None, Some(Nine), None, None, None, None, Some(Two)),

      Row(None, None, None, Some(Seven), None, Some(Nine), Some(One), Some(Eight), None),
      Row(None, None, None, None, None, None, None, None, None),
      Row(None, Some(Six), Some(Two), Some(Four), None, Some(Five), None, None, None),

      Row(Some(Four), None, None, None, None, Some(Three), None, None, None),
      Row(Some(Eight), None, None, None, None, Some(Seven), None, None, Some(One)),
      Row(None, Some(Three), None, None, None, None, Some(Four), Some(Nine), None)
    )
  }

  val sudoku2059hard = {
    // 6 9 8 | _ _ _ | _ _ _
    // 4 _ _ | 5 _ _ | _ 2 _
    // 3 _ _ | 6 _ _ | _ _ _
    //-----------------------
    // 5 6 1 | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | 7 3 9
    //-----------------------
    // _ _ _ | _ _ 4 | _ _ 2
    // _ 3 _ | _ _ 7 | _ _ 6
    // _ _ _ | _ _ _ | 5 9 1
    Board(
      Row(Some(Six), Some(Nine), Some(Eight), None, None, None, None, None, None),
      Row(Some(Four), None, None, Some(Five), None, None, None, Some(Two), None),
      Row(Some(Three), None, None, Some(Six), None, None, None, None, None),

      Row(Some(Five), Some(Six), Some(One), None, None, None, None, None, None),
      Row(None, None, None, None, None, None, None, None, None),
      Row(None, None, None, None, None, None, Some(Seven), Some(Three), Some(Nine)),

      Row(None, None, None, None, None, Some(Four), None, None, Some(Two)),
      Row(None, Some(Three), None, None, None, Some(Seven), None, None, Some(Six)),
      Row(None, None, None, None, None, None, Some(Five), Some(Nine), Some(One))
    )
  }

  "Sudoku" should "calculate graphs" in {
    // 6 9 8 | _ _ _ | _ _ _
    // 4 _ _ | 5 _ _ | _ 2 _
    // 3 _ _ | 6 _ _ | _ _ _
    //-----------------------
    // 5 6 1 | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | 7 3 9
    //-----------------------
    // _ _ _ | _ _ 4 | _ _ 2
    // _ 3 _ | _ _ 7 | _ _ 6
    // _ _ _ | _ _ _ | 5 9 1

    val graph = Sudoku.graph(sudoku2059hard)

    graph.getNode((One, One)).get.value should be(Set(Six))
    graph.getNode((Two, One)).get.value should be(Set(Nine))
    graph.getNode((Three, One)).get.value should be(Set(Eight))
    graph.getNode((Four, One)).get.value should be(Set(One, Two, Three, Four, Seven))
    graph.getNode((Five, One)).get.value should be(Set(One, Two, Three, Four, Seven))
    graph.getNode((Six, One)).get.value should be(Set(One, Two, Three))
    graph.getNode((Seven, One)).get.value should be(Set(One, Three, Four))
    graph.getNode((Eight, One)).get.value should be(Set(One, Four, Five, Seven))
    graph.getNode((Nine, One)).get.value should be(Set(Three, Four, Five, Seven))

    graph.getNode((One, Two)).get.value should be(Set(Four))
    graph.getNode((Two, Two)).get.value should be(Set(One, Seven))
    graph.getNode((Three, Two)).get.value should be(Set(Seven))
    graph.getNode((Four, Two)).get.value should be(Set(Five))
    graph.getNode((Five, Two)).get.value should be(Set(One, Three, Seven, Eight, Nine))
    graph.getNode((Six, Two)).get.value should be(Set(One, Three, Eight, Nine))
    graph.getNode((Seven, Two)).get.value should be(Set(One, Three, Six, Eight, Nine))
    graph.getNode((Eight, Two)).get.value should be(Set(Two))
    graph.getNode((Nine, Two)).get.value should be(Set(Three, Seven, Eight))

    // TODO: Finish...

    Sudoku.board(graph) should be(
      Board(
        sudoku2059hard.first,
        Row(Some(Four), None, Some(Seven), Some(Five), None, None, None, Some(Two), None),
        sudoku2059hard.third,
        sudoku2059hard.fourth,
        sudoku2059hard.fifth,
        sudoku2059hard.sixth,
        sudoku2059hard.seventh,
        sudoku2059hard.eighth,
        sudoku2059hard.ninth
      )
    )
  }

  it should "solve sudoku2054easy" in {
    val solution = Sudoku.solve(sudoku2054easy)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(
      Board(
        Row(Some(Three), Some(Two), Some(Seven), Some(Five), Some(Nine), Some(Four), Some(Eight), Some(Six), Some(One)),
        Row(Some(Four), Some(Nine), Some(Five), Some(Six), Some(Eight), Some(One), Some(Two), Some(Three), Some(Seven)),
        Row(Some(Eight), Some(One), Some(Six), Some(Two), Some(Seven), Some(Three), Some(Four), Some(Nine), Some(Five)),
        Row(Some(Nine), Some(Three), Some(Four), Some(Seven), Some(One), Some(Eight), Some(Five), Some(Two), Some(Six)),
        Row(Some(Five), Some(Seven), Some(One), Some(Four), Some(Six), Some(Two), Some(Nine), Some(Eight), Some(Three)),
        Row(Some(Two), Some(Six), Some(Eight), Some(Nine), Some(Three), Some(Five), Some(Seven), Some(One), Some(Four)),
        Row(Some(Seven), Some(Four), Some(Nine), Some(One), Some(Two), Some(Six), Some(Three), Some(Five), Some(Eight)),
        Row(Some(Six), Some(Five), Some(Three), Some(Eight), Some(Four), Some(Nine), Some(One), Some(Seven), Some(Two)),
        Row(Some(One), Some(Eight), Some(Two), Some(Three), Some(Five), Some(Seven), Some(Six), Some(Four), Some(Nine))
      )
    )
  }

  it should "solve sudoku2055medium" in {
    val solution = Sudoku.solve(sudoku2055medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(
      Board(
        Row(Some(Four), Some(Six), Some(Seven), Some(Two), Some(Five), Some(Eight), Some(Nine), Some(One), Some(Three)),
        Row(Some(One), Some(Two), Some(Five), Some(Nine), Some(Seven), Some(Three), Some(Six), Some(Eight), Some(Four)),
        Row(Some(Nine), Some(Three), Some(Eight), Some(One), Some(Four), Some(Six), Some(Five), Some(Two), Some(Seven)),
        Row(Some(Five), Some(One), Some(Three), Some(Seven), Some(Two), Some(Four), Some(Eight), Some(Nine), Some(Six)),
        Row(Some(Two), Some(Nine), Some(Four), Some(Eight), Some(Six), Some(One), Some(Three), Some(Seven), Some(Five)),
        Row(Some(Seven), Some(Eight), Some(Six), Some(Five), Some(Three), Some(Nine), Some(Two), Some(Four), Some(One)),
        Row(Some(Six), Some(Four), Some(Two), Some(Three), Some(Eight), Some(Seven), Some(One), Some(Five), Some(Nine)),
        Row(Some(Three), Some(Five), Some(One), Some(Four), Some(Nine), Some(Two), Some(Seven), Some(Six), Some(Eight)),
        Row(Some(Eight), Some(Seven), Some(Nine), Some(Six), Some(One), Some(Five), Some(Four), Some(Three), Some(Two))
      )
    )
  }

  it should "solve sudoku2056medium" in {
    val solution = Sudoku.solve(sudoku2056medium)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(
      Board(
        Row(Some(One), Some(Two), Some(Seven), Some(Four), Some(Eight), Some(Five), Some(Nine), Some(Six), Some(Three)),
        Row(Some(Nine), Some(Four), Some(Eight), Some(Six), Some(One), Some(Three), Some(Seven), Some(Two), Some(Five)),
        Row(Some(Three), Some(Five), Some(Six), Some(Nine), Some(Seven), Some(Two), Some(One), Some(Four), Some(Eight)),
        Row(Some(Eight), Some(Nine), Some(Four), Some(One), Some(Five), Some(Seven), Some(Six), Some(Three), Some(Two)),
        Row(Some(Six), Some(Seven), Some(Two), Some(Three), Some(Nine), Some(Four), Some(Five), Some(Eight), Some(One)),
        Row(Some(Five), Some(Three), Some(One), Some(Two), Some(Six), Some(Eight), Some(Four), Some(Seven), Some(Nine)),
        Row(Some(Seven), Some(Eight), Some(Three), Some(Five), Some(Four), Some(One), Some(Two), Some(Nine), Some(Six)),
        Row(Some(Two), Some(One), Some(Nine), Some(Seven), Some(Three), Some(Six), Some(Eight), Some(Five), Some(Four)),
        Row(Some(Four), Some(Six), Some(Five), Some(Eight), Some(Two), Some(Nine), Some(Three), Some(One), Some(Seven))
      )
    )
  }

  it should "solve sudoku2057hard" in {
    val solution = Sudoku.solve(sudoku2057hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(
      Board(
        Row(Some(Two), Some(Eight), Some(Nine), Some(Five), Some(Three), Some(Six), Some(Seven), Some(One), Some(Four)),
        Row(Some(Seven), Some(Five), Some(Four), Some(Eight), Some(Nine), Some(One), Some(Three), Some(Two), Some(Six)),
        Row(Some(Three), Some(Six), Some(One), Some(Seven), Some(Two), Some(Four), Some(Nine), Some(Five), Some(Eight)),
        Row(Some(Eight), Some(Four), Some(Seven), Some(Six), Some(Five), Some(Two), Some(One), Some(Nine), Some(Three)),
        Row(Some(Six), Some(Nine), Some(Three), Some(One), Some(Four), Some(Seven), Some(Two), Some(Eight), Some(Five)),
        Row(Some(Five), Some(One), Some(Two), Some(Three), Some(Eight), Some(Nine), Some(Six), Some(Four), Some(Seven)),
        Row(Some(Four), Some(Two), Some(Eight), Some(Nine), Some(Six), Some(Three), Some(Five), Some(Seven), Some(One)),
        Row(Some(One), Some(Three), Some(Five), Some(Two), Some(Seven), Some(Eight), Some(Four), Some(Six), Some(Nine)),
        Row(Some(Nine), Some(Seven), Some(Six), Some(Four), Some(One), Some(Five), Some(Eight), Some(Three), Some(Two))
      )
    )
  }

  it should "solve sudoku2058hard" in {
    val solution = Sudoku.solve(sudoku2058hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(
      Board(
        Row(Some(Three), Some(Eight), Some(Four), Some(One), Some(Seven), Some(Two), Some(Five), Some(Six), Some(Nine)),
        Row(Some(Seven), Some(Two), Some(Nine), Some(Five), Some(Six), Some(Four), Some(Three), Some(One), Some(Eight)),
        Row(Some(Six), Some(Five), Some(One), Some(Nine), Some(Three), Some(Eight), Some(Seven), Some(Four), Some(Two)),
        Row(Some(Five), Some(Four), Some(Three), Some(Seven), Some(Two), Some(Nine), Some(One), Some(Eight), Some(Six)),
        Row(Some(Nine), Some(Seven), Some(Eight), Some(Three), Some(One), Some(Six), Some(Two), Some(Five), Some(Four)),
        Row(Some(One), Some(Six), Some(Two), Some(Four), Some(Eight), Some(Five), Some(Nine), Some(Seven), Some(Three)),
        Row(Some(Four), Some(One), Some(Seven), Some(Six), Some(Nine), Some(Three), Some(Eight), Some(Two), Some(Five)),
        Row(Some(Eight), Some(Nine), Some(Five), Some(Two), Some(Four), Some(Seven), Some(Six), Some(Three), Some(One)),
        Row(Some(Two), Some(Three), Some(Six), Some(Eight), Some(Five), Some(One), Some(Four), Some(Nine), Some(Seven))
      )
    )
  }

  it should "solve sudoku2059hard" in {
    val solution = Sudoku.solve(sudoku2059hard)

    solution should be('defined)
    solution.get.valid should be(true)
    solution.get.solved should be(true)

    solution.get should be(
      Board(
        Row(Some(Six), Some(Nine), Some(Eight), Some(Seven), Some(Two), Some(One), Some(Four), Some(Five), Some(Three)),
        Row(Some(Four), Some(One), Some(Seven), Some(Five), Some(Nine), Some(Three), Some(Six), Some(Two), Some(Eight)),
        Row(Some(Three), Some(Two), Some(Five), Some(Six), Some(Four), Some(Eight), Some(Nine), Some(One), Some(Seven)),
        Row(Some(Five), Some(Six), Some(One), Some(Three), Some(Seven), Some(Nine), Some(Two), Some(Eight), Some(Four)),
        Row(Some(Nine), Some(Seven), Some(Three), Some(Four), Some(Eight), Some(Two), Some(One), Some(Six), Some(Five)),
        Row(Some(Two), Some(Eight), Some(Four), Some(One), Some(Six), Some(Five), Some(Seven), Some(Three), Some(Nine)),
        Row(Some(Eight), Some(Five), Some(Six), Some(Nine), Some(One), Some(Four), Some(Three), Some(Seven), Some(Two)),
        Row(Some(One), Some(Three), Some(Nine), Some(Two), Some(Five), Some(Seven), Some(Eight), Some(Four), Some(Six)),
        Row(Some(Seven), Some(Four), Some(Two), Some(Eight), Some(Three), Some(Six), Some(Five), Some(Nine), Some(One))
      )
    )
  }

}
