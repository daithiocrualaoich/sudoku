package com.gu.sudoku

import com.gu.sudoku.GraphColouringProblem._
import scala.util.Random

object Generator {

  def generateColouring(graphColouringProblem: GraphColouringProblem = unconstrained): Option[GraphColouringProblem] = {
    Solver(graphColouringProblem) flatMap { reduced =>
      reduced match {
        case Solved() => Some(reduced)
        case _ =>
          // Pick a search and recur
          val alternatives = graphColouringProblem.reduceBySearch().shuffled

          // TODO: Peek ahead by breath here and reduce by unsolvable?
          val colourings = alternatives map { generateColouring } collect {
            case Some(colouring) => colouring
          }

          colourings.headOption
      }
    }
  }

  def generateEasyPuzzle(
    maxInitialPlacings: Int = 32,
    graphColouringProblem: GraphColouringProblem = generateColouring().get): Option[GraphColouringProblem] = {
    // println("generateEasyPuzzle: (%s, %s)".format(Difficulty.difficulty(graphColouringProblem), graphColouringProblem.numPlacings))

    Difficulty(graphColouringProblem) match {
      case Easy if graphColouringProblem.numPlacings <= maxInitialPlacings =>
        Some(graphColouringProblem)

      case Medium | Hard | NotPermitted => // Either hard or not permitted and further search won't improve
        None

      case _ =>
        // Pick a search and recur
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here and reduce by not easy, sort by difficulty?
        // val fast_eliminated_alternatives = (alternatives.toList sortBy { Difficulty.difficulty }).toIterator
        // val fast_eliminated_alternatives = alternatives filter { alternative =>
        //   Difficulty(alternative) == Easy
        // }

        val puzzles = alternatives map { generateEasyPuzzle(maxInitialPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption

    }
  }

  def generateMediumPuzzle(
    maxInitialPlacings: Int = 28,
    graphColouringProblem: GraphColouringProblem = generateColouring().get): Option[GraphColouringProblem] = {
    // println("generateMediumPuzzle: (%s, %s)".format(Difficulty.difficulty(graphColouringProblem), graphColouringProblem.numPlacings))

    Difficulty(graphColouringProblem) match {
      case Medium if graphColouringProblem.numPlacings <= maxInitialPlacings =>
        Some(graphColouringProblem)

      case Hard | NotPermitted => // Either hard or not permitted and further search won't improve
        None

      case _ => // try to expand to medium
        // Pick a search and recur
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here, preference by medium and reduce by not easy or medium?
        // val fast_eliminated_alternatives = (alternatives.toList sortBy { Difficulty.difficulty }).toIterator
        // val fast_eliminated_alternatives = alternatives filter { alternative =>
        //   Difficulty(alternative) != Hard
        // }

        val puzzles = alternatives map { generateMediumPuzzle(maxInitialPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption
    }
  }

  def generateHardPuzzle(
    maxInitialPlacings: Int = 26,
    graphColouringProblem: GraphColouringProblem = generateColouring().get): Option[GraphColouringProblem] = {
    // println("generateHardPuzzle: (%s, %s)".format(Difficulty.difficulty(graphColouringProblem), graphColouringProblem.numPlacings))

    Difficulty(graphColouringProblem) match {
      case Hard if graphColouringProblem.numPlacings <= maxInitialPlacings =>
        Some(graphColouringProblem)

      case NotPermitted => // Further search won't improve
        None

      case _ => // try to expand to hard
        // Pick a search and recur
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here, preference by hard then medium and reduce by not permitted?
        // val fast_eliminated_alternatives = (alternatives.toList sortBy { Difficulty.difficulty }).toIterator

        val puzzles = alternatives map { generateHardPuzzle(maxInitialPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption
    }
  }

}