import AssemblyKeys._

organization := "com.gu"

name := "sudoku"

version := "1-SNAPSHOT"

scalaVersion := "2.9.1"




// Target Settings

seq(sbtassembly.Plugin.assemblySettings: _*)

jarName in assembly := "sudoku.jar"





// Dependency Management

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "daithiocrualaoich GitHub Releases" at "http://daithiocrualaoich.github.com/maven/repo-releases"
)

libraryDependencies ++= Seq(
  "scalax" %% "graph-core" % "1.3.2",
  "batik" % "batik-svg-dom" % "1.6-1",
  "batik" % "batik-svggen" % "1.6-1",
  "batik" % "batik-rasterizer" % "1.6-1",
  "batik" % "batik-extension" % "1.6-1",
  "crimson" % "crimson" % "1.1.3",
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "org.slf4j" % "slf4j-jdk14" % "1.6.4"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)





// Development Settings

seq(scalariformSettings: _*)




// Compilation

maxErrors := 20

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalacOptions ++= Seq("-unchecked", "-optimise", "-deprecation")
