<template>
  <v-app>
    <v-container fluid class="pa-0">
      <MonthDatePicker :date="this.date" @change="change(date)" />
      <canvas ref="polarArea" />
    </v-container>
  </v-app>
</template>

<script>
import { Chart, registerables } from "chart.js";
import MonthDatePicker from "./MonthDatePicker.vue";
Chart.register(...registerables);

export default {
  components: { MonthDatePicker },
  name: "MonthChart",
  data: () => ({
    date: new Date(),
    charts: [],
  }),
  computed: {},
  methods: {
    createChart() {
      this.charts.forEach((element) => {
        element.destroy();
      });
      let chart = new Chart(this.$refs.polarArea, {
        type: "polarArea",
        data: {
          labels: this.$store.getters.monthLabels,
          datasets: [
            {
              label: "월별 차트",
              data: this.$store.getters.monthData,
              backgroundColor: [
                "#007AFF",
                "#FFDB1E",
                "#EA3838",
                "#FF9500",
                "#5AC165",
              ],
            },
          ],
        },
      });
      this.charts.push(chart);
    },
    change(date) {
      this.date = date;
      this.$store
        .dispatch("getMonthChart", {
          userSeq: 1,
          date: this.date.toISOString().substring(0, 10),
        })
        .then(() => {
          this.createChart();
        });
    },
  },
  mounted() {
    this.date.setDate(1);
    this.change(this.date);
  },
};
</script>
