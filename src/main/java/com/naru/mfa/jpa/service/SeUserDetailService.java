package com.naru.mfa.jpa.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.naru.mfa.jpa.constant.Role;
import com.naru.mfa.jpa.dto.UserRequest;
import com.naru.mfa.jpa.entities.User;
import com.naru.mfa.jpa.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeUserDetailService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public User saveUser( UserRequest userRequest ) {
		User user = User.builder().userId(userRequest.getUserId())
				                  .name(userRequest.getName())
				                  .phone(userRequest.getPhone())
				                  .email(userRequest.getEmail())
				                  .birth(userRequest.getBirth())
				                  .addr(userRequest.getAddr())
				                  .zipcode(userRequest.getZipcode())
				                  .activation("Y")
				                  .role(Role.User)
				                  .build();
		
		log.error("user entity info: {}", user.toString() );
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
