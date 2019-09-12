package gravitrips

import scala.io.StdIn
import scala.util.{Success, Try}

object GetColumn {
  def apply(state: GameState): Try[Int] = {
    print(s"${state.currentPlayer} selecting column: ")
    Try(StdIn.readInt())
      .map(_ - 1)
      .map(c =>
        if (c >= 0 && c < state.field.width) c
        else throw new IllegalArgumentException("Invalid column number")
      )
  }
}

object ThrowDisk {
  def apply(state: GameState, columnIndex: Int): Try[GameState] = {
    def doThrow(): GameState = {
      val column = state.field.column(columnIndex)

      column.lastIndexOf(EmptyCell) match {
        case -1 => throw new IllegalArgumentException("Column is full")
        case row => GameState(
          state.field.update(columnIndex, row, state.currentPlayer.disk),
          state.currentPlayer,
          state.output
        )
      }
    }

    Try(doThrow())
  }
}

object SwitchPlayer {
  def apply(state: GameState): Try[GameState] =
    Success(GameState(state.field, state.currentPlayer.switch, state.output))
}
