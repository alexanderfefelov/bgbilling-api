name := "bgbilling-api-soap-kernel"
organization := "com.github.alexanderfefelov"

scalaVersion := "2.11.12"

val dispatchV = "0.11.3"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6",
  "net.databinder.dispatch" %% "dispatch-core" % dispatchV
)

scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV
scalaxbPackageName in (Compile, scalaxb) := "com.github.alexanderfefelov.bgbilling.api.soap.kernel"

lazy val root = (project in file("."))
  .enablePlugins(ScalaxbPlugin)

doc in Compile := target.map(_ / "none").value
