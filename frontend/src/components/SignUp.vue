<template>
  <v-app id="inspire">
    <v-main class="background">
      <!-- SignUp Component -->
      <v-container style="max-width: 450px" fill-height>
        <v-row align="center">
          <v-col cols="12">
            <v-card>
              <div class="box pa-10">
                <h1 style="text-align: center" class="mb-10">SIGN UP</h1>
                <v-form ref="form">
                  <v-text-field
                    v-model="nickname"
                    required
                    prepend-inner-icon="mdi-account"
                    label="NICKNAME"
                    :counter="10"
                    :rules="nameRules"
                  />
                  <v-card-actions>
                    <v-btn
                      class="pill"
                      rounded
                      large
                      block
                      dark
                      @click="signUpSubmit()"
                    >
                      click
                    </v-btn>
                  </v-card-actions>
                </v-form>
              </div>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
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
  },
};
</script>
<style lang="scss">
@font-face {
  font-family: "ComingSoon-Regular";
  src: url("../../public/fonts/ComingSoon-Regular.ttf");
}
#inspire {
  font-family: "ComingSoon-Regular";
}
.pill {
  border-radius: 50px;
  background: linear-gradient(#97c7ff, #855cf8);
  font-weight: bold;
}
.background {
  background: linear-gradient(#855cf8, #d3b0ff, #97c7ff);
}
.v-sheet.v-card {
  border-radius: 20px;
}
</style>
>
