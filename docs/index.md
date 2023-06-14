# Cross Library

A zero-purpose library published to demonstrate the ease of the process.

It is published for Scala 2.13 and 3.2+ and also fully supports Scala.js 1.13+ and Scala Native 0.4.

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

### Using `cross`

```scala mdoc
import dev.toniogela.cross.Platform

// Using Scala 3+ on JVM
Platform.platformAndVersion
```

## Logical steps



