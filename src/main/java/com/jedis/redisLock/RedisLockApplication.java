package com.jedis.redisLock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisLockApplication {

	public static void main(final String[] args) {
		SpringApplication.run(RedisLockApplication.class, args);
	}

}
