package gravitrips

case class State(
  field: Field = Field(),
  currentPlayer: Player = Player1
)

case class Field(
  width: Int,
  height: Int,
  cells: Vector[Cell]
) {
  def update(column: Int, row: Int, cell: Cell): Field =
    Field(width, height, cells.updated(column * height + row, cell))

  def rows(): Seq[Seq[Cell]] =
    for (i <- 0 until height) yield row(i)

  def columns(): Seq[Seq[Cell]] =
    for (i <- 0 until width) yield column(i)

  def row(row: Int): Seq[Cell] =
    for (i <- 0 until width) yield cells(height * i + row)

  def column(column: Int): Seq[Cell] =
    for (i <- 0 until height) yield cells(column * height + i)
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

  override def toString: String = "Player 1"
}

case object Player2 extends Player {
  override def switch: Player = Player1

  override def disk: Disk = Player2Disk

  override def toString: String = "Player 2"
}
