resolvers ++= Seq(
  Classpaths.typesafeResolver
)

addSbtPlugin("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse" % "2.0.0-M1")
