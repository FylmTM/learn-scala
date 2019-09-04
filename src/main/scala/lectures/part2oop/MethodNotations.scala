package lectures.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"$name is hanging out with ${person.name}"
    def +(str: String): String = s"$name (str)"

    def unary_!(): String = s"$name is angry"
    def unary_+(): Person = new Person(name, favoriteMovie, age + 1)

    def isAlive: Boolean = true

    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
    def apply(n: Int): String = s"Mary watched $favoriteMovie $n times"

    def learns(thing: String) = s"$name learns $thing"
    def learnsScala: String = learns("Scala")
  }

  val mary = new Person("Mary", "Inception", 28)

  println(mary.likes("Inception"))
  println(mary likes "Inception")

  val tom = new Person("Tom", "Fight Club", 5)
  println(mary + tom)

  println(1 + 2)

  println(!tom)

  println(tom.isAlive)

  println(mary.apply())
  println(mary())

  mary learns "Scala"
  mary learnsScala

  println(+mary age)
  mary(2)
}
