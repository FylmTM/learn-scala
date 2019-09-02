package lectures.part1basics

object Functions extends App {

  def greet(name: String, age: Int): String = s"Hi, my name is $name and I am $age years old."

  println(greet("Dmitry", 26))

  def factorial(n: Int): Int = {
    if (n == 1) 1
    else n * factorial(n - 1)
  }

  println(factorial(1))
  println(factorial(2))
  println(factorial(3))
  println(factorial(4))

  def fibonacci(n: Int): Int = {
    if (n == 1) 1
    else if (n == 2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)
  }

  println(fibonacci(1))
  println(fibonacci(2))
  println(fibonacci(3))
  println(fibonacci(4))
  println(fibonacci(5))
  println(fibonacci(6))

  def isPrime(n: Int): Boolean = {
    if (n == 1) return true
    if (n == 2) return true

    @scala.annotation.tailrec
    def check(divider: Int): Boolean = {
      if (divider == n) true
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
}
