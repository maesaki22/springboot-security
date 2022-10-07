package com.cos.security1.config.oauth.provider;

// 여러종류(google,facebook,naver)의 유져들이 접속이 가능하니 각각 provider로 관리하기위해서 interface를 만들어준다.
public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
