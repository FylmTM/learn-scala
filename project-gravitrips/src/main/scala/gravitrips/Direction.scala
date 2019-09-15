package gravitrips

import gravitrips.Direction.Direction

case class Delta(column: Int, row: Int)

object Direction extends Enumeration {
  type Direction = Val
  protected case class Val(delta: Delta) extends super.Val

  val UpLeft = Val(Delta(column = -1, row = -1))
  val Up = Val(Delta(column = 0, row = -1))
  val UpRight = Val(Delta(column = +1, row = -1))

  val DownLeft = Val(Delta(column = -1, row = +1))
  val Down = Val(Delta(column = 0, row = +1))
  val DownRight = Val(Delta(column = +1, row = +1))

  val Left = Val(Delta(column = -1, row = 0))
  val Right = Val(Delta(column = +1, row = 0))
}

object Axis extends Enumeration {
  type Axis = Val
  protected case class Val(direction1: Direction, direction2: Direction) extends super.Val
  import scala.language.implicitConversions
  implicit def valueToAxisVal(x: Value): Val = x.asInstanceOf[Val]

  val Vertical = Val(Direction.Up, Direction.Down)
  val Horizontal = Val(Direction.Left, Direction.Right)
  val TopLeftBottomRight = Val(Direction.UpLeft, Direction.DownRight)
  val BottomLeftTopRight = Val(Direction.DownLeft, Direction.UpRight)
}

