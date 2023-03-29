<template>
  <v-bottom-navigation
    v-model="value"
    :background-color="color"
    dark
    class="bottom-nav d-flex justify-between"
    v-if="showBottomNavigation"
    grow
  >
    <v-btn @click="toCalendar">
      <span></span>
      <v-icon large>
        {{ icons.mdiCalendarMonth }}
      </v-icon>
    </v-btn>

    <v-btn @click="toRecord">
      <v-icon color="red" size="60">
        {{ icons.mdiRadioboxMarked }}
      </v-icon>
    </v-btn>

    <v-btn>
      <span></span>
      <v-icon large>
        {{ icons.mdiChartBellCurve }}
      </v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
import {
  mdiRadioboxMarked,
  mdiChartBellCurve,
  mdiCalendarMonth,
} from "@mdi/js";
import { mapGetters } from "vuex";

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
  computed: {
    ...mapGetters(["showBottomNavigation"]),
    color() {
      switch (this.value) {
        case 0:
          return "#5AC165";
        case 1:
          return "#855CF8";
        case 2:
          return "#FF9500";
        default:
          return "#5AC165";
      }
    },
  },
  methods: {
    toRecord() {
      this.$router.push("/record");
    },
    toCalendar() {
      this.$router.push("/calendar");
    },
  },
};
</script>

<style></style>
