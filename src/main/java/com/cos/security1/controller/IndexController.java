package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller // view 리턴.
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) { // 여기서 PrincipalDetails 는 UserDetails를 상속받아서 같은놈이다.
		// test login 후 Authentication객체를 받아온다면? 어떤정보가 있을까?
		System.out.println("test/login ::::::::");
		// 받아온 Authentication authentication
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 넘겨받은 Authentication 를
																								// 다운캐스팅해서
																								// PrincipalDetails 객체에
																								// 집어넣는다.
		System.out.println("principalDetails.getUser() ::::::::" + principalDetails.getUser());
		// 받아온 @AuthenticationPrincipal UserDetails userDetails
		System.out.println("principalDetails.getUsername() ::::::::" + principalDetails.getUsername());
		System.out.println("userDetails.getUsername() ::::::::" + userDetails.getUsername());
		return "일반유저 로그인";
	}

	@GetMapping("/test/oauth/login")
	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) { //
		System.out.println("google test/oauth/login ########");
		// 받아온 Authentication authentication
		// PrincipalDetails principalDetails = (PrincipalDetails)
		// authentication.getPrincipal();
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("getUser ::::::::" + oAuth2User.getAttributes());
		// 받아온 @AuthenticationPrincipal OAuth2User oauth
		System.out.println("oauth ::::::::" + oauth.getAttributes());
		return "oauth 로그인";
	}

	// 위 두함수를 통해서 비교할것은 일반유져가 로그인을할땐
	// PrincipalDetails principalDetails = (PrincipalDetails)
	// authentication.getPrincipal(); authentication 가 PrincipalDetails 가진다.
	// oauth 유져가 로그인을하면
	// OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
	// authentication 가 OAuth2User를 가진다.
	// 두개의 타입이 들어가진다는 것 Authentication 는 2가지 type를 가진다.
	// UserDetails(일반사용자로그인) / Oauth2User(oauth로그인ex.구글)
	// 즉 두개의 타입이 다르기때문에 @AuthenticationPrincipal UserDetails or @AuthenticationPrincipal Oauth2User 두개를 받는걸 따로해줘야 불편하다
	// 이점을 해결하기 위해서 우린 두개다를 포함하는
	
	
	// localhost:8080
	// localhost:8080/
	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		// 머스테치 는 springboot 기본이라 src/main/resourcses/
		// 뷰리졸버 설정 : templates (pefix) , .mustache(suffix) :: html 파일을 읽으려면 config에서
		// 설정을해줘야한다. WebMvcConfig
		return "index Page";
	}

	// OAuth 로그인 / 일반 로그인을 해도 PrincipalDetails로 받을수 있게 설계가 되었다.
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
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
	public String join(User user) { // @ResponseBody 내의 정보르 USER 에 매핑해서 넣어준다.

		user.setRole("ROLE_USER");
		user.setPassword(bCryptPasswordEncoder.encode((user.getPassword())));
		userRepository.save(user); // 가입전 비밀번호를 암호화를 해야한다.
		return "redirect:/loginForm";
	}

	@Secured("ROLE_ADMIN") // 권한 설정
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "info Page";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 권한 설정 :: data() 가 실행되기직전에 적용
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "data Page";
	}
}
