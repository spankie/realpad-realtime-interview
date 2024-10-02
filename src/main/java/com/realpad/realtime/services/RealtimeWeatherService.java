package com.realpad.realtime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.realpad.realtime.models.WeatherData;

import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@Slf4j
public class RealtimeWeatherService {
  private static final String CACHE_NAME = "weatherData";

  @Autowired
  private WeatherService weatherService;

  @Autowired
  private CacheManager cacheManager;

  @Cacheable(CACHE_NAME)
  public WeatherData getWeather(float longitude, float latitude) throws Exception {
    log.error("getting weather");
    try {
      return weatherService.getWeather(longitude, latitude);
    } catch (Exception e) {
      throw new Exception("error getting weather forecast", e);
    }
  }

  // cache hourly
  @Scheduled(fixedRate = 60 * 60 * 1000)
  public void clearCache() {
    log.debug("clearing cache {}", CACHE_NAME);
    cacheManager.getCache(CACHE_NAME).clear();
  }
}
