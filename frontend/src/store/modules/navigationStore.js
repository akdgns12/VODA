const navigationStore = {
  state: {
    showBottomNavigation: true,
    showTopNavigation: true,
  },
  getters: {
    showBottomNavigation(state) {
      return state.showBottomNavigation;
    },
    showTopNavigation(state) {
      return state.showTopNavigation;
    },
  },
  mutations: {
    setShowBottomNavigation(state, payload) {
      state.showBottomNavigation = payload;
    },
    setShowTopNavigation(state, payload) {
      state.showTopNavigation = payload;
    },
  },
  actions: {
    setShowBottomNavigation({ commit }, payload) {
      commit("setShowBottomNavigation", payload);
    },
    setShowTopNavigation({ commit }, payload) {
      commit("setShowTopNavigation", payload);
    },
  },
};

export default navigationStore;
