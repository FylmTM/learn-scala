package gravitrips

case class State(
  field: Field = Field(),
  currentPlayer: Player = Player1,
  isWinner: Boolean = false
)

case class Field(
  width: Int,
  height: Int,
  cells: Vector[Cell]
) {
  def update(column: Int, row: Int, cell: Cell): Field =
    Field(width, height, cells.updated(column * height + row, cell))

  def contains(cell: Cell): Boolean = cells.contains(cell)

  def apply(column: Int, row: Int): Cell =
    cells(column * height + row)

  def row(row: Int): Seq[Cell] =
    for (i <- 0 until width) yield cells(height * i + row)

  def rows(): Seq[Seq[Cell]] =
    for (i <- 0 until height) yield row(i)

  def column(column: Int): Seq[Cell] =
    for (i <- 0 until height) yield cells(column * height + i)

  def columns(): Seq[Seq[Cell]] =
    for (i <- 0 until width) yield column(i)
}

object Field {
  def apply(width: Int = 3, height: Int = 3): Field =
    Field(width, height, Vector.fill(width * height)(EmptyCell))
}

sealed trait Cell

sealed trait Disk extends Cell

case object EmptyCell extends Cell

case object Player1Disk extends Disk

case object Player2Disk extends Disk

sealed trait Player {
  def disk: Disk
}

case object Player1 extends Player {
  override def disk: Disk = Player1Disk
}

case object Player2 extends Player {
  override def disk: Disk = Player2Disk
}
