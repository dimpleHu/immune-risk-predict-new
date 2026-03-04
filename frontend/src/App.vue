<!-- src/App.vue -->
<template>
  <div id="app">
    <!-- 使用Header组件替换顶部导航栏 -->
    <Header />

    <!-- 移动端菜单按钮 -->
    <div class="mobile-menu-button" @click="toggleMobileMenu">
      <i :class="['el-icon', mobileMenuVisible ? 'el-icon-close' : 'el-icon-menu']"></i>
    </div>

    <!-- 移动端菜单抽屉 -->
    <el-drawer
      :visible.sync="mobileMenuVisible"
      direction="ltr"
      size="70%"
      :show-close="false"
      :with-header="false"
      class="mobile-menu-drawer">
      <div class="mobile-menu">
        <div class="mobile-menu-header">
          <span>系统性红斑狼疮风险预测平台</span>
        </div>
      </div>
    </el-drawer>

    <!-- 主要内容区 -->
    <div class="content-wrapper">
      <el-main class="main-content">
        <transition name="fade" mode="out-in">
          <keep-alive :include="cachedViews">
            <router-view />
          </keep-alive>
        </transition>
      </el-main>
    </div>

    <!-- 回到顶部
    <el-backtop :visibility-height="100" :right="20" :bottom="20">
      <div class="back-to-top">
        <i class="el-icon-caret-top"></i>
      </div>
    </el-backtop>-->

    <!-- 底部信息栏 -->
    <Footer />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Header from '@/components/Header';
import Footer from '@/components/Footer';

export default {
  name: 'App',
  components: {
    Header,
    Footer
  },
  data() {
    return {
      activeIndex: '/home',
      requireAuthPaths: ['/user', '/appointment', '/fitness', '/activity', '/parking', '/payment', '/community-services'],
      mobileMenuVisible: false,
      cachedViews: [] // 清空不需要的缓存组件
    }
  },
  computed: {
    ...mapGetters([
      'isLoggedIn',
      'username',
      'role'
    ]),
    computedActiveIndex() {
      const path = this.$route.path;
      // 添加调试信息
      console.log('App.vue - 当前路径:', path);

      if (path.startsWith('/payment/utility') ||
          path.startsWith('/parking/record') ||
          path.startsWith('/activity/room') ||
          path.startsWith('/fitness')) {
        return 'community-services';
      }

      console.log('App.vue - 返回路径:', path);
      return path;
    }
  },
  methods: {
    goToHome() {
      this.$router.push('/home');
    },
    toggleMobileMenu() {
      this.mobileMenuVisible = !this.mobileMenuVisible;
      // 通知Header组件切换移动菜单状态
      this.$root.$emit('toggle-mobile-menu', this.mobileMenuVisible);
    },
    handleSelect(key) {
      // 添加调试日志
      console.log('菜单点击', key, '登录状态:', this.isLoggedIn);
      console.log('localStorage中的角色:', localStorage.getItem('roles'));
      console.log('Vuex中的角色:', this.$store.getters.role);
      console.log('需要授权的路径:', this.requireAuthPaths);
      console.log('当前路径是否需要授权:', this.requireAuthPaths.some(path => key.startsWith(path)));

      // 检查是否需要登录的路径
      if (this.requireAuthPaths.some(path => key.startsWith(path)) && !this.isLoggedIn) {
        console.log('未登录，重定向到登录页面');
        this.$message.warning('请先登录');
        this.$router.push('/login');
        return;
      }

      // 处理导航（添加显式路由导航）
      console.log('准备导航到:', key);
      if (key !== this.$route.path) {
        // 使用replace替代push，避免多余的历史记录
        this.$router.replace(key).catch(err => {
          console.error('路由导航错误:', err);
        });
      }
    },
    handleMobileSelect(key) {
      // 关闭菜单
      this.mobileMenuVisible = false;

      // 添加调试日志
      console.log('移动菜单点击', key, '登录状态:', this.isLoggedIn);
      console.log('localStorage中的角色:', localStorage.getItem('roles'));
      console.log('Vuex中的角色:', this.$store.getters.role);

      // 检查是否需要登录的路径
      if (this.requireAuthPaths.some(path => key.startsWith(path)) && !this.isLoggedIn) {
        console.log('未登录，重定向到登录页面');
        this.$message.warning('请先登录');
        this.$router.push('/login');
        return;
      }

      // 处理导航
      console.log('准备导航到:', key);
      this.$router.replace(key).catch(err => {
        console.error('路由导航错误:', err);
      });
    },
    handleCommand(command) {
      if (command === 'profile') {
        this.$router.push('/user/profile');
      } else if (command === 'logout') {
        this.handleLogout();
      }
    },
    async handleLogout() {
      this.$store.dispatch('user/logout');
      this.$message.success('已退出登录');
      // 如果当前在需要登录的页面，退出后跳转到首页
      if (this.requireAuthPaths.some(path => this.$route.path.startsWith(path))) {
        this.$router.push('/home');
      }
    },
    goToLogin() {
      // 保存当前路径用于登录后重定向
      const currentPath = this.$route.path;
      if (currentPath !== '/login' && !currentPath.startsWith('/register')) {
        this.$router.push(`/login?redirect=${encodeURIComponent(currentPath)}`);
      } else {
        this.$router.push('/login');
      }
      // 如果移动端菜单开着，关闭它
      this.mobileMenuVisible = false;
    },
    goToRegister() {
      this.$router.push('/register');
      // 如果移动端菜单开着，关闭它
      this.mobileMenuVisible = false;
    },
    checkAuth() {
      // 检查当前路由是否需要登录
      if (this.requireAuthPaths.some(path => this.$route.path.startsWith(path)) && !this.isLoggedIn) {
        this.$message.warning('请先登录');
        const currentPath = this.$route.path;
        this.$router.push(`/login?redirect=${encodeURIComponent(currentPath)}`);
      }
    }
  },
  watch: {
    // 监听路由变化，检查是否需要登录
    '$route'(to) {
      // 检查是否需要登录
      this.checkAuth();
    }
  },
  created() {
    // 检查当前路由是否需要登录
    this.checkAuth();
  }
}
</script>

