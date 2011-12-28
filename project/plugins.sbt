resolvers ++= Seq(
  Classpaths.typesafeResolver
)

addSbtPlugin("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.7.2")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse" % "2.0.0-M1")
