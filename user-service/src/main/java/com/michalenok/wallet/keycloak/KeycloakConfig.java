package com.michalenok.wallet.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Configuration;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class KeycloakConfig {

    static Keycloak keycloak = null;
    final static String serverUrl = "http://keycloak:8080/auth/";
    public final static String  masterRealm = "master";
    public final static String realm = "wallet";
    final static String clientId = "admin-cli";
    final static String clientSecret = "7nXKC0SNUP253hPj09OB5pIURaFU1nNJ";
    final static String userName = "admin";
    final static String password = "admin";

    public static Keycloak getInstance(){
        if(keycloak == null){
            log.info("Keycloak");
            keycloak = KeycloakBuilder.builder()
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(masterRealm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(userName)
                    .password(password)
                    .serverUrl(serverUrl)
                    .build();
        }
        return keycloak;
    }
}