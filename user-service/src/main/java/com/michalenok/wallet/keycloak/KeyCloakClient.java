//package com.michalenok.wallet.keycloak;
//
//import lombok.RequiredArgsConstructor;
//import org.keycloak.OAuth2Constants;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.keycloak.admin.client.resource.UsersResource;
//import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.NotAuthorizedException;
//import javax.ws.rs.core.Response;
//import java.util.Collections;
//
//
//@Component
//@RequiredArgsConstructor
//public class KeyCloakClient {
//    @Value("${keycloak.auth-server-url}")
//    private String keyCloakUrl;
//    @Value("${keycloak.resource}")
//    private String clientId;
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    private final RestTemplate restTemplate;
//
//    public String authenticate(UserCreateDto request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("username",request.mail());
//        parameters.add("password",request.password());
//        parameters.add("grant_type", "password");
//        parameters.add("client_id", clientId);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
//
//        return restTemplate.exchange(getAuthUrl(),
//                HttpMethod.POST,
//                entity,
//                String.class).getBody();
//    }
//
//    public String refreshToken(String refreshToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("grant_type", "refresh_token");
//        parameters.add("client_id", clientId);
//        parameters.add("refresh_token", refreshToken);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
//
//        return restTemplate.exchange(getAuthUrl(),
//                HttpMethod.POST,
//                entity,
//                String.class).getBody();
//    }
//
//    private String getAuthUrl() {
//        return UriComponentsBuilder.fromHttpUrl(keyCloakUrl)
//                .pathSegment("realms")
//                .pathSegment(realm)
//                .pathSegment("protocol")
//                .pathSegment("openid-connect")
//                .pathSegment("token")
//                .toUriString();
//    } @Value("${keycloak.auth-server-url}")
//    private String serverURL;
//    @Value("${keycloak.realm}")
//    private String realm;
//    @Value("${keycloak.resource}")
//    private String clientID;
//    @Value("${keycloak.credentials.secret}")
//    private String clientSecret;
//
//    private final Keycloak keycloakAdminClient;
//
//    public void registerExisting(SignUpRequest request) {
//        this.createKeycloakUser(request);
//    }
//
//
////    public AccessTokenResponse login(LoginRequest request) {
////        try (Keycloak keycloak = this.keycloakCredentialBuilder(request)) {
////            return keycloak.tokenManager().getAccessToken();
////        } catch (NotAuthorizedException e) {
////            throw new KeycloakUnauthorizedException(e.getMessage());
////        } catch (BadRequestException e) {
////            throw new KeycloakBadRequestException(e.getMessage());
////        }
////    }
//
//    public void createKeycloakUser(UserCreateDto request) {
//        UsersResource usersResource = this.keycloakAdminClient.realm(this.realm).users();
//        CredentialRepresentation credentialRepresentation = createPasswordCredentials(request.getPassword());
//
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername(request.mail());
//        user.setEmail(request.mail());
//
//
//        try (Response response = usersResource.create(user)) {
//            if(response.getStatus() == 201) {
//                System.out.println("Created");
//            } else {
//                System.out.println(response.getStatus());
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private Keycloak keycloakAdminBuilder() {
//        return KeycloakBuilder.builder()
//                .realm(realm)
//                .serverUrl(serverURL)
//                .clientId(clientID)
//                .clientSecret(clientSecret)
//                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
//                .build();
//    }
//
//    private Keycloak keycloakCredentialBuilder(LoginRequest request) {
//        return KeycloakBuilder.builder()
//                .realm(this.realm)
//                .serverUrl(this.serverURL)
//                .clientId(this.clientID)
//                .clientSecret(this.clientSecret)
//                .username(request.getUsername())
//                .password(request.getPassword())
//                .build();
//    }
//
//    private CredentialRepresentation createPasswordCredentials(String password) {
//        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
//        passwordCredentials.setTemporary(false);
//        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
//        passwordCredentials.setValue(password);
//        return passwordCredentials;
//    }
//}
//}
