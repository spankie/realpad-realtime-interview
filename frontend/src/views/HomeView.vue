<script setup lang="ts">
import type { WeatherTimeline, IWeather } from '@/schema/weather';
import { ref, watch } from 'vue';
import Chart from '@/components/Chart.vue';

const weather = ref<IWeather>({
  current: {
    temperature_2m: 0,
    time: '',
  },
  current_units: {
    temperature_2m: '°C',
  },
  hourly: {
    temperature_2m: [],
    time: [],
  },
});
const loading = ref(false);
// default to birmingham UK.
const longitude = ref(52.48);
const latitude = ref(-1.88);
const next_five_hours = ref<WeatherTimeline[]>([]);
const xaxis = ref<string[]>([]);
const yaxis = ref<number[]>([]);


const fetchWeather = async (lon: number, lat: number) => {
  if (!lon) lon = longitude.value;
  if (!lat) lat = latitude.value;
  try {
    const response = await fetch(`${import.meta.env.VITE_API_URL}/weather?longitude=${lon}&latitude=${lat}`);
    if (response.status != 200) {
      // TODO: handle errors
      console.error(response);
      return;
    }
    const data = await response.json();
    weather.value = data.data;
  } catch (error) {
    // TODO: handle errors
    console.error(error);
  }
};

if (navigator.geolocation) {
  loading.value = true;
  navigator.geolocation.getCurrentPosition(function (position) {
    fetchWeather(position.coords.longitude, position.coords.latitude).finally(() => {
      loading.value = false;
    });
  });
} else {
  // TODO: handle errors
  console.log("Geolocation is not supported by this browser.");
}

function getAverageTemperatures(temperature_2m: number[]) {
  let average_temparatures = [];
  let avg = 0;
  for (let i = 0; i < temperature_2m.length; i++) {
    avg += temperature_2m[i];
    if (i % 24 == 23) {
      average_temparatures.push(parseFloat((avg / 24).toFixed(2)));
      avg = 0;
    }
  }
  return average_temparatures;
}

watch(weather, () => {
  // get the time from the weather.current.time string "2024-10-02T17:00"
  const [_, time_part] = weather.value.current.time.split('T');
  // get the next five hours
  const hour_number = Number(time_part.split(':')[0]);
  next_five_hours.value = weather.value.hourly.time
    .slice(hour_number, hour_number + 5)
    .map((time, index) => ({
      time: time.split('T')[1],
      temperature: weather.value.hourly.temperature_2m[hour_number + index],
    }));

  xaxis.value = [...new Set(weather.value.hourly.time.map((item) => item.split('T')[0]))];
  yaxis.value = getAverageTemperatures(weather.value.hourly.temperature_2m);
});

</script>

<template>
  <div
    class="flex flex-col items-center justify-center w-screen min-h-screen text-gray-700 p-10 bg-gradient-to-br from-pink-200 via-purple-200 to-indigo-200 space-y-8">

    <div class="w-full max-w-screen-sm bg-white p-10 rounded-xl ring-8 ring-white ring-opacity-40">
      <div class="flex justify-between">
        <div class="flex flex-col">
          <span class="text-6xl font-bold">
            {{ weather.current?.temperature_2m + weather.current_units?.temperature_2m }}
          </span>
          <span class="font-semibold mt-1 text-gray-500">My Location</span>
        </div>
        <svg class="h-24 w-24 fill-current text-yellow-400" xmlns="http://www.w3.org/2000/svg" height="24"
          viewBox="0 0 24 24" width="24">
          <path d="M0 0h24v24H0V0z" fill="none" />
          <path
            d="M6.76 4.84l-1.8-1.79-1.41 1.41 1.79 1.79zM1 10.5h3v2H1zM11 .55h2V3.5h-2zm8.04 2.495l1.408 1.407-1.79 1.79-1.407-1.408zm-1.8 15.115l1.79 1.8 1.41-1.41-1.8-1.79zM20 10.5h3v2h-3zm-8-5c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6zm0 10c-2.21 0-4-1.79-4-4s1.79-4 4-4 4 1.79 4 4-1.79 4-4 4zm-1 4h2v2.95h-2zm-7.45-.96l1.41 1.41 1.79-1.8-1.41-1.41z" />
        </svg>
      </div>
      <div class="flex justify-between mt-12">
        <div v-for="(item, index) in next_five_hours" :key="index" class="flex flex-col items-center">
          <span class="font-semibold text-lg">{{ item.temperature + weather.current_units?.temperature_2m }}</span>
          <svg class="h-10 w-10 fill-current text-gray-400 mt-3" xmlns="http://www.w3.org/2000/svg" height="24"
            viewBox="0 0 24 24" width="24">
            <path d="M0 0h24v24H0V0z" fill="none" />
            <path
              d="M6.76 4.84l-1.8-1.79-1.41 1.41 1.79 1.79zM1 10.5h3v2H1zM11 .55h2V3.5h-2zm8.04 2.495l1.408 1.407-1.79 1.79-1.407-1.408zm-1.8 15.115l1.79 1.8 1.41-1.41-1.8-1.79zM20 10.5h3v2h-3zm-8-5c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6zm0 10c-2.21 0-4-1.79-4-4s1.79-4 4-4 4 1.79 4 4-1.79 4-4 4zm-1 4h2v2.95h-2zm-7.45-.96l1.41 1.41 1.79-1.8-1.41-1.41z" />
          </svg>
          <span class="font-semibold mt-1 text-sm">{{ item.time }}</span>
        </div>
      </div>
    </div>
    <div v-if="xaxis.length && yaxis.length" class="w-full max-w-screen-sm bg-white p-10 rounded-xl ring-8 ring-white ring-opacity-40">
      <Chart title="Temperature forecast for the next 7 days" name="Temperature" :xaxis="xaxis" :yaxis="yaxis" :suffix="weather.current_units.temperature_2m" />
    </div>
  </div>
</template>

<style scoped></style>
