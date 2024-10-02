export interface WeatherTimeline {
  time: string;
  temperature: number;
}

export interface IWeather {
  current: {
    temperature_2m: number;
    time: string;
  };
  current_units: {
    temperature_2m: string;
  };
  hourly: {
    temperature_2m: number[];
    time: string[];
  };
}