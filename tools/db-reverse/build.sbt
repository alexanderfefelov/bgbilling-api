name := "db-reverse"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.12"

lazy val p = (project in file(".")).enablePlugins(ScalikejdbcPlugin)

scalikejdbcGeneratorSettings in Compile ~= { setting =>
  setting.copy(tableNameToSyntaxName = { tableName =>
    setting.tableNameToSyntaxName(tableName) match {
      case "as" => "as_"
      case syntaxName => syntaxName
    }
  })
}
