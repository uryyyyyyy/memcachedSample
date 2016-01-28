package com.github.uryyyyyyy.redisTest.memcached

import java.net.InetSocketAddress

import net.spy.memcached.MemcachedClient
import collection.JavaConversions._

object Hello {
  def main(args: Array[String]): Unit = {
    val c = new MemcachedClient(
      new InetSocketAddress("172.17.0.2", 11121)
    )

    // Store a value (async) for one hour
    c.set("someKey", 1, "valueee")
    // Retrieve a value (synchronously).
    val myObject2 = c.get("someKey")
    val ss = myObject2.asInstanceOf[String]
    println(ss)

    c.set("someKey2", 3600, "valueee2")
    val map = c.getBulk("someKey", "someKey2")
    map.foreach(v => println(v._1 + ", " + v._2.asInstanceOf[String]))

    Thread.sleep(1500)
    val myObject3 = c.get("someKey")
    val s3 = myObject3.asInstanceOf[String]
    println(s3)
    c.shutdown()
  }
}
