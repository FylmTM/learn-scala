package lectures.part3fp

object MapFlatmapFilterFor extends App {

  val numbers = List(1, 2, 3)
  var chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")

  val combinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield s"$c $n $color"
}
