package com.gu.sudoku

import scala.util.Random

object Generator {

  private def generateColouring(graph: GraphColouringProblem): Option[GraphColouringProblem] = {
    graph.solved match {
      case true => Some(graph)
      case _ =>
        // Pick a search and recur
        val choices = graph.reduceBySearch().toList.shuffled.toIterator

        val fast_eliminated_choices = choices map {
          Solver.solveByIterateEliminateByLatinBlockExclusionAndLatinBlockSinglePlacementsAndLatinBlockSinglePlacementSetsAndTwoAndThreeElementCoverings
        } collect {
          case Some(choice) => choice
        }

        val colourings = fast_eliminated_choices map { generateColouring } collect {
          case Some(colouring) => colouring
        }

        colourings.take(1).toList.headOption
    }
  }

  def generateSolvedBoard(): Board = generateColouring(GraphColouringProblem.unconstrained).get.toBoard

  // TODO: Delete following
  private implicit def string2Rotate(s: String) = new {
    def rotate(i: Int): String = (s drop i) ++ (s take i)
    def unrotate(i: Int): String = rotate(s.length - i)
  }

  private def generatePuzzles(board: Board): Iterator[Board] = {
    Difficulty.isPermitted(board) match {
      case false => Iterator()
      case _ =>
        // Remove elements and recur
        val s = board.toString
        val splits = (0 to s.length) map { split => s.rotate(split).replaceFirst("\\d", "_").unrotate(split) }
        val choices = splits.toSet.toList.shuffled.toIterator

        Iterator(board) ++ (choices map { Board(_) } flatMap { generatePuzzles })
    }
  }

  def generatePuzzleBoards(): Iterator[Board] = generatePuzzles(generateSolvedBoard())

}