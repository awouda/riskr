package com.natalinobusa.riskr

import akka.actor.{Actor,Props}

class Customer(name:String) extends Actor {

  def receive = {
    case WhoAmI => sender !  name
  }
}


case object WhoAmI


