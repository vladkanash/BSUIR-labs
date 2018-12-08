name := "SI"

version := "0.1"

scalaVersion := "2.12.7"

Compile/mainClass := Some("Main")

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"
libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration"