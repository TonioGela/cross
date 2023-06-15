# Cross Library

A **zero-purpose** library published to demonstrate the **ease of the process**.

You can refer to the blog article in the related links for **a practical how-to guide**.

It is published for Scala 2.13+ and 3.3+ and fully supports Scala.js 1.13.1+ and Scala Native 0.4.14+.

### QuickStart
@:select(build-tool)
@:choice(scala-cli)
```scala
//> using dep dev.toniogela::cross::@VERSION@
```
@:choice(sbt)
```scala
libraryDependencies ++= Seq(
  "dev.toniogela" %% "cross" % "@VERSION@"
)
```
@:@

and then use it like

```scala mdoc
import dev.toniogela.cross.Platform

// Using Scala 3+ on JVM
Platform.platformAndVersion
```