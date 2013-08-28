package com.natalinobusa.riskr

import java.io.File
import org.parboiled.common.FileUtils
import scala.concurrent.duration._
import akka.actor.{Props, Actor}
import spray.routing.{HttpService, RequestContext}
import spray.can.server.Stats
import spray.can.Http
import spray.httpx.marshalling.Marshaller
import spray.util._
import spray.http._
import MediaTypes._
import spray.httpx.SprayJsonSupport

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class DemoServiceActor extends Actor with DemoService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing,
  // timeout handling or alternative handler registration
  def receive = runRoute(demoRoute)
}

// this trait defines our service behavior independently from the service actor
trait DemoService extends HttpService  with SprayJsonSupport {

  // we use the enclosing ActorContext's or ActorSystem's dispatcher for our Futures and Scheduler
  implicit def executionContext = actorRefFactory.dispatcher

  val login = path("login" / ".*".r)

  val demoRoute = {
    get {
      path("") {
        complete(index("alex"))
      } ~
      login {  cust_name => ctx =>
            actorRefFactory.actorOf(Props(classOf[SourceActor],ctx, cust_name))
      }
    }
  }

  def  index(name:String) = <html>
      <body>
        <h1>hello {name}
          !</h1>
        <ul>
          <li>
            <a href="/stop?method=post">/stop</a>
          </li>
        </ul>
      </body>
    </html>





}