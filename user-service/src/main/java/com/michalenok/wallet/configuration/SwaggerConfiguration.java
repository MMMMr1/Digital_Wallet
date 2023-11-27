package com.michalenok.wallet.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@OpenAPIDefinition
@SecurityScheme(name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                clientCredentials  = @OAuthFlow(tokenUrl = "${openapi.oAuthFlow.tokenUrl}",
                scopes = {
                @OAuthScope(name = "openid", description = "openid scope")
        })))
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi(
            @Value("${openapi.service.name}") String appName,
            @Value("${openapi.service.version}") String serviceVersion,
            @Value("${openapi.service.description}") String appDescription,
            @Value("${openapi.service.url}") String url
    ) {
        return new OpenAPI()
                .info(new Info().title(appName)
                        .version(serviceVersion)
                        .description(appDescription))
                .servers(List.of(new Server().url(url)));
    }
}