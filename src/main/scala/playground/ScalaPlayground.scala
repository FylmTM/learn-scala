package playground

object ScalaPlayground extends App {
  println("Hello world")

  val list = List[String]("a", "", "b")
    .filter(_.nonEmpty)

  print(list)
}
