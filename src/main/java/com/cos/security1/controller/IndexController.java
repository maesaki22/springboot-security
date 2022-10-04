package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 	//view 리턴.
public class IndexController {

	// localhost:8080
	// localhost:8080/
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 는 springboot 기본이라 src/main/resourcses/ 
		// 뷰리졸버 설정 : templates (pefix) , .mustache(suffix)
		return "index";
	}
}
