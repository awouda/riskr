package com.natalinobusa.riskr

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

import akka.testkit.TestKit
import akka.testkit.ImplicitSender

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import org.scalatest.BeforeAndAfterAll

class WorldActorTestkitSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpec with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem())

  // instantiate the actor before starting the tests
  val world = system.actorOf(Props[WorldActor])

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A World actor" must {

    "send back a war chant when receiving a text" in {
      world ! "risk"
      expectMsg("RISK: time to conquer the world!")
    }

    "send back 42 to TheQuestion" in {
      world ! TheQuestion
      expectMsg(42)
    }

  }
}