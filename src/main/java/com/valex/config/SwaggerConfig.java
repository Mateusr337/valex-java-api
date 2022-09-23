package com.valex.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public Docket docket () {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("com.valex.controller"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo () {
    return new ApiInfoBuilder ()
        .title("Valex")
        .description("Api de usuarios seus cartões e as lojas que ele pode comprar")
        .version("1.0")
        .contact(contact())
        .build();
  }

  private Contact contact () {
    return new Contact(
        "Mateus Rossetto",
        "https://github.com/Mateusr337",
        "null"
    );
  }

  public ApiKey apiKey () {
    return new ApiKey ("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext () {
    return SecurityContext.builder().securityReferences(defaultAuth())
        .forPaths(PathSelectors.any()).build();
  }

  public List<SecurityReference> defaultAuth () {
    AuthorizationScope authorizationScope = new AuthorizationScope(
        "global",
        "accessEverything"
        );

    AuthorizationScope[] scopes = new AuthorizationScope[1];
    scopes[0] = authorizationScope;

    SecurityReference reference = new SecurityReference("JWT", scopes);
    List<SecurityReference> auths = new ArrayList<>();
    auths.add(reference);

    return auths;
  }

}
