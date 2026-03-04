<template>
  <div class="user-center-container">
    <!-- 用户中心内容 -->
    <el-row :gutter="20">
      <!-- 侧边栏 -->
      <el-col :span="5">
        <el-card class="user-sidebar" shadow="never">
          <el-menu
            :default-active="activeMenu"
            router
          >
            <el-menu-item index="/user/profile">
              <i class="el-icon-user"></i>
              <span slot="title">个人信息</span>
            </el-menu-item>
            <el-menu-item index="/user/appointments">
              <i class="el-icon-date"></i>
              <span slot="title">我的预约</span>
            </el-menu-item>
            <el-menu-item index="/user/reviews">
              <i class="el-icon-chat-line-round"></i>
              <span slot="title">我的评价</span>
            </el-menu-item>
          </el-menu>
        </el-card>
      </el-col>

      <!-- 主内容 -->
      <el-col :span="19">
        <el-card class="user-main" shadow="never">
          <router-view />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import storage from '@/utils/storage';
import { getUserInfo } from '@/api/user';

export default {
  name: 'UserCenter',
  data() {
    return {
      activeMenu: '/user/profile'
    };
  },
  created() {
    // 检查登录状态
    this.checkLoginStatus();
    
    // 根据当前路由设置激活的菜单项
    this.activeMenu = this.$route.path;
  },
  watch: {
    '$route'(to) {
      this.activeMenu = to.path;
    }
  },
  methods: {
    checkLoginStatus() {
      const token = storage.get('Token');
      if (!token) {
        this.$message.error('请先登录');
        this.$router.push('/login?redirect=' + encodeURIComponent(this.$route.fullPath));
        return;
      }
      
      // 验证Token有效性
      this.validateToken();
    },
    
    validateToken() {
      // 调用获取用户信息API验证Token有效性
      getUserInfo().then(res => {
        if (res && res.code === 0) {
          console.log('用户已登录，信息获取成功:', res.data);
        } else {
          console.error('Token无效:', res);
          this.$message.error(res.msg || '登录已过期，请重新登录');
          storage.remove('Token');
          this.$router.push('/login?redirect=' + encodeURIComponent(this.$route.fullPath));
        }
      }).catch(error => {
        console.error('验证Token失败:', error);
        this.$message.error('登录状态验证失败，请重新登录');
        storage.remove('Token');
        this.$router.push('/login?redirect=' + encodeURIComponent(this.$route.fullPath));
      });
    }
  }
};
</script>

<style scoped>
.user-center-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.user-sidebar {
  background-color: #fff;
  border-radius: 4px;
}

.user-sidebar .el-menu {
  border-right: none;
}

.user-main {
  background-color: #fff;
  min-height: 500px;
  border-radius: 4px;
}
</style>