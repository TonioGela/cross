import laika.helium.config._
import laika.rewrite.nav.{ChoiceConfig, SelectionConfig, Selections}

ThisBuild / organization     := "dev.toniogela"
ThisBuild / organizationName := "TonioGela"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.MIT)
ThisBuild / homepage         := Some(url(s"https://cross.toniogela.dev/"))
ThisBuild / developers       := List(tlGitHubDev("TonioGela", "Antonio Gelameris"))

ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlBaseVersion           := "0.0"
ThisBuild / tlVersionIntroduced     := Map("3" -> "0.0.1")

ThisBuild / crossScalaVersions := List("2.13.11", "3.3.0")
ThisBuild / scalaVersion       := "3.3.0"

ThisBuild / githubWorkflowJavaVersions := List(JavaSpec.temurin("8"), JavaSpec.temurin("17"))

ThisBuild / tlSitePublishBranch := Some("main")

ThisBuild / mergifyStewardConfig ~= {
  _.map(_.copy(mergeMinors = true, author = "toniogela-s-scala-steward[bot]"))
}

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = tlCrossRootProject.aggregate(core, tests, unidocs)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(name := "cross")

lazy val tests = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    name                                    := "cross-tests",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.16" % Test
  )

lazy val unidocs = project
  .in(file("unidocs"))
  .enablePlugins(TypelevelUnidocPlugin)
  .settings(
    name                                       := "cross-unidocs",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(core.jvm)
  )

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(core.jvm)
  .settings(
    scalaVersion := "3.3.0",
    tlSiteHelium ~= {
      _.site
        .topNavigationBar(homeLink =
          TextLink.external(
            "https://cross.toniogela.dev",
            "Cross"
          )
        )
        .site
        .mainNavigation(
          appendLinks = List(
            ThemeNavigationSection(
              "Related Links",
              TextLink.external("https://toniogela.dev/", "[SOON] A blog article about this lib")
            )
          )
        )
    },
    laikaConfig ~= {
      _.withConfigValue(
        Selections(
          SelectionConfig(
            "build-tool",
            ChoiceConfig("scala-cli", "Scala CLI"),
            ChoiceConfig("sbt", "sbt")
          )
        )
      )
    }
  )
