package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationServerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAuthorizationEndpoint() {
        // Test the /oauth/authorize endpoint to ensure it returns a 4xx error when accessed without credentials
        ResponseEntity<String> response = restTemplate.getForEntity("/oauth/authorize", String.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    public void testTokenEndpoint() {
        // Test the /oauth/token endpoint to ensure it returns UNAUTHORIZED when accessed without credentials
        ResponseEntity<String> response = restTemplate.postForEntity("/oauth/token", null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testProtectedResourceWithoutToken() {
        // Test accessing a protected resource without a token to ensure it returns UNAUTHORIZED
        ResponseEntity<String> response = restTemplate.getForEntity("/protected-resource", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testProtectedResourceWithInvalidToken() {
        // Test accessing a protected resource with an invalid token to ensure it returns UNAUTHORIZED
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer invalid-token");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/protected-resource", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testProtectedResourceWithValidToken() {
        // Simulate obtaining a valid token (this would typically be done via the /oauth/token endpoint)
        String validToken = "mock-valid-token"; // Replace with actual token retrieval logic if available

        // Test accessing a protected resource with a valid token to ensure it returns OK and the expected content
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + validToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/protected-resource", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Expected content from protected resource");
    }
}
