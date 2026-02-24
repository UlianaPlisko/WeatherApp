package com.example.assignment;

import com.example.assignment.client.OpenWeatherClient;
import com.example.assignment.dto.OpenWeatherDTO;
import com.example.assignment.dto.ResponseDTO;
import com.example.assignment.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherAppApplicationTests {

	@Autowired
	private WeatherService weatherService;

	@MockitoBean
	private OpenWeatherClient openWeatherClient;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnWeatherWithWindConvertedToKmH() {
		OpenWeatherDTO mockResponse = createMockWeatherResponse("clear sky", 18.5, 6.94);

		when(openWeatherClient.getWeather(anyString())).thenReturn(mockResponse);

		ResponseDTO result = weatherService.getWeatherForCity("Tokyo");

		assertThat(result.getCondition()).isEqualTo("clear sky");
		assertThat(result.getTemperature()).isEqualTo(18.5);
		assertThat(result.getWindSpeed()).isCloseTo(25.0, within(0.02));
	}

	@Test
	void shouldThrowExceptionWhenWeatherDataIsIncomplete() {
		when(openWeatherClient.getWeather(anyString())).thenReturn(new OpenWeatherDTO());

		assertThatThrownBy(() -> weatherService.getWeatherForCity("InvalidCity"))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("Incomplete weather data");
	}

	private OpenWeatherDTO createMockWeatherResponse(String desc, double temp, double windMs) {
		OpenWeatherDTO dto = new OpenWeatherDTO();

		OpenWeatherDTO.Weather w = new OpenWeatherDTO.Weather();
		w.setDescription(desc);
		dto.setWeather(List.of(w));

		OpenWeatherDTO.Main m = new OpenWeatherDTO.Main();
		m.setTemp(temp);
		dto.setMain(m);

		OpenWeatherDTO.Wind wind = new OpenWeatherDTO.Wind();
		wind.setSpeed(windMs);
		dto.setWind(wind);

		return dto;
	}
}