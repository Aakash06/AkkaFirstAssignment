package edu.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class PurchaseRequestHandler(validationActor: ActorRef) extends Actor  with ActorLogging{

  override def receive ={
  case Customer(name,address,creditCardNumber,mobileNumber)=>
    log.info(s"Request by Customer Name :$name 1st ")
    validationActor.forward(Customer(name,address,creditCardNumber,mobileNumber))

  case msg=> log.error("Wrong Purchase")
  }
}