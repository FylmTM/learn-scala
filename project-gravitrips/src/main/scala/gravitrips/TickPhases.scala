package gravitrips

object CheckNoMoreMoves {
  def apply(state: State): Either[Error, State] = {
    if (state.field.contains(EmptyCell)) Right(state)
    else Left(NoMoreMoves(state))
  }
}

object ThrowDisk {
  def apply(state: State, columnIndex: Int): Either[Error, State] = {
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
  def apply(state: State, columnIndex: Int): Either[Error, State] = {
    Right(state)
  }
}

object SwitchPlayer {
  def apply(state: State): Either[Error, State] = Right(State(
    state.field,
    state.currentPlayer match {
      case Player1 => Player2
      case Player2 => Player1
    }
  ))
}
