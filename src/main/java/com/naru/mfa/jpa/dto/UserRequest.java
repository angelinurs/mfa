package com.naru.mfa.jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Schema(title = "Sign up Request DTO")
@ToString
public class UserRequest {
	
	private String userId;

	private String name;

	private String phone;

	private String email;

	private String birth;

	private String addr;

	private String zipcode;

	@Builder
	public UserRequest(String userId, String name, String phone, String email, String birth, String addr, String zipcode) {
		this.userId = userId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.birth = birth;
		this.addr = addr;
		this.zipcode = zipcode;
	}
}
