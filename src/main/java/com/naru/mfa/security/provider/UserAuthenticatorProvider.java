package com.naru.mfa.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.naru.mfa.jpa.entities.User;
import com.naru.mfa.security.auth.UserPrincipalDetailService;
import com.naru.mfa.security.auth.UserPrincipalDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserAuthenticatorProvider implements AuthenticationProvider {
	
	@Autowired
	private UserPrincipalDetailService userPrincipalDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
		
		UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) userPrincipalDetailService.loadUserByUsername(username);
		
		String dbPassword = userPrincipalDetails.getPassword();
		
		// compare passwords using BCrypt
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if( !passwordEncoder.matches(password, dbPassword) ) {
			log.error("[ user : {} ] Password does not match", username );
			throw new BadCredentialsException("[ user ] Username and Password does not match");
		}
		
		User user = userPrincipalDetails.getUser();
		if( user == null || !user.getActivation().equalsIgnoreCase("Y") ) {
			log.error("[ user : {} ] This account is unavailable.", username );
			throw new BadCredentialsException("[ user ] This account is unavailable.");
		}
		
		return new UsernamePasswordAuthenticationToken(userPrincipalDetails, userPrincipalDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
