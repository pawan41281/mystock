package org.mystock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE")
						.allowedHeaders("*");
						//.allowCredentials(true);
				WebMvcConfigurer.super.addCorsMappings(registry);
			}
		};
	}
}
