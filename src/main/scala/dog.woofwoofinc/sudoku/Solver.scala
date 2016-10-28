package dog.woofwoofinc.sudoku

object Solved {
  def unapply(graphColouringProblem: GraphColouringProblem): Option[GraphColouringProblem] = {
    if (graphColouringProblem.solved) Some(graphColouringProblem) else None
  }
}

object Solver {
  def solveByIterateEliminateByLatinBlockExclusion(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      _.eliminateByLatinBlockExclusion()
    }

    Some(solution) filter { _.valid }
  }

  def solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacements(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      _.eliminateByLatinBlockExclusion().
        eliminateByLatinBlockSinglePlacements()
    }

    Some(solution) filter { _.valid }
  }

  def solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSets(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      _.eliminateByLatinBlockExclusion().
        eliminateByLatinBlockSinglePlacements().
        eliminateByLatinBlockSinglePlacementSets()
    }

    Some(solution) filter { _.valid }
  }

  def solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSetsAndTwoElementCoverings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      _.eliminateByLatinBlockExclusion().
        eliminateByLatinBlockSinglePlacements().
        eliminateByLatinBlockSinglePlacementSets().
        eliminateByTwoElementCoverings()
    }

    Some(solution) filter { _.valid }
  }

  def solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSetsAndTwoAndThreeElementCoverings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      _.eliminateByLatinBlockExclusion().
        eliminateByLatinBlockSinglePlacements().
        eliminateByLatinBlockSinglePlacementSets().
        eliminateByTwoElementCoverings().
        eliminateByThreeElementCoverings()
    }

    Some(solution) filter { _.valid }
  }

  def apply(puzzle: GraphColouringProblem): Iterator[GraphColouringProblem] = {
    val reduced = solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSetsAndTwoAndThreeElementCoverings(puzzle)

    reduced.toIterator flatMap {
      case Solved(solved) => Iterator(solved)
      case unsolved =>
        // TODO: Peek ahead by breath here and reduce by unsolvable?
        unsolved.reduceBySearch() filter { _.valid } flatMap { apply }
    }
  }
}
