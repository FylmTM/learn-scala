package gravitrips

case class GameState(field: Field = Field(), output: String = "") {
  def output(output: String): GameState = GameState(field, output)

  def resetOutput: GameState = output("")

  def error(throwable: Throwable): GameState = output(throwable.toString)
}

case class Field(
  width: Int,
  height: Int,
  cells: Vector[Cell]
) {
  def throwDisk(player: Player, column: Int): Field = ???

  private def getColumn(column: Int): Vector[Cell] = cells.slice(column - 1 * height, column * height)
}
object Field {
  def apply(width: Int = 8, height: Int = 5): Field =
    Field(width, height, Vector.fill(width * height)(EmptyCell))
}

sealed trait Cell

sealed trait Disk extends Cell

case object EmptyCell extends Cell

case object Player1Disk extends Disk

case object Player2Disk extends Disk

sealed trait Player {
  def switch: Player

  def disk: Disk
}

case object Player1 extends Player {
  override def switch: Player = Player2

  override def disk: Disk = Player1Disk
}

case object Player2 extends Player {
  override def switch: Player = Player1

  override def disk: Disk = Player2Disk
}
