import Vue from 'vue'
import App from '@/App.vue'
import router from '@/router/index.js'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

// 导入全局样式
import '@/assets/css/element-variables.scss'
import '@/assets/css/style.scss'

// Element UI 按需引入
import './plugins/element'

// 导入图标
import './icons'

// 导入全局插件和组件
import './plugins'

// 导入工具类
import * as utils from '@/utils/utils'
import * as validate from '@/utils/validate.js'
import api from '@/utils/api'
import storage from "@/utils/storage"
import { http } from '@/utils/request'
import base from '@/utils/base'



// 配置axios基础路径（根据后端实际后端地址修改）
axios.defaults.baseURL = 'http://localhost:8085/immuse-risk' // 假设后端接口统一前缀是/api

// 注册全局工具方法
Vue.prototype.$validate = validate
Vue.prototype.$http = http
Vue.prototype.$base = base.get()
Vue.prototype.$project = base.getProjectName()
Vue.prototype.$storage = storage
Vue.prototype.$api = api
Vue.prototype.$axios = axios

// 注册常用工具方法
Vue.prototype.isAuth = utils.isAuth
Vue.prototype.getCurDateTime = utils.getCurDateTime
Vue.prototype.getCurDate = utils.getCurDate

Vue.use(ElementUI)

Vue.config.productionTip = false

// 创建 Vue 实例
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app') 