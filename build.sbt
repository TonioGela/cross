import laika.ast.LengthUnit.px
import laika.helium.config._
import laika.config.{ChoiceConfig, SelectionConfig, Selections}

ThisBuild / organization     := "dev.toniogela"
ThisBuild / organizationName := "TonioGela"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.MIT)
ThisBuild / homepage         := Some(url(s"https://cross.toniogela.dev/"))
ThisBuild / developers       := List(tlGitHubDev("TonioGela", "Antonio Gelameris"))

ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlBaseVersion           := "0.0"
ThisBuild / tlVersionIntroduced     := Map("3" -> "0.0.1")

ThisBuild / crossScalaVersions := List("2.13.13", "3.4.1")
ThisBuild / scalaVersion       := "3.4.1"

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
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.18" % Test
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
    scalaVersion := "3.4.1",
    tlSiteHelium ~= { helium =>
      val favicon            = Favicon.external("https://toniogela.dev/favicon.ico", "32x32", "image/vnd.microsoft.icon")
      val homeLink           = TextLink.external("https://cross.toniogela.dev", "❌ Cross Library")
      val blogLink           = TextLink.external("https://toniogela.dev/cross-library", "A blog article about this lib")
      val sbtTL              = TextLink.external("https://typelevel.org/sbt-typelevel", "sbt-typelevel")
      val chatLink: IconLink = IconLink.external("https://discord.com/users/372358874243661825", HeliumIcon.chat)
      val twitter: IconLink  = IconLink.external("https://twitter.com/toniogela", HeliumIcon.twitter)

      helium.site.darkMode.disabled.site
        .favIcons(favicon)
        .site
        .layout(topBarHeight = px(50))
        .site
        .topNavigationBar(
          homeLink = homeLink,
          navLinks = twitter :: chatLink :: Nil
        )
        .site
        .mainNavigation(appendLinks = ThemeNavigationSection("Related Links", blogLink, sbtTL) :: Nil)
    },
    laikaConfig ~= {
      _.withConfigValue(
        Selections(SelectionConfig("build-tool", ChoiceConfig("scala-cli", "Scala CLI"), ChoiceConfig("sbt", "sbt")))
      )
    }
  )
