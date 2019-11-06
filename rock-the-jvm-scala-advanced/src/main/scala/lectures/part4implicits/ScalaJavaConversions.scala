package lectures.part4implicits

import java.{util => ju}

object ScalaJavaConversions extends App {
  import collection.JavaConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)

  println(javaSet)

  val scalaSet = javaSet.asScala

  import collection.mutable._
  val numbersBuffers = ArrayBuffer[Int](1, 2, 3)
  val juNumberBuffer = numbersBuffers.asJava

  println(juNumberBuffer.asScala eq numbersBuffers)

  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get) else None
  )
}
