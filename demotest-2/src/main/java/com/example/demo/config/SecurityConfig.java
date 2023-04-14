package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//security사용할 때 환경설정 필요
@Configuration //Bean을 등록하기 위해 붙여주는 어노테이션으로 , 설정한다는 의미
@EnableWebSecurity
public class SecurityConfig {
	
	//security 사용할 때 환경설정 필요
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().disable()
			.csrf().disable()
			.formLogin().disable() //시큐리티 기본 로그인 폼 사용 안함
			.headers().frameOptions().disable();
		return http.build();
	}
	
	
	//패스워드 암호화 해줄 객체
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
