import Vue from 'vue';
//配置路由
import Router from 'vue-router'
//1.创建组件
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import Forbidden from '@/views/403'
import register from '@/views/register'
import Layout from '@/views/Layout'
import Prediction from '@/views/prediction'
import UserProfile from '@/views/user/UserProfile'
import History from '@/views/history'
import DiseasePage from '@/views/disease'

Vue.use(Router);

//2.配置路由
export const constantRoutes = [
  {
    path: '/login',
    name: '登录',
    component: Login,
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: '注册',
    component: register,
    meta: { title: '注册' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: '首页',
        component: Home,
        meta: { title: '首页' }
      },
      {
        path: 'disease',
        name: '常见疾病',
        component: DiseasePage,
        meta: { title: '常见疾病' }
      },
      {
        path: 'history',
        name: '历史记录',
        component: History,
        meta: { title: '历史记录' }
      },
      {
        path: 'prediction',
        name: '风险预测',
        component: Prediction,
        meta: { title: '风险预测' }
      },
      {
        path: 'profile',
        name: '个人信息',
        component: UserProfile,
        meta: { title: '个人信息', requireAuth: true }
      }
    ]
  },
  {
    path: '/403',
    name: '无权限',
    component: Forbidden,
    meta: { title: '无权限访问' }
  },
  {
    path: '*',
    component: NotFound
  },
  {
    path: '/disease/:id',
    name: 'DiseaseDetail',
    component: () => import('@/views/disease/detail')
  },
  {
    path: '/history/:id',
    name: 'HistoryDetail',
    component: () => import('@/views/history/detail')
  }
]

//3.实例化VueRouter
const createRouter = () => new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

// 路由守卫
router.beforeEach((to, from, next) => {
  console.log('--- 路由导航开始 ---');
  console.log('从:', from.path, '到:', to.path);

  // 设置标题
  if (to.meta.title) {
    document.title = to.meta.title;
  }

  // 获取token
  const token = localStorage.getItem('token');
  console.log('Token:', token ? '存在' : '不存在');

  // 公开页面无需登录
  const publicPages = ['/login', '/register', '/home', '/projects'];
  const isPublicPage = publicPages.includes(to.path) || to.path.startsWith('/projects/');
  console.log('是否公共页面:', isPublicPage);

  if (isPublicPage) {
    // 如果是登录页面且已登录，重定向到首页
    if (to.path === '/login' && token) {
      console.log('已登录，重定向到首页');
      next('/');
      return;
    }
    console.log('公共页面，允许访问');
    next();
  } else {
    if (!token) {
      console.log('无Token，重定向到登录页');
      next(`/login?redirect=${encodeURIComponent(to.path)}`);
    } else {
      console.log('路由无需特定角色，允许访问');
      next();
    }
  }
  console.log('--- 路由导航结束 ---');
});

export default router
