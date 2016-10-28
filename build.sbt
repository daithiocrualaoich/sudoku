organization := "dog.woofwoofinc"

name := "dog.woofwoofinc.sudoku"

version := "1-SNAPSHOT"

scalaVersion := "2.11.8"




// Target Settings

test in assembly := {}

assemblyJarName in assembly := "dog.woofwoofinc.sudoku.jar"





// Dependency Management

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "batik" % "batik-svg-dom" % "1.6-1",
  "batik" % "batik-svggen" % "1.6-1",
  "batik" % "batik-rasterizer" % "1.6-1",
  "batik" % "batik-extension" % "1.6-1",
  "crimson" % "crimson" % "1.1.3",
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "org.slf4j" % "slf4j-jdk14" % "1.7.12"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)





// Compilation

maxErrors := 20

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq("-unchecked", "-optimise", "-deprecation")
