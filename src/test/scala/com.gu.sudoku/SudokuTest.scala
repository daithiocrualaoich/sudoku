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
    // _ 3 _ | _ _ 7 | _ 6 _
    // _ _ 6 | _ _ _ | 8 _ _
    Board(
      Row(None, None, Some(Nine), None, None, None, Some(Seven), None, None),
      Row(None, Some(Five), None, None, None, None, None, Some(Two), None),
      Row(Some(Three), None, None, Some(Seven), None, Some(Four), None, None, Some(Eight)),

      Row(None, None, Some(Seven), None, Some(Five), None, Some(One), None, None),
      Row(None, None, None, Some(One), None, Some(Seven), None, None, None),
      Row(None, None, Some(Two), None, Some(Eight), None, Some(Six), None, None),

      Row(Some(Four), None, None, Some(Nine), None, Some(Three), None, None, Some(One)),
      Row(None, Some(Three), None, None, None, Some(Seven), None, Some(Six), None),
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

    graph.getNodes((One, One)).value should be(Set(Six))
    graph.getNodes((Two, One)).value should be(Set(Nine))
    graph.getNodes((Three, One)).value should be(Set(Eight))
    graph.getNodes((Four, One)).value should be(Set(One, Two, Three, Four, Seven))
    graph.getNodes((Five, One)).value should be(Set(One, Two, Three, Four, Seven))
    graph.getNodes((Six, One)).value should be(Set(One, Two, Three))
    graph.getNodes((Seven, One)).value should be(Set(One, Three, Four))
    graph.getNodes((Eight, One)).value should be(Set(One, Four, Five, Seven))
    graph.getNodes((Nine, One)).value should be(Set(Three, Four, Five, Seven))

    graph.getNodes((One, Two)).value should be(Set(Four))
    graph.getNodes((Two, Two)).value should be(Set(One, Seven))
    graph.getNodes((Three, Two)).value should be(Set(Seven))
    graph.getNodes((Four, Two)).value should be(Set(Five))
    graph.getNodes((Five, Two)).value should be(Set(One, Three, Seven, Eight, Nine))
    graph.getNodes((Six, Two)).value should be(Set(One, Three, Eight, Nine))
    graph.getNodes((Seven, Two)).value should be(Set(One, Three, Six, Eight, Nine))
    graph.getNodes((Eight, Two)).value should be(Set(Two))
    graph.getNodes((Nine, Two)).value should be(Set(Three, Seven, Eight))

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
    Sudoku.solve(sudoku2055medium) should be(Some(
      Board(
        sudoku2055medium.first,
        sudoku2055medium.second,
        sudoku2055medium.third,
        sudoku2055medium.fourth,
        sudoku2055medium.fifth,
        sudoku2055medium.sixth,
        sudoku2055medium.seventh,
        sudoku2055medium.eighth,
        sudoku2055medium.ninth
      )
    ))
  }

  it should "solve sudoku2056medium" in {
    Sudoku.solve(sudoku2056medium) should be(Some(
      Board(
        sudoku2056medium.first,
        sudoku2056medium.second,
        sudoku2056medium.third,
        sudoku2056medium.fourth,
        sudoku2056medium.fifth,
        sudoku2056medium.sixth,
        sudoku2056medium.seventh,
        sudoku2056medium.eighth,
        sudoku2056medium.ninth
      )
    ))
  }

  it should "solve sudoku2057hard" in {
    Sudoku.solve(sudoku2057hard) should be(Some(
      Board(
        sudoku2057hard.first,
        sudoku2057hard.second,
        Row(Some(Three), None, Some(One), Some(Seven), None, Some(Four), None, None, Some(Eight)),
        sudoku2057hard.fourth,
        sudoku2057hard.fifth,
        Row(None, None, Some(Two), None, Some(Eight), Some(Nine), Some(Six), None, None),
        sudoku2057hard.seventh,
        sudoku2057hard.eighth,
        sudoku2057hard.ninth
      )
    ))
  }

  it should "solve sudoku2058hard" in {
    Sudoku.solve(sudoku2058hard) should be(Some(
      Board(
        sudoku2058hard.first,
        sudoku2058hard.second,
        sudoku2058hard.third,
        sudoku2058hard.fourth,
        sudoku2058hard.fifth,
        sudoku2058hard.sixth,
        sudoku2058hard.seventh,
        sudoku2058hard.eighth,
        sudoku2058hard.ninth
      )
    ))
  }

  it should "solve sudoku2059hard" in {
    Sudoku.solve(sudoku2059hard) should be(Some(
      Board(
        sudoku2059hard.first,
        Row(Some(Four), Some(One), Some(Seven), Some(Five), None, None, None, Some(Two), None),
        sudoku2059hard.third,
        sudoku2059hard.fourth,
        sudoku2059hard.fifth,
        sudoku2059hard.sixth,
        sudoku2059hard.seventh,
        sudoku2059hard.eighth,
        sudoku2059hard.ninth
      )
    ))
  }

}
