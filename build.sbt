name := """anonymous-chat"""
organization := "com.github.lpld"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  evolutions,
  cacheApi,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.postgresql" % "postgresql" % "42.2.2",
  "com.zaxxer" % "HikariCP" % "3.1.0",
  "io.getquill" %% "quill-jdbc" % "2.4.1",
  "org.mindrot" % "jbcrypt" % "0.4"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.github.lpld.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.github.lpld.binders._"
