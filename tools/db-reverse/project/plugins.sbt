libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.45"
)

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.2.0")
