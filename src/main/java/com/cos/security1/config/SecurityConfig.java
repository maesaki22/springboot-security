package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;

@Configuration // 메모리에 띄우고
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) 	// secured @ 활성화 preAuthorize/postAuthorize @ 활성화  :: 권한 설정하는 @
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() 	// 인증되면 들어갈수 있는 주소.
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login") 	// /login 주소가 호출이 되면 시큐리티가 낚아채서 대신
			.defaultSuccessUrl("/")
		.and()
			.oauth2Login()
			.loginPage("/login") 	// 구글 인증이 된후 돌아온 code로 처리해줘야한다.   1. 코드받기(인증완료) 2. 엑세스토큰(구글정보접근권한) 3.가져오는 정보 4. 정보사용
			// 구글로그인이 완료되었다 ( 엑세스토큰 + 사용자프로필정보 가지고왔다)
			.userInfoEndpoint()
			.userService(principalOauth2UserService); 	//받아온 정보를 principalOauth2UserService에서 사용할것이다.
	}

}