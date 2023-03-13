<template>
  <v-app id="inspire">
    <v-main class="blue-grey lighten-4">
      <!-- SignUp Component -->
      <v-container
        style="max-width: 450px"
        fill-height
      >
        <v-row align="center">
          <v-col cols="12">
            <v-card>
              <div class="pa-10">
                <h1
                  style="text-align: center"
                  class="mb-10"
                >
                  SignUp
                </h1>
                <v-form ref="form">
                  <v-text-field
                    v-model="nickname"
                    required
                    prepend-inner-icon="mdi-account"
                    label="Nickname"
                    :counter="10"
                    :rules="nameRules"
                  />
                  <v-card-actions>
                    <v-btn
                      color="#855CF8"
                      rounded = "pill"
                      large
                      block
                      dark
                      class="mb-3"
                      @click="signUpSubmit()"
                    >
                      SIGN UP
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
export default {
    data: () => ({
        nickname : "",
        nameRules: [
          v => !!v || '닉네임을 작성해주세요',
          v => (v && v.length <= 10) || '닉네임을 10글자를 넘을 수 없습니다.',
      ],
    }),
    methods: {
        signUpSubmit(){
          const validate = this.$refs.form.validate()
          if(validate){
          let saveData = {};
          saveData.nickname = this.nickname;

          try {
             this.$axios.post("/api/member", JSON.stringify(saveData), {
               headers: {
                 "Content-Type": `application/json`,
                  },
                  })
                  .then((response) => {
                    console.log(response)
                    if (response.data.errorCode === 400) {
                      alert(response.data.errorMessage)

                      }
                      else{
                        alert("회원가입이 완료되었습니다. 로그인 화면으로 돌아갑니다")
                        this.$router.push({path: './main'});
                      }
                      })
            .catch(error =>{
              console.log(error.response);

            });
      } catch (error) {
        console.error(error);
      }
        }},
        linkToLogin(){
          this.$router.push({path:"./main"});
        }
    }
}
</script>