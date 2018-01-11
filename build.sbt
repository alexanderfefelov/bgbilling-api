name := "bgbilling-api"
organization := "com.github.alexanderfefelov"

lazy val scalaV = "2.11.12"

lazy val dispatchV = "0.11.3"
lazy val logbackClassicV = "1.2.3"
lazy val mysqlConnectorJavaV = "5.1.45"
lazy val scalaParserCombinatorsV = "1.0.6"
lazy val scalaXmlV = "1.0.6"
lazy val scalikejdbcV = "3.1.0"
lazy val scalikejdbcSyntaxSupportMacro = scalikejdbcV
lazy val typesafeConfigV = "1.3.1"

lazy val root = (project in file("."))
  .aggregate(db, soap, util)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val commonSettings = Seq(
  organization := "com.github.alexanderfefelov",
  scalaVersion := scalaV,
  version := "0.1.0-SNAPSHOT",
  doc in Compile := target.map(_ / "none").value,
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion,
    "builtBy" -> {System.getProperty("user.name")},
    "builtOn" -> {java.net.InetAddress.getLocalHost.getHostName},
    "builtAt" -> {new java.util.Date()},
    "builtAtMillis" -> {System.currentTimeMillis()}
  )
)

lazy val db = (project in file("db"))
  .aggregate(dbBill, dbInet, dbKernel, dbMoneta, dbOss, dbQiwi, dbRscm, dbUtil)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val dbTargetPackage = "com.github.alexanderfefelov.bgbilling.api.db"

lazy val dbBillTargetPackage = dbTargetPackage + ".bill"
lazy val dbBill = (project in file("db/bill"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-bill",
    commonSettings,
    buildInfoPackage := dbBillTargetPackage
  )

lazy val dbInetTargetPackage = dbTargetPackage + ".inet"
lazy val dbInet = (project in file("db/inet"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-inet",
    commonSettings,
    buildInfoPackage := dbInetTargetPackage
  )

lazy val dbKernelTargetPackage = dbTargetPackage + ".kernel"
lazy val dbKernel = (project in file("db/kernel"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-kernel",
    commonSettings,
    buildInfoPackage := dbKernelTargetPackage
  )

lazy val dbMonetaTargetPackage = dbTargetPackage + ".moneta"
lazy val dbMoneta = (project in file("db/moneta"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-moneta",
    commonSettings,
    buildInfoPackage := dbMonetaTargetPackage
  )

lazy val dbOssTargetPackage = dbTargetPackage + ".oss"
lazy val dbOss = (project in file("db/oss"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-oss",
    commonSettings,
    buildInfoPackage := dbOssTargetPackage
  )

lazy val dbQiwiTargetPackage = dbTargetPackage + ".qiwi"
lazy val dbQiwi = (project in file("db/qiwi"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-qiwi",
    commonSettings,
    buildInfoPackage := dbQiwiTargetPackage
  )

lazy val dbRscmTargetPackage = dbTargetPackage + ".rscm"
lazy val dbRscm = (project in file("db/rscm"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-rscm",
    commonSettings,
    buildInfoPackage := dbRscmTargetPackage
  )

lazy val dbUtilTargetPackage = dbTargetPackage + ".util"
lazy val dbUtil = (project in file("db/util"))
  .dependsOn(util)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-util",
    commonSettings,
    buildInfoPackage := soapUtilTargetPackage,
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcV,
      "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcSyntaxSupportMacro,
      "mysql" % "mysql-connector-java" % mysqlConnectorJavaV,
      "ch.qos.logback" % "logback-classic" % logbackClassicV
    )
  )

lazy val soap = (project in file("soap"))
  .aggregate(soapBill, soapCard, soapInet, soapKernel, soapMoneta, soapOss, soapQiwi, soapRscm, soapSubscription, soapUtil)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val soapTargetPackage = "com.github.alexanderfefelov.bgbilling.api.soap"

lazy val scalaxbLibraryDependencies = Seq(
  "org.scala-lang.modules" %% "scala-xml" % scalaXmlV,
  "org.scala-lang.modules" %% "scala-parser-combinators" % scalaParserCombinatorsV,
  "net.databinder.dispatch" %% "dispatch-core" % dispatchV
)

lazy val soapBillTargetPackage = soapTargetPackage + ".bill"
lazy val soapBill = (project in file("soap/bill"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-bill",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapBillTargetPackage,
    buildInfoPackage := soapBillTargetPackage
  )

lazy val soapCardTargetPackage = soapTargetPackage + ".card"
lazy val soapCard = (project in file("soap/card"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-card",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapCardTargetPackage,
    buildInfoPackage := soapCardTargetPackage
  )

lazy val soapInetTargetPackage = soapTargetPackage + ".inet"
lazy val soapInet = (project in file("soap/inet"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-inet",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapInetTargetPackage,
    buildInfoPackage := soapInetTargetPackage
  )

lazy val soapKernelTargetPackage = soapTargetPackage + ".kernel"
lazy val soapKernel = (project in file("soap/kernel"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-kernel",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapKernelTargetPackage,
    buildInfoPackage := soapKernelTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapMonetaTargetPackage = soapTargetPackage + ".moneta"
lazy val soapMoneta = (project in file("soap/moneta"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-moneta",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapMonetaTargetPackage,
    buildInfoPackage := soapMonetaTargetPackage
  )

lazy val soapOssTargetPackage = soapTargetPackage + ".oss"
lazy val soapOss = (project in file("soap/oss"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-oss",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapOssTargetPackage,
    buildInfoPackage := soapOssTargetPackage
  )

lazy val soapQiwiTargetPackage = soapTargetPackage + ".qiwi"
lazy val soapQiwi = (project in file("soap/qiwi"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-qiwi",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapQiwiTargetPackage,
    buildInfoPackage := soapQiwiTargetPackage
  )

lazy val soapRscmTargetPackage = soapTargetPackage + ".rscm"
lazy val soapRscm = (project in file("soap/rscm"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-rscm",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapRscmTargetPackage,
    buildInfoPackage := soapRscmTargetPackage
  )

lazy val soapSubscriptionTargetPackage = soapTargetPackage + ".subscription"
lazy val soapSubscription = (project in file("soap/subscription"))
  .dependsOn(soapKernel)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-subscription",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapSubscriptionTargetPackage,
    buildInfoPackage := soapSubscriptionTargetPackage
  )

lazy val soapUtilTargetPackage = soapTargetPackage + ".util"
lazy val soapUtil = (project in file("soap/util"))
  .dependsOn(util)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-util",
    commonSettings,
    buildInfoPackage := soapUtilTargetPackage,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % typesafeConfigV
    )
  )

lazy val utilTargetPackage = "com.github.alexanderfefelov.bgbilling.api.util"
lazy val util = (project in file("util"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-util",
    commonSettings,
    buildInfoPackage := utilTargetPackage,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % typesafeConfigV
    )
  )
