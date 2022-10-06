package com.cos.security1.config.auth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;


// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailService 타입으로 ioc되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	// 시큐리티 session 내부 Authentication 내부 UserDetails
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 	// html에서 name사용중 username 으로 맞춰야한다
		User userEntity = userRepository.findByUsername(username);
		System.out.println(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		// TODO Auto-generated method stub
		return null;
	}


}
