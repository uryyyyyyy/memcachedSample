name := """stressTest"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "2.2.4" % "test",
	"com.typesafe.akka" %% "akka-actor" % "2.3.14",
	"redis.clients" % "jedis" % "2.8.0",
	"net.spy" % "spymemcached" % "2.12.0",
	"com.couchbase.client" % "java-client" % "2.2.3",
	"com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0-rc1"
)
