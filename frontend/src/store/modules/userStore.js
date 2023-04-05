import { doGetUser } from "@/api/login";

const userStore = {
  state: {
    userData: {},
    userStatus: null,
    error: null,
  },
  getters: {
    userData(state) {
      return state.userData;
    },
    userStatus(state) {
      return state.userStatus;
    },
    error(state) {
      return state.error;
    },
  },
  mutations: {
    SET_USER_DATA(state, userData) {
      state.userData = userData;
    },
    SET_USER_STATUS(state, userStatus) {
      state.userStatus = userStatus;
    },
  },
  actions: {
    async getUserInfo({ commit }, { data }) {
      try {
        const response = await doGetUser(data); // 받아온 데이터를 userData 변수에 저장
        const userData = response.data;
        const userStatus = response.status;

        commit("SET_USER_STATUS", userStatus);
        commit("SET_USER_DATA", userData); // mutations 를 통해 state.userData 에 반영

        // 로컬 스토리지에 accessToken과 refreshToken을 저장
        localStorage.setItem("accessToken", userData.accessToken);
        localStorage.setItem("refreshToken", userData.refreshToken);
        localStorage.setItem("userSeq", userData.userSeq);

        return userData; // userData 변수를 반환
      } catch (error) {
        console.log(error);
        throw error; // 에러 발생 시 호출자에게 에러를 전달
      }
    },
  },
};

export default userStore;
