import AssemblyKeys._

assemblySettings

// first two lines are for sbt-assembly

name := "CatoGui"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq("net.liftweb" %% "lift-json" % "2.5+",
                            "org.clapper" %% "argot" % "1.0.3")


