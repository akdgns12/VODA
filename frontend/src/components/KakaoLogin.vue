<template>
  <v-app>
    <div class="container">
      <video autoplay muted loop class="video-background">
        <source src="../assets/bg.mp4" type="video/mp4"/>
      </video>
      <v-container>
        <v-row>
          <div class="logo"></div>
        </v-row>
  
        <v-row justify="center" align-end gutter="md">
          
          <v-col cols="12" sm="6" md="4"> 
            <v-spacer></v-spacer>     
            <div class="loginBtn">
              <a @click="loginWithKakao">
                <img
                  src="@/assets/KakaoLogin/kakaoLogin.png"
                  width="222"
                  alt="카카오 로그인 버튼"
                />
              </a>
            </div>
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
    showBottomNav:false,
    URL: "",
  }),
  methods: {
    async loginWithKakao() {
      // 카카오 로그인 API 호출
      window.location.href='https://kauth.kakao.com/oauth/authorize?response_type=code' +
          '&client_id=' +
          process.env.VUE_APP_KAKAO_APP_KEY +
          '&redirect_uri=' +
          // 
          process.env.VUE_APP_KAKAO_REDIRECT_URI +
          '&scope=account_email'    
      // this.$router.push('/user/login/oauth/kakao')
    }
    
  },
    created() {
     this.$store.dispatch("setShowBottomNavigation", false);

  },
  // mounted(){
  //       //  this.URL = 'https://kauth.kakao.com/oauth/authorize?response_type=code' +
  //       //   '&client_id=' +
  //       //   process.env.VUE_APP_KAKAO_APP_KEY +
  //       //   '&redirect_uri=' +
  //       //   process.env.VUE_APP_KAKAO_REDIRECT_URI+
  //       //   '&scope=account_email'
  // }
}
</script>

<style scoped lang="scss">
  @font-face {
    font-family: 'NanumSquareNeoHv';
    src: url('../../public/fonts/NanumSquareNeo-eHv.ttf');
  }
  .logo{
    margin-top: 20vh;
    margin-left: auto;
    margin-right: auto;
    align-self: center;
    background-image: url("../assets/logo.svg");
    background-size: contain;
    width: 400px;
    height: 110px;
  }
  .container {
    position: relative;
    height: 100vh;
    overflow: hidden;
    // background: linear-gradient(#855CF8,#D3B0FF,#97C7FF);  
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
  .loginBtn{
    position: relative;
    bottom: 0;
  }
  @media only screen and (max-width: 600px) {
  .loginBtn {
    margin-top: 10px;
    position: absolute;
    left: 50%;
    bottom: 20px;
    transform: translateX(-50%);
  }
  }
</style>