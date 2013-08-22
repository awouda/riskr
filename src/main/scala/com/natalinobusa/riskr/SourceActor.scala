package com.natalinobusa.riskr

import scala.concurrent.duration._
import akka.actor.Actor
import spray.http._
import spray.can.Http
import HttpHeaders._
import CacheDirectives._
import MediaTypes._
import spray.util._

import spray.routing.RequestContext

class SourceActor(ctx: RequestContext, name:String) extends Actor with SprayActorLogging {
  import context._

  val `text/event-stream` = MediaType.custom("text/event-stream")
  MediaTypes.register(`text/event-stream`)
  
  override def preStart() =
    system.scheduler.scheduleOnce(1.second, self, "tick")
 
  // override postRestart so we don't call preStart and schedule a new message
  override def postRestart(reason: Throwable) = {}

  log.info("Starting streaming message")
  val responseStart = HttpResponse(entity = HttpEntity(`text/event-stream`, ":\n\n"))
    .withHeaders(List(`Cache-Control`(`no-cache`)))



  ctx.responder ! ChunkedResponseStart(responseStart)

  def receive = {
    case "tick" ⇒
      // send another periodic tick after the specified delay
      system.scheduler.scheduleOnce(1.second, self, "tick")
      
      // do something useful here
      val nextChunk = MessageChunk("data for "+name+" :" + DateTime.now.toIsoDateTimeString + "\n\n")

      ctx.responder ! nextChunk
      
    case ev: Http.ConnectionClosed =>
      log.warning("Stopping response streaming due to {}", ev)

  }
}
