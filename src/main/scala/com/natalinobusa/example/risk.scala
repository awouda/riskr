// Copyright 2013, Natalino Busa 
// http://www.linkedin.com/in/natalinobusa
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

