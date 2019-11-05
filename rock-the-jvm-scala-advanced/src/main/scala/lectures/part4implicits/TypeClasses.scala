package lectures.part4implicits

object TypeClasses extends App {

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name ($age yo) <a href=$email/></div>"
  }

  // option 2 - pattern matching
  object HTMLSerializerPM {
    def serializeToHTML(value: Any) = value match {
      case _ => ???
    }
  }

  // option 3?

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div color=blue>$value</div>"
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String =
      s"<div>${user.name} (${user.age} yo) <a href=${user.email}/></div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")
  println(UserSerializer.serialize(john))

  // 1 - we can define serializers for other types
  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString}</div>"
  }
  // 2 - we can define MULTIPLE serializers
  //object PartialUserSerializer extends HTMLSerializer[User] {
  //  override def serialize(user: User): String = s"<div>${user.name}</div>"
  //}

  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]): HTMLSerializer[T] = serializer
  }

  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize(john))
  println(HTMLSerializer[User].serialize(john))

  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML)
  /*
  - extend to new types
  - choose implementation
   */
  //println(john.toHTML(UserSerializer))
  println(2.toHTML)

  /*
  - type class itself --- HTMLSerializer[T] { .. }
  - type class instances (some of which are implicit) --- UserSerializer, IntSerializer
  - conversion with implicit classes --- HTMLEnrichment
   */

  def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body> ${content.toHTML(serializer)} </body></html>"

  def htmlSugar[T : HTMLSerializer](content: T): String =
    s"<html><body> ${content.toHTML} </body></html>"

  def htmlSugarExplicitly[T : HTMLSerializer](content: T): String =
    s"<html><body> ${content.toHTML(implicitly[HTMLSerializer[T]])} </body></html>"

  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code
  val standardPerms = implicitly[Permissions]
}
