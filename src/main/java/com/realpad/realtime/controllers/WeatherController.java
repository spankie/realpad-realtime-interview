package com.realpad.realtime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realpad.realtime.controllers.apiresponse.ApiResponse;
import com.realpad.realtime.models.WeatherData;
import com.realpad.realtime.services.RealtimeWeatherService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WeatherController {

	@Autowired
	private RealtimeWeatherService realtimeWeatherService;

	@GetMapping("/weather")
	public ResponseEntity<ApiResponse<WeatherData>> index(
			@RequestParam("longitude") float longitude,
			@RequestParam("latitude") float latitude) {
		ApiResponse<WeatherData> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			response = new ApiResponse<WeatherData>(
					realtimeWeatherService.getWeather(longitude, latitude),
					"Success",
					org.springframework.http.HttpStatus.OK);

		} catch (Exception e) {
			log.error("error getting weather forecast", e);
		}
		return response.toResponseEntity();
	}

}