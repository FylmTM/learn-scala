package exercises.filesystem.commands

import exercises.filesystem.filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def from(input: String): Command = new UnknownCommand(input)
}
