package gravitrips.frontend

import gravitrips._

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object ConsoleFrontend extends Frontend {

  override def handleWinner(winner: Winner): Unit = {
    val state = winner.state
    render(state, winner.paths.flatten)

    val color = state.currentPlayer match {
      case Player1 => Console.BLUE
      case Player2 => Console.GREEN
    }
    val playerName = state.currentPlayer match {
      case Player1 => "Player 1"
      case Player2 => "Player 2"
    }

    println(s"$color$playerName wins!${Console.RESET}")
  }


  override def handleError(error: RecoverableError): Unit = {
    print(Console.RED)
    error match {
      case ColumnFullError => print(s"Column is full!")
      case InvalidInput => print(s"Invalid input!")
      case InvalidColumnIndex => print(s"Invalid column index!")
    }
    println(Console.RESET)
  }


  override def handleUnrecoverableError(state: State, error: UnrecoverableError): Unit = {
    error match {
      case NoMoreMoves(state) =>
        render(state)
        println(s"${Console.RED}No more moves!${Console.RESET}")
    }
  }

  @scala.annotation.tailrec
  override def selectColumn(state: State): Int = {
    val color = state.currentPlayer match {
      case Player1 => Console.BLUE
      case Player2 => Console.GREEN
    }
    val playerName = state.currentPlayer match {
      case Player1 => "Player 1"
      case Player2 => "Player 2"
    }
    print(s"$color$playerName selecting column: ${Console.RESET}")

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

  override def render(state: State, highlighted: Seq[LocatableCell] = Seq()): Unit = {
    println()
    for (i <- 1 to state.field.width) {
      print(s" $i")
    }
    println()

    for (rowIndex <- 0 until state.field.height) {
      print("│")
      val row = state.field.row(rowIndex)

      for (columnIndex <- 0 until state.field.width) {
        cellRenderer(
          rowIndex,
          columnIndex,
          row(columnIndex),
          highlighted.exists(c => c.column.equals(columnIndex) && c.row.equals(rowIndex))
        )
      }
      println()
    }
    println()
  }

  private def cellRenderer(rowIndex: Int, columnIndex: Int, cell: Cell, isHighlighted: Boolean): Unit = {
    if (isHighlighted) print(Console.MAGENTA_B)
    cell match {
      case EmptyCell => print(" ")
      case Player1Disk => print(s"${Console.BLUE}●")
      case Player2Disk => print(s"${Console.GREEN}●")
    }
    print(Console.RESET)
    print("│")
  }
}
