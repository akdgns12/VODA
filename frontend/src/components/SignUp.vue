<template>
  <v-app>
    <!-- <v-main class="background"> -->
    <!-- SignUp Component -->
    <v-container class="background" style="height: 100vh">
      <!-- <v-row align="center">
        <v-col cols="12"> -->
      <v-card class="mt-5" style="border-radius: 20px; max-width: 450px">
        <div class="box pa-5">
          <h1 style="text-align: center">VODA에서 사용할</h1>
          <h1 style="text-align: center" class="mb-5">
            닉네임을 입력해주세요.
          </h1>
          <v-form ref="form">
            <v-text-field
              v-model="nickname"
              required
              prepend-inner-icon="mdi-account"
              label="닉네임"
              :counter="10"
              :rules="nameRules"
            />
            <v-card-actions> </v-card-actions>
          </v-form>
        </div>
      </v-card>
      <v-row style="max-width: 450px; margin: 0px">
        <v-btn class="pill" rounded large block dark @click="signUpSubmit()">
          가 입 하 기
        </v-btn>
      </v-row>
      <!-- </v-col>
      </v-row> -->
    </v-container>
    <!-- </v-main> -->
  </v-app>
</template>
<script>
import { doSignUp } from "@/api/login";

export default {
  data: () => ({
    nickname: "",
    nameRules: [
      (v) => !!v || "닉네임을 작성해주세요",
      (v) => (v && v.length <= 10) || "닉네임을 10글자를 넘을 수 없습니다.",
    ],
  }),
  methods: {
    signUpSubmit() {
      const validate = this.$refs.form.validate();
      if (validate) {
        let saveData = {};
        saveData.nickname = this.nickname;
        saveData.accessToken = localStorage.getItem("accessToken");
        console.log(saveData);

        try {
          doSignUp(this.nickname, localStorage.getItem("accessToken"))
            .then((response) => {
              console.log(response);
              if (response.data.errorCode === 400) {
                alert(response.data.errorMessage);
              } else {
                alert("회원가입이 완료되었습니다. 로그인 화면으로 돌아갑니다");
                this.$router.push({ path: "/" });
              }
            })
            .catch((error) => {
              console.log(error.response);
            });
        } catch (error) {
          console.error(error);
        }
      }
    },
  },
  created() {
    this.$store.dispatch("setShowBottomNavigation", false);
    this.$store.dispatch("setShowTopNavigation", false);
  },
};
</script>
<style lang="scss">
.pill {
  border-radius: 50px;
  background: linear-gradient(#97c7ff, #855cf8);
  font-weight: bold;
  max-width: 450px;
}
.background {
  background: linear-gradient(#855cf8, #d3b0ff, #97c7ff);
  position: relative;
  display: grid;
  justify-content: center;
  align-items: center;
}

.box {
  margin-top: 30px;
  font-size: 15px;
}
@media only screen and (max-width: 600px) {
  .pill {
    position: relative;
    margin-top: 20vh;
  }
}
@media (min-width: 960px) {
  .container {
    max-width: 100vw;
  }
}
</style>
>
