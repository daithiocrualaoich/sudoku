package com.gu.sudoku

object Solver {

  private def iterateReduceByElementCandidateSets(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByElementCandidateSets() }
  }

  private def iterateReduceByOnlyPossibleZonePlacings(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByOnlyPossiblePlacings(includeRows = false, includeColumns = false, includeZones = true) }
  }

  private def iterateReduceByOnlyPossiblePlacings(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByOnlyPossiblePlacings() }
  }

  private def iterateReduceByTwoElementCoverings(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByTwoElementCoverings() }
  }

  private def iterateReduceByThreeElementCoverings(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByThreeElementCoverings() }
  }

  private def solveByIterateReduceByElementCandidateSets(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    Some(iterateReduceByElementCandidateSets(puzzle)) filter { _.valid }
  }

  private def solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      (iterateReduceByElementCandidateSets _) andThen iterateReduceByOnlyPossiblePlacings
    }

    Some(solution) filter { _.valid }
  }

  private def solveByIterateReduceByElementCandidateSetsAndOnlyPossibleZonePlacings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      (iterateReduceByElementCandidateSets _) andThen iterateReduceByOnlyPossibleZonePlacings
    }

    Some(solution) filter { _.valid }
  }

  private def solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacingsAndTwoElementCoverings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      (iterateReduceByElementCandidateSets _) andThen iterateReduceByOnlyPossiblePlacings andThen iterateReduceByTwoElementCoverings
    }

    Some(solution) filter { _.valid }
  }

  private def solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacingsAndTwoAndThreeElementCoverings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      (iterateReduceByElementCandidateSets _) andThen iterateReduceByOnlyPossiblePlacings andThen iterateReduceByTwoElementCoverings andThen iterateReduceByThreeElementCoverings
    }

    Some(solution) filter { _.valid }
  }

  private def solveByReduceBySearch(puzzle: GraphColouringProblem): Iterator[GraphColouringProblem] = {
    puzzle.reduceBySearch() filter { _.valid }
  }

  private def solutions(puzzle: GraphColouringProblem): Iterator[GraphColouringProblem] = {
    val reduction = solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacingsAndTwoAndThreeElementCoverings(puzzle)

    val search = reduction.toIterator flatMap { reduced: GraphColouringProblem =>
      reduced.solved match {
        case true => Iterator(reduced)
        case false => solveByReduceBySearch(reduced) flatMap { solutions }
      }
    }

    search filter { _.solved }
  }

  def solutions(board: Board): Iterator[Board] = solutions(GraphColouringProblem(board)) map { _.toBoard }

  def solve(board: Board): Option[Board] = (solutions(board) take 1).toList.headOption

  def hasUniqueSolution(board: Board): Boolean = (solutions(board) take 2).length == 1

  def isEasy(board: Board): Boolean = {
    board.numValues >= 26
  }

  def isMedium(board: Board): Boolean = {
    !isEasy(board) && (
      solveByIterateReduceByElementCandidateSetsAndOnlyPossibleZonePlacings(GraphColouringProblem(board)) exists { _.solved }
    )
  }

  def isHard(board: Board): Boolean = !isEasy(board) && !isMedium(board) && isPermitted(board)

  def isPermitted(board: Board): Boolean = {
    solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacings(GraphColouringProblem(board)) exists { _.solved }
  }

}