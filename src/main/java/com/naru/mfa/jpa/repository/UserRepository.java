package com.naru.mfa.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naru.mfa.jpa.entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	
	User findByPhone(String phone);
	
	User findByUserId(String userId);

}
