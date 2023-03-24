import Vue from "vue";
import VueRouter from "vue-router";
import SignUp from "../components/SignUp.vue";
import KakaoLogin from "../components/KakaoLogin.vue";
import Calendar from "../components/MainView.vue";
import Record from "../audio/components/recorder.vue";
<<<<<<< HEAD
import DiaryDetail from "../components/DiaryDetail.vue"
import RedirectHandler from "../components/OAuth2RedirectHadeler.vue"
=======
import Chart from "../components/ChartView.vue";
>>>>>>> b264400091c990ca3400cf3b7397dd0876c74e8c

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
    path: "/calendar/:userSeq",
    name: "calendar",
    component: Calendar,
  },
  {
    path: "/record",
    name: "record",
    component: Record,
  },
  {
<<<<<<< HEAD
    path: "/calendar/diary/:calendarSeq",
    name: 'DiaryDetail',
    component: DiaryDetail,
    props: true
  },
  {
    path: "/user/login/oauth/kakao/callback",
    name: 'RedirectHandler',
    component: RedirectHandler,
  }
=======
    path: "/chart",
    name: "chart",
    component: Chart,
  },
>>>>>>> b264400091c990ca3400cf3b7397dd0876c74e8c
];

// eslint-disable-next-line no-new
const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
