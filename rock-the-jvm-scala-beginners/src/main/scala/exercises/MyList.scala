package exercises

abstract class MyList[+A] {
  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B]

  def printElements: String

  override def toString: String = s"[$printElements]"

  def ++[B >: A](list: MyList[B]): MyList[B]

  def map[B](transformer: MyTransformer[A, B]): MyList[B]

  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

  def filter(predicate: MyPredicate[A]): MyList[A]
}

case object Empty extends MyList {

  override def head: Nothing = throw new NoSuchElementException

  override def tail: Nothing = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[A](element: A): MyList[A] = new Cons(element, Empty)

  override def printElements: String = ""

  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty

  override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) head.toString
    else head + " " + t.printElements

  override def map[B](transformer: MyTransformer[A, B]): MyList[B] =
    new Cons[B](transformer.transform(this.head), this.tail.map(transformer))

  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(head) ++ tail.flatMap(transformer)
  }

  override def filter(predicate: MyPredicate[A]): MyList[A] =
    if (predicate.test(this.head)) Cons(this.head, this.tail.filter(predicate))
    else this.tail.filter(predicate)

  override def ++[B >: A](list: MyList[B]): MyList[B] = Cons(h, t ++ list)
}

class Animal

class Cat extends Animal

class Dog extends Animal


trait MyPredicate[-T] {
  def test(e: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(e: A): B
}

object MylistApp extends App {
  val list = Cons(1, Cons(2, Cons(3, Cons(4, new Cons(5, new Cons(6, Empty))))))

  println(list)
  println(list.map(new MyTransformer[Int, Int] {
    override def transform(e: Int): Int = e * 2
  }))
  println(list.filter(new MyPredicate[Int] {
    override def test(e: Int): Boolean = e % 2 == 0
  }))
  println(list.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(e: Int): MyList[Int] = new Cons(e, new Cons(e, Empty))
  }))
}
