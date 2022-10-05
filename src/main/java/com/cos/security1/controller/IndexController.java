package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 	//view 리턴.
public class IndexController {

	// localhost:8080
	// localhost:8080/
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 는 springboot 기본이라 src/main/resourcses/ 
		// 뷰리졸버 설정 : templates (pefix) , .mustache(suffix)  :: html 파일을 읽으려면 config에서 설정을해줘야한다. WebMvcConfig
		return "index";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	// login을 주면 스프링 시큐리티가 conroll 해버린다.
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨!";
	}
}
