package gravitrips

sealed trait Error

sealed trait RecoverableError extends Error

sealed trait UnrecoverableError extends Error

case class NoMoreMoves(state: State) extends UnrecoverableError

case object ColumnFullError extends RecoverableError

case object InvalidInput extends RecoverableError

case object InvalidColumnIndex extends RecoverableError
