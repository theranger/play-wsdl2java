name := "play-wsdl2java"
organization := "ee.risk.play.modules"
version := "0.2.0"
scalaVersion := "2.11.8"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
bintrayVcsUrl := Some("https://github.com/theranger/play-wsdl2java.git")
bintrayRepository := "play-plugins"
crossPaths := false
publishMavenStyle := true

lazy val `play-wsdl2java` = (project in file(".")).enablePlugins(PlayScala)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
