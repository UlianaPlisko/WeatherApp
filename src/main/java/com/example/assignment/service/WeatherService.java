package com.example.assignment.service;

import com.example.assignment.client.OpenWeatherClient;
import com.example.assignment.dto.OpenWeatherDTO;
import com.example.assignment.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final OpenWeatherClient openWeatherClient;

    public ResponseDTO getWeatherForCity(String city) {

        OpenWeatherDTO dto = openWeatherClient.getWeather(city);

        if (dto == null ||
                dto.getWeather() == null ||
                dto.getWeather().isEmpty() ||
                dto.getMain() == null ||
                dto.getWind() == null) {
            throw new IllegalStateException("Incomplete weather data for city: " + city);
        }

        String condition = dto.getWeather().get(0).getDescription();
        Double temperature = dto.getMain().getTemp();

        Double windSpeedKmh = dto.getWind().getSpeed() * 3.6;

        if (windSpeedKmh < 0) {
            windSpeedKmh = 0.0;
        }

        return new ResponseDTO(
                condition,
                temperature,
                windSpeedKmh
        );
    }
}