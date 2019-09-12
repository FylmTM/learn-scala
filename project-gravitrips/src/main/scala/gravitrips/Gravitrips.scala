package gravitrips

object Gravitrips extends App {
  Stream.from(0).foldLeft(GameState())(GameLoop)
}
