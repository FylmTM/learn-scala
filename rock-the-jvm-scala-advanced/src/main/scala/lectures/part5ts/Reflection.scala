package lectures.part5ts

object Reflection extends App {

  // reflection + macros + quasiquotes => METAPROGRAMMING

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"Hi, my name is $name")
  }

  // 0 - import
  import scala.reflect.runtime.{universe => ru}

  // 1 - mirror
  val m = ru.runtimeMirror(getClass.getClassLoader)

  // 2  create a class object
  val clazz = m.staticClass("lectures.part5ts.Reflection.Person")

  // 3 - create a reflected mirror = "can do things"
  val cm = m.reflectClass(clazz)

  // 4 - get the constructor
  val constructor = clazz.primaryConstructor.asMethod

  // 5 - reflect the constructor
  val constructorMirror = cm.reflectConstructor(constructor)

  // 6 - invoke constructor
  val instance = constructorMirror.apply("John") // from the wire as a serialized object

  println(instance)

  // I have an instance
  val p = Person("mary") //from the wire as a serialized object
  // method name computed from somewhere else
  val methodName = "sayMyName"
  // 1 - mirror
  // 2 - reflect the instance
  val reflected = m.reflect(p)
  // 3 - method symbol
  val methodSymbol = ru.typeOf[Person].decl(ru.TermName(methodName)).asMethod
  // 4 - reflect the method
  val method = reflected.reflectMethod(methodSymbol)
  // 5 - invoke the method
  method.apply()

  // type erasure

  // pp #1: differentiate types at runtime
  val numbers = List(1, 2, 3)
  //numbers match {
  //  case listOfStrings: List[String] => println("list of strings")
  //  case listOfInt: List[Int] => println("list of int")
  //}

  // pp #2: limitation on overloads
  //def processList(list: List[Int]): Int = 43
  //def processList(list: List[String]): Int = 45

  // Type Tags

  // 0 - import
  import ru._

  // 1 - creating a tag type "manually"
  val ttag = typeTag[Person]
  println(ttag.tpe)

  class MyMap[K, V]
  def getTypeArguments[T](value: T)(implicit typeTag: TypeTag[T]) = typeTag.tpe match {
    case TypeRef(_, _, typeArguments) => typeArguments
    case _ => List()
  }

  val myMap = new MyMap[Int, String]
  val typeArgs = getTypeArguments(myMap)
  println(typeArgs)

  def isSubtype[A, B](implicit ttagA: TypeTag[A], ttagB: TypeTag[B]): Boolean = {
    ttag.tpe <:< ttagB.tpe
  }

  class Animal
  class Dog extends Animal
  println(isSubtype[Dog, Animal])

  val anotherMethodSymbol = typeTag[Person].tpe.decl(ru.TermName(methodName)).asMethod
  val sameMethod = reflected.reflectMethod(anotherMethodSymbol)
  sameMethod.apply()
}
