import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router";
import axios from "axios";
import VCalendar from "v-calendar";

Vue.prototype.$http = axios;
Vue.config.productionTip = false;
Vue.use(VCalendar, {
  componentPrefix: "vc",
});

new Vue({
  vuetify,
  router,
  render: (h) => h(App),
}).$mount("#app");
