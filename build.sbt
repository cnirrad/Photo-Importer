name := "photo importer"

version := "0.0.1"

scalaVersion := "2.10.1"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies += "com.drewnoakes" % "metadata-extractor" % "2.4.0-beta-1"

libraryDependencies += "commons-io" % "commons-io" % "2.1"

