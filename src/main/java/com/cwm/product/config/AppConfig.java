package com.cwm.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwm.product.utils.JwtUtils;

@Configuration
	public class AppConfig {
	    @Bean
	    public JwtUtils jwtUtils() {
	        return new JwtUtils();
	    }
	}
