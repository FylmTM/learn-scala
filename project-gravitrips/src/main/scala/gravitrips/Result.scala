package gravitrips

sealed trait Result

case class Winner(state: State, paths: Seq[Seq[LocatableCell]]) extends Result

sealed trait Error extends Result

sealed trait RecoverableError extends Error

sealed trait UnrecoverableError extends Error

case class NoMoreMoves(state: State) extends UnrecoverableError

case object ColumnFullError extends RecoverableError

case object InvalidInput extends RecoverableError

case object InvalidColumnIndex extends RecoverableError
