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
      //   try{
      //     const response = await doGetCalendar(userSeq, date);
      //     const calendarData = response.data;
      //     const calendarStatus = response.status;

      //     commit("SET_CALENDAR_DATA", calendarData);
      //     commit("SET_CALENDAR_STATUS", calendarStatus);

      //     return calendarData;
      //   }catch(error){
      //       console.log(error);
      //     }
      // },
    },
  },
};

export default calendarStore;
