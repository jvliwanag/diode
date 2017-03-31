name := "diode"

//version := "2017.1.0"

enablePlugins(ScalaJSPlugin)

val scala211 = "2.11.8"

val scala212 = "2.12.0"

scalaVersion := scala211

crossScalaVersions := Seq(scala211, scala212)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions"
)

//Dependencies

val diodeVersion = "1.1.0"

libraryDependencies += "scalajs-react-interface" %%% "core" % "2017.3.26-beta" % Provided

libraryDependencies += "me.chrons" %%% "diode" % diodeVersion

//bintray
resolvers += Resolver.jcenterRepo

organization := "scalajs-react-interface"

licenses += ("Apache-2.0", url(
  "https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization := Some("scalajs-react-interface")

bintrayRepository := "maven"

bintrayVcsUrl := Some("git@github.com:scalajs-react-interface/diode.git")

publishArtifact in Test := false

//Test
resolvers += Resolver.bintrayRepo("scalajs-react-interface", "maven")
libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.0" % Test
scalaJSStage in Global := FastOptStage
