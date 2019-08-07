package com.crow;


import com.crow.filter.TokenFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;


@ServletComponentScan
@SpringBootApplication
@MapperScan("com.crow.dao")
public class WsNewsSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsNewsSpiderApplication.class, args);
	}
}
