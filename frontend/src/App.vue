<template>
  <div id="app">  
    <router-view/>
    <v-btn @click = "toSignUp">sign Up </v-btn>
    <v-btn @click = "toMain"> Main </v-btn>
  </div>
</template>

<script>


export default {
  name: 'App',
  methods:{
    toSignUp(){
      this.$router.push("/signUp")
    },
    toMain(){
      this.$router.push("/main")
    }
  },
}
</script>

<style>
  @font-face {
    font-family: 'NanumSquareNeo';
    src: url('../public/fonts/NanumSquareNeo-aLt.ttf');
  }
  @font-face {
    font-family: 'NanumSquareNeo';
    src: url('../public/fonts/NanumSquareNeo-bRg.ttf');
    font-weight: 200;
  }
  @font-face {
    font-family: 'NanumSquareNeo';
    src: url('../public/fonts/NanumSquareNeo-cBd.ttf');

  } 
  @font-face {
    font-family: 'NanumSquareNeoa';
    src: url('../public/fonts/NanumSquareNeo-dEb.ttf');
    font-weight: 900;
  }
  #app {
    /* font-family: Avenir, Helvetica, Arial, sans-serif; */
    font-family: 'NanumSquareNeoa';
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
    font-weight: 900;
  }
</style>
