package gravitrips

import gravitrips.Axis.Axis
import gravitrips.Direction.Direction

import scala.annotation.tailrec

object CheckNoMoreMoves {
  def apply(state: State): Either[Result, State] = {
    if (state.field.contains(EmptyCell)) Right(state)
    else Left(NoMoreMoves(state))
  }
}

object ThrowDisk {
  def apply(state: State, columnIndex: Int): Either[Result, State] = {
    val column = state.field.column(columnIndex)
    column.lastIndexOf(EmptyCell) match {
      case -1 => Left(ColumnFullError)
      case rowIndex => Right(State(
        state.field.update(columnIndex, rowIndex, state.currentPlayer.disk),
        state.currentPlayer
      ))
    }
  }
}

object CheckWinner {
  private val WINNER_PATH_SIZE = 4

  def apply(state: State, columnIndex: Int): Either[Result, State] = {
    val disk = state.currentPlayer.disk
    val rowIndex = state.field.column(columnIndex).indexOf(disk)
    val startCell = state.field(columnIndex, rowIndex) match {
      case Some(startCell) => startCell
      case None => throw new IllegalStateException(s"Can not find cell: $rowIndex,$columnIndex")
    }

    @tailrec
    def traverseDirection(
      cell: LocatableCell,
      direction: Direction,
      path: Seq[LocatableCell] = Seq()
    ): Seq[LocatableCell] = {
      val maybeCell = state.field(cell.column + direction.delta.column, cell.row + direction.delta.row)
      maybeCell match {
        case Some(nextCell) if nextCell.cell.equals(disk) =>
          traverseDirection(nextCell, direction, path :+ nextCell)
        case _ => path
      }
    }

    def traverseAxis(axis: Axis): Seq[LocatableCell] = {
      traverseDirection(startCell, axis.direction1) ++ traverseDirection(startCell, axis.direction2) :+ startCell
    }

    val paths = Axis.values.toSeq
      .map(traverseAxis(_))
      .filter(_.length >= WINNER_PATH_SIZE)

    if (paths.nonEmpty) Left(Winner(state, paths))
    else Right(state)
  }
}

object SwitchPlayer {
  def apply(state: State): Either[Result, State] = Right(State(
    state.field,
    state.currentPlayer match {
      case Player1 => Player2
      case Player2 => Player1
    }
  ))
}
