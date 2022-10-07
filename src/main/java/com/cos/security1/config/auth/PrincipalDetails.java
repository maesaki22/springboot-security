package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티는 session 을 만들어줍니다 ( Security ContextHolder )
// 오브젝트 타입 -> Authentication 객체
// Authentication 안에 User 정보가 있어야 됨

@Data
// Security Session -> Authentication -> UserDetails (PrincipalDetails)
public class PrincipalDetails implements UserDetails, OAuth2User{
	
	private User user;  // 콤포지션
	private Map<String,Object> attributes;
	
	// 일반 로그인 생성자
	public PrincipalDetails(User user) {
		this.user= user;
	}
	// OAuth 로그인 생성자
	public PrincipalDetails(User user,Map<String,Object>attributes) {
		this.user= user;
		this.attributes = attributes;
	}

	// 해당  User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();		// ArrayList는 Collection 의 자식
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect; 	//user.getRole();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}
	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트 1년동안 회원의 접속기록이 없다면 휴먼등록
		return true;
	}

	// -- OAuth2User overridding
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
}
