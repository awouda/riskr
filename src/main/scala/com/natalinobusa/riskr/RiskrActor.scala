package com.natalinobusa.riskr

import akka.actor.{Actor,Props}

class RiskrActor extends Actor {
  val worldActor = context.actorOf(Props[WorldActor])
  def receive = {
    case Start => worldActor ! "risk"
    case s: String =>
      println("Received message: %s".format(s))
  }
}

