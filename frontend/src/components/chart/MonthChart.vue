<template>
  <v-app>
    <v-container fluid class="pa-0">
      <MonthDatePicker />
      <canvas ref="polarArea" />
    </v-container>
  </v-app>
</template>

<script>
import { Chart, registerables } from "chart.js";
import MonthDatePicker from "./MonthDatePicker.vue";
Chart.register(...registerables);

export default {
  components: {
    MonthDatePicker,
  },
  data: () => ({
    type: "line",
    data: {
      labels: ["Red", "Green", "Yellow", "Grey", "Blue"],
      datasets: [
        {
          label: "My First Dataset",
          data: [11, 16, 7, 3, 14],
          backgroundColor: [
            "rgb(255, 99, 132)",
            "rgb(75, 192, 192)",
            "rgb(255, 205, 86)",
            "rgb(201, 203, 207)",
            "rgb(54, 162, 235)",
          ],
        },
      ],
    },
    options: {
      scales: {
        y: {
          beginAtZero: true,
        },
      },
    },
  }),
  mounted() {
    this.createChart();
  },
  methods: {
    createChart() {
      new Chart(this.$refs.polarArea, {
        type: "polarArea",
        data: this.data,
        options: this.options,
      });
    },
  },
};
</script>
