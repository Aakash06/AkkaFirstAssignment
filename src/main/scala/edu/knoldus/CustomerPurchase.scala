package edu.knoldus

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration.DurationInt

object CustomerPurchase extends App{

  val system = ActorSystem("CustomerActorSystem")

  val props = Props(classOf[PurchaseActor])
  val ref = system.actorOf(props)

  val props2 = Props(classOf[ValidationActor],ref)
  val ref2 = system.actorOf(props2)

  val props3 = Props(classOf[PurchaseRequestHandler],ref2)
  val ref3 = system.actorOf(props3)

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val fpurchase = ref3 ? Customer("Aakash","Knoldus",456321254,89658965)
  fpurchase.foreach(println)

  val fpurchase2 = ref3 ? Customer("Aakash 2","Knoldus",456321254,89658965)
  fpurchase2.foreach(println)
}

