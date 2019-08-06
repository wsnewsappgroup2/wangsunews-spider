package com.crow;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//@RestController
@SpringBootApplication
@MapperScan("com.crow.dao")
public class WsNewsSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsNewsSpiderApplication.class, args);
	}
}
