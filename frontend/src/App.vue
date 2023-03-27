<template>
  <v-app>
    <div id="app">
      <router-view />

      <BottomNavigation></BottomNavigation>

      <v-btn @click="toSignUp">sign Up </v-btn>
      <v-btn @click="toMain"> Main </v-btn>
    </div>
  </v-app>
</template>

<script>
import {
  mdiRadioboxMarked,
  mdiChartBellCurve,
  mdiCalendarMonth,
} from "@mdi/js";
import BottomNavigation from "./components/BottomNavigation.vue";

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
    BottomNavigation,
  },
  methods: {
    toSignUp() {
      this.$router.push("/user/signup");
    },
    toMain() {
      this.$router.push("/main");
    },
    toMain() {
      this.$router.push("/calendar/:userSeq");
    },
  },
  computed: {
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
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
}
</style>
