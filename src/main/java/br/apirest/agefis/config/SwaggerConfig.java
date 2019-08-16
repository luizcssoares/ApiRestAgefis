package br.apirest.agefis.config;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiProduct() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.apirest.agefis"))
				.paths(PathSelectors.any())				
				.build()
				.apiInfo(informacoesApi().build());		
	}
	
	private ApiInfoBuilder informacoesApi() {
		 
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		
		apiInfoBuilder.title("API Rest Agefis");
		apiInfoBuilder.description("API REST do Gerenciamento Estacionamento");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.termsOfServiceUrl("Termo de uso: Deve ser usada para estudos.");
		apiInfoBuilder.license("Licença - Open Source");
		apiInfoBuilder.licenseUrl("http://");
		//apiInfoBuilder.contact(this.contato());
 
		return apiInfoBuilder;
 
	}
	
	/*
	private Contact contato() {
		return new Contact("Luiz Carlos",	"http://",	"luizcssoares@gmail.com");
	}
	*/
}
