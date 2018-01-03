name := "bgbilling-api-soap-util"
organization := "com.github.alexanderfefelov"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.1"
)

val targetPackage = "com.github.alexanderfefelov.bgbilling.api.soap.util"

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion,
  "builtBy" -> {System.getProperty("user.name")},
  "builtOn" -> {java.net.InetAddress.getLocalHost.getHostName},
  "builtAt" -> {new java.util.Date()},
  "builtAtMillis" -> {System.currentTimeMillis()}
)
buildInfoPackage := targetPackage

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)

doc in Compile := target.map(_ / "none").value
