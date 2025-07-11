package org.mystock.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
//@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenAPIConfiguration {

	@Bean
	OpenAPI defineOpenApi() {
		Server localhost = new Server();
		localhost.setUrl("http://localhost:9090");
		localhost.setDescription("Development");

		Contact myContact = new Contact();
		myContact.setName("Pawan Kumar");
		myContact.setEmail("pawan.kumar@gmail.com");

		Info information = new Info().title("My Stock API").version("1.0")
				.description("Exposes endpoints for mystock api.").contact(myContact)
				.license(new License().name("Apache 2.0").url("http://springdoc.org"));

		return new OpenAPI().info(information).servers(List.of(localhost));
	}

}
