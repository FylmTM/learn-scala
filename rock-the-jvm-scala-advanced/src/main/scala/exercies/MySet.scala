package exercies

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {

  def apply(elem: A): Boolean = contains(elem)

  def contains(elem: A): Boolean

  def +(elem: A): MySet[A]

  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]

  def flatMap[B](f: A => MySet[B]): MySet[B]

  def filter(predicate: A => Boolean): MySet[A]

  def forEach(f: A => Unit): Unit

  def -(elem: A): MySet[A]

  def &(anotherSet: MySet[A]): MySet[A]

  def --(anotherSet: MySet[A]): MySet[A]

  def unary_! : MySet[A]
}

class EmptySet[A] extends MySet[A] {

  override def contains(elem: A): Boolean = false

  override def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  override def filter(predicate: A => Boolean): MySet[A] = this

  override def forEach(f: A => Unit): Unit = ()

  override def -(elem: A): MySet[A] = this

  override def &(anotherSet: MySet[A]): MySet[A] = this

  override def --(anotherSet: MySet[A]): MySet[A] = this

  override def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {

  override def contains(elem: A): Boolean = property(elem)

  override def +(elem: A): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || x == elem)

  override def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  override def map[B](f: A => B): MySet[B] = politelyFail

  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  override def filter(predicate: A => Boolean): MySet[A] =
    new PropertyBasedSet[A](x => property(x) && predicate(x))

  override def forEach(f: A => Unit): Unit = politelyFail

  override def -(elem: A): MySet[A] = filter(x => !x.equals(elem))

  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  private def politelyFail = throw new IllegalArgumentException("Really deep rabiit hole!")
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {

  override def contains(elem: A): Boolean = elem.equals(head) || tail.contains(elem)

  override def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): MySet[B] = (tail map f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)

  override def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  override def forEach(f: A => Unit): Unit = {
    f(head)
    tail forEach f
  }

  override def -(elem: A): MySet[A] = filter(!_.equals(elem))

  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet(_))

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this(x))
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A] = new EmptySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq)
  }
}

object MySetPlayground extends App {
  val s = MySet(1, 2, 3, 4)
  s + 5 ++ MySet(-1, -2) + 3 map (_ * 10) forEach println

  println("--")
  s.-(3).forEach(println)

  val s1 = MySet(2, 3, 4, 5)
  val s2 = MySet(1, 2, 3, 4)

  println("--")
  s1.&(s2).forEach(println)
  println("--")
  s1.--(s2).forEach(println)

  val negative = !s
  println(negative(2))
  println(negative(99))

  val negativeEven = negative.filter(_ % 2 == 0)
  println(negativeEven(5))
  println(negativeEven(6))

  val negativeEven5 = negativeEven + 5
  println(negativeEven5(5))
  println(negativeEven5(7))
}
