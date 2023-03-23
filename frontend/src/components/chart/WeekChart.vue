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
  name: "WeekChart",
  data: () => ({}),

  methods: {
    createChart() {
      new Chart(this.$refs.barChart, {
        type: "line",
        data: {
          labels: [
            "03/18",
            "03/19",
            "03/20",
            "03/21",
            "03/23",
            "03/22",
            "03/23",
          ],
          datasets: [
            {
              label: "슬픔",
              data: this.$store.getters.weeks[0].data,
              backgroundColor: "#007AFF",
              borderColor: "#007AFF",
            },
            {
              label: "놀람",
              data: this.$store.getters.weeks[1].data,
              backgroundColor: "#FFDB1E",
              borderColor: "#FFDB1E",
            },
            {
              label: "화남",
              data: this.$store.getters.weeks[2].data,
              backgroundColor: "#EA3838",
              borderColor: "#EA3838",
            },
            {
              label: "보통",
              data: this.$store.getters.weeks[3].data,
              backgroundColor: "#FF9500",
              borderColor: "#FF9500",
            },
            {
              label: "행복",
              data: this.$store.getters.weeks[4].data,
              backgroundColor: "#5AC165",
              borderColor: "#5AC165",
            },
          ],
        },
      });
    },
  },
  mounted() {
    this.$store
      .dispatch("getWeekChart", { userSeq: 1, date: "2023-03-23" })
      .then(() => {
        this.createChart();
      });
  },
};
</script>
