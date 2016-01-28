package com.github.uryyyyyyy.redisTest.kyotoTycoon

import redis.clients.jedis.{JedisPool, JedisPoolConfig}

object Write {

  def main(args: Array[String]) {
    val pool: JedisPool = new JedisPool(new JedisPoolConfig, "172.17.0.2")
    val jedis = pool.getResource() //try with resources

    jedis.set("mykey", "Hello")
    System.out.println(jedis.keys("*"))
    System.out.println(jedis.get("mykey"))
  }
}