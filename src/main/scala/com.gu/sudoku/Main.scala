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

  val targetNumValues1 = 26 + Random.nextInt(7)
  val easy1 = (Generator.generatePuzzleBoards() filter { _.numValues <= targetNumValues1 } filter { Difficulty.isEasy }).head
  val targetNumValues2 = 26 + Random.nextInt(7)
  val easy2 = (Generator.generatePuzzleBoards() filter { _.numValues <= targetNumValues2 } filter { Difficulty.isEasy }).head

  val medium3 = (Generator.generatePuzzleBoards() filter { Difficulty.isMedium }).head
  val medium4 = (Generator.generatePuzzleBoards() filter { Difficulty.isMedium }).head

  val hard5 = (Generator.generatePuzzleBoards() filter { Difficulty.isHard }).head
  val hard6 = (Generator.generatePuzzleBoards() filter { Difficulty.isHard }).head

  val graphics = {
    val dom = GenericDOMImplementation.getDOMImplementation
    val document = dom.createDocument("http://www.w3.org/2000/svg", "svg", null)

    new SVGGraphics2D(document)
  }

  def print(board: Board) {
    val difficulty = {
      if (Difficulty.isEasy(board)) "Easy"
      else if (Difficulty.isMedium(board)) "Medium"
      else if (Difficulty.isHard(board)) "Hard"
      else ""
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
        val element = board(Z_9(col), Z_9(row))
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

  graphics.translate(scale / 9, scale / 9)
  print(easy1)
  graphics.translate(scale, 0)

  graphics.translate(scale / 9, 0)
  print(easy2)
  graphics.translate(scale, 0)

  // New row
  graphics.translate(-2 * scale, scale)
  graphics.translate(-2 * scale / 9, 2 * scale / 9)

  graphics.translate(scale / 9, 0)
  print(medium3)
  graphics.translate(scale, 0)

  graphics.translate(scale / 9, 0)
  print(medium4)
  graphics.translate(scale, 0)

  // New row
  graphics.translate(-2 * scale, scale)
  graphics.translate(-2 * scale / 9, 2 * scale / 9)

  graphics.translate(scale / 9, 0)
  print(hard5)
  graphics.translate(scale, 0)

  graphics.translate(scale / 9, 0)
  print(hard6)
  graphics.translate(scale, 0)

  graphics.dispose()

  graphics.stream(args(0) + ".svg", true)
  PDFTranscoder.transcode(args(0) + ".svg", args(0) + ".pdf")

}
