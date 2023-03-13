<template>
  <div>
    <img class="logo">
    <a @click="kakaoLogin()">
      <img
        src="@/assets/KakaoLogin/kakaoLogin.png"
        width="222"
        alt="카카오 로그인 버튼"
      />
    </a>
    <div @click="kakaoLogout()">로그아웃</div>
  </div>

</template>

<script>
export default {
  methods: {
    kakaoLogin() {
      window.Kakao.Auth.login({
        scope: "account_email",
        success: this.getKakaoAccount,
      });
    },
    getKakaoAccount() {
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: (res) => {
          const kakao_account = res.kakao_account;
          const email = kakao_account.email;
          console.log("email", email);

          //로그인처리구현

          alert("로그인 성공!");
        },
        fail: (error) => {
          console.log(error);
        },
      });
    },
    kakaoLogout() {
      window.Kakao.Auth.logout((res) => {
        console.log(res);
      });
    },
  },
};
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