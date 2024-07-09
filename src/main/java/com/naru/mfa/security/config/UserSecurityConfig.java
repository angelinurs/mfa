package com.naru.mfa.security.config;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naru.mfa.jpa.constant.Role;
import com.naru.mfa.security.auth.UserPrincipalDetailService;
import com.naru.mfa.security.provider.UserAuthenticatorProvider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfig {
	
	@Autowired
	private final UserAuthenticatorProvider userAuthenticatorProvider;
	
	@Autowired	
	private final UserPrincipalDetailService userPrincipalDetailService;

	@Autowired
	public void configure( AuthenticationManagerBuilder auth ) throws Exception {
		auth.authenticationProvider(userAuthenticatorProvider);
	}
	
	@Bean
	public SecurityFilterChain userSecurityFilterChain( HttpSecurity http ) throws Exception { 
		http
			// csrf(Cross site Request forgery) : disable
//			.csrf(AbstractHttpConfigurer::disable)
//			.formLogin( AbstractHttpConfigurer::disable )
			.csrf( CsrfConfigurer::disable )
			// html form
			.formLogin(formLogin ->
				formLogin
						.loginPage("/login/login")
						.usernameParameter("username")
						.passwordParameter("password")
						.loginProcessingUrl("/login/login-proc")
						.defaultSuccessUrl("/", true)
			)
			.logout(logoutConfig ->
				logoutConfig.logoutSuccessUrl("/")
			)			
			// for using h2-console 
			.headers( headerConfig ->
					headerConfig.frameOptions( FrameOptionsConfig::disable )
			)
			.authorizeHttpRequests( authorizeRequests ->
					authorizeRequests
						// for using h2-console 
						.requestMatchers(PathRequest.toH2Console()).permitAll()
						.requestMatchers( "/", "/login/**" ).permitAll()
						.requestMatchers( "/posts", "/api/v1/posts/**" ).hasRole(Role.User.name())
						.requestMatchers( "/admin", "/api/v1/admins/**" ).hasRole(Role.Admin.name())
						.anyRequest().authenticated()
			)
			// 401, 403 exception
			.exceptionHandling( exceptionConfig ->
					exceptionConfig
							.authenticationEntryPoint(unAuthorizedEntryPoint)
							.accessDeniedHandler(accessDeniedHandler)
			);
		
		return http.build();
	}
	
	@Getter
	@RequiredArgsConstructor
	public class ErrorResponse {
		private final HttpStatus status;
		private final String message;
	}
	
	private final AuthenticationEntryPoint unAuthorizedEntryPoint = 
			(request, response, authException) -> {
				
				response.setCharacterEncoding(StandardCharsets.UTF_8.name());
				
				ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unautorized....");				
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				
				String json = new ObjectMapper().writeValueAsString(fail);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				
				PrintWriter writer = response.getWriter();
				writer.write(json);
				writer.flush();
			};
			
	private final AccessDeniedHandler accessDeniedHandler = 
			(request, response, accessDeniedException) -> {
				
				response.setCharacterEncoding(StandardCharsets.UTF_8.name());
				
				ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden....");				
				response.setStatus(HttpStatus.FORBIDDEN.value());
				
				String json = new ObjectMapper().writeValueAsString(fail);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				
				PrintWriter writer = response.getWriter();
				writer.write(json);
				writer.flush();
				
			};

}
