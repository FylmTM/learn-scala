package lectures.part3fp

class AnonymousFunctions extends App {

  val doubler: Int => Int = x => x * 2
  val superAdd = (x: Int) => (y: Int) => x + y

}
