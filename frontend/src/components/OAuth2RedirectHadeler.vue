<template>
  <div class = "spinner-div" v-if="isLoading">
    로딩 중입니다.
  </div>   
</template>

<script>
import userStore from '@/store/modules/userStore';
export default {
  name: 'RedirectHandler',
    data(){
        return{
          isLoading:true
        }
    },
    async mounted(){
      try{
        

        let params = new URL(window.location.href).searchParams;
        const code = params.get("code");
        console.log(code);
         
        this.$store.dispatch("getUserInfo", { data : code })
        console.log(userStore.state.userData)
      
        // await api.get("/user/login/oauth/kakao")
        //   .then((response) => {
        //         console.log(response)
        //         if (response.data.errorCode === 400) {
        //           alert(response.data.errorMessage)

        //           }
        //         else{
        //           alert("회원가입이 완료되었습니다. 로그인 화면으로 돌아갑니다")
        //           this.$router.push({path: '/'});
        //         }
        //         })
        // .catch(error =>{
        //   console.log(error.response);

        // });
      } catch(error){
        console.log(error)
      }
    }
}
</script>

<style>

</style>