<style lang="scss">
/* 全局滚动条样式 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 3px;

  &:hover {
    background: rgba(0, 0, 0, 0.3);
  }
}

::-webkit-scrollbar-track {
  background: transparent;
}

/* 全局样式重置 */
html, body {
  margin: 0;
  padding: 0;
  overflow-x: hidden;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", Arial, sans-serif;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
}

/* 顶部导航栏 */
.header {
  padding: 0;
  background-color:  #6087bc;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: fixed;
  width: 100%;
  top: 0;
  z-index: 1000;
  height: 60px;
}

.header-container {
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
  height: 100%;
  max-width: 1200px;
}

.logo {
  display: flex;
  align-items: center;
  height: 100%;
  flex-shrink: 0;
  margin-right: 40px;
  padding-left: 20px;
  cursor: pointer;
}

.logo-text {
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  margin: 0;
}

.nav-wrapper {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-menu {
  border-bottom: none !important;
  height: 100%;
  flex: 1;
  display: flex;
  justify-content: flex-start;
}

.nav-menu .el-menu-item {
  height: 60px;
  line-height: 60px;
  padding: 0 20px;
  border-bottom: none !important;
  color: #fff !important;
}

.nav-menu .el-menu-item.is-active {
  border-bottom: 2px solid #ffd04b !important;
  background-color: rgba(255, 255, 255, 0.1) !important;
  color: #ffd04b !important;
}

.nav-menu .el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.2) !important;
}

/* 主内容区 */
.content-wrapper {
  flex: 1;
  margin-top: 20px;
  min-height: calc(100vh - 60px);
  display: flex;
  overflow: hidden;
}

.main-content {
  flex: 1;
  padding: 20px;
  margin: 0 auto;
  max-width: 1200px;
  width: 100%;
  overflow-y: auto;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 底部 */
.footer {
  background-color: #f0f0f0;
  padding: 20px;
  text-align: center;
  border-top: 1px solid #ddd;
}

.footer-content p {
  margin: 5px 0;
  color: #666;
  font-size: 14px;
}

.user-status {
  display: flex;
  align-items: center;
  margin-right: 20px;
}

.user-status .el-button {
  color: #fff;
  margin-left: 15px;
  font-size: 14px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #fff;
  display: flex;
  align-items: center;
}

.el-dropdown-link:hover {
  color: #ffd04b;
}

/* 移动端菜单按钮 */
.mobile-menu-button {
  display: none;
  position: fixed;
  right: 15px;
  top: 15px;
  z-index: 1001;
  width: 40px;
  height: 40px;
  background-color:  #6087bc;
  border-radius: 50%;
  color: #fff;
  text-align: center;
  line-height: 40px;
  font-size: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

/* 移动端菜单样式 */
.mobile-menu {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.mobile-menu-header {
  height: 60px;
  background-color:  #6087bc;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: bold;
}

.mobile-menu-user-actions {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.mobile-username {
  padding: 10px;
  text-align: center;
  color:  #6087bc;
  font-weight: bold;
}

/* 页面过渡动画 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

/* 回到顶部按钮样式 */
.back-to-top {
  width: 40px;
  height: 40px;
  background-color:  #6087bc;
  color: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 响应式设计 */
@media screen and (max-width: 992px) {
  .logo-text {
    font-size: 18px;
  }

  .nav-menu .el-menu-item {
    padding: 0 15px;
  }

  .main-content {
    margin: 15px;
    padding: 15px;
  }
}

@media screen and (max-width: 768px) {
  .header-container .nav-wrapper {
    display: none;
  }

  .mobile-menu-button {
    display: block;
  }

  .logo {
    margin: 0 auto;
  }

  .header-container {
    padding: 0 10px;
  }

  .main-content {
    margin: 10px;
    padding: 15px;
  }
}
</style>
