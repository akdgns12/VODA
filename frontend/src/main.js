import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import VCalendar from 'v-calendar'

Vue.prototype.$http = axios
Vue.config.productionTip = false
Vue.use(VCalendar,{
  componentPrefix: 'vc',
})
window.Kakao.init("f9b4997781cb8ed4ade3e74780032523");
new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

