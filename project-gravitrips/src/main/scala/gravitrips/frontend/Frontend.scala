package gravitrips.frontend

import gravitrips.{RecoverableError, State, UnrecoverableError}

trait Frontend {
  def render(state: State): Unit

  def selectColumn(state: State): Int

  def handleError(error: RecoverableError): Unit

  def handleUnrecoverableError(state: State, error: UnrecoverableError): Unit
}

