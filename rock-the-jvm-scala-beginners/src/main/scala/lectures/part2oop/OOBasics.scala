package lectures.part2oop

object OOBasics extends App {

}

class Person(name: String, age: Int)

class Writer(firstName: String, surname: String, val year: Int) {

  def fullName(): String = s"$firstName $surname"
}
class Novel(name: String, yearOfRelease: Int, author: Writer) {

  def authorAge(): Int = yearOfRelease - author.year

  def isWrittenBy(author: Writer): Boolean = this.author.fullName() == author.fullName()

  def copy(yearOfRelease: Int): Novel = new Novel(name, yearOfRelease, author)
}


class Counter(val value: Int) {

  def increment: Counter = new Counter(value + 1)
  def increment(amount: Int): Counter = new Counter(value + amount)

  def decrement: Counter = new Counter(value - 1)
  def decrement(amount: Int): Counter = new Counter(value - amount)
}
