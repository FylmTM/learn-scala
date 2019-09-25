package lectures.part4pm

import exercises.{Cons, Empty, MyList}

object PatternMatching extends App {

  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The Truth"
    case PatternMatching => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard

  val matchAnything = x match {
    case _ =>
  }
  // 2.2 variable
  val matchVariable = x match {
    case something => s"I have found $something"
  }

  // 3 - Tuple

  val aTuple = (1, 2)
  val matchATupole = aTuple match {
    case (1, 1) =>
    case (something, 2) => s"I have found $something"
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }

  // 4 - case classes - contructor pattern

  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchAList = aList match {
    case Empty =>
    case Cons(head, tail) =>
    case Cons(head, Cons(subhead, subtail)) =>
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => // extractor
    case List(1, _*) => // list of arbitrary length
    case 1 :: List(_) => // infix pattern
    case List(1, 2, 3) :+ 42 => // infix patterns
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _ =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList@Cons(_, _) => // name binding => use the name later
    case Cons(1, rest@Cons(2, _)) => // name binding inside rested patterns
  }

  // 8 - multi patterns
  val multiPattern = aList match {
    case Empty | Cons(0, _) => // compound patterns (multi-pattern)
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 =>
  }

  // ----

  List(1, 2, 3).map {
    case 1 => "the one"
    case _ => "everything else"
  }
}
