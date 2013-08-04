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
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  )
}

object Dependencies {
  val akkaVersion       = "2.2.0"

  val allDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion
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
