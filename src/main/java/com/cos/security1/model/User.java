package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
@Entity
@Data
public class User {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		private String username;
		private String password;
		private String email;
		private String role;
		private String provider;	// google id로그인
		private String providerId; 	// google에서 사용하는 id저장
		@CreationTimestamp
		private Timestamp createDate;
}
