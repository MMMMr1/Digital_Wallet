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
@WireMockTest(httpPort = 8183)
class ResourceServerSecurityAccountConfigurationTest {
    @Autowired
    WebTestClient webTestClient;

    @TestConfiguration
    static class TestGatewayConfiguration {
        public TestGatewayConfiguration(ApiGatewayServiceConfigData properties) {
            String wireMockPort = "8183";
            properties.setUserServiceUri("http://localhost:8182");
            properties.setAccountServiceUri("http://localhost:" + wireMockPort);
        }
    }

    @Test
    void givenRequestNotAuthorized_whenAccountServiceGet_Accounts_thenUnauthorized() {
        webTestClient
                .get()
                .uri("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void givenRequestWithRoleAdmin_whenAccountServiceGet_Accounts_thenOk() {
        stubFor(get(urlPathMatching("/api/v1/accounts"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200))
        );
        webTestClient
                .get()
                .uri("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        verify(1, getRequestedFor(urlEqualTo("/api/v1/accounts")));
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void givenRequestWithRoleUserAndAdmin_whenAccountServiceGet_Accounts_thenOk() {
        stubFor(get(urlPathMatching("/api/v1/accounts"))
                .willReturn(aResponse()
                        .withStatus(200)
                )
        );
        webTestClient
                .get()
                .uri("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();
        verify(1, getRequestedFor(urlEqualTo("/api/v1/accounts")));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequestWithRoleUser_whenAccountServiceGet_Accounts_thenAccsessDenied() {
        webTestClient
                .get()
                .uri("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isForbidden();
    }
}