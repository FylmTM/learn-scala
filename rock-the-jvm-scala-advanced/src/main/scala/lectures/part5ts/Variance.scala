package lectures.part5ts

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Kitty extends Cat
  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  class Cage[T]
  // yes - covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  // no - invariance
  class ICage[T]
  val icage: ICage[Animal] = new ICage[Animal]

  // hello no - opposite = contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](animal: T) // invariant

  class CovariantCage[+T](val animal: T) // covariant position

  //class ContravariantCage[-T](val animal: T) // contravariant
  // val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)

  //class CovariantVariableCage[+T](var animal: T) // types of vars are in contravariant position
  //val ccage: CCage[Animal] = new CCage[Cat](new Cat)
  //ccage.animal = new Crocodile

  //class ContravariantVariableCage[-T](var animal: T) // also in covariant position
  //val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)

  class InvariantVariableCage[T](var animal: T) // ok

  //trait AnotherCovariantCage[+T] {
  //  def addAnimal(animal: T) // contravarient position
  //}
  //val ccage: CCage[Animal] = new CCage[Dog]
  //ccage.add(new Cat)

  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true
  }
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  acc.addAnimal(new Kitty)

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B]
  }
  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  // Method arguments are in contravariant position

  class PetShop[-T] {
    //def get(isItaPuppy: Boolean): T // method return types are in covariant position
    // val catShop = new PetShop[Animal] {
    //   def get(isItaPuppy: Boolean): Animal = new Cat
    // }
    // val dogShop: PetShop[Dog] = catShop
    // dogShop.get(true) // Evil cat!

    def get[S <: T](isItaPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  //val evilCat = shop.get(true, new Cat)

  class TerraNova extends Dog
  val bigFurry = shop.get(true, new TerraNova)

  /*
  Big rule
  - method arguments are in contravariant position
  - return types are in covariant position
   */

  /**
   * 1. invariant, covariant, contravariant Parking[T](things: List[T])
   *  def park(vehicle: T)
   *  def impound(vehicle: List[T])
   *  def checkVehicles(conditions: String): List[T]
   * 2. Use someone else API: IList[T]
   * 3. Parking = monad
   *   - flatMap
   */

  class IList[T]

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IParking[T](vehicles: List[T]) {
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVechicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  class CParking[+T](vehicles: List[T]) {
    def park[S >: T](vehicle: S): CParking[S] = ???
    def impound[S >: T](vehicles: List[S]): CParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  class XParking[-T](vehicles: List[T]) {
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
    def checkVehicles[S <: T](conditions: String): List[S] = ???

    def flatMap[R <: T, S](f: R => XParking[S]): XParking[S] = ???
  }

  /*
  Rule of thumb
  - use covariance = collection of things
  - use contravariance = group of actions
   */

  class CParking2[+T](vehicles: IList[T]) {
    def park[S >: T](vehicle: S): CParking2[S] = ???
    def impound[S >: T](vehicles: IList[S]): CParking2[S] = ???
    def checkVehicles[S >: T](conditions: String): IList[S] = ???
  }

  class XParking2[-T](vehicles: IList[T]) {
    def park(vehicle: T): XParking2[T] = ???
    def impound[S <: T](vehicles: IList[S]): XParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???
  }

  // flatMap

}
