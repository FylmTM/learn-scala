package lectures.part3concurrency

import java.util.concurrent.Executors

object Intro extends App {

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Running in parallel")
  })

  //aThread.start()
  //aThread.join()

  val threadHello = new Thread(() => {
    (1 to 5).foreach(_ => println("hello"))
  })
  val threadGoodbye = new Thread(() => {
    (1 to 5).foreach(_ => println("goodbye"))
  })
  //threadHello.start()
  //threadGoodbye.start()

  // executors
  val pool = Executors.newFixedThreadPool(10)
  //pool.execute(() => println("something in the thread pool"))
  //pool.execute(() => {
  //  Thread.sleep(1000)
  //  println("done after 1 second")
  //})
  //pool.execute(() => {
  //  Thread.sleep(1000)
  //  println("almost done")
  //  Thread.sleep(1000)
  //  println("done after 2 seconds")
  //})

  pool.shutdown()

  def runInParallel = { // race condition
    var x = 0
    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })
    thread1.start()
    thread2.start()
    println(x)
  }
  //for(_ <- 1 to 10000) runInParallel

  class BankAccount(var amount: Int) {
    override def toString: String = amount.toString
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
    //println(s"I've bought $thing")
    //println("my account is now " + account)
  }

  /*
  for (_ <- 1 to 1000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iphone12", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(10)
    if (account.amount != 43000) println("AHA: " + account)
  }
  */

  // option #1: synchronized
  def buySafe(account: BankAccount, thing: String, price: Int) =
    account.synchronized {
      account.amount -= price
    }

  // option #2: use @volatile

  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread =
    new Thread(() => {
      if (i < maxThreads) {
        val newThreads = inceptionThreads(maxThreads, i + 1)
        newThreads.start()
        newThreads.join()
      }
      println(s"Hello from thread $i")
    })

  inceptionThreads(50).start()
}
