package com.github.jbrasileiro.lernaeanhydra.springboot.api.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.bind.annotation.RestController;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
	
    @Bean
    public Docket api(@Value("${spring.application.name}")
    					final String applicationName,
                        @Value("${VERSION:latest}")
    					final String version) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(version)
                .apiInfo(getApiInfo(version, applicationName))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .pathMapping("/")
                ;
    }

    private ApiInfo getApiInfo(final String version
    		, final String aplicationName) {
        return new ApiInfoBuilder()
                .title(aplicationName)
                .description(description())
                .contact(new Contact("developer", "https://github.com/jbrasileiro/game-minesweeper-API", ""))
                .version(version)
                .build();
    }

	private String description() {
		StringBuilder builder = new StringBuilder();
		builder.append("Minesweeper (video game)");
		return builder.toString();
	}

}
