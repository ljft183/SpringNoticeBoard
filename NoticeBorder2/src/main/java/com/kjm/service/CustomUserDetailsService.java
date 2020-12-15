package com.kjm.service;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kjm.dao.BoardDAO;
import com.kjm.vo.CustomUserDetails;


public class CustomUserDetailsService implements UserDetailsService {
	@Inject
	private BoardDAO dao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //로그인 시 형식에 맞는 데이터 호출
		CustomUserDetails users = dao.getUserById(username);
		if(users == null) {
			 throw new UsernameNotFoundException("username " + username + " not found");
		}
		System.out.println("**************Found user***************");
		System.out.println("id : " + users.getUsername());
		return users;
	}

}