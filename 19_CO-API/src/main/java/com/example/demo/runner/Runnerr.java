package com.example.demo.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Runnerr implements ApplicationRunner {

	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Override
	public void run(ApplicationArguments args) throws Exception {
   
		HashOperations<String,Object,Object> hashops=redisTemplate.opsForHash();
		hashops.put("DHS","DHS_OFC_ADDRESS","1/H#ISLAND#RI#+1 (401) 6785#dhs@gmail.com#www.dhs.com");
	}

}
