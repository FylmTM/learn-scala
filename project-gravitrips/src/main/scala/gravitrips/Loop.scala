package gravitrips

import gravitrips.frontend.Frontend

import scala.annotation.tailrec

object Loop {
  @tailrec
  def apply(state: State = State())(implicit frontend: Frontend): Unit = {
    frontend.render(state)

    @tailrec
    def doTick(): Either[UnrecoverableError, State] = {
      Tick(state, frontend.selectColumn(state)) match {
        case Left(error: UnrecoverableError) => Left(error)
        case Left(error: RecoverableError) => {
          frontend.handleError(error)
          doTick()
        }
        case Right(newState) => Right(newState)
      }
    }

    doTick() match {
      case Left(error) => frontend.handleUnrecoverableError(state, error)
      case Right(newState) => Loop(newState)
    }
  }
}

object Tick {
  def apply(state: State, columnIndex: Int): Either[Error, State] = {
    for {
      state <- ThrowDisk(state, columnIndex)
      state <- CheckWinner(state, columnIndex)
      state <- CheckNoMoreMoves(state)
      state <- SwitchPlayer(state)
    } yield state
  }
}
