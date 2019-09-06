package lectures.part2oop

object Exceptions extends App {
  try {
    throw new IllegalArgumentException
  } catch {
    case e: Throwable => println(e.getMessage)
  }
}
