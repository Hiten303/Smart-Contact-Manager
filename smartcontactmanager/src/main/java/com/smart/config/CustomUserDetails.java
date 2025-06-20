package com.smart.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.entities.user;

public class CustomUserDetails implements UserDetails {

	private user User;
	
	public CustomUserDetails(user user) {
		super();
		User = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(User.getRole());
		
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		
		return User.getPassword();
	}

	@Override
	public String getUsername() {
		
		return User.getEmail();
	}

}
