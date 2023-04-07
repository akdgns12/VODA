<template>
  <v-app-bar>
    <v-layout>
      <v-bottom-navigation
        v-model="value"
        grow
        class="bottom-nav d-flex justify-between"
      >
        <v-btn @click="toCalendar">
          <v-icon large color="black">
            {{ icons.mdiCalendarMonth }}
          </v-icon>
        </v-btn>

        <v-btn @click="toRecord">
          <v-icon color="red" size="60">
            {{ icons.mdiRadioboxMarked }}
          </v-icon>
        </v-btn>

        <v-btn @click="toChart">
          <v-icon large color="black">
            {{ icons.mdiChartBellCurve }}
          </v-icon>
        </v-btn>
      </v-bottom-navigation>
    </v-layout>
  </v-app-bar>
</template>

<script>
import {
  mdiRadioboxMarked,
  mdiChartBellCurve,
  mdiCalendarMonth,
} from "@mdi/js";

export default {
  name: "BottomNavigation",
  data: () => ({
    value: 3,
    icons: {
      mdiChartBellCurve,
      mdiCalendarMonth,
      mdiRadioboxMarked,
    },
  }),
  methods: {
    toRecord() {
      this.$router.push("/record");
    },
    toCalendar() {
      this.$router.push("/calendar");
    },
    toChart() {
      this.$router.push("/chart");
    },
  },
};
</script>

<style scoped lang="scss">
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
  margin-left: 0px;
}
.v-bottom-navigation {
  justify-content: space-evenly;
}
</style>
