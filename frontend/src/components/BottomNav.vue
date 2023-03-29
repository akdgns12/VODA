<template>
  <v-app-bar>
    <v-layout class="bottom-nav">
      <v-bottom-navigation grow>
        <v-btn @click="toMain">
          <v-icon large color="black">
            {{ icons.mdiCalendarMonth }}
          </v-icon>
        </v-btn>

        <v-btn @click="toRecord">
          <v-icon color="red" size="60">
            {{ icons.mdiRadioboxMarked }}
          </v-icon>
        </v-btn>

        <v-btn>
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
  data: () => ({
    icons: {
      mdiChartBellCurve,
      mdiCalendarMonth,
      mdiRadioboxMarked,
    },
    value: 3,
  }),
  methods: {
    toRecord() {
      this.$router.push("/record");
    },
    toMain() {
      this.$router.push("/main");
    },
  },
};
</script>

<style scoped lang="scss">
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
  margin-left: -16px;
}
// .v-app-bar {
//   position: fixed;
//   bottom: 0;
//   width: 100%;
//   margin-left: -16px;
// }
</style>
