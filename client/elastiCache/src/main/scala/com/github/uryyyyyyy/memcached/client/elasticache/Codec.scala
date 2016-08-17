package com.github.uryyyyyyy.memcached.client.elasticache

import com.twitter.chill.{KryoBase, KryoInjection, ScalaKryoInstantiator}

private[elasticache] object Codec{

  val kryoInjection = {
    val kryoInstantiator = new ScalaKryoInstantiator(){
      override def newKryo(): KryoBase = {
        val kryo = super.newKryo()
        kryo.setClassLoader(getClass.getClassLoader)
        kryo
      }
    }
    KryoInjection.instance(kryoInstantiator)
  }

  def encode[A](obj: A):Array[Byte] = {
    kryoInjection(obj)
  }

  def decode[A](bytes: Array[Byte], clazz: Class[A]):A = {
    kryoInjection.invert(bytes).get.asInstanceOf[A]
  }
}