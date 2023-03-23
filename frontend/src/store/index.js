import Vue from "vue";
import Vuex from "vuex";

import bottomBarStore from "./modules/bottomBarStore";
import calendarStore from "./modules/caldendarStore";
import chartStore from "./modules/chartStore";
import diaryStore from "./modules/diaryStore";
import userStore from "./modules/userStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    bottomBarStore,
    calendarStore,
    chartStore,
    diaryStore,
    userStore,
  },
});
