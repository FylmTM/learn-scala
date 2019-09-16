package exercises.filesystem.commands

import exercises.filesystem.filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def emptyCommand: Command = state => state

  def incompleteCommand(name: String): Command = state => state.setMessage(s"$name: incomplete command")

  val MKDIR = "mkdir"
  val TOUCH = "touch"
  val LS = "ls"
  val PWD = "pwd"
  val CD = "cd"
  val RM = "rm"

  def from(input: String): Command = {
    val tokens: Array[String] = input
      .split("\\s")
      .filter { _.nonEmpty }

    val command = tokens(0)

    if (tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(command)) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else {
        val name = tokens(1)
        new Mkdir(name)
      }
    } else if (LS.equals(command)){
      Ls
    } else if (PWD.equals(command)) {
      Pwd
    } else if (TOUCH.equals(command)) {
      if (tokens.length < 2) incompleteCommand(TOUCH)
      else {
        val name = tokens(1)
        new Touch(name)
      }
    } else if (CD.equals(command)) {
      if (tokens.length < 2) incompleteCommand(CD)
      else {
        val path = tokens(1)
        new Cd(path)
      }
    } else if (RM.equals(command)) {
      if (tokens.length < 2) incompleteCommand(RM)
      else new Rm(tokens(1))
    }
    else new UnknownCommand(input)
  }
}
