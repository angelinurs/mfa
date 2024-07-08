package com.naru.mfa.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naru.mfa.jpa.dto.UserRequest;
import com.naru.mfa.jpa.service.SeUserDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
	
	private final SeUserDetailService seUserDetailService;
	
	@PostMapping
	@Operation( summary = "Sign Up API summary", description = "Sign Up")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200", 
					description = "Success",
					content = {@Content(schema = @Schema(implementation = UserRequest.class))}
					),
			@ApiResponse(responseCode = "404", description = "Not Found")
	})
	public String signUp( @RequestBody @Valid UserRequest userRequest) {
		seUserDetailService.saveUser(userRequest);
		
		return userRequest.toString();
	}

}
