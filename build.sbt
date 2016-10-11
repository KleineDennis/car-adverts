name := """car-adverts"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  evolutions,
  "com.typesafe.play" % "play-slick-evolutions_2.11" % "2.0.2",
  "com.typesafe.play" % "play-slick_2.11" % "2.0.2",
  "org.postgresql"    %  "postgresql"  % "9.4-1201-jdbc41",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  specs2 % Test

)
