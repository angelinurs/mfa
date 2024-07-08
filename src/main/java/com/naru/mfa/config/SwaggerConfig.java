package com.naru.mfa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	private static final String BEARER_TOKEN_PREFIX = "Bearer";
	
	@Bean
	public OpenAPI openAPI() {
		String securtiyJwtName = "JWT";
		
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securtiyJwtName);
		
		Components components = new Components().addSecuritySchemes(securtiyJwtName, new SecurityScheme()
												.name(securtiyJwtName)
												.type(SecurityScheme.Type.HTTP)
												.scheme(BEARER_TOKEN_PREFIX)
												.bearerFormat(securtiyJwtName));
		
		return new OpenAPI().addSecurityItem(securityRequirement)
				            .components(components);
	}

}
