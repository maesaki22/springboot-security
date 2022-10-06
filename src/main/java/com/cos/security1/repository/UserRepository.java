package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// CRUD 함수를 JpaRepository가 가지고 있다.
// @Repository라는 @없이 Ioc가 된다. JpaRepository를 상속했기때문에 가능!
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// finBy규칙 ->Username문법
	// select * from user where username = username?
	public User findByUsername(String username);	//Jpa 쿼리 메소드 ( query method )

}
