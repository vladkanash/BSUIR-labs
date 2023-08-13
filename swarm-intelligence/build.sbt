name := "SI"

version := "0.1"

scalaVersion := "2.12.7"

Compile/mainClass := Some("fss.Main")

libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration"

mainClass in Compile := Some("fss.Main")