import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from '../router'
import store from '../store/index.js'
import http from 'axios'
import JsonExcel from 'vue-json-excel'
import dataV from '@jiaminghi/data-view'
// import echarts from 'echarts'
import * as echarts from 'echarts';

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(dataV)
Vue.use(echarts)
Vue.component('downloadExcel', JsonExcel)
http.defaults.baseURL = 'http://localhost:9090'
Vue.prototype.$http = http
new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')
