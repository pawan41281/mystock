package org.mystock.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

	@Bean
	ModelMapper initializeModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
