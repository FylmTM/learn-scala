package lectures.part4implicits


trait MyTypeClassTemplate[T] {
  def action(value: T): String
}
object MyTypeClassTemplate {
  def apply[T](implicit instance: MyTypeClassTemplate[T]): MyTypeClassTemplate[T] = instance
}
