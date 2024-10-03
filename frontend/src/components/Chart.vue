<script setup lang="ts">
import { ref, watch } from 'vue';
import ApexCharts from 'vue3-apexcharts';

const {name, xaxis, yaxis, suffix, title} = defineProps({
  xaxis: Array<string>,
  yaxis: Array<number>,
  suffix: {
    type: String,
    default: '˚C',
  },
  name: {
    type: String,
    default: 'Temperature',
  },
  title: {
    type: String,
    default: 'Temperature forecast for the next 7 days',
  },
});

// Chart data and options
const series = ref([
  {
    name: name,
    data: yaxis,
  },
]);
const chartOptions = ref({
  chart: {
    height: 350,
    type: 'bar',
  },
  plotOptions: {
    bar: {
      borderRadius: 10,
      dataLabels: {
        position: 'top', // top, center, bottom
      },
    },
  },
  dataLabels: {
    enabled: true,
    formatter: function (val: any) {
      return val + suffix;
    },
    offsetY: -20,
    style: {
      fontSize: '12px',
      colors: ['#304758'],
    },
  },
  xaxis: {
    categories: xaxis,

    position: 'top',
    axisBorder: {
      show: false,
    },
    axisTicks: {
      show: false,
    },
    crosshairs: {
      fill: {
        type: 'gradient',
        gradient: {
          colorFrom: '#D8E3F0',
          colorTo: '#BED1E6',
          stops: [0, 100],
          opacityFrom: 0.4,
          opacityTo: 0.5,
        },
      },
    },
    tooltip: {
      enabled: true,
    },
  },
  yaxis: {
    axisBorder: {
      show: false,
    },
    axisTicks: {
      show: false,
    },
    labels: {
      show: false,
      formatter: function (val: any) {
        return val + suffix;
      },
    },
  },
  title: {
    text: title,
    floating: true,
    offsetY: 330,
    align: 'center',
    style: {
      color: '#444',
    },
  },
});
</script>

<template>
  <div class="">
    <ApexCharts width="" type="bar" :options="chartOptions" :series="series" />
  </div>
</template>
