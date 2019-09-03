package lectures.part1basics

object StringsOps extends App {

  val str: String = "Hello, I am learning Scala"

  println(str.charAt(2))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.length)

  val aNumber = "45".toInt

  println('a' +: str)
  println(str :+ 'z')
  println('a' +: str :+ 'z')
}
