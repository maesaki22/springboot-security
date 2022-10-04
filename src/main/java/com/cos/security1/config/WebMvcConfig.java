package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// html 파일을 mustache로 인식하게 만들어주는 작업
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// mustache 파일 재설정
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html"); 	// 요설정으로인해서 .html 로 인식이된다.

		registry.viewResolver(resolver);

	}

}
