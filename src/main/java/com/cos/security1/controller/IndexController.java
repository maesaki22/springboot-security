package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller 	//view 리턴.
public class IndexController {

		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder ;
	// localhost:8080
	// localhost:8080/
	@GetMapping({"","/"})
	public @ResponseBody String index() {
		// 머스테치 는 springboot 기본이라 src/main/resourcses/ 
		// 뷰리졸버 설정 : templates (pefix) , .mustache(suffix)  :: html 파일을 읽으려면 config에서 설정을해줘야한다. WebMvcConfig
		return "index Page";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user Page";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin Page";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager Page";
	}
	// login을 주면 스프링 시큐리티가 controll 해버린
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) { 	// @ResponseBody 내의 정보르 USER 에 매핑해서 넣어준다.

		user.setRole("ROLE_USER");
		user.setPassword( bCryptPasswordEncoder.encode( (user.getPassword() ) ) );
		userRepository.save(user); 	// 가입전 비밀번호를 암호화를 해야한다.
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN") 	// 권한 설정
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "info Page";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") 	// 권한 설정 :: data() 가 실행되기직전에 적용
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "data Page";
	}
}
