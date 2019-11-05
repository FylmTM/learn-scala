package exercies

import lectures.part4implicits.TypeClasses.User

object EqualityPlayground extends App {

  /**
   * Equality
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }
  object Equal {
    def apply[T](a: T, b: T)(implicit equal: Equal[T]): Boolean = equal.apply(a, b)
  }
  implicit class EqualOps[T](value: T) {
    def ===(other: T)(implicit equal: Equal[T]): Boolean = equal.apply(value, other)
    def !==(other: T)(implicit equal: Equal[T]): Boolean = !equal.apply(value, other)
  }
  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }
  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a == b
  }

  val john = User("John", 32, "john@rockthejvm.com")
  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  println(Equal(john, anotherJohn))

  /*
  Exercise - improve the Equal TC with an implicit conversion class
  ===(anotherValue: T)
  !==(anotherValue: T)
   */

  println(john === anotherJohn)
  println(john !== anotherJohn)
}
