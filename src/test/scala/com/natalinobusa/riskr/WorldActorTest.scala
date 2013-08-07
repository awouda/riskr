package com.natalinobusa.riskr

import org.scalatest.FunSuite

import akka.actor.{ActorSystem,Props}
import akka.testkit.TestActorRef

class WorldActorTest extends FunSuite {
  
  implicit val system = ActorSystem("TestSys")
  
  val testActorRef = TestActorRef[WorldActor]
  val actor = testActorRef.underlyingActor
  
  test("The world knows that the aswer is 42"){
    assert(actor.theAnswer==42)
  }
  
  // the following will not work
  // Internals of the actor reference are not directly accessible
  
  test("The world actor answer is not directly accessible"){
    //val world = system.actorOf(Props[WorldActor])
    //assert(world.theAnswer==42)
    assert(true)
  }
  
}
