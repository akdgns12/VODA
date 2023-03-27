<template>
<<<<<<< HEAD
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
=======
  <div class = "spinner-div">
    <div v-if="isLoading">
      로딩 중입니다.
    </div>    
  </div>   
</template>

<script>

export default {
  name: 'RedirectHandler',
    data(){
        return{
          isLoading:true,
        }
    },
    async mounted(){
      try{
        
        let params = new URL(window.location.href).searchParams;
        const code = params.get("code");

        await this.$store.dispatch("getUserInfo", { data : code });
        const userData = this.$store.getters.userData;

        this.isLoading = false;

        if (this.$store.getters.userStatus == 200) {
          this.$router.push(`/calendar/${userData.userSeq}`);
        } else {
          // 없으면 회원가입 페이지로 라우팅
          this.$router.push("/user/signup");
        }
      } catch(error){
        console.log(error)
      }
>>>>>>> b72db6e1075f240854e9634cd78eead7fe0b3141
    }
  },
};
</script>

<style></style>
