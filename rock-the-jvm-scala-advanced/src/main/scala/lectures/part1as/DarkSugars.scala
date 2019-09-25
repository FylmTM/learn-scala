package lectures.part1as

import scala.util.Try

object DarkSugars extends App {

  // #1: methods with single param
  def singleArgMethod(arg: Int): String = ???

  val description = singleArgMethod {
    // ... code
    42
  }

  val aTryInstance = Try {
  }

  List(1, 2, 3).map { x =>
    x + 1
  }

  // #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anAction: Action = x => x + 1

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = a => println("sweet")

  // #3: the :: and #:: methods are special
  val prependedList = 2 :: List(3, 4)

  // scala spec: last char decides associativity of method
  1 :: 2 :: 3 :: List(4, 5)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // #4: multi-word method naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = {
      println(s"$name said $gossip")
    }
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  // #5: infix types
  class Composite[A, B]
  val composite1: Composite[Int, String] = ???
  val composite2: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // #6: update() is very special, much like apply()
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)
  // used in mutable collections
  // remember apply() and update()

  // #7: setter for mutable containers
  class Mutable {
    private var internalMember: Int = 0

    def member: Int = internalMember
    def member_=(value: Int): Unit = internalMember = value
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // aMutableContainer.member_=(42)
}
