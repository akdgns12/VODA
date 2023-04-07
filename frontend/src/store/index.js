import Vue from "vue";
import Vuex from "vuex";

import navigationStore from "./modules/navigationStore";
import calendarStore from "./modules/calendarStore";
import chartStore from "./modules/chartStore";
import diaryStore from "./modules/diaryStore";
import userStore from "./modules/userStore";
import recordStore from "./modules/recordStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    navigationStore,
    calendarStore,
    chartStore,
    diaryStore,
    userStore,
    recordStore,
  },
});
