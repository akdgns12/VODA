import { doGetMonthChart, doGetWeekChart } from "@/api/chart";

const chartStore = {
  namespaced: true,
  state: {
    monthLabels: [],
    monthData: [],
    weeks: [],
  },
  getters: {
    monthLabels(state) {
      return state.monthLabels;
    },
    monthData(state) {
      return state.monthData;
    },
    weeks(state) {
      return state.weeks;
    },
  },
  mutations: {
    SET_MONTH_LABELS(state, monthLabels) {
      state.monthLabels = monthLabels;
    },
    SET_MONTH_DATA(state, monthData) {
      state.monthData = monthData;
    },
    SET_WEEKS(state, weeks) {
      state.weeks = weeks;
    },
  },
  actions: {
    async getMonthChart({ commit }, { userSeq, date }) {
      await doGetMonthChart(
        userSeq,
        date,
        ({ data }) => {
          commit("SET_MONTH_LABELS", data.labels);
          commit("SET_MONTH_DATA", data.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getWeekChart({ commit }, { userSeq, date }) {
      await doGetWeekChart(
        userSeq,
        date,
        ({ data }) => {
          commit("SET_WEEKS", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default chartStore;
