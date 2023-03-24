import { doGetUser } from "@/api/login";

const userStore = {
  state: {
    userData: [],
  },
  getters: {
    userData(state) {
      return state.userData;
    },
  },
  mutations: {
    SET_USER_DATA(state, userData) {
      state.userData = userData;
    },
  },
  actions: {
    async getUserInfo({commit},{data}){
      console.log(data)
      await doGetUser( 
        data,
        ({data})=>{
          commit("SET_USER_DATA",data)
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
}

export default userStore;
