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
    maxInitialPlacings: Int = 26,
    graphColouringProblem: GraphColouringProblem = generateColouring().get): Option[GraphColouringProblem] = {

    graphColouringProblem match {
      case Easy() =>
        if (graphColouringProblem.numPlacings <= maxInitialPlacings) {
          Some(graphColouringProblem)
        }

        // Pick a search and recur
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here and reduce by not easy?
        val puzzles = alternatives map { generateEasyPuzzle(maxInitialPlacings, _) } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption

      case _ => // Either not easy or not permitted and further search won't improve
        None
    }
  }

  def generateMediumPuzzle(
    graphColouringProblem: GraphColouringProblem = generateColouring().get): Option[GraphColouringProblem] = {

    graphColouringProblem match {
      case Medium() => Some(graphColouringProblem)

      case Easy() => // try to expand to medium
        // Pick a search and recur
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here, preference by medium and reduce by not easy or medium?
        val puzzles = alternatives map { generateMediumPuzzle } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption

      case _ => // Either hard or not permitted and further search won't improve
        None
    }
  }

  def generateHardPuzzle(
    graphColouringProblem: GraphColouringProblem = generateColouring().get): Option[GraphColouringProblem] = {

    Difficulty(graphColouringProblem) match {
      case Hard => Some(graphColouringProblem)

      case NotPermitted => // Further search won't improve
        None

      case _ => // try to expand to hard
        // Pick a search and recur
        val alternatives = graphColouringProblem.expandBySearch().shuffled

        // TODO: Peek ahead by breath here, preference by hard then medium and reduce by not permitted?
        val puzzles = alternatives map { generateHardPuzzle } collect {
          case Some(puzzle) => puzzle
        }

        puzzles.headOption
    }
  }

}