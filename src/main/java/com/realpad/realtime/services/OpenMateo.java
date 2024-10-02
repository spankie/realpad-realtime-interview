package com.realpad.realtime.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.realpad.realtime.models.WeatherData;

@Service
public class OpenMateo implements WeatherService {

  @Value("${open-mateo.api.url}")
  private String API_URL;
  
  private final RestTemplate restTemplate = new RestTemplate();

  @Override
  public WeatherData getWeather(float longitude, float latitude) throws RestClientException {
    final String url = String.format(API_URL, latitude, longitude);
    return restTemplate.getForObject(url, WeatherData.class);
  }
}
