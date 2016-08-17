package com.github.uryyyyyyy.memcached.client.elasticache

import java.util.{Date, Random}


/**
  * how to use ElastiCacheClientM
  */
object Main {

  def main(args: Array[String]): Unit = {
    val client: ElastiCacheClientM = new ElastiCacheClientMImpl(args(0), 11211)

    val random: Random = new Random(111)

    while(true){
      val randKey: String = random.nextInt(100).toString
      val result = action(client, randKey)
      println(result + ". time: " + new Date().toString)
    }
    client.shutdown()
  }

  def action(client: ElastiCacheClientM, key: String): String = {
    Thread.sleep(100)
    client.get(key, classOf[String]) match {
      case Some(res) => res
      case None => {
        val result = heavyAction(key)
        client.setAsync(key, 3600, result)
        result
      }
    }
  }

  def heavyAction(key: String): String ={
    println("heavyAction start")
    Thread.sleep(500)
    "result of " + key
  }

}
