// store/index.js
import Vue from 'vue'
import Vuex from 'vuex'
import storage from '@/utils/storage'

// 确保在创建store之前安装Vuex
Vue.use(Vuex)

// 用户模块
const user = {
  namespaced: true,
  state: {
    token: storage.get('token') || '',
    username: storage.get('username') || '',
    role: storage.get('role') || '',
    userInfo: null,
    isLoggedIn: !!storage.get('token')
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token
      if (token) {
        storage.set('token', token)
        storage.set('Token', token) // 兼容性处理
      } else {
        storage.remove('token')
        storage.remove('Token')
      }
    },
    SET_USER_INFO(state, { username, role, userInfo }) {
      state.username = username || userInfo.phoneNumber // 如果username为空，使用手机号
      state.role = role
      state.userInfo = userInfo
      state.isLoggedIn = true
      
      storage.set('username', state.username)
      storage.set('role', role)
      storage.set('userInfo', userInfo)
    },
    CLEAR_USER_INFO(state) {
      state.token = ''
      state.username = ''
      state.role = ''
      state.userInfo = null
      state.isLoggedIn = false
      
      // 清除所有相关的存储
      storage.remove('token')
      storage.remove('Token')
      storage.remove('username')
      storage.remove('role')
      storage.remove('userInfo')
      storage.remove('rememberedMobile')
    }
  },
  actions: {
    // 登录成功后调用
    loginSuccess({ commit }, { token, username, role, userInfo }) {
      commit('SET_TOKEN', token)
      commit('SET_USER_INFO', { username, role, userInfo })
    },
    // 退出登录
    logout({ commit }) {
      commit('CLEAR_USER_INFO')
    },
    // 更新用户信息
    updateUserInfo({ commit }, userInfo) {
      commit('SET_USER_INFO', {
        username: userInfo.username || userInfo.mobile,
        role: userInfo.role || 'user',
        userInfo
      })
    }
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
    username: state => state.username,
    role: state => state.role,
    token: state => state.token,
    userInfo: state => state.userInfo
  }
}

// 创建store实例
const store = new Vuex.Store({
  modules: {
    user
  },
  // 根级别的状态，为了向后兼容，保留根级别的getter
  getters: {
    isLoggedIn: state => state.user.isLoggedIn,
    username: state => state.user.username,
    role: state => state.user.role,
    token: state => state.user.token
  }
})

export default store 