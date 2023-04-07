<template>
  <v-app>
    <v-container fluid class="pa-0">
      <WeekDatePicker
        :startDate="this.startDate"
        :endDate="this.endDate"
        @change="change(startDate, endDate)"
      />
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
  data: () => ({
    startDate: new Date(new Date().setDate(new Date().getDate() - 6)),
    endDate: new Date(),
    charts: [],
    labels: [],
  }),
  methods: {
    createChart() {
      this.charts.forEach((element) => {
        element.destroy();
      });
      let chart = new Chart(this.$refs.barChart, {
        type: "line",
        data: {
          labels: this.labels,
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
      this.charts.push(chart);
    },
    change(startDate, endDate) {
      this.startDate = startDate;
      this.endDate = endDate;
      this.$store
        .dispatch("getWeekChart", {
          userSeq: localStorage.getItem("userSeq"),
          date: endDate.toISOString().substring(0, 10),
        })
        .then(() => {
          this.labels = [];
          for (
            var day = startDate;
            day <= endDate;
            day.setDate(day.getDate() + 1)
          ) {
            this.labels.push(new Date(day).toISOString().substring(5, 10));
          }
          this.createChart();
        });
    },
  },
  mounted() {
    this.change(this.startDate, this.endDate);
  },
};
</script>
