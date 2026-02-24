package com.example.assignment.dto;

import lombok.Data;
import java.util.List;

@Data
public class OpenWeatherDTO {

    private List<Weather> weather;
    private Main main;
    private Wind wind;

    @Data
    public static class Weather {
        private String description;
    }

    @Data
    public static class Main {
        private Double temp;
    }

    @Data
    public static class Wind {
        private Double speed;
    }
}