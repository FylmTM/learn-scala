package lectures.part3fp

import scala.annotation.tailrec

object HOFsCurries extends App {

  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))

  def nTimesBetter(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n - 1)(f(x))

  val plusOne = (x: Int) => x + 1

  println(nTimes(_ + 1, 10, 1))

  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(1))

  def curriedFormatter(c: String)(x: Double): String = c.format(x)


  def toCurry(f: (Int, Int) => Int): Int => Int => Int =
    x => y => f(x, y)

  def fromCurry(f: Int => Int => Int): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))
}
