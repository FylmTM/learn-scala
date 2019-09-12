package gravitrips.frontend

import gravitrips.{Error, State}

trait Frontend {
  def render(state: State): Unit

  def selectColumn(state: State): Int

  def handleError(error: Error): Unit
}

