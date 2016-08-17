name := """memcachedClient"""

version := "1.0"

lazy val commonSettings = Seq(
  organization := "com.github.uryyyyyyy",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )
)

lazy val normal = (project in file("normal"))
  .settings(commonSettings: _*)
  .settings(Seq(
    mainClass in assembly := Some("com.github.uryyyyyyy.memcached.client.normal.Main"),
    libraryDependencies ++= Seq(
      "net.spy" % "spymemcached" % "2.12.1"
    )
  ))

lazy val elastiCache = (project in file("elastiCache"))
  .settings(commonSettings: _*)
  .settings(Seq(
    mainClass in assembly := Some("com.github.uryyyyyyy.memcached.client.elasticache.Main"),
    libraryDependencies ++= Seq(
      "com.amazonaws" % "elasticache-java-cluster-client" % "1.1.0",
      "com.twitter" %% "chill-bijection" % "0.8.0"
    )
  ))