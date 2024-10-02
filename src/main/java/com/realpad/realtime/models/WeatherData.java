package com.realpad.realtime.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;

@Setter
public class WeatherData {
  public float latitude;
  public float longitude;
  @JsonProperty("generationtime_ms")
  public float generationtimeMs;
  @JsonProperty("utc_offset_seconds")
  public int utcOffsetSeconds;
  public String timezone;
  @JsonProperty("timezone_abbreviation")
  public String timezoneAbbreviation;
  public int elevation;
  @JsonProperty("current_units")
  public CurrentUnits currentUnits;
  public Current current;
  @JsonProperty("hourly_units")
  public HourlyUnits hourlyUnits;
  public Hourly hourly;

  private static class CurrentUnits {
    public String time;
    public String interval;
    @JsonProperty("temperature_2m")
    public String temperature2m;
    @JsonProperty("wind_speed_10m")
    public String windSpeed10m;
  }
  
  private static class Current {
    public String time;
    public int interval;
    @JsonProperty("temperature_2m")
    public float temperature2m;
    @JsonProperty("wind_speed_10m")
    public int windSpeed10m;
  }

  private static class HourlyUnits {
    public String time;
    @JsonProperty("temperature_2m")
    public String temperature2m;
    @JsonProperty("relative_humidity_2m")
    public String relativeHumidity2m;
    @JsonProperty("wind_speed_10m")
    public String windSpeed10m;
  }

  private static class Hourly {
    public String[] time;
    @JsonProperty("temperature_2m")
    public float[] temperature2m;
    @JsonProperty("relative_humidity_2m")
    public int[] relativeHumidity2m;
    @JsonProperty("wind_speed_10m")
    public float[] windSpeed10m;
  }
}
