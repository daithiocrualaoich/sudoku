package com.gu.sudoku

object Sudoku {

  def solve(puzzle: Board): Option[Board] = {
    val solution = iterateEliminateByRowColumnAndZoneElements(puzzle)

    Some(solution)
  }

  def graph(board: Board): Graph[(Z_9, Z_9), Set[Z_9]] = {
    val g = new Graph[(Z_9, Z_9), Set[Z_9]]
    def candidates(i: Z_9, j: Z_9): Set[Z_9] = {
      board(i, j) map { Set(_) } getOrElse {
        Z_9.all.toSet -- (board.row(j).values ++ board.column(i).values ++ board.zone(i, j).values)
      }
    }

    for (i <- Z_9.all) {
      for (j <- Z_9.all) {
        g.addNode((i, j), candidates(i, j))
      }
    }

    for (i <- Z_9.all) {
      for (j <- Z_9.all) {

        // row neighbours
        (Z_9.all filter { _ > i }) foreach { k =>
          g.addEdge((i, j), (k, j))
        }

        // column neighbours
        (Z_9.all filter { _ > j }) foreach { k =>
          g.addEdge((i, j), (i, k))
        }

        // zone neighbours
        val zoneUpperLeftCol = (i.representative - 1) / 3 * 3 + 1
        val zoneUpperLeftRow = (j.representative - 1) / 3 * 3 + 1
        for (row <- 0 to 2) {
          for (col <- 0 to 2) {
            val rowZ_9 = Z_9.fromInt(zoneUpperLeftRow + row)
            val colZ_9 = Z_9.fromInt(zoneUpperLeftCol + col)

            if (rowZ_9 > i || (rowZ_9 == i && colZ_9 > j)) {
              g.addEdge((i, j), (colZ_9, rowZ_9))
            }

          }
        }
      }
    }

    g
  }

  def board(graph: Graph[(Z_9, Z_9), Set[Z_9]]): Board = {
    val nodes = graph.getNodes
    def boardValue(i: Z_9, j: Z_9) = {
      nodes((i, j)).value.toList match {
        case head :: Nil => Some(head)
        case _ => None
      }
    }

    val rows = (Z_9.all map { j =>
      (j, Row(
        boardValue(One, j),
        boardValue(Two, j),
        boardValue(Three, j),
        boardValue(Four, j),
        boardValue(Five, j),
        boardValue(Six, j),
        boardValue(Seven, j),
        boardValue(Eight, j),
        boardValue(Nine, j)
      ))
    }).toMap

    Board(
      rows(One),
      rows(Two),
      rows(Three),
      rows(Four),
      rows(Five),
      rows(Six),
      rows(Seven),
      rows(Eight),
      rows(Nine)
    )
  }

  def iterateEliminateByRowColumnAndZoneElements(puzzle: Board): Board = {
    var previous = puzzle
    var current = eliminateByRowColumnAndZoneElements(previous)

    while (current != previous) {
      previous = current
      current = eliminateByRowColumnAndZoneElements(previous)
    }

    current
  }

  def eliminateByRowColumnAndZoneElements(puzzle: Board): Board = board(graph(puzzle))

}