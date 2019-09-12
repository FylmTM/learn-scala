package gravitrips

object GameStateRenderer {
  def apply(state: GameState): Unit = {
    println("field")
    if (state.output.nonEmpty) {
      println(state.output)
    }
  }
}
