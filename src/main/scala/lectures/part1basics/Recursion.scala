package lectures.part1basics

object Recursion extends App {

  def concat(str: String, n: Int): String = {
    @scala.annotation.tailrec
    def c(n: Int, acc: String): String = {
      if (n == 0) acc
      else c(n - 1, acc + str)
    }

    c(n, "")
  }

  println(concat("batman ", 3))

  def isPrime(n: Int): Boolean = {
    if (n == 1) return true
    if (n == 2) return true

    val end = n / 2 + 1

    @scala.annotation.tailrec
    def check(divider: Int): Boolean = {
      if (divider == end) true
      else if (n % divider == 0) false
      else check(divider + 1)
    }

    check(2)
  }

  println(isPrime(1))
  println(isPrime(2))
  println(isPrime(3))
  println(isPrime(4))
  println(isPrime(5))
  println(isPrime(10))
  println(isPrime(17))
  println(isPrime(17 * 31))
}
