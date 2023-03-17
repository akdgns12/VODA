<template>
  <v-app>
    <div class="container">
      <video autoplay muted loop class="video-background">
        <source src="../assets/production ID_4508060.mp4" type="video/mp4"/>
      </video>
      <v-container>
        <v-row>
          <img class="logo">
        </v-row>
  
        <v-row justify="center" class="fill-height">
          <v-col cols="12" sm="6" md="4">
            <a @click="loginWithKakao()">
              <img
                src="@/assets/KakaoLogin/kakaoLogin.png"
                width="222"
                alt="카카오 로그인 버튼"
              />
            </a>
         </v-col>
        </v-row>
      </v-container>
    </div>
  </v-app>

</template>

<script>
export default {
  name : 'loginPage',
  data: () => ({

  }),
  methods: {
    async loginWithKakao() {
      // 카카오 로그인 API 호출
      window.location.href = 'https://kauth.kakao.com/oauth/authorize?response_type=code' +
          '&client_id=' +
          process.env.VUE_APP_KAKAO_APP_KEY +
          '&redirect_uri=' +
          process.env.VUE_APP_KAKAO_REDIRECT_URI+
          '&scope=account_email'
      
    }
  }
}
</script>

<style>
  @font-face {
    font-family: 'NanumSquareNeoHv';
    src: url('../../public/fonts/NanumSquareNeo-eHv.ttf');
  }
  .logo{
    margin-top: 220px;
    margin-left: auto;
    margin-right: auto;
    justify-self: center;
    align-self: center;
    background-size: contain;
    background-image: url("../assets/logo.png");
    width:385px;
    height: 94px;
  }
  .container {
    position: relative;
    height: 100vh;
    overflow: hidden;
  }
  .video-background {
    position: absolute;
    background-size: cover;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .fill-height {
    height: 100%;
  }
</style>