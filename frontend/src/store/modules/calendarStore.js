import { doGetCalendar } from "@/api/calendar";
const calendarStore = {
  state: {
    calendarData: {},
  },
  getters: {
    calendarData(state) {
      return state.calendarData;
    },
  },
  mutations: {
    SET_CALENDAR_DATA(state, calendarData) {
      state.calendarData = calendarData;
    },
  },
  actions: {
    async getCalendarInfo({ commit }, { userSeq, date }) {
      await doGetCalendar(
        userSeq,
        date,
        ({ data }) => {
          commit("SET_CALENDAR_DATA", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default calendarStore;
