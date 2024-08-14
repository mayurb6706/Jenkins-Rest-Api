package com.cwm.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwm.ecom.utils.JwtUtils;

@Configuration
	public class AppConfig {
	    @Bean
	    public JwtUtils jwtUtils() {
	        return new JwtUtils();
	    }
	}
