package com.github.uryyyyyyy.redisTest.redis

import java.util

import redis.clients.jedis.{JedisPool, HostAndPort, JedisCluster}
import collection.JavaConversions._
import scala.collection.mutable

object Read {
  def main(args: Array[String]) {

    val jedisClusterNodes = new util.HashSet[HostAndPort]()
    //Jedis Cluster will attempt to discover cluster nodes automatically
    jedisClusterNodes.add(new HostAndPort("172.17.0.2", 7000))
    jedisClusterNodes.add(new HostAndPort("172.17.0.3", 7000))
    val jc = new JedisCluster(jedisClusterNodes)
    val clusterNodes:mutable.Map[String, JedisPool] = jc.getClusterNodes
    clusterNodes.foreach(v => {
      println(v._1)
    })

    jc.set("mykey", "Hello")
    System.out.println(retry(jc.get, "mykey", 1))
    Thread.sleep(3000)
    System.out.println(retry(jc.get, "mykey", 1))
  }

  def retry(func: String => String, params: String, num: Int): String ={
    println(s"try connect #${num}")
    try{
      func(params)
    }catch {
      case e:Exception => {
        if(num > 100) {
          throw e
        } else{
          Thread.sleep(1000)
          retry(func, params, num + 1)
        }
      }
    }
  }
}
