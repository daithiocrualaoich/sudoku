package dog.woofwoofinc.sudoku

sealed abstract class Difficulty(val description: String) {
  override def toString(): String = description
}

object Easy extends Difficulty("Easy")
object Medium extends Difficulty("Medium")
object Hard extends Difficulty("Hard")
object NotPermitted extends Difficulty("Not Permitted")

object Difficulty {

  def apply(graphColouringProblem: GraphColouringProblem): Difficulty = {
    val diff = difficulty(graphColouringProblem)
    val permitted = isPermitted(graphColouringProblem)

    (diff, permitted) match {
      case (_, false) => NotPermitted
      case (diff, _) if diff > 40 => Hard
      case (diff, _) if diff > 15 => Medium
      case _ => Easy
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
    // TODO: Reintroduce more prescriptive isPermitted test
    // Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacements(graphColouringProblem) exists { _.solved }
    Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSets(graphColouringProblem) exists { _.solved }
  }
}
