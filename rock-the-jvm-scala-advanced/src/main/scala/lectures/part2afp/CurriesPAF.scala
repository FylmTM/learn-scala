package lectures.part2afp

import scala.util.Random

object CurriesPAF extends App {

  def superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3)
  println(add3(5))
  println(superAdder(3)(5))

  def curriedAdder(x: Int)(y: Int): Int = x + y

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION
  // functions != methods (JVM limitations)

  def inc(x: Int) = x + 1
  List(1, 2, 3).map(inc) // ETA-expansion

  val add5 = curriedAdder(5) _ // ETA expansion

  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  val add7 = (x: Int) => simpleAddFunction(7, x)
  val add7_2 = simpleAddFunction.curried(7)
  val add7_3 = curriedAddMethod(7) _
  val add7_4 = curriedAddMethod(7)(_)
  val add7_5 = simpleAddFunction(7, _: Int)

  def concatenator(a: String, b: String, c: String) = a + b + c

  val insertName = concatenator("Hello I am ", _: String, " how are you?")
  println(insertName("Daniel"))

  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12, 1512512)
  val simpleFormat = curriedFormatter("%4.2f") _
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(simpleFormat))
  println(numbers.map(seriousFormat))
  println(numbers.map(preciseFormat))

  def byName(n: => Int) = n

  def method: Int = Random.nextInt()
  def parenMethod(): Int = Random.nextInt()

  println(byName(method))
  println(byName(method))

  println(byName(parenMethod))
  println(byName(parenMethod()))
}
