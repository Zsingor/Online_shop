import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false
Vue.config.silent=true
Vue.use(ElementUI)
Vue.prototype.$baseUrl=process.env.VUE_APP_BSAEURL


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
