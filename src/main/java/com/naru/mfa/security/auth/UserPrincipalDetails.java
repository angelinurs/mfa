package com.naru.mfa.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.naru.mfa.jpa.entities.User;

public class UserPrincipalDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4635534537633241162L;
	
	private final User user; 
	
	// init
	public UserPrincipalDetails( User user ) {
		this.user = user;
	}
	
	// constructor
	public User getUser() {
		return user;
	}

	// for storing user account's grant
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add( new SimpleGrantedAuthority(user.getRole().toString()));
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	// true is not expired
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// true is not locked
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// true is that account's password is not expired
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getActivation().equalsIgnoreCase("Y");
	}

}
