<template>
  <v-app style="margin-top: 70px; margin-bottom: 70px">
    <h1 align="left">감정곡선</h1>
    <v-container fluid class="pa-0">
      <v-card style="width: 100%; height: 50px">
        <v-tabs v-model="tab" fixed-tabs bg-color="primary">
          <v-tab v-for="item in items" :key="item.tab">
            {{ item.tab }}
          </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
          <v-tab-item v-for="item in items" :key="item.tab">
            <v-card flat>
              <v-card-text>
                <component
                  v-bind:is="item.content"
                  style="margin-top: 16px"
                ></component>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
      </v-card>
    </v-container>
  </v-app>
</template>

<script>
import WeekChart from "./chart/WeekChart.vue";
import MonthChart from "./chart/MonthChart.vue";

export default {
  components: {
    WeekChart,
    MonthChart,
  },
  data: () => ({
    tab: null,
    items: [
      { tab: "Week", content: "WeekChart" },
      { tab: "Month", content: "MonthChart" },
    ],
  }),
  created() {
    this.$store.dispatch("setShowBottomNavigation", true);
    this.$store.dispatch("setShowTopNavigation", true);
  },
};
</script>

<style>
h1 {
  margin-left: 16px;
}
.v-card__subtitle,
.v-card__text,
.v-card__title {
  padding: 0px;
}
.v-application--wrap {
  min-height: 500px;
}
</style>
