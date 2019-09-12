package gravitrips

import gravitrips.frontend.Frontend

object Loop {
  @scala.annotation.tailrec
  def apply(state: State = State())(implicit frontend: Frontend): Unit = {
    frontend.render(state)

    @scala.annotation.tailrec
    def doTick(): State = {
      Tick(state, frontend.selectColumn(state)) match {
        case Left(error) =>
          frontend.handleError(error)
          doTick()
        case Right(newState) => newState
      }
    }

    Loop(doTick())
  }
}

object Tick {
  def apply(state: State, columnIndex: Int): Either[Error, State] = {
    for {
      state <- ThrowDisk(state, columnIndex)
      state <- SwitchPlayer(state)
    } yield state
  }
}

private object ThrowDisk {
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

private object SwitchPlayer {
  def apply(state: State): Either[Error, State] =
    Right(State(state.field, state.currentPlayer.switch))
}
