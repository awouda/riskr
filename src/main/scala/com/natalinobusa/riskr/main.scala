// Copyright 2013, Natalino Busa 
// http://www.linkedin.com/in/natalinobusa
//
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

package com.natalinobusa.riskr

import akka.actor.{ ActorSystem, Actor, Props }
import akka.io.IO
import spray.can.Http

case object Start
case object TheQuestion

object Boot extends App {
  implicit val system = ActorSystem()
  
  // create and start our service actor
  val service = system.actorOf(Props[DemoServiceActor], "demo-service")
  
  // add my own actors
  val world   =  system.actorOf(Props[WorldActor])

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, "localhost", port = 8080)

}

