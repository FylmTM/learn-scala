package gravitrips.frontend

import gravitrips.{Cell, ColumnFullError, EmptyCell, Error, InvalidColumnIndex, InvalidInput, Player1, Player1Disk, Player2, Player2Disk, State}

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object ConsoleFrontend extends Frontend {

  override def handleError(error: Error): Unit = {
    print(Console.RED)
    error match {
      case ColumnFullError => print(s"Column is full!")
      case InvalidInput => print(s"Invalid input!")
      case InvalidColumnIndex => print(s"Invalid column index!")
    }
    println(Console.RESET)
  }

  @scala.annotation.tailrec
  override def selectColumn(state: State): Int = {
    val color = state.currentPlayer match {
      case Player1 => Console.BLUE
      case Player2 => Console.GREEN
    }
    print(s"$color${state.currentPlayer} selecting column: ${Console.RESET}")

    val input = Try(StdIn.readInt()) match {
      case Failure(_) => Left(InvalidInput)
      case Success(columnIndex) =>
        if (columnIndex >= 1 && columnIndex <= state.field.width) Right(columnIndex)
        else Left(InvalidColumnIndex)
    }

    input match {
      case Right(columnIndex) => columnIndex - 1
      case Left(error) =>
        handleError(error)
        selectColumn(state)
    }
  }

  override def render(state: State): Unit = stateRenderer(state)

  private def stateRenderer(state: State): Unit = {
    println()
    for (i <- 1 to state.field.width) {
      print(s" $i")
    }
    println()
    state.field
      .rows()
      .foreach(rowRenderer)
    println()
  }

  private def rowRenderer(row: Seq[Cell]): Unit = {
    print("│")
    row.foreach(cellRenderer)
    println()
  }

  private def cellRenderer(cell: Cell): Unit = {
    cell match {
      case EmptyCell => print(" ")
      case Player1Disk => print(s"${Console.BLUE}●${Console.RESET}")
      case Player2Disk => print(s"${Console.GREEN}●${Console.RESET}")
    }
    print("│")
  }
}
