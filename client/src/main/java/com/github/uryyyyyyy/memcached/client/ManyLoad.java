package com.github.uryyyyyyy.memcached.client;


import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class ManyLoad {

	public static void main(String[] args) throws IOException, InterruptedException {
		MemcachedClient client = new MemcachedClient(
				new InetSocketAddress("172.17.0.2", 11211)
		);

		// Store a value (async) for one hour
		client.set("someKey", 1, "valueee");

		setValues(client);
		System.out.println(client.get("key" + 333));
		client.shutdown();
	}

	private static void setValues(MemcachedClient client) {
		long start2 = System.currentTimeMillis();
		for(int i = 0; i < 100000; i++){
			client.set("key" + i, 1000, "valueee" + i);
		}
		System.out.println(System.currentTimeMillis() - start2 + " ms");
	}
}
