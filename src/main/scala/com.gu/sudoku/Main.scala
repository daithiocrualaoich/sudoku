package com.gu.sudoku

import com.gu.pdf.PDFTranscoder
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Dimension
import java.awt.geom.Line2D
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.dom.GenericDOMImplementation
import scala.util.Random

object Main extends App {

  val graphics = {
    val dom = GenericDOMImplementation.getDOMImplementation
    val document = dom.createDocument("http://www.w3.org/2000/svg", "svg", null)

    new SVGGraphics2D(document)
  }

  def print(board: Board) {
    val difficulty = {
      if (Solver.isEasy(board)) "Easy"
      else if (Solver.isMedium(board)) "Medium"
      else if (Solver.isHard(board)) "Hard"
      else "Undetermined"
    }

    graphics.setPaint(Color.lightGray)
    graphics.draw(new Line2D.Float(1 * scale / 9, 0, 1 * scale / 9, scale))
    graphics.draw(new Line2D.Float(2 * scale / 9, 0, 2 * scale / 9, scale))
    graphics.draw(new Line2D.Float(4 * scale / 9, 0, 4 * scale / 9, scale))
    graphics.draw(new Line2D.Float(5 * scale / 9, 0, 5 * scale / 9, scale))
    graphics.draw(new Line2D.Float(7 * scale / 9, 0, 7 * scale / 9, scale))
    graphics.draw(new Line2D.Float(8 * scale / 9, 0, 8 * scale / 9, scale))

    graphics.draw(new Line2D.Float(0, 1 * scale / 9, scale, 1 * scale / 9))
    graphics.draw(new Line2D.Float(0, 2 * scale / 9, scale, 2 * scale / 9))
    graphics.draw(new Line2D.Float(0, 4 * scale / 9, scale, 4 * scale / 9))
    graphics.draw(new Line2D.Float(0, 5 * scale / 9, scale, 5 * scale / 9))
    graphics.draw(new Line2D.Float(0, 7 * scale / 9, scale, 7 * scale / 9))
    graphics.draw(new Line2D.Float(0, 8 * scale / 9, scale, 8 * scale / 9))

    graphics.setFont(new Font("SanSerif", Font.BOLD, 18))
    for (row <- 1 to 9) {
      for (col <- 1 to 9) {
        val element = board(Z_9.fromInt(col), Z_9.fromInt(row))
        if (element.isDefined) {
          graphics.drawString(
            element.get.representative.toString,
            (col - 1) * scale / 9f + scale / 9f / 3,
            (row - 1) * scale / 9f + 2 * scale / 9f / 3)
        }
      }
    }

    graphics.setPaint(Color.black)
    graphics.draw(new Line2D.Float(0, 0, 0, scale))
    graphics.draw(new Line2D.Float(3 * scale / 9, 0, 3 * scale / 9, scale))
    graphics.draw(new Line2D.Float(6 * scale / 9, 0, 6 * scale / 9, scale))
    graphics.draw(new Line2D.Float(scale, 0, scale, scale))

    graphics.draw(new Line2D.Float(0, 0, scale, 0))
    graphics.draw(new Line2D.Float(0, 3 * scale / 9, scale, 3 * scale / 9))
    graphics.draw(new Line2D.Float(0, 6 * scale / 9, scale, 6 * scale / 9))
    graphics.draw(new Line2D.Float(0, scale, scale, scale))

    graphics.setFont(new Font("Serif", Font.BOLD, 15))
    graphics.drawString(difficulty, 3f / 4 * scale / 9, -1f / 4 * scale / 9)
  }

  val scale = 297
  graphics.setSVGCanvasSize(new Dimension(210 * 4, 297 * 4))
  graphics.translate(2 * scale / 9, 2 * scale / 9)

  val targetNumValues1 = 26 + Random.nextInt(7)
  val easy1 = (Generator.generatePuzzleBoards() filter { _.numValues <= targetNumValues1 } filter { Solver.isEasy }).head
  //  val easy1 = {
  //    Board("""
  //       _ 2 7 | 5 _ _ | _ _ _
  //       4 _ _ | 6 _ 1 | 2 3 _
  //       8 _ _ | _ _ _ | _ 9 _
  //      -----------------------
  //       9 3 _ | 7 _ _ | _ 2 _
  //       _ _ _ | _ 6 _ | _ _ _
  //       _ 6 _ | _ _ 5 | _ 1 4
  //      -----------------------
  //       _ 4 _ | _ _ _ | _ _ 8
  //       _ 5 3 | 8 _ 9 | _ _ 2
  //       _ _ _ | _ _ 7 | 6 4 _
  //    """)
  //  }
  graphics.translate(scale / 9, scale / 9)
  print(easy1)
  graphics.translate(scale, 0)

