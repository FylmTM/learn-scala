package gravitrips.frontend

import gravitrips._

trait Frontend {
  def render(state: State, highlighted: Seq[LocatableCell] = Seq()): Unit

  def selectColumn(state: State): Int

  def handleError(error: RecoverableError): Unit

  def handleUnrecoverableError(state: State, error: UnrecoverableError): Unit

  def handleWinner(winner: Winner): Unit
}

