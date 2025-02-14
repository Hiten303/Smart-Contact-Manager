package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entities.user;

public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user User =userRepository.getUserByUserName(username);
		
		if(User==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		CustomUserDetails customUserDetails =new CustomUserDetails(User);
		
		return customUserDetails;
	}

}
