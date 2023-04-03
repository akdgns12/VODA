import { doGetDiary, doDelDiary } from "@/api/diary";
const diaryStore = {
  state: {
    diaryData: {},
    test: {},
  },
  getters: {
    diaryData(state) {
      return state.diaryData;
    },
  },
  mutations: {
    SET_DIARY_DATA(state, diaryData) {
      state.diaryData = diaryData;
    },
    SET_TEST(state, test) {
      state.test = test;
    },
  },
  actions: {
    async getDiaryData({ commit }, { calendarSeq }) {
      await doGetDiary(
        calendarSeq,
        ({ data }) => {
          commit("SET_DIARY_DATA", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async delDiaryData({ commit }, { diarySeq }) {
      console.log(typeof diarySeq);
      await doDelDiary(
        diarySeq,
        ({ data }) => {
          commit("SET_TEST", data);
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default diaryStore;
