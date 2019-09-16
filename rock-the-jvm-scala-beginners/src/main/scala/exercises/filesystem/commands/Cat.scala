package exercises.filesystem.commands
import exercises.filesystem.filesystem.State

class Cat(filename: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    val dirEntry = wd.findEntry(filename)
    if (dirEntry== null || dirEntry.isDirectory) {
      state.setMessage(s"$filename: no such file")
    } else {
      state.setMessage(dirEntry.asFile.contents)
    }
  }
}
