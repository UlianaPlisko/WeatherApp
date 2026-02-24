# WeatherApp (Assignment)

Spring Boot backend service that fetches weather data from OpenWeather and exposes it through a REST API.

## Features

- `GET /api/weather?city=<city>` endpoint
- Input validation for `city` query parameter
- Global error handling for validation errors
- Cache manager implemented with Caffeine (`weather` cache)
- Automated tests implemented (service + controller tests)

## Tech Stack

- Java 17
- Spring Boot
- Spring Web / WebFlux (`WebClient`)
- Spring Cache
- Caffeine
- Maven
- JUnit / MockMvc / Mockito

## Configuration

The app reads the OpenWeather API key from an environment variable:

- `OPENWEATHER_API_KEY`

`src/main/resources/application.properties` maps it to:

- `openweather.api-key=${OPENWEATHER_API_KEY}`

## Run the Project

Before starting the application, export the API key in your terminal.

### PowerShell (Windows)

```powershell
$env:OPENWEATHER_API_KEY="your_api_key_here"
mvn spring-boot:run
```

### Bash (Linux/macOS)

```bash
export OPENWEATHER_API_KEY="your_api_key_here"
mvn spring-boot:run
```

If Maven is not installed globally, use the Maven wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

## Run Tests

```bash
mvn test
```

Implemented tests include:

- `WeatherAppApplicationTests` (service/business logic)
- `WeatherControllerTests` (controller validation + API responses)

## Example Request

```http
GET /api/weather?city=Tokyo
```

Example response:

```json
{
  "condition": "clear sky",
  "temperature": 18.5,
  "wind_speed": 25.0
}
```
