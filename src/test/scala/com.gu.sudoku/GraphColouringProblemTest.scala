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

    val graph = sudoku2059hard

    graph(One, One).candidates should be(Set(Six))
    graph(Two, One).candidates should be(Set(Nine))
    graph(Three, One).candidates should be(Set(Eight))
    graph(Four, One).candidates should be(Z_9.all.toSet)
    graph(Five, One).candidates should be(Z_9.all.toSet)
    graph(Six, One).candidates should be(Z_9.all.toSet)
    graph(Seven, One).candidates should be(Z_9.all.toSet)
    graph(Eight, One).candidates should be(Z_9.all.toSet)
    graph(Nine, One).candidates should be(Z_9.all.toSet)

    graph(One, Two).candidates should be(Set(Four))
    graph(Two, Two).candidates should be(Z_9.all.toSet)
    graph(Three, Two).candidates should be(Z_9.all.toSet)
    graph(Four, Two).candidates should be(Set(Five))
    graph(Five, Two).candidates should be(Z_9.all.toSet)
    graph(Six, Two).candidates should be(Z_9.all.toSet)
    graph(Seven, Two).candidates should be(Z_9.all.toSet)
    graph(Eight, Two).candidates should be(Set(Two))
    graph(Nine, Two).candidates should be(Z_9.all.toSet)

    graph(One, Three).candidates should be(Set(Three))
    graph(Two, Three).candidates should be(Z_9.all.toSet)
    graph(Three, Three).candidates should be(Z_9.all.toSet)
    graph(Four, Three).candidates should be(Set(Six))
    graph(Five, Three).candidates should be(Z_9.all.toSet)
    graph(Six, Three).candidates should be(Z_9.all.toSet)
    graph(Seven, Three).candidates should be(Z_9.all.toSet)
    graph(Eight, Three).candidates should be(Z_9.all.toSet)
    graph(Nine, Three).candidates should be(Z_9.all.toSet)

    // TODO: Finish...
  }

  it should "reduce using three element coverings" in {
    val graph = Board("""
       _ _ _ | 4 5 6 | 7 8 9   <<--- Three element covering in first zone. 
	   _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
	  -----------------------
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	  -----------------------
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _
        """).toGraphColouringProblem.eliminateByLatinBlockExclusion

    graph(One, One).candidates should be(Set(One, Two, Three))
    graph(Two, One).candidates should be(Set(One, Two, Three))
    graph(Three, One).candidates should be(Set(One, Two, Three))
    graph(One, Two).candidates should be(Z_9.all.toSet)
    graph(Two, Two).candidates should be(Z_9.all.toSet)
    graph(Three, Two).candidates should be(Z_9.all.toSet)
    graph(One, Three).candidates should be(Z_9.all.toSet)
    graph(Two, Three).candidates should be(Z_9.all.toSet)
    graph(Three, Three).candidates should be(Z_9.all.toSet)

    val reduced = graph.eliminateByThreeElementCoverings

    reduced(One, One).candidates should be(Set(One, Two, Three))
    reduced(Two, One).candidates should be(Set(One, Two, Three))
    reduced(Three, One).candidates should be(Set(One, Two, Three))
    reduced(One, Two).candidates should be(Z_9.all.toSet -- Set(One, Two, Three))
    reduced(Two, Two).candidates should be(Z_9.all.toSet -- Set(One, Two, Three))
    reduced(Three, Two).candidates should be(Z_9.all.toSet -- Set(One, Two, Three))
    reduced(One, Three).candidates should be(Z_9.all.toSet -- Set(One, Two, Three))
    reduced(Two, Three).candidates should be(Z_9.all.toSet -- Set(One, Two, Three))
    reduced(Three, Three).candidates should be(Z_9.all.toSet -- Set(One, Two, Three))
  }
}
