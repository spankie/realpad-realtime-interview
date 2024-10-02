package com.realpad.realtime.services;

import com.realpad.realtime.models.WeatherData;

public interface WeatherService {
  public WeatherData getWeather(float longitude, float latitude);
}
