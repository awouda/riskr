package com.natalinobusa.riskr

import org.scalatest._

import akka.actor.ActorSystem
import akka.testkit.TestActorRef

import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout

class WorldActorSpec extends FunSpec {

  implicit val system = ActorSystem("TestSys")
  implicit val timeout = Timeout(1.seconds)

  val actorRef = TestActorRef[WorldActor]
  val actor = actorRef.underlyingActor

  describe("A World actor") {
    it("knows the answer is 42") {
      assert(actor.theAnswer === 42)
    }
    
    // the following is ok, but the akka-testkit is even better
    // see class WorldActorTestkitSpec for the example
    
    it("send back 42 to TheQuestion") {
       val future = actorRef ? TheQuestion
       val result = Await.result(future, timeout.duration).asInstanceOf[Int]
       assert(result===42)
    }
  }
}
