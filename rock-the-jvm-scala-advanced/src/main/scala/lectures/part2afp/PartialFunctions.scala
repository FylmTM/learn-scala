package lectures.part2afp

import scala.io.Source

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  // {1, 2, 5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
  }
  println(aPartialFunction(1))
  println(aPartialFunction.isDefinedAt(1))
  println(aPartialFunction.isDefinedAt(67))

  val lifted = aPartialFunction.lift
  println(lifted(1))
  println(lifted(67))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(1))
  println(pfChain(45))

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial functions as well

  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  val manualPF = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = ???

    override def apply(v1: Int): Int = ???
  }


  Source.stdin.getLines().foreach {
    case "some" => "other"
  }

}
