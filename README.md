Sudoku
======
Sudoku solver exercise.



Development
-----------
The exercise is built using SBT. To start SBT use
the script:

    ./sbt011

 To use in intellij, setup and use the idea plugin by creating the
 following definition in `~/.sbt/plugins/build.sbt`:

     resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

     libraryDependencies <+= (sbtVersion) {
       case "0.10.0" => "com.github.mpeltonen" % "sbt-idea_2.8.1" % "0.10.0" from "http://mpeltonen.github.com/maven/com/github/mpeltonen/sbt-idea_2.8.1/0.10.0/sbt-idea_2.8.1-0.10.0.jar"
       case "0.10.1" => "com.github.mpeltonen" % "sbt-idea_2.8.1" % "0.10.1-SNAPSHOT" from "http://mpeltonen.github.com/maven/com/github/mpeltonen/sbt-idea_2.8.1/0.10.1-SNAPSHOT/sbt-idea_2.8.1-0.10.1-SNAPSHOT.jar"
       case "0.11.0" => "com.github.mpeltonen" % "sbt-idea_2.9.1" % "0.11.0" from "http://mpeltonen.github.com/maven/com/github/mpeltonen/sbt-idea_2.9.1_0.11.0/0.11.0/sbt-idea-0.11.0.jar"
       case sbtv => error("Unsupported SBT version: " + sbtv)
     }


 Then run the following in interactive `sbt`:

     gen-idea

 The `sbt` console is very useful. FYou can do e.g. the following:

     > console
     ...
     Welcome to Scala version 2.9.1.final (Java HotSpot(TM) 64-Bit Server VM, Java 1.6.0_16).
     Type in expressions to have them evaluated.
     Type :help for more information.

     scala> ...

 Further documentation notes and useful items can be found in `dev`.





[sbt]: http://books.google.com/p/simple-build-tool/
[scalariform]: http://github.com/olim7t/sbt-scalariform
[markdown]: http://daringfireball.net/projects/markdown
