package com.example.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient openWeatherWebClient(WebClient.Builder builder,
                                          OpenWeatherProperties properties) {

        return builder
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}