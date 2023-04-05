<template>
  <v-app>
    <div id="app">
      <TopNavigation
        class="top-nav"
        v-if="showTopNavigation"
        style="position: fixed; z-index: 10"
      />
      <router-view />
      <BottomNavigation
        class="bottom-nav"
        v-if="showBottomNavigation"
        style="position: fixed; z-index: 10"
      />
    </div>
  </v-app>
</template>

<script>
import {
  mdiRadioboxMarked,
  mdiChartBellCurve,
  mdiCalendarMonth,
} from "@mdi/js";
import TopNavigation from "./components/TopNavigation.vue";
import BottomNavigation from "./components/BottomNavigation.vue";
import { mapGetters } from "vuex";

export default {
  name: "App",
  data: () => ({
    value: 3,
    icons: {
      mdiChartBellCurve,
      mdiCalendarMonth,
      mdiRadioboxMarked,
    },
  }),
  components: {
    TopNavigation,
    BottomNavigation,
  },
  computed: {
    ...mapGetters(["showTopNavigation", "showBottomNavigation"]),
  },
  methods: {},
};
</script>

<style>
@font-face {
  font-family: "NanumSquareNeo";
  src: url("../public/fonts/NanumSquareNeo-aLt.ttf");
}
@font-face {
  font-family: "NanumSquareNeo";
  src: url("../public/fonts/NanumSquareNeo-bRg.ttf");
  font-weight: 200;
}
@font-face {
  font-family: "NanumSquareNeo";
  src: url("../public/fonts/NanumSquareNeo-cBd.ttf");
}
@font-face {
  font-family: "NanumSquareNeoa";
  src: url("../public/fonts/NanumSquareNeo-dEb.ttf");
  font-weight: 900;
}
#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  font-family: "NanumSquareNeoa";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  font-weight: 900;
}
</style>
