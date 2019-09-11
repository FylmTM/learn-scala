package exercises.filesystem.commands

import exercises.filesystem.files.Directory
import exercises.filesystem.filesystem.State

class Mkdir(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage(s"Entry $name already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"Entry $name is invalid: must not contain directory separator")
    } else if (checkIllegal(name)) {
      state.setMessage(s"Entry $name is illegal entry name")
    } else {
      doMkdir(state, name)
    }
  }

  def doMkdir(state: State, name: String): State = {
    ???
  }

  def checkIllegal(str: String): Boolean = {
    name.contains('.') || name.contains(' ')
  }
}
