const recordStore = {
  state: {
    recordData: {},
    recordStatus: null,
  },
  getters: {
    recordData(state) {
      return state.recordData;
    },
  },
  mutations: {
    SET_RECORD_DATA(state, recordData) {
      state.recordData = recordData;
    },
    SET_RECORD_STATUS(state, recordData) {
      state.recordData = recordData;
    },
  },
  actions: {
    setRecord({ commit }, { data }) {
      const recordData = data;
      commit("SET_RECORD_DATA", recordData); // mutations 를 통해 state.userData 에 반영
    },
  },
};

export default recordStore;
