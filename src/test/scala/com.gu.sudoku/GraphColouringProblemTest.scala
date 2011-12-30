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
    graph(Four, One) should be(Z_9.all.toSet)
    graph(Five, One) should be(Z_9.all.toSet)
    graph(Six, One) should be(Z_9.all.toSet)
    graph(Seven, One) should be(Z_9.all.toSet)
    graph(Eight, One) should be(Z_9.all.toSet)
    graph(Nine, One) should be(Z_9.all.toSet)

    graph(One, Two) should be(Set(Four))
    graph(Two, Two) should be(Z_9.all.toSet)
    graph(Three, Two) should be(Z_9.all.toSet)
    graph(Four, Two) should be(Set(Five))
    graph(Five, Two) should be(Z_9.all.toSet)
    graph(Six, Two) should be(Z_9.all.toSet)
    graph(Seven, Two) should be(Z_9.all.toSet)
    graph(Eight, Two) should be(Set(Two))
    graph(Nine, Two) should be(Z_9.all.toSet)

    graph(One, Three) should be(Set(Three))
    graph(Two, Three) should be(Z_9.all.toSet)
    graph(Three, Three) should be(Z_9.all.toSet)
    graph(Four, Three) should be(Set(Six))
    graph(Five, Three) should be(Z_9.all.toSet)
    graph(Six, Three) should be(Z_9.all.toSet)
    graph(Seven, Three) should be(Z_9.all.toSet)
    graph(Eight, Three) should be(Z_9.all.toSet)
    graph(Nine, Three) should be(Z_9.all.toSet)

    // TODO: Finish...

    graph.toBoard should be(sudoku2059hard)
  }

}
