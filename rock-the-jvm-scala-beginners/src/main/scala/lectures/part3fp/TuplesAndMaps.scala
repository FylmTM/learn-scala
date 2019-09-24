package lectures.part3fp

object TuplesAndMaps extends App {

  val phonebook = Map(("Jim", 555), "Daniel" -> 789)
  println(phonebook)


  type Network = Map[String, Set[String]]

  def add(network: Network, person: String): Network =
    network + (person -> Set())

  def friend(network: Network, a: String, b: String): Network = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: Network, a: String, b: String): Network = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Network, person: String): Network = {
    network.mapValues(_ - person) - person
  }

  val empty: Network = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriend(network: Network, person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriend(testNet, "Bob"))

  def mostFriends(network: Network): String =
    network.maxBy(_._2.size)._1

  println(mostFriends(testNet))

  def nPeopleWithNoFriend(network: Network): Int =
    network.count(_._2.isEmpty)

  println(nPeopleWithNoFriend(testNet))
}
