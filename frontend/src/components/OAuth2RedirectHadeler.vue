<template>
  <v-app class="container" style="margin-top: 70px; margin-bottom: 70px">
    <div class="record-loading">
      <img src="../assets/loading.gif" />
    </div>
    <h2>로딩중입니다.</h2>
  </v-app>
</template>

<script>
export default {
  name: "RedirectHandler",
  data() {
    return {
      isLoading: true,
    };
  },
  async mounted() {
    try {
      let params = new URL(window.location.href).searchParams;
      const code = params.get("code");

      await this.$store.dispatch("getUserInfo", { data: code });

      this.isLoading = false;

      if (this.$store.getters.userStatus == 200) {
        this.$router.push(`/calendar`);
      } else {
        // 없으면 회원가입 페이지로 라우팅
        this.$router.push("/user/signup");
      }
    } catch (error) {
      console.log(error);
    }
  },
};
</script>

<style scoped lang="scss">
img {
  height: 200px;
  margin-top: 150px;
  object-fit: cover;
}
.container {
  &-record-loading {
    position: absolute;
    margin-top: 10px;
    size: 10px;
    width: 20%;
    height: 20%;
    widows: 300px;
    height: 150px;
    object-fit: cover;
  }
}
</style>
