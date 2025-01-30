package com.springboot.enotes.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		
		OpenAPI openapi=new OpenAPI();
		
		
		Info info=new Info();
		info.setTitle("Enotes API") ;
		info.setDescription("Enotes API") ;
		info.setVersion("1.0.0") ;
		info.termsOfService("http://www.google.com") ;
		info.setContact(new Contact().email("gouravvohra3@gmai.com").name("Gourav Bohra").url("http://www.google.com/contact"));
		info.setLicense(new License().name("Enotes 1.0").url("http://www.google.com/licenece"));

	List<Server>	serverList=List.of(new Server().description("Dev").url("http://localhost:8080"),
		new Server().description("Test").url("http://localhost:8081"),
		new Server().description("Prod").url("http://localhost:8082"));
	
	//bearer sbhj.jhubuyb.ijbhubhu.
	
	
	SecurityScheme securityScheme =new SecurityScheme().name("Authorization")
									.scheme("bearer").type(Type.HTTP)
									.bearerFormat("JWT").in(In.HEADER);

	Components components=new Components().addSecuritySchemes("Token", securityScheme);
	openapi.setServers(serverList);
	openapi.setInfo(info);
	openapi.setComponents(components);
	openapi.setSecurity(List.of(new SecurityRequirement().addList("Token")));
	
		return openapi;
	}
	
	
}
