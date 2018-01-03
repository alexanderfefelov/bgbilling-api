name := "bgbilling-api-soap-card"
organization := "com.github.alexanderfefelov"

scalaVersion := "2.11.12"

val dispatchV = "0.11.3"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6",
  "net.databinder.dispatch" %% "dispatch-core" % dispatchV
)

val targetPackage = "com.github.alexanderfefelov.bgbilling.api.soap.card

scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV
scalaxbPackageName in (Compile, scalaxb) := targetPackage

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion,
  "builtBy" -> {System.getProperty("user.name")},
  "builtOn" -> {java.net.InetAddress.getLocalHost.getHostName},
  "builtAt" -> {new java.util.Date()},
  "builtAtMillis" -> {System.currentTimeMillis()}
)
buildInfoPackage := targetPackage

lazy val root = (project in file("."))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)

doc in Compile := target.map(_ / "none").value
