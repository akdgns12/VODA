<template>
  <v-layout class="mx-auto overflow-hidden">
    <v-app-bar
      class="top-nav"
      color="deep-purple accent-4"
      dark
      prominent
      height="60"
    >
      <v-spacer></v-spacer>
      <v-btn icon @click="showLogoutDialog = true">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>

    <v-dialog v-model="showLogoutDialog" max-width="400">
      <v-card>
        <v-card-title class="justify-center">
          로그아웃 하시겠습니까?
        </v-card-title>
        <v-divider></v-divider>
        <v-card-actions style="justify-content: space-around">
          <v-btn
            style="width: 50%"
            color="blue darken-1"
            text
            @click="showLogoutDialog = false"
          >
            취소
          </v-btn>
          <v-divider vertical="true"></v-divider>
          <v-btn style="width: 50%" color="red darken-1" text @click="logout()">
            로그아웃
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
export default {
  data: () => ({
    drawer: false,
    group: null,
    showLogoutDialog: false,
  }),

  watch: {
    group() {
      this.drawer = false;
    },
  },
  methods: {
    // 카카오 로그아웃
    async logout() {
      window.location.href =
        `https://kauth.kakao.com/oauth/logout?client_id=` +
        process.env.VUE_APP_KAKAO_APP_KEY +
        `&logout_redirect_uri=` +
        process.env.VUE_APP_KAKAO_LOGOUT_REDIRECT_URI;
      this.showLogoutDialog = false;
    },
  },
};
</script>

<style>
.top-nav {
  top: 0;
  width: 100%;
}
</style>
