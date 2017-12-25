name := "bgbilling-api-card"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.3"
)

scalaxbDispatchVersion in (Compile, scalaxb) := "0.11.3"
scalaxbPackageName in (Compile, scalaxb) := "bgbilling.card"

lazy val root = (project in file("."))
  .enablePlugins(ScalaxbPlugin)

doc in Compile := target.map(_ / "none").value
