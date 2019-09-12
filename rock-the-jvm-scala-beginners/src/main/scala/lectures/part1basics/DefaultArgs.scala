package lectures.part1basics

object DefaultArgs extends App {

  @scala.annotation.tailrec
  def trFact(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else trFact(n - 1, n * acc)

  val fact10 = trFact(10)

  def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1920): Unit = ???

  savePicture(width = 800)

}
