package gravitrips

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object GameLoop extends ((GameState, Int) => GameState){

  def apply(state: GameState, i: Int): GameState = {
    render(state)
    tick(state.resetOutput) match {
      case Success(newState) => newState
      case Failure(error) => state.error(error)
    }
  }

  private def tick(state: GameState) =
    for {
      column <- GetColumn(state)
      state <- ThrowDisk(state, column)
    } yield state

  private def render(state: GameStateRenderer): Unit = {
    state.render()
  }
}

private object GetColumn {
  def apply(state: GameState): Try[Int] =
    Try(StdIn.readInt())
      .map(c =>
        if (c > 0 && c <= state.field.width) c
        else throw new IllegalArgumentException("Invalid column number")
      )
}

private object ThrowDisk {
  def apply(state: GameState, column: Int): Try[GameState] = Failure(new IllegalArgumentException("column full"))
}
