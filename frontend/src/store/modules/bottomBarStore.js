const bottomBarStore = {
  state: {
    showBottomNavigation: true,
  },
  getters: {
    showBottomNavigation(state) {
      return state.showBottomNavigation;
    },
  },
  mutations: {
    setShowBottomNavigation(state, payload) {
      state.showBottomNavigation = payload;
    },
  },
  actions: {
    setShowBottomNavigation({ commit }, payload) {
      commit("setShowBottomNavigation", payload);
    },
  },
};

export default bottomBarStore;
