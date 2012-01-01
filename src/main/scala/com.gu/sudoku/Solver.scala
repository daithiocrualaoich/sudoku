package com.gu.sudoku

object Solved {
  def unapply(graphColouringProblem: GraphColouringProblem): Boolean = Solver(graphColouringProblem).isDefined
}

object Solver {
  def solveByIterateEliminateByLatinBlockExclusion(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    Some(puzzle.eliminateByLatinBlockExclusion()) filter { _.valid }
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

  def solveByReduceBySearch(puzzle: GraphColouringProblem): Iterator[GraphColouringProblem] = {
    puzzle.reduceBySearch() filter { _.valid }
  }

  def solutions(puzzle: GraphColouringProblem): Iterator[GraphColouringProblem] = {
    val reduction = solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSetsAndTwoAndThreeElementCoverings(puzzle)

    val search = reduction.toIterator flatMap { reduced: GraphColouringProblem =>
      reduced.solved match {
        case true => Iterator(reduced)
        case false => solveByReduceBySearch(reduced) flatMap { solutions }
      }
    }

    search filter { _.solved }
  }

  def hasUniqueSolution(graphColouringProblem: GraphColouringProblem): Boolean = {
    (solutions(graphColouringProblem) take 2).length == 1
  }

  def apply(graphColouringProblem: GraphColouringProblem): Option[GraphColouringProblem] = {
    solutions(graphColouringProblem).headOption
  }
}

