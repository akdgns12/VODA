<template>
  <div>
    <img class="logo">
    <a @click="loginWithKakao()">
      <img
        src="@/assets/KakaoLogin/kakaoLogin.png"
        width="222"
        alt="카카오 로그인 버튼"
      />
    </a>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  methods: {
    loginWithKakao() {
      // 카카오 로그인 API 호출
      axios.get('/user/login/oauth/kakao')
        .then(response => {
          if (response.data.success) {
            // 로그인 성공
            this.$router.push('/main');
          } else {
            // 회원이 아님
            this.$router.push('/signup');
          }
        })
        .catch(error => {
          console.log(error);
        });
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
    background-size: contain;
    background-image: url("../assets/logo.png");
    width:385px;
    height: 94px;
    border: black;
  }
</style>