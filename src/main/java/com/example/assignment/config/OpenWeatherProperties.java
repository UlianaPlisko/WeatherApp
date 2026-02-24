package com.example.assignment.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openweather")
@Getter
@Setter
public class OpenWeatherProperties {

    private String baseUrl;
    private String apiKey;

}