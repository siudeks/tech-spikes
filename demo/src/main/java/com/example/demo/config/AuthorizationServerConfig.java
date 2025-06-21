package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

// Mark this class as a configuration class and enable the authorization server
@Configuration
public class AuthorizationServerConfig {

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // Configure the authorization server using the latest settings
        http
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        // Define the settings for the authorization server
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        // Customize the JWT authentication converter if needed
        return new JwtAuthenticationConverter();
    }
}
