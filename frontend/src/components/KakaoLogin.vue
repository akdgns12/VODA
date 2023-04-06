<template>
  <div class="signUpWrapper">
    <video autoplay muted loop class="video-background">
      <source src="../assets/bg.mp4" type="video/mp4" />
    </video>
    <v-container style="height: 100vh; display: flex; flex-direction: column">
      <v-row justify="center">
        <div class="logo">
          <img src="../assets/logo.svg" alt="로고 이미지" />
        </div>
      </v-row>

      <v-row
        class="loginBtn"
        style="align-items: center; justify-content: center"
      >
        <div>
          <a @click="loginWithKakao">
            <img
              src="@/assets/KakaoLogin/kakaoLogin.png"
              alt="카카오 로그인 버튼"
            />
          </a>
        </div>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "loginPage",
  data: () => ({
    showBottomNav: false,
  }),
  methods: {
    async loginWithKakao() {
      // 카카오 로그인 API 호출
      window.location.href =
        "https://kauth.kakao.com/oauth/authorize?response_type=code" +
        "&client_id=" +
        process.env.VUE_APP_KAKAO_APP_KEY +
        "&redirect_uri=" +
        //
        process.env.VUE_APP_KAKAO_REDIRECT_URI +
        "&scope=account_email";
    },
  },
  created() {
    this.$store.dispatch("setShowBottomNavigation", false);
    this.$store.dispatch("setShowTopNavigation", false);
  },
};
</script>

<style scoped lang="scss">
.logo {
  align-self: center;
  background-size: contain;
  min-width: 350px;
  // min-height: 110px;
  z-index: 10;
}
.signUpWrapper {
  position: relative;
  height: 100vh;
  overflow: hidden;
}
.video-background {
  position: absolute;
  // background-size: cover;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.loginBtn {
  position: relative;
  margin-top: 20vh;
}
@media only screen and (max-width: 600px) {
}
@media (min-width: 960px) {
  .container {
    max-width: 100vw;
  }
}
</style>
