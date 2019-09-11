package exercises.filesystem.commands

import exercises.filesystem.files.{DirEntry, Directory}
import exercises.filesystem.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {

  override def createEntry(state: State): DirEntry = Directory.empty(state.wd.path, name)
}
