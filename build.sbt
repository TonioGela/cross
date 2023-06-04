ThisBuild / organization     := "dev.toniogela"
ThisBuild / organizationName := "TonioGela"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.MIT)
ThisBuild / developers       := List(tlGitHubDev("TonioGela", "Antonio Gelameris"))

ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlBaseVersion           := "0.0"
ThisBuild / tlVersionIntroduced     := Map("3" -> "0.0.1")

ThisBuild / crossScalaVersions := List("2.13.10", "3.3.0")
ThisBuild / scalaVersion       := "3.3.0"

ThisBuild / githubWorkflowJavaVersions := List(JavaSpec.temurin("8"), JavaSpec.temurin("17"))

lazy val root = tlCrossRootProject.aggregate(core, tests)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(
    name                                    := "cross-core",
    libraryDependencies += "org.typelevel" %%% "cats-core" % "2.9.0"
  )

lazy val tests = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    name                                    := "cross-core-tests",
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M7" % Test
  )
