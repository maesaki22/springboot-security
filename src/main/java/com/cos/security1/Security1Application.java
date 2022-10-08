package com.cos.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Springboot 시큐리티를 통한 기본로그인 + OAuth2.0 로그인 구현 샘플 프로젝트

@SpringBootApplication
public class Security1Application {
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}	
	public static void main(String[] args) {
		SpringApplication.run(Security1Application.class, args);
	}

}