  val targetNumValues2 = 26 + Random.nextInt(7)
  val easy2 = (Generator.generatePuzzleBoards() filter { _.numValues <= targetNumValues2 } filter { Solver.isEasy }).head
  //  val easy2 = {
  //    Board("""
  //         _ _ 1 | _ 2 _ | _ 3 _
  //         _ _ _ | 1 _ _ | 2 _ 7
  //         6 _ _ | _ _ 7 | _ 4 _
  //        -----------------------
  //         _ 2 _ | 4 _ _ | _ _ 1
  //         _ _ 3 | _ 5 _ | 4 _ _
  //         7 _ _ | _ _ 9 | _ 2 _
  //        -----------------------
  //         _ 4 _ | 6 _ _ | _ _ 2
  //         2 _ 8 | _ _ 4 | _ _ _
  //         _ 5 _ | _ 1 _ | 3 _ _
  //      """)
  //  }
  graphics.translate(scale / 9, 0)
  print(easy2)
  graphics.translate(scale, 0)

  // New row
  graphics.translate(-2 * scale, scale)
  graphics.translate(-2 * scale / 9, 2 * scale / 9)

  val medium3 = (Generator.generatePuzzleBoards() filter { Solver.isMedium }).head
  //  val medium3 = {
  //    Board("""
  //         1 _ 6 | _ _ 3 | _ _ _
  //         _ _ _ | 8 _ _ | _ _ 4
  //         7 _ _ | _ 1 _ | _ 5 _
  //        -----------------------
  //         _ 6 _ | _ _ _ | 1 _ _
  //         9 _ _ | 4 _ 2 | _ _ 7
  //         _ _ 7 | _ _ _ | _ 4 _
  //        -----------------------
  //         _ 9 _ | _ 4 _ | _ _ 8
  //         3 _ _ | _ _ 9 | _ _ _
  //         _ _ _ | 1 _ _ | 7 _ 6
  //      """)
  //  }
  graphics.translate(scale / 9, 0)
  print(medium3)
  graphics.translate(scale, 0)

  val medium4 = (Generator.generatePuzzleBoards() filter { Solver.isMedium }).head
  //  val medium4 = {
  //    Board("""
  //       _ _ 5 | 4 _ _ | _ _ _
  //       _ 7 _ | _ 9 _ | _ 6 _
  //       3 _ _ | _ _ 6 | _ _ 1
  //      -----------------------
  //       2 _ _ | _ _ 3 | _ _ 6
  //       _ 9 _ | _ 2 _ | _ 8 _
  //       6 _ _ | 5 _ _ | _ _ 3
  //      -----------------------
  //       5 _ _ | 1 _ _ | _ _ 8
  //       _ 8 _ | _ 4 _ | _ 5 _
  //       _ _ _ | _ _ 9 | 6 _ _
  //    """)
  //  }
  graphics.translate(scale / 9, 0)
  print(medium4)
  graphics.translate(scale, 0)

  // New row
  graphics.translate(-2 * scale, scale)
  graphics.translate(-2 * scale / 9, 2 * scale / 9)

  val hard5 = (Generator.generatePuzzleBoards() filter { Solver.isHard }).head
  //  val hard5 = {
  //    Board("""
  //       2 _ _ | _ 9 _ | _ _ 8
  //       3 _ _ | 1 _ _ | _ _ 4
  //       _ _ _ | 8 _ _ | _ 9 _
  //      -----------------------
  //       _ _ 7 | _ _ _ | _ 5 _
  //       _ _ 3 | _ _ _ | 7 _ _
  //       _ 8 _ | _ _ _ | 6 _ _
  //      -----------------------
  //       _ 2 _ | _ _ 4 | _ _ _
  //       5 _ _ | _ _ 2 | _ _ 6
  //       1 _ _ | _ 5 _ | _ _ 2
  //    """)
  //  }
  graphics.translate(scale / 9, 0)
  print(hard5)
  graphics.translate(scale, 0)

  val hard6 = (Generator.generatePuzzleBoards() filter { Solver.isHard }).head
  //  val hard6 = {
  //    Board("""
  //         _ 2 _ | _ 4 _ | _ 5 _
  //         8 _ _ | _ 7 _ | _ _ 6
  //         _ _ _ | 3 _ 9 | _ _ _
  //        -----------------------
  //         _ _ 8 | _ _ _ | 1 _ _
  //         3 6 _ | _ _ _ | _ 4 8
  //         _ _ 9 | _ _ _ | 2 _ _
  //        -----------------------
  //         _ _ _ | 8 _ 7 | _ _ _
  //         9 _ _ | _ 2 _ | _ _ 5
  //         _ 5 _ | _ 6 _ | _ 7 _
  //      """)
  //  }
  graphics.translate(scale / 9, 0)
  print(hard6)
  graphics.translate(scale, 0)

  graphics.dispose()

  graphics.stream(args(0) + ".svg", true)
  PDFTranscoder.transcode(args(0) + ".svg", args(0) + ".pdf")

}
