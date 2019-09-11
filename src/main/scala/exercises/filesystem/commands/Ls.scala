package exercises.filesystem.commands
import exercises.filesystem.files.DirEntry
import exercises.filesystem.filesystem.State

object Ls extends Command {

  def createNiceOutput(contents: List[DirEntry]): String =
    contents.map(_.toString).mkString("\n")

  override def apply(state: State): State = {
    val contents = state.wd.contents

    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }
}
