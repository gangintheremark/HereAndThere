package com.ssafy.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/swagger-ui.html

//@OpenAPIDefinition(
//	    info = @Info(
//	        title = "SSAFY Board API 명세서",
//	        description = "<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">",
//	        version = "v1",
//	        contact = @Contact(
//	            name = "hissam",
//	            email = "hissam@ssafy.com",
//	            url = "http://edu.ssafy.com"
//	        )
//	    )
//	)

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("Enjoy Trip API 명세서").description(
				"<h3>Enjoy Trip API Reference for Developers</h3>Enjoy trip API<br>")
				.version("v1");

		return new OpenAPI().components(new Components()).info(info);
	}

	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder().group("ssafy-all").pathsToMatch("/**/**").build();
	}
	
	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("ssafy-user").pathsToMatch("/user/**").build();
	}
	
	@Bean
	public GroupedOpenApi tripApi() {
		return GroupedOpenApi.builder().group("ssafy-trip").pathsToMatch("/trip/**").build();
	}
	
	@Bean
	public GroupedOpenApi boardApi() {
		return GroupedOpenApi.builder().group("ssafy-board").pathsToMatch("/board/**").build();
	}
	
	@Bean
	public GroupedOpenApi reviewApi() {
		return GroupedOpenApi.builder().group("ssafy-review").pathsToMatch("/review/**").build();
	}
	
	@Bean
	public GroupedOpenApi planApi() {
		return GroupedOpenApi.builder().group("ssafy-plan").pathsToMatch("/plan/**").build();
	}

}