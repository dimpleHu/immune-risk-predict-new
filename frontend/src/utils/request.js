// utils/request.js
import axios from 'axios';
import storage from '@/utils/storage';
import router from '@/router';
import {Loading, Message} from 'element-ui';
import store from '@/store';

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8085/immuse-risk', // 基础URL
  timeout: 30000, // 请求超时时间
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
});

// 请求计数器和loading实例
let requestCount = 0;
let loadingInstance;

// 显示loading
const showLoading = () => {
  if (requestCount === 0) {
    loadingInstance = Loading.service({
      text: '加载中...',
      background: 'rgba(0, 0, 0, 0.7)',
      spinner: 'el-icon-loading',
    });
  }
  requestCount++;
};

// 隐藏loading
const hideLoading = () => {
  requestCount--;
  if (requestCount === 0) {
    loadingInstance.close();
  }
};

// 不需要登录就可以访问的API路径
const publicApiPaths = [
  '/flightProject/list',
  '/flightProject/hot',
  '/flightProject/detail',
  '/location/list',
  '/location/project',
  '/user/login',
  '/user/register',
    '/symptoms/search'
];

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 显示loading
    showLoading();

    // 检查是否是公开API
    const isPublicApi = publicApiPaths.some(path => config.url.includes(path));
    console.group('请求拦截器');
    console.log('请求URL:', config.baseURL + config.url);
    console.log('请求方法:', config.method.toUpperCase());
    console.log('是否公开API:', isPublicApi);
    
    // 从storage获取token
    const token = storage.get('Token');
    console.log('Token状态:', token ? '存在' : '不存在');
    
    // 如果不是公开API且没有token，则跳转到登录页
    if (!isPublicApi && !token) {
      console.warn('非公开API请求但未找到Token，重定向到登录页');
      hideLoading();
      Message.warning('请先登录');
      router.push('/login');
      console.groupEnd();
      return Promise.reject(new Error('No token found'));
    }

    // 如果有token，则添加到请求头
    if (token) {
      config.headers['Token'] = token;
      console.log('已添加Token到请求头');
    }

    // 打印请求详情
    console.log('请求头:', config.headers);
    if (config.data) {
      console.log('请求数据:', JSON.stringify(config.data, null, 2));
    }
    if (config.params) {
      console.log('请求参数:', JSON.stringify(config.params, null, 2));
    }
    console.groupEnd();

    return config;
  },
  error => {
    hideLoading();
    console.group('请求错误');
    console.error('错误类型:', error.constructor.name);
    console.error('错误信息:', error.message);
    console.error('错误堆栈:', error.stack);
    console.groupEnd();
    Message.error('请求发送失败');
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    hideLoading();
    console.group('响应拦截器');
    console.log('响应状态码:', response.status);
    console.log('响应数据:', response.data);
    console.groupEnd();

    const res = response.data;
    
    // 如果返回的状态码为1，说明接口请求成功
    if (res.code === 1) {
      return res;
    }
    
    // 未登录或token过期
    if (res.code === 401) {
      Message.error('登录已过期，请重新登录');
      storage.remove('Token');
      router.push('/login');
      return Promise.reject(new Error('未登录或登录已过期'));
    }
    
    // 其他错误
    Message.error(res.msg || '请求失败');
    return Promise.reject(res);
  },
  error => {
    hideLoading();
    console.group('响应错误');
    console.error('错误类型:', error.constructor.name);
    console.error('错误信息:', error.message);
    if (error.response) {
      console.error('响应状态:', error.response.status);
      console.error('响应数据:', error.response.data);
    }
    console.error('错误堆栈:', error.stack);
    console.groupEnd();

    // 根据错误状态码处理
    if (error.response) {
      switch (error.response.status) {
        case 401:
          Message.error('未授权，请重新登录');
          router.push('/login');
          break;
        case 403:
          Message.error('拒绝访问');
          break;
        case 404:
          Message.error('请求的资源不存在');
          break;
        case 500:
          Message.error('服务器内部错误');
          break;
        default:
          Message.error(error.response.data?.msg || '未知错误');
      }
    } else {
      Message.error('网络错误，请检查网络连接');
    }

    return Promise.reject(error);
  }
);

// 导出请求方法
const http = {
  get(url, params, config = {}) {
    return service({
      method: 'get',
      url,
      params,
      ...config
    });
  },
  post(url, data, config = {}) {
    return service({
      method: 'post',
      url,
      data,
      ...config
    });
  },
  put(url, data, config = {}) {
    return service({
      method: 'put',
      url,
      data,
      ...config
    });
  },
  delete(url, params, config = {}) {
    return service({
      method: 'delete',
      url,
      params,
      ...config
    });
  }
};

export default service;
export { http };