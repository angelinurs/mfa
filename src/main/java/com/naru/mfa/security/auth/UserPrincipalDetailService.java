package com.naru.mfa.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.naru.mfa.jpa.entities.User;
import com.naru.mfa.jpa.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserPrincipalDetailService implements UserDetailsService {
	// spring security intercept username, password from request of login 
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUserId(username);
		log.info( "username : {}", username );
		log.info( "user : {}", user );
		
		if( user == null )
			throw new UsernameNotFoundException( username + " is not found" );
		
		if( !user.getActivation().equalsIgnoreCase("Y") )
			throw new UsernameNotFoundException( username + " is not activated" );
		
		return new UserPrincipalDetails(user);
	}
}
