package com.gu.sudoku

object Difficulty {

  // TODO: Change to use GraphColouringProblem instead of Board

  def apply(board: Board) = {
    // Difficulty is how many placements need to be filled after five untreated
    // rounds of candidate reduction and zone only possible placings.

    var puzzle = GraphColouringProblem(board)

    for (i <- 1 to 5) {
      puzzle = puzzle.singleEliminateByLatinBlockExclusion().singleEliminateByZoneSinglePlacements()
    }

    81 - puzzle.toBoard.numValues
  }

  def isEasy(board: Board): Boolean = this(board) <= 15 && isPermitted(board)

  def isMedium(board: Board): Boolean = {
    val diff = this(board)
    15 < diff && diff <= 40 && isPermitted(board)
  }

  def isHard(board: Board): Boolean = 40 < this(board) && isPermitted(board)

  def isPermitted(board: Board): Boolean = {
    Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacements(GraphColouringProblem(board)) exists { _.solved }
  }

}

object Easy {
  def unapply(board: Board): Boolean = Difficulty.isEasy(board)
}

object Medium {
  def unapply(board: Board): Boolean = Difficulty.isMedium(board)
}

object Hard {
  def unapply(board: Board): Boolean = Difficulty.isHard(board)
}

object Permitted {
  def unapply(board: Board): Boolean = Difficulty.isPermitted(board)
}
