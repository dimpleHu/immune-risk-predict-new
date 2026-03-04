// src/api/user.js
import request from '@/utils/request'

// 获取用户信息
export function getUserInfo(userId) {
  return request({
    url: `/user/info/${userId}`,
    method: 'get'
  })
}

// 更新用户信息
export function updateUserInfo(userId, data) {
  return request({
    url: `/user/update/${userId}`,
    method: 'post',
    data
  })
}

// 修改密码
export function updatePassword(userId, data) {
  return request({
    url: `/user/password/${userId}`,
    method: 'post',
    data
  })
}

// 用户注册
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

// 用户登录
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 用户退出
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}