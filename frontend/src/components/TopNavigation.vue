<template>
  <v-layout class="mx-auto overflow-hidden">
    <v-app-bar
      class="top-nav"
      color="deep-purple accent-4"
      dark
      prominent
      height="60"
    >
      <v-btn icon>
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-spacer></v-spacer>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      bottom
      temporary
      height="120"
    >
      <v-list nav dense bottom>
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item @click="logout()">
            <v-list-item-title>Logout</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </v-layout>
</template>

<script>
export default {
  data: () => ({
    drawer: false,
    group: null,
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
