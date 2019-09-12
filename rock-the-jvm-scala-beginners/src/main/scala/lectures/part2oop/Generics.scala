package lectures.part2oop

object Generics extends App {

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = ???
  }

  object MyList {
    def empty[A]: MyList[A] = new MyList[A]
  }

  class MyMap[Key, Value] {
  }

  trait MyTrait[A] {
  }

  val emptyListOfIntegers = MyList.empty[Int]

  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes List[Cat] extends List[Animal] = Covariance

  class CovariantList[+A]

  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ??? HARD QUESTION.

  // 2. no = INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell, no! CONTRAVARIENCE
  class Trainer[-A]
  val contravariantList: Trainer[Cat] = new Trainer[Animal]

  // bounded types
  class Cage[A <: Animal](animal: A)

  val cage = new Cage(new Dog)

//  class Car
//  val newCage = new Cage(new Car)
}
