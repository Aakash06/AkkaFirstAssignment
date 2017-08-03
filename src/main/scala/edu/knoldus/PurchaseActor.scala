package edu.knoldus

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

case object SamsungGalaxyS8

class workers extends Actor with ActorLogging{

  override def receive = {
    case Customer(name,address,creditCardNumber,mobileNumber)=> log.info(s"Purchase Completed :$name ")
      sender() ! SamsungGalaxyS8

    case msg => log.error("Sorry! System Errors")
  }
}

class PurchaseActor extends Actor {
  var router = {
    val routees = Vector.fill(5) {
      val r = context.actorOf(Props[workers])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case w: Customer =>
      router.route(w, sender())
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[workers])
      context watch r
      router = router.addRoutee(r)
  }
}