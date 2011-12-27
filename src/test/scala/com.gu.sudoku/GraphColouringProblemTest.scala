package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class GraphColouringProblemTest extends FlatSpec with ShouldMatchers with TestBoards {

  "GraphColouringProblem" should "calculate graphs" in {
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

    val graph = GraphColouringProblem(sudoku2059hard)

    graph(One, One) should be(Set(Six))
    graph(Two, One) should be(Set(Nine))
    graph(Three, One) should be(Set(Eight))
    graph(Four, One) should be(Set(One, Two, Three, Four, Seven))
    graph(Five, One) should be(Set(One, Two, Three, Four, Seven))
    graph(Six, One) should be(Set(One, Two, Three))
    graph(Seven, One) should be(Set(One, Three, Four))
    graph(Eight, One) should be(Set(One, Four, Five, Seven))
    graph(Nine, One) should be(Set(Three, Four, Five, Seven))

    graph(One, Two) should be(Set(Four))
    graph(Two, Two) should be(Set(One, Seven))
    graph(Three, Two) should be(Set(Seven))
    graph(Four, Two) should be(Set(Five))
    graph(Five, Two) should be(Set(One, Three, Seven, Eight, Nine))
    graph(Six, Two) should be(Set(One, Three, Eight, Nine))
    graph(Seven, Two) should be(Set(One, Three, Six, Eight, Nine))
    graph(Eight, Two) should be(Set(Two))
    graph(Nine, Two) should be(Set(Three, Seven, Eight))

    graph(One, Three) should be(Set(Three))
    graph(Two, Three) should be(Set(One, Two, Five, Seven))
    graph(Three, Three) should be(Set(Two, Five, Seven))
    graph(Four, Three) should be(Set(Six))
    graph(Five, Three) should be(Set(One, Two, Four, Seven, Eight, Nine))
    graph(Six, Three) should be(Set(One, Two, Eight, Nine))
    graph(Seven, Three) should be(Set(One, Four, Eight, Nine))
    graph(Eight, Three) should be(Set(One, Four, Five, Seven, Eight))
    graph(Nine, Three) should be(Set(Four, Five, Seven, Eight))

    // TODO: Finish...

    graph.toBoard should be(
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

}
