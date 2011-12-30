package com.gu.sudoku

object Difficulty {

  def difficulty(board: Board) = {
    // Difficulty is how many placements need to be filled after five untreated rounds of candidate reduction and zone only possible placings.
    var puzzle = GraphColouringProblem(board)

    (1 to 5).toList foreach { _ =>
      puzzle = puzzle.singleEliminateByLatinBlockExclusion().singleEliminateByZoneSinglePlacements()
    }

    81 - puzzle.toBoard.numValues
  }

  def isEasy(board: Board): Boolean = difficulty(board) <= 20 && isPermitted(board)

  def isMedium(board: Board): Boolean = {
    val diff = difficulty(board)
    20 < diff && diff <= 40 && isPermitted(board)
  }

  def isHard(board: Board): Boolean = 40 < difficulty(board) && isPermitted(board)

  def isPermitted(board: Board): Boolean = {
    Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacements(GraphColouringProblem(board)) exists { _.solved }
  }

}