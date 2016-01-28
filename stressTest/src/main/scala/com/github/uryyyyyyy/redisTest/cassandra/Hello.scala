package com.github.uryyyyyyy.redisTest.cassandra

import com.datastax.driver.core.Cluster

import scala.collection.JavaConversions._

object Hello {
	def main(args: Array[String]): Unit = {
		val cluster = Cluster.builder().addContactPoint("172.17.0.3").build()
		val session = cluster.connect("demo")

		// Insert one record into the users table
		session.execute("INSERT INTO users (lastname, age, city, email, firstname) VALUES ('Jones', 35, 'Austin', 'bob@example.com', 'Bob')")

		// Use select to get the user we just entered
		val results = session.execute("SELECT * FROM users WHERE lastname='Jones'")
		for (row <- results.all()) {
			println(row.getString("firstname") + ", " + row.getInt("age"))
		}
	}
}
