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
  components: { MonthDatePicker },
  name: "MonthChart",
  data: () => ({}),
  computed: {},
  methods: {
    createChart() {
      new Chart(this.$refs.polarArea, {
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
    },
  },
  mounted() {
    this.$store
      .dispatch("getMonthChart", { userSeq: 1, date: "2023-03-23" })
      .then(() => {
        this.createChart();
      });
  },
};
</script>
