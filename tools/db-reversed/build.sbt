name := "db-reverse"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.12"

lazy val p = (project in file(".")).enablePlugins(ScalikejdbcPlugin)

scalikejdbcGeneratorSettings in Compile ~= { settings =>
  settings.copy(tableNameToSyntaxName = { tableName =>
    settings.tableNameToSyntaxName(tableName) match {
      case "as" => "as_" // https://github.com/scalikejdbc/scalikejdbc/issues/810
      case syntaxName => syntaxName
    }
  })
}
