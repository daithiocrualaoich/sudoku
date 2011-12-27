package com.gu.sudoku

object Solver {

  def iterateReduceByElementCandidateSets(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByElementCandidateSets() }
  }

  def iterateReduceByOnlyPossiblePlacings(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByOnlyPossiblePlacings() }
  }

  def iterateReduceByTwoElementCoverings(puzzle: GraphColouringProblem): GraphColouringProblem = {
    iterate(puzzle) { puzzle => puzzle.reduceByTwoElementCoverings() }
  }

  def solveByIterateReduceByElementCandidateSets(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    Some(iterateReduceByElementCandidateSets(puzzle)) filter { _.valid }
  }

  def solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      (iterateReduceByElementCandidateSets _) andThen iterateReduceByOnlyPossiblePlacings
    }

    Some(solution) filter { _.valid }
  }

  def solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacingsAndTwoElementCoverings(puzzle: GraphColouringProblem): Option[GraphColouringProblem] = {
    val solution = iterate(puzzle) {
      (iterateReduceByElementCandidateSets _) andThen iterateReduceByOnlyPossiblePlacings andThen iterateReduceByTwoElementCoverings
    }

    Some(solution) filter { _.valid }
  }

  def solve(board: Board): Option[Board] = {
    val puzzle = GraphColouringProblem(board)

    //    val solution = solveByIterateReduceByElementCandidateSets(puzzle)
    val solution = solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacings(puzzle)
    //    val solution = solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacingsAndTwoElementCoverings(puzzle)

    solution map { _.toBoard }
  }
}