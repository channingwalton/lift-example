import com.earldouglas.xwp.JettyPlugin
import sbt._
import Keys._

object Build extends Build {

  val jettyVersion = "9.3.6.v20151106"

  val defaults = Seq(
    organization := "com.casualmiracles",
    scalaVersion := "2.11.7",
    scalacOptions in (Compile, compile) += "-deprecation",
    externalResolvers := Seq(
      Resolver.defaultLocal,
      "Boost all-maven repo" at "http://boost-build-imac.local:8081/nexus/content/groups/all-maven/",
      Resolver.url("boost-all-ivy", new URL("http://boost-build-imac.local:8081/nexus/content/groups/all-ivy/"))(Resolver.ivyStylePatterns)
    )
  )

  val exampleSettings = Seq(
    libraryDependencies ++= List(
      "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
      "net.liftweb"       %% "lift-webkit"    % "2.6.2",
      "net.liftmodules"   %% "lift-jquery-module_2.6" % "2.8",
      "org.eclipse.jetty" % "jetty-webapp"            % jettyVersion,
      "org.eclipse.jetty" % "jetty-plus"              % jettyVersion,
      "org.eclipse.jetty" % "jetty-servlets"          % jettyVersion
    )
  )

  lazy val example = Project(
    "example", file("example"),
    settings = defaults ++ exampleSettings
  ).enablePlugins(JettyPlugin)

  lazy val root = Project("rxlift", file(".")).aggregate(example)
}
