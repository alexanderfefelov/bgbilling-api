name := "bgbilling-api-soap-util"
organization := "com.github.alexanderfefelov"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.1"
)

lazy val root = project in file(".")

doc in Compile := target.map(_ / "none").value
