package edu.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, Props}


class ValidationActor(purchaseActor: ActorRef) extends Actor  with ActorLogging{
  var availability = 1000
  override def receive ={
    case Customer(name,address,creditCardNumber,mobileNumber) if availability > 0 =>{ log.info(s"Validation Completed")
      availability-=1
      purchaseActor.forward(Customer(name,address,creditCardNumber,mobileNumber))}
    case msg => log.error(s"Out of stock ")
  }
}
