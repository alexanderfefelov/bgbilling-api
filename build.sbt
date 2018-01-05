lazy val root = (project in file("."))
  .aggregate(soap)
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

lazy val soap = (project in file("soap"))
  .aggregate(bill, card, inet, kernel, moneta, oss, qiwi, rscm, subscription, util)
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

lazy val billTargetPackage = soapTargetPackage + ".bill"
lazy val bill = (project in file("soap/bill"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-bill",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := billTargetPackage,
    buildInfoPackage := billTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val cardTargetPackage = soapTargetPackage + ".card"
lazy val card = (project in file("soap/card"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-card",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := cardTargetPackage,
    buildInfoPackage := cardTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val inetTargetPackage = soapTargetPackage + ".inet"
lazy val inet = (project in file("soap/inet"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-inet",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := inetTargetPackage,
    buildInfoPackage := inetTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val kernelTargetPackage = soapTargetPackage + ".kernel"
lazy val kernel = (project in file("soap/kernel"))
  .dependsOn(util)
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-kernel",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := kernelTargetPackage,
    buildInfoPackage := kernelTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val monetaTargetPackage = soapTargetPackage + ".moneta"
lazy val moneta = (project in file("soap/moneta"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-moneta",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := monetaTargetPackage,
    buildInfoPackage := monetaTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val ossTargetPackage = soapTargetPackage + ".oss"
lazy val oss = (project in file("soap/oss"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-oss",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := ossTargetPackage,
    buildInfoPackage := ossTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val qiwiTargetPackage = soapTargetPackage + ".qiwi"
lazy val qiwi = (project in file("soap/qiwi"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-qiwi",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := qiwiTargetPackage,
    buildInfoPackage := qiwiTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val rscmTargetPackage = soapTargetPackage + ".rscm"
lazy val rscm = (project in file("soap/rscm"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-rscm",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := rscmTargetPackage,
    buildInfoPackage := rscmTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val subscriptionTargetPackage = soapTargetPackage + ".subscription"
lazy val subscription = (project in file("soap/subscription"))
  .enablePlugins(ScalaxbPlugin)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-subscription",
    commonSettings,
    scalaxbDispatchVersion in (Compile, scalaxb) := dispatchV,
    scalaxbPackageName in (Compile, scalaxb) := subscriptionTargetPackage,
    buildInfoPackage := subscriptionTargetPackage,
    libraryDependencies ++= scalaxbLibraryDependencies
  )

lazy val utilTargetPackage = soapTargetPackage + ".util"
lazy val util = (project in file("soap/util"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "bgbilling-api-soap-util",
    commonSettings,
    buildInfoPackage := billTargetPackage,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.1"
    )
  )
