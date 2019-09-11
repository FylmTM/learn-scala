package exercises.filesystem.commands

import exercises.filesystem.files.{DirEntry, Directory}
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
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd

    val allDirsInPath = wd.getAllFoldersInPath

    val newDir = Directory.empty(wd.path, name)

    val newRoot = updateStructure(state.root, allDirsInPath, newDir)

    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  def checkIllegal(str: String): Boolean = {
    name.contains('.') || name.contains(' ')
  }
}
