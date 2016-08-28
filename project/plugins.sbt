logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.5")
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

// Example how to use the companion SBT plugin
resolvers += Resolver.bintrayIvyRepo("theranger", "play-plugins")
addSbtPlugin("ee.risk.sbt.plugins" % "sbt-wsdl2java" % "0.2.0")
