package com.in28minutes.microservices.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange(auth -> auth.anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);

        httpSecurity.headers(headers -> headers.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable));

        return httpSecurity.build();
    }
}
