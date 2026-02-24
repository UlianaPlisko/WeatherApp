package com.example.assignment.controller;

import com.example.assignment.dto.ResponseDTO;
import com.example.assignment.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<ResponseDTO> getWeather(
            @RequestParam("city")
            @NotBlank(message = "city must not be blank")
            @Size(min = 2, max = 100, message = "city must be between 2 and 100 characters")
            @Pattern(regexp = "[A-Za-zÀ-ž0-9\\s\\-']+", message = "city contains invalid characters")
            String city) {

        ResponseDTO response = weatherService.getWeatherForCity(city.trim());
        return ResponseEntity.ok(response);
    }
}