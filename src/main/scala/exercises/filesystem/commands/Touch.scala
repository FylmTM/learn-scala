package exercises.filesystem.commands

import exercises.filesystem.files.{DirEntry, File}
import exercises.filesystem.filesystem.State

class Touch(name: String) extends CreateEntry(name) {

  override def createEntry(state: State): DirEntry = File.empty(state.wd.path, name)
}
