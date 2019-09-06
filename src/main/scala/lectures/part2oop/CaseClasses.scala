package lectures.part2oop

object CaseClasses extends App {
  case class Person(name: String, age: Int)

  val jim = new Person("Jim", 34)
  println(jim.name)
  println(jim)
}
