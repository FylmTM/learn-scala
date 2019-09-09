package exercises.filesystem.commands
import exercises.filesystem.filesystem.State

class UnknownCommand(input: String) extends Command {

  override def apply(state: State): State = state.setMessage(s"Unknown command '$input'")
}
