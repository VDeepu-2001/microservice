package com.RestAPI.microservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ApiService {
    private static final Logger log = LoggerFactory.getLogger(ApiService.class);

    @Value("${url}")
    private String swaggerUrl; // Removed static modifier

    @Value("${api.username}")
    private String username; // Removed static modifier

    @Value("${api.password}")
    private String password; // Removed static modifier

    private static RestTemplate restTemplate = null;

    // Constructor-based dependency injection for RestTemplate
    public ApiService(RestTemplate restTemplate) {
        ApiService.restTemplate = restTemplate;
    }

    // Method to call the external API with basic authentication
    public String fetchServiceCategory(int offset, int limit) {

        System.out.println("URL is :" + swaggerUrl);
        String url = swaggerUrl + "?offset=" + offset + "&limit=" + limit;

        log.info("Calling Swagger URL: {}", url);

        // Prepare HTTP headers with Basic Authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getBasicAuthHeader(username, password));

        // Create the HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the API call using RestTemplate
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            log.info("Received response with status: {}", response.getStatusCode());
            log.debug("Response body: {}", response.getBody());

            // Return the response body
            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while calling the API: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch data from API", e);
        }
    }

    // Helper method to generate Basic Authentication header
    private static String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth, StandardCharsets.US_ASCII);
    }
}
