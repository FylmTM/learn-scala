package gravitrips

sealed trait Error

case object ColumnFullError extends Error

case object InvalidInput extends Error

case object InvalidColumnIndex extends Error
