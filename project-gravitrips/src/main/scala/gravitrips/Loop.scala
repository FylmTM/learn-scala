package gravitrips

import gravitrips.frontend.Frontend

import scala.annotation.tailrec

object Loop {
  @tailrec
  def apply(state: State = State())(implicit frontend: Frontend): Unit = {
    frontend.render(state)

    @tailrec
    def doTick(): Either[Result, State] = {
      Tick(state, frontend.selectColumn(state)) match {
        case Left(error: RecoverableError) => {
          frontend.handleError(error)
          doTick()
        }
        case Left(result) => Left(result)
        case Right(newState) => Right(newState)
      }
    }

    doTick() match {
      case Left(error: RecoverableError) => throw new IllegalStateException(s"Unexpected recoverable error: $error")
      case Left(error: UnrecoverableError) => frontend.handleUnrecoverableError(state, error)
      case Left(winner: Winner) => frontend.handleWinner(winner)
      case Right(newState) => Loop(newState)
    }
  }
}

object Tick {
  def apply(state: State, columnIndex: Int): Either[Result, State] = {
    for {
      state <- ThrowDisk(state, columnIndex)
      state <- CheckWinner(state, columnIndex)
      state <- CheckNoMoreMoves(state)
      state <- SwitchPlayer(state)
    } yield state
  }
}
