<template>
  <div class="spinner-div" v-if="isLoading">로딩 중입니다.</div>
</template>

<script>
import userStore from "@/store/modules/userStore";
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
      console.log(code);

      this.$store.dispatch("getUserInfo", { data: code });
      console.log(userStore.state.userData);
    } catch (error) {
      console.log(error);
    }
  },
};
</script>

<style></style>
