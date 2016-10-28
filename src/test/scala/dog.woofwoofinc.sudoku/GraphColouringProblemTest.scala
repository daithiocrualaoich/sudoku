package dog.woofwoofinc.sudoku

import org.scalatest.{ FlatSpec, Matchers }

class GraphColouringProblemTest extends FlatSpec with Matchers with TestBoards {

  val emptyBoard = Board(
    Row.empty, Row.empty, Row.empty,
    Row.empty, Row.empty, Row.empty,
    Row.empty, Row.empty, Row.empty
  ).toGraphColouringProblem

  val partialBoard = Board("""
     1 _ _ | 2 _ _ | 3 _ _
     _ 4 _ | _ 5 _ | _ 6 _
     _ _ 7 | _ _ 8 | _ _ 9
    -----------------------
     2 _ _ | 3 _ _ | 1 _ _
     _ 5 _ | _ 6 _ | _ 4 _
     _ _ 8 | _ _ 9 | _ _ 7
    -----------------------
     3 _ _ | 1 _ _ | 2 _ _
     _ 6 _ | _ 4 _ | _ 5 _
     _ _ 9 | _ _ 7 | _ _ 8
    """).toGraphColouringProblem

  val fullBoard = Board("""
     1 2 3 | 4 5 6 | 7 8 9
     4 5 6 | 7 8 9 | 1 2 3
     7 8 9 | 1 2 3 | 4 5 6
    -----------------------
     2 3 4 | 5 6 7 | 8 9 1
     5 6 7 | 8 9 1 | 2 3 4
     8 9 1 | 2 3 4 | 5 6 7
    -----------------------
     3 4 5 | 6 7 8 | 9 1 2
     6 7 8 | 9 1 2 | 3 4 5
     9 1 2 | 3 4 5 | 6 7 8
    """).toGraphColouringProblem

  val invalidBoard = {
    // 1 1 _ | 2 _ _ | 3 _ _
    // _ 4 _ | _ 5 _ | _ 6 _
    // _ _ 7 | _ _ 8 | _ _ 9
    //-----------------------
    // 2 _ _ | 3 _ _ | 1 _ _
    // _ 5 _ | _ 6 _ | _ 4 _
    // _ _ 8 | _ _ 9 | _ _ 7
    //-----------------------
    // 3 _ _ | 1 _ _ | 2 _ _
    // _ 6 _ | _ 4 _ | _ 5 _
    // _ _ 9 | _ _ 7 | _ _ 8
    partialBoard.toBoard.copy(
      first = partialBoard.toBoard.first.copy(second = Some(One))
    ).toGraphColouringProblem
  }

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
    graph(Four, One).candidates should be(Z_9.set)
    graph(Five, One).candidates should be(Z_9.set)
    graph(Six, One).candidates should be(Z_9.set)
    graph(Seven, One).candidates should be(Z_9.set)
    graph(Eight, One).candidates should be(Z_9.set)
    graph(Nine, One).candidates should be(Z_9.set)

    graph(One, Two).candidates should be(Set(Four))
    graph(Two, Two).candidates should be(Z_9.set)
    graph(Three, Two).candidates should be(Z_9.set)
    graph(Four, Two).candidates should be(Set(Five))
    graph(Five, Two).candidates should be(Z_9.set)
    graph(Six, Two).candidates should be(Z_9.set)
    graph(Seven, Two).candidates should be(Z_9.set)
    graph(Eight, Two).candidates should be(Set(Two))
    graph(Nine, Two).candidates should be(Z_9.set)

    graph(One, Three).candidates should be(Set(Three))
    graph(Two, Three).candidates should be(Z_9.set)
    graph(Three, Three).candidates should be(Z_9.set)
    graph(Four, Three).candidates should be(Set(Six))
    graph(Five, Three).candidates should be(Z_9.set)
    graph(Six, Three).candidates should be(Z_9.set)
    graph(Seven, Three).candidates should be(Z_9.set)
    graph(Eight, Three).candidates should be(Z_9.set)
    graph(Nine, Three).candidates should be(Z_9.set)

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
    graph(One, Two).candidates should be(Z_9.set)
    graph(Two, Two).candidates should be(Z_9.set)
    graph(Three, Two).candidates should be(Z_9.set)
    graph(One, Three).candidates should be(Z_9.set)
    graph(Two, Three).candidates should be(Z_9.set)
    graph(Three, Three).candidates should be(Z_9.set)

    val reduced = graph.eliminateByThreeElementCoverings

