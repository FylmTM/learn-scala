package lectures.part2oop

object Objects extends App {

  object Person {
    val N_EYES = 2

    def canFly: Boolean = false

    def apply(mother: Person, father: Person) = new Person("Bobby")
  }
  class Person(val name: String) {
  }

  println(Person.N_EYES)
  println(Person.canFly)

  val mary = new Person("Mary")
  val john = new Person("John")

  println(mary)
  println(john)
  println(Person)

  val bobbie = Person(mary, john)
}
