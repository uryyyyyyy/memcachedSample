package com.github.uryyyyyyy.memcached.client.elasticache

import org.scalatest.{FunSpec, MustMatchers}


class CodecTest extends FunSpec with MustMatchers {

  describe("Codec") {
    it("can encode&decode long value") {
      val obj = 100L
      val bytes = Codec.encode(obj)
      val result = Codec.decode(bytes, classOf[Long])
      result mustBe obj
    }

    it("can encode&decode custom value") {
      val obj = Seq(ParentObj(ChildObj(1L, 2), "hello"), ParentObj(ChildObj(5L, 9), "hessllo"))
      val bytes = Codec.encode(obj)
      val result = Codec.decode(bytes, classOf[Seq[ParentObj]])
      result mustBe obj
    }
  }
}

case class ChildObj(ll: Long, ii: Int)

case class ParentObj(child1: ChildObj, str: String)