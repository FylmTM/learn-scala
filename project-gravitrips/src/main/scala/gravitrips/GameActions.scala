package gravitrips

import scala.io.StdIn
import scala.util.{Failure, Try}

object GetColumn {
  def apply(state: GameState): Try[Int] =
    Try(StdIn.readInt())
      .map(c =>
        if (c > 0 && c <= state.field.width) c
        else throw new IllegalArgumentException("Invalid column number")
      )
}

object ThrowDisk {
  def apply(state: GameState, column: Int): Try[GameState] =
    Failure(new IllegalArgumentException("column full"))
}
