const diaryStore = {
  state: {
    emotions:[]
  },
  getters: {
    getEmotionByType:(state) => (type) =>{
      return state.emotions.find((emotion) => emotion.type===type);
    }
  },
  mutations: {
    setEmotions(state,emotions){
      state.emotions = emotions
    }
  },
  actions: {
    fetchEmotions({ commit }, diarySeq){
      return axios.get(`/diary/${diarySeq}`)
      .then((response)=>{
        commit('setEmotions', response.data);
      })
    }
  },
};

