<template>
  <v-app>
    <v-container fluid class="pa-0">
      <WeekDatePicker />
      <canvas ref="barChart" />
    </v-container>
  </v-app>
</template>

<script>
import { Chart, registerables } from "chart.js";
import WeekDatePicker from "./WeekDatePicker.vue";
Chart.register(...registerables);

export default {
  components: { WeekDatePicker },
  data: () => ({
    type: "line",
    data: {
      labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
      datasets: [
        {
          label: "# of Votes",
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            "rgba(255, 99, 132, 0.2)",
            "rgba(54, 162, 235, 0.2)",
            "rgba(255, 206, 86, 0.2)",
            "rgba(75, 192, 192, 0.2)",
            "rgba(153, 102, 255, 0.2)",
            "rgba(255, 159, 64, 0.2)",
          ],
          borderColor: [
            "rgba(255, 99, 132, 1)",
            "rgba(54, 162, 235, 1)",
            "rgba(255, 206, 86, 1)",
            "rgba(75, 192, 192, 1)",
            "rgba(153, 102, 255, 1)",
            "rgba(255, 159, 64, 1)",
          ],
          borderWidth: 1,
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
      new Chart(this.$refs.barChart, {
        type: "line",
        data: this.data,
        options: this.options,
      });
    },
  },
};
</script>
