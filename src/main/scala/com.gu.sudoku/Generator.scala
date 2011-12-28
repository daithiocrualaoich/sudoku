package com.gu.sudoku

import scala.util.Random

object Generator {

  val initial_board = {
    Board("""
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
      -----------------------
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
      -----------------------
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
    """)
  }

  private def generateColouring(graph: GraphColouringProblem): Option[GraphColouringProblem] = {
    graph.solved match {
      case true => Some(graph)
      case _ =>
        // Pick a search and recur
        val choices = graph.reduceBySearch().toList.shuffle.toIterator

        val fast_eliminated_choices = choices map {
          Solver.solveByIterateReduceByElementCandidateSetsAndOnlyPossiblePlacingsAndTwoAndThreeElementCoverings
        } collect {
          case Some(choice) => choice
        }

        val colourings = fast_eliminated_choices map { generateColouring } collect {
          case Some(colouring) => colouring
        }

        colourings.take(1).toList.headOption
    }
  }

  def generateSolvedBoard(): Board = generateColouring(GraphColouringProblem(initial_board)).get.toBoard

  private def generatePuzzles(board: Board): Iterator[Board] = {
    Solver.isPermitted(board) match {
      case false => Iterator()
      case _ =>
        // Remove elements and recur
        val s = board.toString
        val splits = (0 to s.length) map { split => s.rotate(split).replaceFirst("\\d", "_").unrotate(split) }
        val choices = splits.toSet.toList.shuffle.toIterator

        Iterator(board) ++ (choices map { Board(_) } flatMap { generatePuzzles })
    }
  }

  def generatePuzzleBoards(): Iterator[Board] = generatePuzzles(generateSolvedBoard())

}