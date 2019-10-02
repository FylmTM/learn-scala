package lectures.part2afp

object LazyEvaluation extends App {

  lazy val x: Int = {
    println("hello")
    42
  }

  println(x)
  println(x)

  // examples of implications:

  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no")

  // in conjuction with call by name
  def byNameMethod(n: => Int): Int = {
    // call by need
    lazy val t = n
    t + t + t + 1
  }

  def retrieveMagicValue = {
    // ...
    println("waiting")
    Thread.sleep(1000)
    42
  }
  println(byNameMethod(retrieveMagicValue))

  // filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }
  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30)
  val gt20lazy = lt30lazy.withFilter(greaterThan20)
  println
  println(gt20lazy)
  gt20lazy.foreach(println)

  for {
    a <- List(1, 2, 3) if a % 2 == 0
  } yield a + 1
  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)
}
