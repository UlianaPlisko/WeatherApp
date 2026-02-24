package com.example.assignment.client;

import com.example.assignment.config.OpenWeatherProperties;
import com.example.assignment.dto.OpenWeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final WebClient openWeatherWebClient;
    private final OpenWeatherProperties properties;

    public OpenWeatherDTO getWeather(String city) {

        return openWeatherWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("q", city)
                        .queryParam("appid", properties.getApiKey())
                        .queryParam("units", "metric")
                        .build()
                )
                .retrieve()
                .bodyToMono(OpenWeatherDTO.class)
                .block();
    }
}