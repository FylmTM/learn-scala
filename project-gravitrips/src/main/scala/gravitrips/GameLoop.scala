package gravitrips

import scala.util.{Failure, Success}

object GameLoop {
  @scala.annotation.tailrec
  def apply(gameState: GameState = GameState()): Unit = {
    GameLoop(GameTick(gameState))
  }
}

object GameTick {

  def apply(state: GameState): GameState = {
    GameStateRenderer(state)
    tick(state.resetOutput) match {
      case Success(newState) => newState
      case Failure(error) => state.error(error)
    }
  }

  private def tick(state: GameState) =
    for {
      column <- GetColumn(state)
      state <- ThrowDisk(state, column)
      state <- SwitchPlayer(state)
    } yield state
}
