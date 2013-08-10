package com.natalinobusa.riskr

import akka.actor.Actor

class WorldActor extends Actor {
  val theAnswer = 42

  def receive = {
    case TheQuestion => sender ! theAnswer
    case s: String => sender ! s.toUpperCase + ": time to conquer the world!"
  }
}
