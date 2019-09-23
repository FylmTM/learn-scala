package exercises

abstract class MyList[+A] {
  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B]

  def printElements: String

  override def toString: String = s"[$printElements]"

  def ++[B >: A](list: MyList[B]): MyList[B]

  def map[B](transformer: A => B): MyList[B]

  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def filter(predicate: A => Boolean): MyList[A]

  def forEach(f: A => Unit)

  def sort(compare: (A, A) => Int): MyList[A]

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]

  def fold[B](start: B)(operator: (B, A) => B): B
}

case object Empty extends MyList {

  override def head: Nothing = throw new NoSuchElementException

  override def tail: Nothing = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[A](element: A): MyList[A] = Cons(element, Empty)

  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): MyList[B] = Empty

  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def forEach(f: Nothing => Unit): Unit = ()

  override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] = {
    if (!list.isEmpty) throw new RuntimeException("not same lengt")
    else Empty
  }

  override def fold[B](start: B)(operator: (B, Nothing) => B): B = {
    start
  }
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) head.toString
    else head + " " + t.printElements

  override def map[B](transformer: A => B): MyList[B] =
    new Cons[B](transformer(this.head), this.tail.map(transformer))

  override def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(head) ++ tail.flatMap(transformer)
  }

  override def filter(predicate: A => Boolean): MyList[A] =
    if (predicate(this.head)) Cons(this.head, this.tail.filter(predicate))
    else this.tail.filter(predicate)

  override def ++[B >: A](list: MyList[B]): MyList[B] = Cons(h, t ++ list)

  override def forEach(f: A => Unit): Unit = {
    f(head)
    tail.forEach(f)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0) Cons(x, sortedList)
      else Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)

    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
    if (list.isEmpty) throw new RuntimeException("not same length")
    else Cons(zip(h, list.head), tail.zipWith(list.tail, zip))
  }

  override def fold[B](start: B)(operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }
}

class Animal

class Cat extends Animal

class Dog extends Animal

object MylistApp extends App {
  val list = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Cons(6, Empty))))))

  println(list)
  println(list.map((e: Int) => e * 2))
  println(list.filter((e: Int) => e % 2 == 0))
  println(list.flatMap((e: Int) => Cons(e, Cons(e, Empty))))

  list.forEach(println)
  println(list.sort((x, y) => y - x))

  val l1 = Cons(1, Cons(2, Empty))
  val l2 = Cons("hello", Cons("scala", Empty))

  println(l1.zipWith[String, String](l2, _ + " - " + _))

  println(list.fold(0)(_ + _))
}
