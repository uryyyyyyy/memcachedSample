package com.github.uryyyyyyy.memcached.client.elasticache

import java.net.InetSocketAddress
import java.util.{Date, Random}

import net.spy.memcached.MemcachedClient

import scala.collection.JavaConverters._


object Main {

  def main(args: Array[String]): Unit = {
    System.out.print("start")

    val host: String = args(0)
    val inet: InetSocketAddress = new InetSocketAddress(host, 11211)

    val random: Random = new Random(111)
    val c: MemcachedClient = new MemcachedClient(inet)
    val points = c.getAllNodeEndPoints.asScala.toList


    while (true) {
      val randKey: String = random.nextDouble.toString
      val time: String = new Date().toString
      c.set(randKey, 1000, "valueee")
      val myObject3: Any = c.get(randKey)
      System.out.println(randKey + " value: " + myObject3 + " time: " + time)
    }

  }

}
