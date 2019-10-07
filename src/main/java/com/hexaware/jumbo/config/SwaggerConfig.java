package com.hexaware.jumbo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.google.common.base.Predicates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * configuration.
 */
@Configuration
/**
 * Enable swagger2.
 */
@EnableSwagger2
/**
 * SwaggerConfiguration.
 */
public class SwaggerConfig {

    /**
     * SwaggerApi.
     * @return Docket api info.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(Predicates.not(PathSelectors.regex("/error")))
                    .build()
                    .apiInfo(metadata())
                    .useDefaultResponseMessages(false)
                    .securitySchemes(new ArrayList<>(Arrays.asList(new ApiKey("Bearer %token",
                    "Authorization", "Header"))))
                    .genericModelSubstitutes(Optional.class);

    }

    /**
     * Creating swagger metadata.
     */
    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                    .title("Jumbo Application")
                    .description("Jumbo SpringBoot Application")
                    .version("1.0")
                    .build();
    }
}
