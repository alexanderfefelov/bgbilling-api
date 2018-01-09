lazy val root = (project in file("."))
  .aggregate(db, soap)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val commonSettings = Seq(
  organization := "com.github.alexanderfefelov",
  scalaVersion := "2.11.12",
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
  .aggregate(dbOss)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val dbTargetPackage = "com.github.alexanderfefelov.bgbilling.api.db"

lazy val dbOssTargetPackage = dbTargetPackage + ".oss"
lazy val dbOss = (project in file("db/oss"))
  .dependsOn(dbUtil)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-oss",
    commonSettings,
    buildInfoPackage := dbOssTargetPackage
  )

lazy val dbUtilTargetPackage = dbTargetPackage + ".util"
lazy val dbUtil = (project in file("db/util"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-db-util",
    commonSettings,
    buildInfoPackage := soapUtilTargetPackage,
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % "3.1.0",
      "org.scalikejdbc" %% "scalikejdbc-config" % "3.1.0",
      "mysql" % "mysql-connector-java" % "5.1.45",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )

lazy val soap = (project in file("soap"))
  .aggregate(soapBill, soapCard, soapInet, soapKernel, soapMoneta, soapOss, soapQiwi, soapRscm, soapSubscription, soapUtil)
  .settings(
    publishLocal := {},
    publish := {}
  )

lazy val soapTargetPackage = "com.github.alexanderfefelov.bgbilling.api.soap"

lazy val dispatchV = "0.11.3"

lazy val scalaxbLibraryDependencies = Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6",
  "net.databinder.dispatch" %% "dispatch-core" % dispatchV
)

lazy val soapBillTargetPackage = soapTargetPackage + ".bill"
lazy val soapBill = (project in file("soap/bill"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-bill",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapBillTargetPackage,
    buildInfoPackage := soapBillTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapCardTargetPackage = soapTargetPackage + ".card"
lazy val soapCard = (project in file("soap/card"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-card",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapCardTargetPackage,
    buildInfoPackage := soapCardTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapInetTargetPackage = soapTargetPackage + ".inet"
lazy val soapInet = (project in file("soap/inet"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-inet",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapInetTargetPackage,
    buildInfoPackage := soapInetTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
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
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-moneta",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapMonetaTargetPackage,
    buildInfoPackage := soapMonetaTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapOssTargetPackage = soapTargetPackage + ".oss"
lazy val soapOss = (project in file("soap/oss"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-oss",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapOssTargetPackage,
    buildInfoPackage := soapOssTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapQiwiTargetPackage = soapTargetPackage + ".qiwi"
lazy val soapQiwi = (project in file("soap/qiwi"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-qiwi",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapQiwiTargetPackage,
    buildInfoPackage := soapQiwiTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapRscmTargetPackage = soapTargetPackage + ".rscm"
lazy val soapRscm = (project in file("soap/rscm"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-rscm",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapRscmTargetPackage,
    buildInfoPackage := soapRscmTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapSubscriptionTargetPackage = soapTargetPackage + ".subscription"
lazy val soapSubscription = (project in file("soap/subscription"))
  .dependsOn(soapUtil)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-subscription",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := soapSubscriptionTargetPackage,
    buildInfoPackage := soapSubscriptionTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val soapUtilTargetPackage = soapTargetPackage + ".util"
lazy val soapUtil = (project in file("soap/util"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-util",
    commonSettings,
    buildInfoPackage := soapUtilTargetPackage,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.1"
    )
  )
