package com.gu.sudoku

import scala.util.Random

object Main extends App {

  def print(board: Board) {
    val difficulty = {
      if (Solver.isEasy(board)) "Easy"
      else if (Solver.isMedium(board)) "Medium"
      else if (Solver.isHard(board)) "Hard"
      else "Undetermined"
    }

    println(board)
    println()
    println("%s" format difficulty)
    println()
    println()
  }

  val targetNumValues = 26 + Random.nextInt(7)
  val easy = (Generator.generatePuzzleBoards() filter { _.numValues <= targetNumValues } filter { Solver.isEasy }).head

  println(easy)
  println()
  println("Easy")
  println()
  println()

  val medium = (Generator.generatePuzzleBoards() filter { Solver.isMedium }).head

  println(medium)
  println()
  println("Medium")
  println()
  println()

  val hard = (Generator.generatePuzzleBoards() filter { Solver.isHard }).head

  println(hard)
  println()
  println("Hard")
  println()
  println()

}
