package gravitrips

object GameStateRenderer {
  def apply(state: GameState): Unit = {
    state.field
      .rows()
      .foreach(RowRenderer(_))
    println()

    if (state.output.nonEmpty) {
      println(state.output)
      println()
    }
  }
}

object RowRenderer {
  def apply(row: Seq[Cell]): Unit = {
    print("|")
    row.foreach(CellRenderer(_))
    println()
  }
}

object CellRenderer {
  def apply(cell: Cell): Unit = {
    cell match {
      case EmptyCell => print(" ")
      case Player1Disk => print("x")
      case Player2Disk => print("o")
    }
    print("|")
  }
}
