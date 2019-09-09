package exercises.filesystem.filesystem

import java.util.Scanner

import exercises.filesystem.commands.Command
import exercises.filesystem.files.Directory

object Filesystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while (true) {
    state.show()

    val input = scanner.nextLine
    state = Command.from(input)(state)
  }
}
