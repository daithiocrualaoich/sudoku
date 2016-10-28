package dog.woofwoofinc.sudoku

import dog.woofwoofinc.sudoku.GraphColouringProblem._

object Generator {

  def generateColouring(graphColouringProblem: GraphColouringProblem = unconstrained): Option[GraphColouringProblem] = {
    val reduced = Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSetsAndTwoAndThreeElementCoverings(graphColouringProblem)
    reduced flatMap {
      case Solved(solved) => Some(solved)
      case unsolved =>
        val alternatives = unsolved.reduceBySearch().shuffled

        // TODO: Peek ahead by breath here and reduce by unsolvable?
        val colourings = alternatives map { generateColouring } collect {
          case Some(colouring) => colouring
        }

        colourings.headOption
    }
  }

  def generateEasyPuzzle(
    maxPlacings: Int = 32,
    graphColouringProblem: GraphColouringProblem = generateColouring().get
  ): Option[GraphColouringProblem] = {
    // println("generateEasyPuzzle: (%s, %s)".format(Difficulty.difficulty(graphColouringProblem), graphColouringProblem.numPlacings))

    Difficulty(graphColouringProblem) match {
      case Easy if graphColouringProblem.numPlacings <= maxPlacings =>
        Some(graphColouringProblem)

      case Medium | Hard | NotPermitted =>
        None

      case _ =>
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here and reduce by not easy, sort by difficulty?
        // val fast_eliminated_alternatives = (alternatives.toList sortBy { Difficulty.difficulty }).toIterator
        // val fast_eliminated_alternatives = alternatives filter { alternative =>
        //   Difficulty(alternative) == Easy
        // }

        val puzzles = alternatives map { generateEasyPuzzle(maxPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption

    }
  }

  def generateMediumPuzzle(
    maxPlacings: Int = 28,
    graphColouringProblem: GraphColouringProblem = generateColouring().get
  ): Option[GraphColouringProblem] = {
    // println("generateMediumPuzzle: (%s, %s)".format(Difficulty.difficulty(graphColouringProblem), graphColouringProblem.numPlacings))

    Difficulty(graphColouringProblem) match {
      case Medium if graphColouringProblem.numPlacings <= maxPlacings =>
        Some(graphColouringProblem)

      case Hard | NotPermitted =>
        None

      case _ =>
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here, preference by medium and reduce by not easy or medium?
        // val fast_eliminated_alternatives = (alternatives.toList sortBy { Difficulty.difficulty }).toIterator
        // val fast_eliminated_alternatives = alternatives filter { alternative =>
        //   Difficulty(alternative) != Hard
        // }

        val puzzles = alternatives map { generateMediumPuzzle(maxPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption
    }
  }

  def generateHardPuzzle(
    maxPlacings: Int = 26,
    graphColouringProblem: GraphColouringProblem = generateColouring().get
  ): Option[GraphColouringProblem] = {
    // println("generateHardPuzzle: (%s, %s)".format(Difficulty.difficulty(graphColouringProblem), graphColouringProblem.numPlacings))

    Difficulty(graphColouringProblem) match {
      case Hard if graphColouringProblem.numPlacings <= maxPlacings =>
        Some(graphColouringProblem)

      case NotPermitted =>
        None

      case _ =>
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here, preference by hard then medium and reduce by not permitted?
        // val fast_eliminated_alternatives = (alternatives.toList sortBy { Difficulty.difficulty }).toIterator

        val puzzles = alternatives map { generateHardPuzzle(maxPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption
    }
  }
}