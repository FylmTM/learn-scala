package exercises.filesystem.commands

import exercises.filesystem.files.Directory
import exercises.filesystem.filesystem.State

class Rm(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd

    val absolutePath =
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name

    if (Directory.ROOT_PATH.equals(absolutePath)) {
      state.setMessage("Remove root directory is not supported")
    } else {
      doRm(state, absolutePath)
    }
  }

  def doRm(state: State, path: String): State = {
    def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if (nextDirectory.isFile) currentDirectory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if (newNextDirectory == nextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot = rmHelper(state.root, tokens)

    if (newRoot == state.root)
      state.setMessage(s"$path: no such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
  }
}
