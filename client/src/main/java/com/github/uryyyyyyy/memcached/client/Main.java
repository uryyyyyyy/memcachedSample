package com.github.uryyyyyyy.memcached.client;


import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		MemcachedClient c = new MemcachedClient(
				new InetSocketAddress("172.17.0.2", 11211)
		);

		// Store a value (async) for one hour
		c.set("someKey", 1, "valueee");
		// Retrieve a value (synchronously).
		Object myObject2 = c.get("someKey");
		System.out.println(myObject2);

		c.set("someKey2", 3600, "valueee2");
		Map<String, Object> map = c.getBulk("someKey", "someKey2");

		for(Map.Entry<String, Object> entry: map.entrySet()){
			String s = entry.getKey() + ", " + entry.getValue();
			System.out.println(s);
		}

		Thread.sleep(1500);
		Object myObject3 = c.get("someKey");
		System.out.println(myObject3);
		c.shutdown();
	}
}
