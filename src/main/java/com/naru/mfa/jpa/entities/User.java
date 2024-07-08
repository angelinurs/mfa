package com.naru.mfa.jpa.entities;

import java.time.LocalDateTime;

import com.naru.mfa.jpa.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table( name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // sequence strategy
	@Column( name = "ID")
	private Long id;
	
	@Column( name = "USERID", nullable = false, unique = true )
	private String userId;
	
	@Column( name = "PASSWORD", nullable = false, unique = true )
	private String password;

	@Column( name = "NAME", nullable = false )
	private String name;

	@Column( name = "PHONE", nullable = false )
	private String phone;

	@Column( name = "EMAIL", nullable = false )
	private String email;

	@Column( name = "BIRTH", nullable = false )
	private String birth;

	@Column( name = "ADDR", nullable = false )
	private String addr;

	@Column( name = "ZIPCODE", nullable = false )
	private String zipcode;

	@Column( name = "ACTIVATION", nullable = false )
	private String activation;

	@Column( name = "CREATEDAT", nullable = false )
	private LocalDateTime createdAt;

	@Column( name = "MODIFIEDAT", nullable = false, updatable = false )
	private LocalDateTime modifiedAt;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public User(Long id, String userId, String password, String name, String phone, String email, String birth, String addr,
			String zipcode, String activation, LocalDateTime createdAt, LocalDateTime modifiedAt, Role role) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.birth = birth;
		this.addr = addr;
		this.zipcode = zipcode;
		this.activation = activation;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.role = role;
	}
}
