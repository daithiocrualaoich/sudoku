package com.gu.sudoku

sealed abstract class Difficulty(val description: String) {
  override def toString(): String = description
}

object Easy extends Difficulty("Easy") {
  def unapply(graphColouringProblem: GraphColouringProblem): Boolean = Difficulty(graphColouringProblem) == Easy
}

object Medium extends Difficulty("Medium") {
  def unapply(graphColouringProblem: GraphColouringProblem): Boolean = Difficulty(graphColouringProblem) == Medium
}

object Hard extends Difficulty("Hard") {
  def unapply(graphColouringProblem: GraphColouringProblem): Boolean = Difficulty(graphColouringProblem) == Hard
}

object NotPermitted extends Difficulty("Not Permitted") {
  def unapply(graphColouringProblem: GraphColouringProblem): Boolean = Difficulty(graphColouringProblem) == NotPermitted
}

object Difficulty {

  def apply(graphColouringProblem: GraphColouringProblem): Difficulty = {
    val diff = difficulty(graphColouringProblem)
    val permitted = isPermitted(graphColouringProblem)

    if (diff <= 15 && permitted) {
      Easy
    } else if (15 < diff && diff <= 40 && permitted) {
      Medium
    } else if (40 < diff && permitted) {
      Hard
    } else {
      NotPermitted
    }
  }

  def difficulty(graphColouringProblem: GraphColouringProblem) = {
    // Difficulty is how many placements need to be filled after five untreated
    // rounds of candidate reduction and zone only possible placings.

    var puzzle = graphColouringProblem

    for (i <- 1 to 5) {
      puzzle = puzzle.singleEliminateByLatinBlockExclusion().singleEliminateByLatinBlockSingleZonePlacements()
    }

    81 - puzzle.numPlacings
  }

  def isPermitted(graphColouringProblem: GraphColouringProblem): Boolean = {
    Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacements(graphColouringProblem) exists { _.solved }
  }

}
