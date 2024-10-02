package com.realpad.realtime.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.realpad.realtime.models.WeatherData;
import com.realpad.realtime.services.WeatherService;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private WeatherService weatherService;

  @Test
  public void testGetWeatherWithCorrectDetails() throws Exception {
    var latitiude = 1.0f;
    var longitude = 1.0f;
    WeatherData weatherData = new WeatherData();
    weatherData.setLatitude(latitiude);
    weatherData.setLongitude(longitude);

    when(weatherService.getWeather(longitude, latitiude)).thenReturn(weatherData);

    mvc.perform(MockMvcRequestBuilders.get(
        Strings.formatIfArgs("/weather?longitude=%f&latitude=%f", longitude, latitiude))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("Success"))
        .andExpect(jsonPath("$.data").exists())
        .andExpect(jsonPath("$.data.longitude").value(longitude))
        .andExpect(jsonPath("$.data.latitude").value(latitiude));
  }

  @Test
  public void testGetWeatherWithNoLocation() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/weather")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.status").value("400"))
      .andExpect(jsonPath("$.detail").value("Required parameter 'longitude' is not present."));
  }
}
