package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.oauth.provider.FacebookleUserInfo;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder Encoder;
	@Autowired
	private UserRepository userRepository;

	
	//구글에서 받은 userRequest 데이터에 대한  후처리되는 함수
	//함수 종료시 @Authentication @이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("%% getClientRegistration : " + userRequest.getClientRegistration()); 	// registrationId로 어떤 OAuth로 로그인했는지 확인가능
		System.out.println("%% getAccessToken().getTokenValue() : " + userRequest.getAccessToken().getTokenValue());  
		// 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code 리턴(OAuth Client 라이브러리가 받아줌) -> Access Token 요청
		// 위에 정보가 userRequest 정보 -> loadUser(회원프로필을 받아온다)
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println("%% oAuth2User.getAttributes" + oauth2User.getAttributes());
		OAuth2UserInfo oAuth2UserInfo = null; 
		// 사용자 provider에 따른  상황처리 oAuth2UserInfo 를 implement한 GoogleUserInfo , FacebookleUserInfo를 사용가능
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("google");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("facebook");
			oAuth2UserInfo = new FacebookleUserInfo(oauth2User.getAttributes());
		}
		// 인증후 돌아온정보로 강제 회원가입 ID설계는 내맘대로하는중
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();	 	// 구글 로그인엔 sub 있지만 facebook엔 없는 어트리뷰트다.  각각 주는정보가 다르기때문에 체크해줘야한다.
		String username = provider+"_"+providerId; 	// google_12341231412412
		String password = Encoder.encode("oauth");
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		// 회원정보 수집 완료 -> 가입 된 회원인지를 체크
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity==null) {
			System.out.println("새로운 사용자입니다 oauth 정보를 이용한 가입");
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("구글사용자이고 이미 가입이 되어있습니다.");
		}
		
		
		
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
		// 위같이 한이유는 PrincipalDetails로 리턴하기 위함  + 구글사용자의 정보남기기위함.
	}

}
