package com.example.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    private String condition;

    private Double temperature;

    @JsonProperty("wind_speed")
    private Double windSpeed;
}