package com.cwm.product.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

  @Value("${cwm.openapi.dev-url}")
  private String devUrl;



  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");


    Contact contact = new Contact();
    contact.setEmail("codewithmayur@gmail.com");
    contact.setName("Mayur B");
    contact.setUrl("https://www.codewithmayur.com");

//    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Product Management API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage .product rest api").termsOfService("https://www.codewithmayur.com/terms");

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}