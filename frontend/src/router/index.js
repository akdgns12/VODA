import Vue from "vue";
import VueRouter from "vue-router";
import SignUp from "../components/SignUp.vue";
import KakaoLogin from "../components/KakaoLogin.vue";
import Main from "../components/MainView.vue";
import Record from "../audio/components/recorder.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: KakaoLogin,
  },
  {
    path: "/user/signup",
    name: "signup",
    component: SignUp,
  },
  {
    path: "/main",
    name: "main",
    component: Main,
  },
  {
    path: "/record",
    name: "record",
    component: Record,
  },
];

// eslint-disable-next-line no-new
const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
