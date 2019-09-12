package gravitrips

import scala.language.implicitConversions

case class GameStateRenderer(gameState: GameState) {
  def render(): Unit = {
    gameState.field.
    if (gameState.output.nonEmpty) {
      println(gameState.output)
    }
  }
}

object GameStateRenderer {
  implicit def toRenderer(gameState: GameState): GameStateRenderer = {
    GameStateRenderer(gameState)
  }
}

