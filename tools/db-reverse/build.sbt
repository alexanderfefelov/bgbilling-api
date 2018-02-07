name := "db-reverse"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.12"

lazy val p = (project in file(".")).enablePlugins(ScalikejdbcPlugin)
