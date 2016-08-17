package com.github.uryyyyyyy.memcached.client.elasticache

import org.scalatest.{FunSpec, MustMatchers}


class CodecTest extends FunSpec with MustMatchers {

  describe("Codec") {
    it("can encode&decode 1") {
      val obj = 100L
      val bytes = Codec.encode(obj)
      val result = Codec.decode(bytes, classOf[Long])
      result mustBe obj
    }
  }
}