package com.github.uryyyyyyy.memcached.client.elasticache

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import net.spy.memcached.MemcachedClient

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

class ElastiCacheClientMImpl(configHost: String, port:Int) extends ElastiCacheClientM {

  //キューが溢れたら無視するとかやりたいかも？
  implicit val context = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  val configClient: MemcachedClient = new MemcachedClient(new InetSocketAddress(configHost, port))
  var clients: List[MemcachedClient] = configClient.getAllNodeEndPoints.asScala.map(v => new MemcachedClient(v.getInetSocketAddress)).toList
  var clientsLength: Int = clients.length
  var inChanging: Boolean = false

  private def fetchCurrentEndpoints(): Unit ={
    clients.foreach(_.shutdown())
    clients = configClient.getAllNodeEndPoints.asScala.map(v => new MemcachedClient(v.getInetSocketAddress)).toList
    clientsLength = clients.length
    println("new connections.")
    println(configClient.getAllNodeEndPoints.asScala.mkString(","))
    inChanging = false
  }

  override def get[A](key: String, clazz: Class[A]): Option[A] = {
    try{
      val hash = Math.abs(key.hashCode % clientsLength)
      val bytes = clients(hash).get(key).asInstanceOf[Array[Byte]]
      if(bytes == null){
        None
      }else{
        Some(Codec.decode(bytes, clazz))
      }
    }catch {
      case _: Exception => {
        if(!inChanging) {
          inChanging = true
          Future{ fetchCurrentEndpoints() }
        }
        None
      }
    }
  }

  override def setAsync[A](key: String, expireSec: Int, value: A): Unit = {
    val hash = Math.abs(key.hashCode % clientsLength)
    val bytes = Codec.encode(value)
    clients(hash).set(key, expireSec, bytes)
  }

  override def shutdown(): Unit = {
    clients.foreach(_.shutdown())
    configClient.shutdown()
  }
}

//ただの一時キャッシュなので、何かあったらすぐに処理を戻して、特に復旧などしない。。
trait ElastiCacheClientM {
  def get[A](key:String, clazz: Class[A]): Option[A]
  def setAsync[A](key: String, expireSec: Int, value: A): Unit
  def shutdown():Unit
}

class ElastiCacheClientMForLocal(configHost: String, port:Int) extends ElastiCacheClientM {

  val client: MemcachedClient = new MemcachedClient(new InetSocketAddress(configHost, port))

  override def get[A](key: String, clazz: Class[A]): Option[A] = {
    val bytes = client.get(key).asInstanceOf[Array[Byte]]
    if(bytes == null){
      None
    }else{
      Some(Codec.decode(bytes, clazz))
    }
  }

  override def setAsync[A](key: String, expireSec: Int, value: A): Unit = {
    val bytes = Codec.encode(value)
    client.set(key, expireSec, bytes)
  }

  override def shutdown(): Unit = {
    client.shutdown()
  }
}