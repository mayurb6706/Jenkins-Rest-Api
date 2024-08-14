package com.cwm.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Value("${cwm.openapi.dev-url}")
	private String devUrl;

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)

				.bearerFormat("Headers").scheme("Bearer");

	}

	@Bean
	public OpenAPI myOpenAPI() {

		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Server URL in Development environment");

		Contact contact = new Contact();
		contact.setEmail("codewithmayur@gmail.com");
		contact.setName("Mayur B");
		contact.setUrl("https://www.codewithmayur.com");

		License ecomLicens = new License().name("Licene of API").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("Ecom Application").version("1.0").contact(contact)
				.description("This API exposes endpoints to manage .product rest api")
				.termsOfService("https://www.codewithmayur.com/terms")
				.license(ecomLicens);

//    return new OpenAPI().info(info).servers(List.of(devServer));
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Authentication"))
				.components(new Components().addSecuritySchemes("Authentication", createAPIKeyScheme())).info(info);
	}
}