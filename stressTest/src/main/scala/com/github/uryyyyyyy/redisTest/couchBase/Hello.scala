package com.github.uryyyyyyy.redisTest.couchBase

import com.couchbase.client.java.CouchbaseCluster
import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject

object Hello {
  def main(args: Array[String]): Unit = {
    // Create a cluster reference
    val cluster = CouchbaseCluster.create("172.17.0.2")

    // Connect to the bucket and open it
    val bucket = cluster.openBucket("default")

    // Create a JSON document and store it with the ID "helloworld"
    val content = JsonObject.create().put("hello", "world")
    val inserted = bucket.upsert(JsonDocument.create("helloworld", content))

    // Read the document and print the "hello" field
    val found = bucket.get("helloworld")
    println("Couchbase is the best database in the " + found.content().getString("hello"))

    // Close all buckets and disconnect
    cluster.disconnect()
  }
}
