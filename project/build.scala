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

import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "http://natalinobusa.com"
  val buildVersion      = "1.0.0"
  val buildScalaVersion = "2.10.2"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization  := buildOrganization,
    version       := buildVersion,
    scalaVersion  := buildScalaVersion,
    shellPrompt   := ShellPrompt.buildShellPrompt,
    scalacOptions := Seq("-deprecation", "-feature", "-encoding", "utf8")
  )
}

// Shell prompt which show the current project, 
// git branch and build version
object ShellPrompt {
  object devnull extends ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) { }
    def buffer[T] (f: => T): T = f
  }
  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
  )

  val buildShellPrompt = { 
    (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "%s:%s:%s> ".format (
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}

object Resolvers {
  val allResolvers = Seq (
    "Typesafe Repository"     at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype OSS Repository" at "https://oss.sonatype.org/content/groups/public/org/scalatest/"
  )
}

object Dependencies {
  val akkaVersion       = "2.2.0"

  val allDependencies = Seq(
    //akka
    "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    
    //testing
    "org.scalatest"     % "scalatest_2.10"    % "1.9.1" % "test"
  )
}

object DefaultBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  val appName = "risk"

  lazy val root = Project (
    id = appName,
    base = file ("."),
    settings = buildSettings ++ Seq (resolvers ++= allResolvers, libraryDependencies ++= allDependencies)
  ) 

}
