package com.gu.sudoku

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class GeneratorTest extends FlatSpec with ShouldMatchers {

  "Generator" should "generate an easy puzzle" in {
    val puzzle = Generator.generateEasyPuzzle()

    puzzle should be('defined)

    Difficulty(puzzle.get) should be(Easy)
    Difficulty.isPermitted(puzzle.get) should be(true)
  }

  //    it should "generate an easy puzzle with <= 26 values" in {
  //    val puzzle = Generator.generateEasyPuzzle(26)
  //  
  //      puzzle should be('defined)
  //  
  //    puzzle should be('defined)
  //
  //    puzzle.get.numPlacings should be <= 26
  //    Difficulty(puzzle.get) should be(Easy)
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //    }
  //  
  //    it should "generate an easy puzzle with <= 30 values" in {
  //    val puzzle = Generator.generateEasyPuzzle(30)
  //  
  //      puzzle should be('defined)
  //  
  //    puzzle should be('defined)
  //
  //    puzzle.get.numPlacings should be <= 30
  //    Difficulty(puzzle.get) should be(Easy)
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //    }
  //  
  //    it should "generate a medium puzzle" in {
  //    val puzzle = Generator.generateMediumPuzzle()
  //
  //    puzzle should be('defined)
  //
  //    Difficulty(puzzle.get) should be(Medium)
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //    }
  //  
  //    it should "generate a hard puzzle" in {
  //    val puzzle = Generator.generateHardPuzzle()
  //
  //    puzzle should be('defined)
  //
  //    Difficulty(puzzle.get) should be(Hard)
  //    Difficulty.isPermitted(puzzle.get) should be(true)
  //    }

}