    reduced(One, One).candidates should be(Set(One, Two, Three))
    reduced(Two, One).candidates should be(Set(One, Two, Three))
    reduced(Three, One).candidates should be(Set(One, Two, Three))
    reduced(One, Two).candidates should be(Z_9.set -- Set(One, Two, Three))
    reduced(Two, Two).candidates should be(Z_9.set -- Set(One, Two, Three))
    reduced(Three, Two).candidates should be(Z_9.set -- Set(One, Two, Three))
    reduced(One, Three).candidates should be(Z_9.set -- Set(One, Two, Three))
    reduced(Two, Three).candidates should be(Z_9.set -- Set(One, Two, Three))
    reduced(Three, Three).candidates should be(Z_9.set -- Set(One, Two, Three))
  }

  it should "reduce using latin block single row placements" in {
    val graph = Board("""
       _ _ _ | _ _ _ | _ _ _   <<--- Single placing in first column by row. 
       _ _ _ | 1 _ _ | _ _ _
       _ _ _ | _ _ _ | 1 _ _ 
      -----------------------
       _ 1 _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
      -----------------------
       _ _ 1 | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _
    """).toGraphColouringProblem.eliminateByLatinBlockExclusion

    graph(One, One).candidates should be(Z_9.set)
    graph(Two, One).candidates should be(Z_9.set - One)
    graph(Three, One).candidates should be(Z_9.set - One)
    graph(One, Two).candidates should be(Z_9.set - One)
    graph(Two, Two).candidates should be(Z_9.set - One)
    graph(Three, Two).candidates should be(Z_9.set - One)
    graph(One, Three).candidates should be(Z_9.set - One)
    graph(Two, Three).candidates should be(Z_9.set - One)
    graph(Three, Three).candidates should be(Z_9.set - One)

    val reduced = graph.singleEliminateByLatinBlockSingleRowPlacements

    reduced(One, One).candidates should be(Set(One))
    reduced(Two, One).candidates should be(Z_9.set - One)
    reduced(Three, One).candidates should be(Z_9.set - One)
    reduced(One, Two).candidates should be(Z_9.set - One)
    reduced(Two, Two).candidates should be(Z_9.set - One)
    reduced(Three, Two).candidates should be(Z_9.set - One)
    reduced(One, Three).candidates should be(Z_9.set - One)
    reduced(Two, Three).candidates should be(Z_9.set - One)
    reduced(Three, Three).candidates should be(Z_9.set - One)
  }

  it should "reduce using latin block single column placements" in {
    val graph = Board("""
       _ _ _ | _ _ _ | _ _ _   <<--- Single placing in first column by column. 
       _ _ _ | 1 _ _ | _ _ _
       _ _ _ | _ _ _ | 1 _ _ 
      -----------------------
       _ 1 _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
      -----------------------
       _ _ 1 | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _
    """).toGraphColouringProblem.eliminateByLatinBlockExclusion

    graph(One, One).candidates should be(Z_9.set)
    graph(Two, One).candidates should be(Z_9.set - One)
    graph(Three, One).candidates should be(Z_9.set - One)
    graph(One, Two).candidates should be(Z_9.set - One)
    graph(Two, Two).candidates should be(Z_9.set - One)
    graph(Three, Two).candidates should be(Z_9.set - One)
    graph(One, Three).candidates should be(Z_9.set - One)
    graph(Two, Three).candidates should be(Z_9.set - One)
    graph(Three, Three).candidates should be(Z_9.set - One)

    val reduced = graph.singleEliminateByLatinBlockSingleColumnPlacements

    reduced(One, One).candidates should be(Set(One))
    reduced(Two, One).candidates should be(Z_9.set - One)
    reduced(Three, One).candidates should be(Z_9.set - One)
    reduced(One, Two).candidates should be(Z_9.set - One)
    reduced(Two, Two).candidates should be(Z_9.set - One)
    reduced(Three, Two).candidates should be(Z_9.set - One)
    reduced(One, Three).candidates should be(Z_9.set - One)
    reduced(Two, Three).candidates should be(Z_9.set - One)
    reduced(Three, Three).candidates should be(Z_9.set - One)
  }

  it should "reduce using latin block single zone placements" in {
    val graph = Board("""
       _ _ _ | _ _ _ | _ _ _   <<--- Single placing in first column by zone. 
       _ _ _ | 1 _ _ | _ _ _
       _ _ _ | _ _ _ | 1 _ _ 
      -----------------------
       _ 1 _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
      -----------------------
       _ _ 1 | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _
    """).toGraphColouringProblem.eliminateByLatinBlockExclusion

    graph(One, One).candidates should be(Z_9.set)
    graph(Two, One).candidates should be(Z_9.set - One)
    graph(Three, One).candidates should be(Z_9.set - One)
    graph(One, Two).candidates should be(Z_9.set - One)
    graph(Two, Two).candidates should be(Z_9.set - One)
    graph(Three, Two).candidates should be(Z_9.set - One)
    graph(One, Three).candidates should be(Z_9.set - One)
    graph(Two, Three).candidates should be(Z_9.set - One)
    graph(Three, Three).candidates should be(Z_9.set - One)

    val reduced = graph.singleEliminateByLatinBlockSingleZonePlacements

    reduced(One, One).candidates should be(Set(One))
    reduced(Two, One).candidates should be(Z_9.set - One)
    reduced(Three, One).candidates should be(Z_9.set - One)
    reduced(One, Two).candidates should be(Z_9.set - One)
    reduced(Two, Two).candidates should be(Z_9.set - One)
    reduced(Three, Two).candidates should be(Z_9.set - One)
    reduced(One, Three).candidates should be(Z_9.set - One)
    reduced(Two, Three).candidates should be(Z_9.set - One)
    reduced(Three, Three).candidates should be(Z_9.set - One)
  }

  it should "return valid for empty board" in {
    emptyBoard should be('valid)
  }

  it should "return valid for partial board" in {
    partialBoard should be('valid)
  }

  it should "return valid for full board" in {
    fullBoard should be('valid)
  }

  it should "return invalid for invalid board" in {
    invalidBoard should not be 'valid
  }

  it should "return unsolved for empty board" in {
    emptyBoard should not be 'solved
  }

  it should "return unsolved for partial board" in {
    partialBoard should not be 'solved
  }

  it should "return solved for full board" in {
    fullBoard should be('solved)
  }

  it should "return unsolved for invalid board" in {
    invalidBoard should not be 'solved
  }
}
