package com.gu.sudoku

object Main {

  def main(args: Array[String]) {
    val sudoku2060easy = {
      // _ _ 1 | _ 2 _ | _ 3 _
      // _ _ _ | 1 _ _ | 2 _ 7
      // 6 _ _ | _ _ 7 | _ 4 _
      //-----------------------
      // _ 2 _ | 4 _ _ | _ _ 1
      // _ _ 3 | _ 5 _ | 4 _ _
      // 7 _ _ | _ _ 9 | _ 2 _
      //-----------------------
      // _ 4 _ | 6 _ _ | _ _ 2
      // 2 _ 8 | _ _ 4 | _ _ _
      // _ 5 _ | _ 1 _ | 3 _ _
      Board(
        Row(None, None, Some(One), None, Some(Two), None, None, Some(Three), None),
        Row(None, None, None, Some(One), None, None, Some(Two), None, Some(Seven)),
        Row(Some(Six), None, None, None, None, Some(Seven), None, Some(Four), None),

        Row(None, Some(Two), None, Some(Four), None, None, None, None, Some(One)),
        Row(None, None, Some(Three), None, Some(Five), None, Some(Four), None, None),
        Row(Some(Seven), None, None, None, None, Some(Nine), None, Some(Two), None),

        Row(None, Some(Four), None, Some(Six), None, None, None, None, Some(Two)),
        Row(Some(Two), None, Some(Eight), None, None, Some(Four), None, None, None),
        Row(None, Some(Five), None, None, Some(One), None, Some(Three), None, None)
      )
    }

    val solution = Solver.solve(sudoku2060easy)

    println(solution.get)
  }
}
