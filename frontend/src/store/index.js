import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state:{
    showBottomNavigation: true
  },
  getters:{
    showBottomNavigation(state) {
      return state.showBottomNavigation;
    }
  },
  mutations:{
    setShowBottomNavigation(state, payload) {
        state.showBottomNavigation = payload;
      }
  },
  actions:{
    setShowBottomNavigation({ commit }, payload) {
        commit('setShowBottomNavigation', payload);
      }
  },
  modules:{
      
  }
})