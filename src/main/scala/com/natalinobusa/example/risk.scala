package risk

import akka.actor.{ ActorSystem, Actor, Props }

case object Start

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem()
    system.actorOf(Props[RiskActor]) ! Start
  }
}

class RiskActor extends Actor {
  val worldActor = context.actorOf(Props[WorldActor])
  def receive = {
    case Start ⇒ worldActor ! "risk"
    case s: String ⇒
      println("Received message: %s".format(s))
      context.system.shutdown()
  }
}

class WorldActor extends Actor {
  def receive = {
    case s: String ⇒ sender ! s.toUpperCase + ": time to conquer the world!"
  }
}

