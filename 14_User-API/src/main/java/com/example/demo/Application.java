package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.WelcomeClient;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class Application {

	@Autowired
	private WelcomeClient welcomeclient;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	@GetMapping("/greet")
	public String hello() {
		String msg = welcomeclient.invokeGreetMsg();
		return msg +"----------- this is from user api";
	}
}
