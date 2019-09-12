package exercises.filesystem.commands

import exercises.filesystem.filesystem.State

object Pwd extends Command {

  override def apply(state: State): State = state.setMessage(state.wd.path)
}
