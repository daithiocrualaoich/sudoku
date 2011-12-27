package com.gu.sudoku

trait TestBoards {

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

  val sudoku2054easy_solution = {
    // 3 2 7 | 5 9 4 | 8 6 1 
    // 4 9 5 | 6 8 1 | 2 3 7 
    // 8 1 6 | 2 7 3 | 4 9 5 
    //-----------------------
    // 9 3 4 | 7 1 8 | 5 2 6 
    // 5 7 1 | 4 6 2 | 9 8 3 
    // 2 6 8 | 9 3 5 | 7 1 4 
    //-----------------------
    // 7 4 9 | 1 2 6 | 3 5 8 
    // 6 5 3 | 8 4 9 | 1 7 2 
    // 1 8 2 | 3 5 7 | 6 4 9 
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

  val sudoku2055medium_solution = {
    // 4 6 7 | 2 5 8 | 9 1 3 
    // 1 2 5 | 9 7 3 | 6 8 4 
    // 9 3 8 | 1 4 6 | 5 2 7 
    //-----------------------
    // 5 1 3 | 7 2 4 | 8 9 6 
    // 2 9 4 | 8 6 1 | 3 7 5 
    // 7 8 6 | 5 3 9 | 2 4 1 
    //-----------------------
    // 6 4 2 | 3 8 7 | 1 5 9 
    // 3 5 1 | 4 9 2 | 7 6 8 
    // 8 7 9 | 6 1 5 | 4 3 2 
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

  val sudoku2056medium_solution = {
    // 1 2 7 | 4 8 5 | 9 6 3 
    // 9 4 8 | 6 1 3 | 7 2 5 
    // 3 5 6 | 9 7 2 | 1 4 8 
    //-----------------------
    // 8 9 4 | 1 5 7 | 6 3 2 
    // 6 7 2 | 3 9 4 | 5 8 1 
    // 5 3 1 | 2 6 8 | 4 7 9 
    //-----------------------
    // 7 8 3 | 5 4 1 | 2 9 6 
    // 2 1 9 | 7 3 6 | 8 5 4 
    // 4 6 5 | 8 2 9 | 3 1 7 
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

  val sudoku2057hard_solution = {
    // 2 8 9 | 5 3 6 | 7 1 4 
    // 7 5 4 | 8 9 1 | 3 2 6 
    // 3 6 1 | 7 2 4 | 9 5 8 
    //-----------------------
    // 8 4 7 | 6 5 2 | 1 9 3 
    // 6 9 3 | 1 4 7 | 2 8 5 
    // 5 1 2 | 3 8 9 | 6 4 7 
    //-----------------------
    // 4 2 8 | 9 6 3 | 5 7 1 
    // 1 3 5 | 2 7 8 | 4 6 9 
    // 9 7 6 | 4 1 5 | 8 3 2 
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

  val sudoku2058hard_solution = {
    // 3 8 4 | 1 7 2 | 5 6 9 
    // 7 2 9 | 5 6 4 | 3 1 8 
    // 6 5 1 | 9 3 8 | 7 4 2 
    //-----------------------
    // 5 4 3 | 7 2 9 | 1 8 6 
    // 9 7 8 | 3 1 6 | 2 5 4 
    // 1 6 2 | 4 8 5 | 9 7 3 
    //-----------------------
    // 4 1 7 | 6 9 3 | 8 2 5 
    // 8 9 5 | 2 4 7 | 6 3 1 
    // 2 3 6 | 8 5 1 | 4 9 7 
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

  val sudoku2059hard_solution = {
    // 6 9 8 | 7 2 1 | 4 5 3 
    // 4 1 7 | 5 9 3 | 6 2 8 
    // 3 2 5 | 6 4 8 | 9 1 7 
    //-----------------------
    // 5 6 1 | 3 7 9 | 2 8 4 
    // 9 7 3 | 4 8 2 | 1 6 5 
    // 2 8 4 | 1 6 5 | 7 3 9 
    //-----------------------
    // 8 5 6 | 9 1 4 | 3 7 2 
    // 1 3 9 | 2 5 7 | 8 4 6 
    // 7 4 2 | 8 3 6 | 5 9 1 
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
  }

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

  val sudoku2060easy_solution = {
    // 4 7 1 | 9 2 5 | 6 3 8 
    // 3 8 5 | 1 4 6 | 2 9 7 
    // 6 9 2 | 3 8 7 | 1 4 5 
    //-----------------------
    // 5 2 9 | 4 7 3 | 8 6 1 
    // 8 6 3 | 2 5 1 | 4 7 9 
    // 7 1 4 | 8 6 9 | 5 2 3 
    //-----------------------
    // 1 4 7 | 6 3 8 | 9 5 2 
    // 2 3 8 | 5 9 4 | 7 1 6 
    // 9 5 6 | 7 1 2 | 3 8 4 
    Board(
      Row(Some(Four), Some(Seven), Some(One), Some(Nine), Some(Two), Some(Five), Some(Six), Some(Three), Some(Eight)),
      Row(Some(Three), Some(Eight), Some(Five), Some(One), Some(Four), Some(Six), Some(Two), Some(Nine), Some(Seven)),
      Row(Some(Six), Some(Nine), Some(Two), Some(Three), Some(Eight), Some(Seven), Some(One), Some(Four), Some(Five)),

      Row(Some(Five), Some(Two), Some(Nine), Some(Four), Some(Seven), Some(Three), Some(Eight), Some(Six), Some(One)),
      Row(Some(Eight), Some(Six), Some(Three), Some(Two), Some(Five), Some(One), Some(Four), Some(Seven), Some(Nine)),
      Row(Some(Seven), Some(One), Some(Four), Some(Eight), Some(Six), Some(Nine), Some(Five), Some(Two), Some(Three)),

      Row(Some(One), Some(Four), Some(Seven), Some(Six), Some(Three), Some(Eight), Some(Nine), Some(Five), Some(Two)),
      Row(Some(Two), Some(Three), Some(Eight), Some(Five), Some(Nine), Some(Four), Some(Seven), Some(One), Some(Six)),
      Row(Some(Nine), Some(Five), Some(Six), Some(Seven), Some(One), Some(Two), Some(Three), Some(Eight), Some(Four))
    )
  }

  val sudoku2061medium = {
    // _ _ 5 | 4 _ _ | _ _ _
    // _ 7 _ | _ 9 _ | _ 6 _
    // 3 _ _ | _ _ 6 | _ _ 1
    //-----------------------
    // 2 _ _ | _ _ 3 | _ _ 6
    // _ 9 _ | _ 2 _ | _ 8 _
    // 6 _ _ | 5 _ _ | _ _ 3
    //-----------------------
    // 5 _ _ | 1 _ _ | _ _ 8
    // _ 8 _ | _ 4 _ | _ 5 _
    // _ _ _ | _ _ 9 | 6 _ _
    Board(
      Row(None, None, Some(Five), Some(Four), None, None, None, None, None),
      Row(None, Some(Seven), None, None, Some(Nine), None, None, Some(Six), None),
      Row(Some(Three), None, None, None, None, Some(Six), None, None, Some(One)),

      Row(Some(Two), None, None, None, None, Some(Three), None, None, Some(Six)),
      Row(None, Some(Nine), None, None, Some(Two), None, None, Some(Eight), None),
      Row(Some(Six), None, None, Some(Five), None, None, None, None, Some(Three)),

      Row(Some(Five), None, None, Some(One), None, None, None, None, Some(Eight)),
      Row(None, Some(Eight), None, None, Some(Four), None, None, Some(Five), None),
      Row(None, None, None, None, None, Some(Nine), Some(Six), None, None)
    )
  }

  val sudoku2061medium_solution = {
    // 9 6 5 | 4 3 1 | 8 7 2 
    // 8 7 1 | 2 9 5 | 3 6 4 
    // 3 2 4 | 7 8 6 | 5 9 1 
    //-----------------------
    // 2 5 8 | 9 7 3 | 4 1 6 
    // 1 9 3 | 6 2 4 | 7 8 5 
    // 6 4 7 | 5 1 8 | 9 2 3 
    //-----------------------
    // 5 3 9 | 1 6 7 | 2 4 8 
    // 7 8 6 | 3 4 2 | 1 5 9 
    // 4 1 2 | 8 5 9 | 6 3 7 
    Board(
      Row(Some(Nine), Some(Six), Some(Five), Some(Four), Some(Three), Some(One), Some(Eight), Some(Seven), Some(Two)),
      Row(Some(Eight), Some(Seven), Some(One), Some(Two), Some(Nine), Some(Five), Some(Three), Some(Six), Some(Four)),
      Row(Some(Three), Some(Two), Some(Four), Some(Seven), Some(Eight), Some(Six), Some(Five), Some(Nine), Some(One)),

      Row(Some(Two), Some(Five), Some(Eight), Some(Nine), Some(Seven), Some(Three), Some(Four), Some(One), Some(Six)),
      Row(Some(One), Some(Nine), Some(Three), Some(Six), Some(Two), Some(Four), Some(Seven), Some(Eight), Some(Five)),
      Row(Some(Six), Some(Four), Some(Seven), Some(Five), Some(One), Some(Eight), Some(Nine), Some(Two), Some(Three)),

      Row(Some(Five), Some(Three), Some(Nine), Some(One), Some(Six), Some(Seven), Some(Two), Some(Four), Some(Eight)),
      Row(Some(Seven), Some(Eight), Some(Six), Some(Three), Some(Four), Some(Two), Some(One), Some(Five), Some(Nine)),
      Row(Some(Four), Some(One), Some(Two), Some(Eight), Some(Five), Some(Nine), Some(Six), Some(Three), Some(Seven))
    )
  }

  val sudoku2062medium = {
    // 1 _ 6 | _ _ 3 | _ _ _
    // _ _ _ | 8 _ _ | _ _ 4
    // 7 _ _ | _ 1 _ | _ 5 _
    //-----------------------
    // _ 6 _ | _ _ _ | 1 _ _
    // 9 _ _ | 4 _ 2 | _ _ 7
    // _ _ 7 | _ _ _ | _ 4 _
    //-----------------------
    // _ 9 _ | _ 4 _ | _ _ 8
    // 3 _ _ | _ _ 9 | _ _ _
    // _ _ _ | 1 _ _ | 7 _ 6
    Board(
      Row(Some(One), None, Some(Six), None, None, Some(Three), None, None, None),
      Row(None, None, None, Some(Eight), None, None, None, None, Some(Four)),
      Row(Some(Seven), None, None, None, Some(One), None, None, Some(Five), None),

      Row(None, Some(Six), None, None, None, None, Some(One), None, None),
      Row(Some(Nine), None, None, Some(Four), None, Some(Two), None, None, Some(Seven)),
      Row(None, None, Some(Seven), None, None, None, None, Some(Four), None),

      Row(None, Some(Nine), None, None, Some(Four), None, None, None, Some(Eight)),
      Row(Some(Three), None, None, None, None, Some(Nine), None, None, None),
      Row(None, None, None, Some(One), None, None, Some(Seven), None, Some(Six))
    )
  }

  val sudoku2062medium_solution = {
    // 1 4 6 | 5 2 3 | 8 7 9 
    // 5 3 9 | 8 7 6 | 2 1 4 
    // 7 8 2 | 9 1 4 | 6 5 3 
    //-----------------------
    // 4 6 3 | 7 9 5 | 1 8 2 
    // 9 1 5 | 4 8 2 | 3 6 7 
    // 8 2 7 | 3 6 1 | 9 4 5 
    //-----------------------
    // 6 9 1 | 2 4 7 | 5 3 8 
    // 3 7 8 | 6 5 9 | 4 2 1 
    // 2 5 4 | 1 3 8 | 7 9 6 
    Board(
      Row(Some(One), Some(Four), Some(Six), Some(Five), Some(Two), Some(Three), Some(Eight), Some(Seven), Some(Nine)),
      Row(Some(Five), Some(Three), Some(Nine), Some(Eight), Some(Seven), Some(Six), Some(Two), Some(One), Some(Four)),
      Row(Some(Seven), Some(Eight), Some(Two), Some(Nine), Some(One), Some(Four), Some(Six), Some(Five), Some(Three)),

      Row(Some(Four), Some(Six), Some(Three), Some(Seven), Some(Nine), Some(Five), Some(One), Some(Eight), Some(Two)),
      Row(Some(Nine), Some(One), Some(Five), Some(Four), Some(Eight), Some(Two), Some(Three), Some(Six), Some(Seven)),
      Row(Some(Eight), Some(Two), Some(Seven), Some(Three), Some(Six), Some(One), Some(Nine), Some(Four), Some(Five)),

      Row(Some(Six), Some(Nine), Some(One), Some(Two), Some(Four), Some(Seven), Some(Five), Some(Three), Some(Eight)),
      Row(Some(Three), Some(Seven), Some(Eight), Some(Six), Some(Five), Some(Nine), Some(Four), Some(Two), Some(One)),
      Row(Some(Two), Some(Five), Some(Four), Some(One), Some(Three), Some(Eight), Some(Seven), Some(Nine), Some(Six))
    )
  }

  val sudoku2063hard = {
    // _ 2 _ | _ 4 _ | _ 5 _
    // 8 _ _ | _ 7 _ | _ _ 6
    // _ _ _ | 3 _ 9 | _ _ _
    //-----------------------
    // _ _ 8 | _ _ _ | 1 _ _
    // 3 6 _ | _ _ _ | _ 4 8
    // _ _ 9 | _ _ _ | 2 _ _
    //-----------------------
    // _ _ _ | 8 _ 7 | _ _ _
    // 9 _ _ | _ 2 _ | _ _ 5
    // _ 5 _ | _ 6 _ | _ 7 _
    Board(
      Row(None, Some(Two), None, None, Some(Four), None, None, Some(Five), None),
      Row(Some(Eight), None, None, None, Some(Seven), None, None, None, Some(Six)),
      Row(None, None, None, Some(Three), None, Some(Nine), None, None, None),

      Row(None, None, Some(Eight), None, None, None, Some(One), None, None),
      Row(Some(Three), Some(Six), None, None, None, None, None, Some(Four), Some(Eight)),
      Row(None, None, Some(Nine), None, None, None, Some(Two), None, None),

      Row(None, None, None, Some(Eight), None, Some(Seven), None, None, None),
      Row(Some(Nine), None, None, None, Some(Two), None, None, None, Some(Five)),
      Row(None, Some(Five), None, None, Some(Six), None, None, Some(Seven), None)
    )
  }

  val sudoku2063hard_solution = {
    // 1 2 3 | 6 4 8 | 7 5 9 
    // 8 9 4 | 5 7 2 | 3 1 6 
    // 6 7 5 | 3 1 9 | 4 8 2 
    //-----------------------
    // 5 4 8 | 2 3 6 | 1 9 7 
    // 3 6 2 | 7 9 1 | 5 4 8 
    // 7 1 9 | 4 8 5 | 2 6 3 
    //-----------------------
    // 4 3 6 | 8 5 7 | 9 2 1 
    // 9 8 7 | 1 2 4 | 6 3 5 
    // 2 5 1 | 9 6 3 | 8 7 4 
    Board(
      Row(Some(One), Some(Two), Some(Three), Some(Six), Some(Four), Some(Eight), Some(Seven), Some(Five), Some(Nine)),
      Row(Some(Eight), Some(Nine), Some(Four), Some(Five), Some(Seven), Some(Two), Some(Three), Some(One), Some(Six)),
      Row(Some(Six), Some(Seven), Some(Five), Some(Three), Some(One), Some(Nine), Some(Four), Some(Eight), Some(Two)),

      Row(Some(Five), Some(Four), Some(Eight), Some(Two), Some(Three), Some(Six), Some(One), Some(Nine), Some(Seven)),
      Row(Some(Three), Some(Six), Some(Two), Some(Seven), Some(Nine), Some(One), Some(Five), Some(Four), Some(Eight)),
      Row(Some(Seven), Some(One), Some(Nine), Some(Four), Some(Eight), Some(Five), Some(Two), Some(Six), Some(Three)),

      Row(Some(Four), Some(Three), Some(Six), Some(Eight), Some(Five), Some(Seven), Some(Nine), Some(Two), Some(One)),
      Row(Some(Nine), Some(Eight), Some(Seven), Some(One), Some(Two), Some(Four), Some(Six), Some(Three), Some(Five)),
      Row(Some(Two), Some(Five), Some(One), Some(Nine), Some(Six), Some(Three), Some(Eight), Some(Seven), Some(Four))
    )
  }

  val sudoku2064hard = {
    // _ _ _ | 4 _ 5 | _ _ 1
    // _ 7 _ | _ _ _ | _ 2 _
    // _ _ _ | 3 _ 8 | _ _ _
    //-----------------------
    // 2 _ 6 | _ _ _ | 1 _ 3
    // _ _ _ | _ _ _ | _ _ _
    // 1 _ 4 | _ _ _ | 7 _ 9
    //-----------------------
    // _ _ _ | 6 _ 4 | _ _ _
    // _ 5 _ | _ _ _ | _ 4 _
    // 3 _ _ | 1 _ 9 | _ _ _
    Board(
      Row(None, None, None, Some(Four), None, Some(Five), None, None, Some(One)),
      Row(None, Some(Seven), None, None, None, None, None, Some(Two), None),
      Row(None, None, None, Some(Three), None, Some(Eight), None, None, None),

      Row(Some(Two), None, Some(Six), None, None, None, Some(One), None, Some(Three)),
      Row(None, None, None, None, None, None, None, None, None),
      Row(Some(One), None, Some(Four), None, None, None, Some(Seven), None, Some(Nine)),

      Row(None, None, None, Some(Six), None, Some(Four), None, None, None),
      Row(None, Some(Five), None, None, None, None, None, Some(Four), None),
      Row(Some(Three), None, None, Some(One), None, Some(Nine), None, None, None)
    )
  }

  val sudoku2064hard_solution = {
    // 9 6 2 | 4 7 5 | 8 3 1 
    // 8 7 3 | 9 1 6 | 5 2 4 
    // 4 1 5 | 3 2 8 | 6 9 7 
    //-----------------------
    // 2 9 6 | 5 4 7 | 1 8 3 
    // 5 3 7 | 8 9 1 | 4 6 2 
    // 1 8 4 | 2 6 3 | 7 5 9 
    //-----------------------
    // 7 2 9 | 6 8 4 | 3 1 5 
    // 6 5 1 | 7 3 2 | 9 4 8 
    // 3 4 8 | 1 5 9 | 2 7 6
    Board(
      Row(Some(Nine), Some(Six), Some(Two), Some(Four), Some(Seven), Some(Five), Some(Eight), Some(Three), Some(One)),
      Row(Some(Eight), Some(Seven), Some(Three), Some(Nine), Some(One), Some(Six), Some(Five), Some(Two), Some(Four)),
      Row(Some(Four), Some(One), Some(Five), Some(Three), Some(Two), Some(Eight), Some(Six), Some(Nine), Some(Seven)),

      Row(Some(Two), Some(Nine), Some(Six), Some(Five), Some(Four), Some(Seven), Some(One), Some(Eight), Some(Three)),
      Row(Some(Five), Some(Three), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Four), Some(Six), Some(Two)),
      Row(Some(One), Some(Eight), Some(Four), Some(Two), Some(Six), Some(Three), Some(Seven), Some(Five), Some(Nine)),

      Row(Some(Seven), Some(Two), Some(Nine), Some(Six), Some(Eight), Some(Four), Some(Three), Some(One), Some(Five)),
      Row(Some(Six), Some(Five), Some(One), Some(Seven), Some(Three), Some(Two), Some(Nine), Some(Four), Some(Eight)),
      Row(Some(Three), Some(Four), Some(Eight), Some(One), Some(Five), Some(Nine), Some(Two), Some(Seven), Some(Six))
    )
  }

}
