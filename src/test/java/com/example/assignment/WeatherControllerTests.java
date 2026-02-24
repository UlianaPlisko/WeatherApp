package com.example.assignment;

import com.example.assignment.controller.WeatherController;
import com.example.assignment.dto.ResponseDTO;
import com.example.assignment.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void validCity_shouldReturn200AndWeatherData() throws Exception {
        ResponseDTO mockResponse = new ResponseDTO("clear sky", 18.5, 25.0);
        when(weatherService.getWeatherForCity(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/weather")
                        .param("city", "Tokyo")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.condition").value("clear sky"))
                .andExpect(jsonPath("$.temperature").value(18.5))
                .andExpect(jsonPath("$.wind_speed").value(25.0));
    }

    @Test
    void blankCity_shouldReturn400WithValidationError() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .param("city", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages[0]").value("city must not be blank"));
    }

    @Test
    void emptyCityParam_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .param("city", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").value(org.hamcrest.Matchers.hasItem("city must not be blank")));
    }

    @Test
    void cityTooShort_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .param("city", "A"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").value(org.hamcrest.Matchers.hasItem(
                        "city must be between 2 and 100 characters")));
    }

    @Test
    void cityTooLong_shouldReturn400() throws Exception {
        String longCity = "A".repeat(101);

        mockMvc.perform(get("/api/weather")
                        .param("city", longCity))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").value(org.hamcrest.Matchers.hasItem(
                        "city must be between 2 and 100 characters")));
    }

    @Test
    void cityWithInvalidCharacters_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .param("city", "Tokyo!!! "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").value(org.hamcrest.Matchers.hasItem(
                        "city contains invalid characters")));
    }

    @Test
    void cityWithAllowedSpecialChars_shouldBeValid() throws Exception {
        ResponseDTO mockResponse = new ResponseDTO("cloudy", 12.0, 15.0);
        when(weatherService.getWeatherForCity(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/weather")
                        .param("city", "São Paulo"))
                .andExpect(status().isOk());
    }
}