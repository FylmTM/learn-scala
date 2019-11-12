package lectures.part5ts

object StructuralTypes extends App {

  // structural types
  type JavaCloseable = java.io.Closeable

  class HipsterCloseable {
    def close(): Unit = println("closing")

    def closeSilently(): Unit = ()
  }

  type UnifiedCloseable = {
    def close(): Unit
  } // structural type

  def closeQuietly(closeable: UnifiedCloseable): Unit = closeable.close()

  closeQuietly(new JavaCloseable {
    override def close(): Unit = ()
  })
  closeQuietly(new HipsterCloseable)

  // Type refinements
  type AdvancedCloseable = JavaCloseable {
    def closeSilently(): Unit
  }

  class AdvancedJavaCloseable extends JavaCloseable {
    override def close(): Unit = println("Java closes")

    def closeSilently(): Unit = println("Java closes silently")
  }

  def closeShh(closeable: AdvancedCloseable): Unit = closeable.closeSilently()

  closeShh(new AdvancedJavaCloseable)
  //closeShh(new HipsterCloseable)

  // using structural types as standalone types
  def altClose(closeable: {def close(): Unit}): Unit = closeable.close()

  // type-checking => duck typing
  type SoundMaker = {
    def makeSound(): Unit
  }

  class Dog {
    def makeSound(): Unit = println("bark!")
  }
  class Car {
    def makeSound(): Unit = println("vroom!")
  }
  val dog: SoundMaker = new Dog
  val car: SoundMaker = new Car
  // static duck typing

  // Caveat: based on reflection

  // 1.
  trait CBL[+T] {
    def head: T

    def tail: CBL[T]
  }
  class Human {
    def head: Brain = new Brain
  }
  class Brain {
    override def toString: String = "brains"
  }

  def f[T](somethingWithAHead: {def head: T}): Unit = println(somethingWithAHead.head)
  case object CBNil extends CBL[Nothing] {
    def head: Nothing = ???
    def tail: CBL[Nothing] = ???
  }
  case class CBCons[T](override val head: T, override val tail: CBL[T]) extends CBL[T]

  f(CBCons(2, CBNil))
  f(new Human)

  // 2.
  object HeadEqualizer {
    type Headable[T] = {def head: T}

    def ===[T](a: Headable[T], b: Headable[T]): Boolean = a.head == b.head
  }

  val brainsList = CBCons(new Brain, CBNil)
  val stringsList = CBCons("Brainz", CBNil)
  HeadEqualizer.===(brainsList, new Human)
  HeadEqualizer.===(new Human, stringsList) // wrong, not type safe
}
