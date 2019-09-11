package exercises.filesystem.commands

import exercises.filesystem.filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def emptyCommand: Command = state => state

  def incompleteCommand(name: String): Command = state => state.setMessage(s"$name: incomplete command")

  val MKDIR = "mkdir"

  def from(input: String): Command = {
    val tokens: Array[String] = input
      .split("\\s")
      .filterNot { t => t.isEmpty }

    if (tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    }
    else new UnknownCommand(input)
  }
}
