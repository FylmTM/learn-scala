package exercises

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  override def toString: String = s"[$printElements]"
}

object Empty extends MyList {

  override def head: Nothing = throw new NoSuchElementException

  override def tail: Nothing = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[A](element: A): MyList[A] = new Cons(element, Empty)

  override def printElements: String = ""
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) head.toString
    else head + " " + t.printElements
}

class Animal
class Cat extends Animal
class Dog extends Animal

object MylistApp extends App {
  val list: MyList[Cat] = Empty

  val list1 = list.add(new Cat)
  val list2 = list.add(new Dog)
}
