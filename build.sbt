organization := "com.gu"

name := "sudoku"

version := "1-SNAPSHOT"

scalaVersion := "2.9.1"



// Dependency Management

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "org.slf4j" % "slf4j-jdk14" % "1.6.4"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)





// Development Settings

seq(com.typesafe.sbtscalariform.ScalariformPlugin.settings: _*)





// Compilation

maxErrors := 20

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalacOptions ++= Seq("-unchecked", "-optimise", "-deprecation")

//parallelExecution in Test := false
