package com.michalenok.wallet.security;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.michalenok.wallet.configuration.ApiGatewayServiceConfigData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8180)
class ResourceServerSecurityUserConfigurationTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    void givenRequestNotAuthorized_whenUserServiceGet_Users_thenUnauthorized() {
        webTestClient
                .get()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void givenRequestWithRoleAdmin_whenUserServiceGet_Users_thenOk() {
        stubFor(get(urlPathMatching("/api/v1/users"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200))
        );
        webTestClient
                .get()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        verify(1, getRequestedFor(urlEqualTo("/api/v1/users")));
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void givenRequestWithRoleUserAndAdmin_whenUserServiceGet_Users_thenOk() {
        stubFor(get(urlPathMatching("/api/v1/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                )
        );
        webTestClient
                .get()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        verify(1, getRequestedFor(urlEqualTo("/api/v1/users")));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequestWithRoleUser_whenUserServiceGet_Users_thenAccsessDenied() {
        webTestClient
                .get()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    void givenRequestNotAuthorized_whenUserServicePost_User_thenUnauthorized() {
        webTestClient
                .post()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void givenRequestWithRoleAdmin_whenUserServicePost_User_thenOk() {
        stubFor(post(urlPathMatching("/api/v1/users"))
                .willReturn(created())
        );
        webTestClient
                .post()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
        verify(1, postRequestedFor(urlEqualTo("/api/v1/users")));
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void givenRequestWithRoleUserAndAdmin_whenUserServicePost_User_thenOk() {
        stubFor(post(urlPathMatching("/api/v1/users"))
                .willReturn(created())
        );
        webTestClient
                .post()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
        verify(1, postRequestedFor(urlEqualTo("/api/v1/users")));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequestWithRoleUser_whenUserServicePost_User_thenAccsessDenied() {
        webTestClient
                .post()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    void givenRequestNotAuthorized_whenUserServiceGet_UserById_thenUnauthorized() {
        UUID uuid = UUID.randomUUID();
        webTestClient
                .get()
                .uri("/api/v1/users/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void givenRequestWithRoleAdmin_whenUserServiceGet_UserById_thenOk() {
        UUID uuid = UUID.randomUUID();
        stubFor(get(urlPathTemplate("/api/v1/users/{uuid}"))
                .withPathParam("uuid", equalTo(String.valueOf(uuid)))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200))
        );
        webTestClient
                .get()
                .uri("/api/v1/users/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        verify(1, getRequestedFor(urlEqualTo("/api/v1/users/" + uuid)));
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void givenRequestWithRoleUserAndAdmin_whenUserServiceGet_UserById_thenOk() {
        UUID uuid = UUID.randomUUID();
        stubFor(get(urlPathTemplate("/api/v1/users/{uuid}"))
                .withPathParam("uuid", equalTo(String.valueOf(uuid)))
                .willReturn(aResponse()
                        .withStatus(200)
                )
        );
        webTestClient
                .get()
                .uri("/api/v1/users/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        verify(1, getRequestedFor(urlEqualTo("/api/v1/users/" + uuid)));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequestWithRoleUser_whenUserServiceGet_UserById_thenAccsessDenied() {
        UUID uuid = UUID.randomUUID();
        webTestClient
                .get()
                .uri("api/v1/users/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isForbidden();
    }
    @TestConfiguration
    static class TestGatewayConfiguration {
        public TestGatewayConfiguration(ApiGatewayServiceConfigData properties) {
            String wireMockPort = "8180";
            properties.setUserServiceUri("http://localhost:"+ wireMockPort);
            properties.setAccountServiceUri("http://localhost:8089");
        }
    }
}