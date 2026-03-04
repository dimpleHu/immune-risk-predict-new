<!--页面的头部以及导航栏-->
<template>
  <el-header class="header">
    <div class="header-container">
      <div class="logo" @click="goToHome">
        <!-- 添加Logo图片 -->
        <img src="@/assets/img/logo.png" alt="免疫病Logo" class="logo-img">
        <span class="logo-text">系统性红斑狼疮风险预测平台</span>
      </div>
      <div class="nav-wrapper">
        <!-- default-active 属性：设置默认激活的菜单项索引，这里使用变量 activeIndex 动态绑定 -->
        <!-- mode 属性：设置菜单模式，'horizontal' 表示水平布局（横向排列） -->
        <!-- @select 事件：当菜单项被选中时触发 handleSelect 方法 -->
        <!-- active-text-color 属性：设置激活状态下的文字颜色 -->
        <el-menu
          :default-active="activeIndex"
          class="nav-menu"
          mode="horizontal"
          @select="handleSelect"
          background-color="#98bae6"
          text-color="#fff"
          active-text-color="#ffd04b"
          router>
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item index="/disease">常见疾病</el-menu-item>
          <el-menu-item index="/prediction">风险预测</el-menu-item>
          <el-menu-item index="/history">历史记录</el-menu-item>
          <el-menu-item index="/profile">个人空间</el-menu-item>
        </el-menu>

        <!-- 用户登录状态 -->
        <div class="user-status">
          <template v-if="isLoggedIn">
            <el-dropdown @command="handleCommand" trigger="click">
              <!-- 下拉菜单的触发元素 -->
              <span class="el-dropdown-link">
                {{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>

              <!-- 使用 template + v-slot 代替 slot 属性 -->
              <template v-slot:dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人空间</el-dropdown-item>
                  <el-dropdown-item command="information">最新消息</el-dropdown-item>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button type="text" @click="goToLogin">登录</el-button>
            <el-button type="text" @click="goToRegister">注册</el-button>
          </template>
        </div>
      </div>
    </div>
  </el-header>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      requireAuthPaths: ['/orders', '/profile','/history','prediction'],
      mobileMenuVisible: false,
    }
  },
  computed: {
    ...mapGetters([
      'isLoggedIn',
      'username',
      'role'
    ]),
    activeIndex() {
      const path = this.$route.path;
      console.log('当前路径:', path);

      if (path.startsWith('/payment/utility') ||
          path.startsWith('/parking/record') ||
          path.startsWith('/activity/room') ||
          path.startsWith('/fitness')) {
        console.log('识别为社区服务子页面，激活社区服务菜单');
        return 'community-services';
      }

      console.log('返回路径:', path);
      return path;
    }
  },
  methods: {
    goToHome() {
      this.$router.push('/home');
    },
    toggleMobileMenu() {
      this.mobileMenuVisible = !this.mobileMenuVisible;
      // 通知App组件切换移动菜单状态
      this.$root.$emit('toggle-mobile-menu', this.mobileMenuVisible);
    },
    handleSelect(key) {
      console.log('菜单点击', key, '登录状态:', this.isLoggedIn);

      // 检查是否需要登录的路径
      if (this.requireAuthPaths.some(path => key.startsWith(path)) && !this.isLoggedIn) {
        console.log('未登录，重定向到登录页面');
        this.$message.warning('请先登录');
        this.$router.push('/login');
        return;
      }

      // 处理导航
      console.log('准备导航到:', key);
      if (key !== this.$route.path) {
        this.$router.replace(key).catch(err => {
          console.error('路由导航错误:', err);
        });
      }
    },
    handleCommand(command) {
      if (command === 'profile') {
        this.$router.push('/profile');
      }else if (command === 'information') {
        this.$router.push('/profile');
      }else if (command === 'logout') {
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
    },
    goToRegister() {
      this.$router.push('/register');
    }
  },
  created() {
    // 监听来自App组件的移动菜单切换事件
    this.$root.$on('toggle-mobile-menu', (status) => {
      this.mobileMenuVisible = status;
    });
  },
  beforeDestroy() {
    // 注销事件监听
    this.$root.$off('toggle-mobile-menu');
  }
}
</script>

<style scoped>
.header {
  background-color: #98bae6;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  padding: 0;
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
/* 新增logo图片样式 */
.logo-img {
  height: 40px; /* 根据实际图片比例调整 */
  margin-right: 12px; /* 图片与文字间距 */
  border-radius: 8px; /* 添加圆角效果  */
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

/* 下拉菜单样式 */
::v-deep .el-submenu__title {
  color: #fff !important;
  height: 60px;
  line-height: 60px;
}

::v-deep .el-submenu__title:hover {
  color: #ffd04b !important;
  background-color: rgba(255, 255, 255, 0.2) !important;
}

::v-deep .el-submenu.is-active .el-submenu__title {
  border-bottom: 2px solid #ffd04b !important;
  color: #ffd04b !important;
}

/* 下拉菜单项的样式 */
::v-deep .el-menu--popup {
  min-width: 120px;
}

::v-deep .el-menu--popup .el-menu-item {
  color: #333;
  font-size: 14px;
}

::v-deep .el-menu--popup .el-menu-item:hover {
  background-color: #ecf5ff;
}
</style>
