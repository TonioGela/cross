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

lazy val root = tlCrossRootProject.aggregate(core, tests, unidocs)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(name := "cross")

lazy val unidocs = project
  .in(file("unidocs"))
  .enablePlugins(TypelevelUnidocPlugin)
  .settings(
    name                                       := "cross-unidocs",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(core.jvm)
  )

lazy val tests = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    name                                    := "cross-tests",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.16" % Test
  )
