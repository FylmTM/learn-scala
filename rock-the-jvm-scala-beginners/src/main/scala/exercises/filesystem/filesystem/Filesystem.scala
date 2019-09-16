package exercises.filesystem.filesystem

import exercises.filesystem.commands.Command
import exercises.filesystem.files.Directory

object Filesystem extends App {
  val root = Directory.ROOT

  io.Source.stdin
    .getLines()
    .foldLeft(State(root, root))((currentState, newLine) => {
      currentState.show()
      Command.from(newLine)(currentState)
    })
}
