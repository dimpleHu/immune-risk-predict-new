<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>欢迎登录</h2>
        <p class="subtitle">自身免疫病风险预测平台</p>
      </div>

      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        label-position="top"
        @submit.native.prevent>

        <el-form-item label="手机号" prop="phoneNumber">
          <el-input
            v-model="loginForm.phoneNumber"
            prefix-icon="el-icon-mobile-phone"
            placeholder="请输入手机号"
            maxlength="11"
            clearable
            @keyup.enter.native="handleLogin" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            :type="passwordVisible ? 'text' : 'password'"
            prefix-icon="el-icon-lock"
            placeholder="请输入密码"
            @keyup.enter.native="handleLogin">
            <i
              slot="suffix"
              :class="['el-input__icon', passwordVisible ? 'el-icon-view' : 'el-icon-hide']"
              @click="passwordVisible = !passwordVisible">
            </i>
          </el-input>
        </el-form-item>

        <div class="login-options">
          <el-checkbox v-model="rememberMe">记住登录状态</el-checkbox>
        </div>

        <el-button
          type="primary"
          class="login-button"
          :loading="loading"
          @click="handleLogin">
          {{ loading ? '登录中...' : '登录' }}
        </el-button>
      </el-form>

      <div class="login-footer">
        <span>还没有账号？</span>
        <router-link to="/register" class="register-link">立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import storage from '@/utils/storage';
import { login } from '@/api/user';

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        phoneNumber: '',
        password: ''
      },
      loginRules: {
        phoneNumber: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ]
      },
      loading: false,
      rememberMe: false,
      passwordVisible: false
    };
  },
  created() {
    // 从本地存储中恢复登录信息
    const savedMobile = localStorage.getItem('rememberedMobile');
    if (savedMobile) {
      this.loginForm.phoneNumber = savedMobile;
      this.rememberMe = true;
    }

    // 如果有重定向参数，显示提示
    if (this.$route.query.redirect) {
      this.$message.info('请先登录后继续');
    }
  },
  methods: {
    ...mapActions({
      loginSuccess: 'user/loginSuccess'
    }),

    async handleLogin() {
      try {
        await this.$refs.loginForm.validate();
        this.loading = true;
        const res = await login(this.loginForm);
        
        console.log('登录响应数据:', res);
        console.log('用户信息:', res.data.userInfo);
        
        // 记住登录状态
        if (this.rememberMe) {
          localStorage.setItem('rememberedMobile', this.loginForm.phoneNumber);
        } else {
          localStorage.removeItem('rememberedMobile');
        }

        // 保存token和用户信息
        storage.set('Token', res.data.token);
        storage.set('userInfo', res.data.userInfo);
        
        // 构造用户显示名称
        const userInfo = res.data.userInfo;
        const displayName = userInfo.userName || userInfo.name || userInfo.phoneNumber;
        console.log('用户信息:', userInfo);
        console.log('显示名称:', displayName);
        
        // 更新vuex状态
        await this.loginSuccess({
          token: res.data.token,
          username: displayName,
          role: userInfo.role || 'user',
          userInfo: userInfo
        });

        this.$message.success('登录成功');

        // 获取重定向地址或默认跳转到首页
        const redirect = this.$route.query.redirect || '/';
        this.$router.push(redirect);
      } catch (error) {
        console.error('登录失败:', error);
        this.$message.error(error.message || '登录失败，请重试');
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 180px);
  padding: 40px 20px;
  background-color: #f5f7fa;
}

.login-card {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  max-width: 400px;
  width: 100%;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  font-size: 24px;
  color: #303133;
  margin-bottom: 8px;
}

.subtitle {
  color: #909399;
  font-size: 16px;
  margin: 0;
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.login-button {
  width: 100%;
  margin-top: 10px;
  height: 46px;
  font-size: 16px;
  border-radius: 4px;
}

.login-footer {
  margin-top: 30px;
  text-align: center;
  color: #606266;
}

.register-link {
  color: #409EFF;
  text-decoration: none;
  margin-left: 5px;
  font-weight: bold;
}

.register-link:hover {
  text-decoration: underline;
}

.forgot-password {
  padding: 0;
  margin: 0;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-card {
    padding: 25px;
  }

  .login-header h2 {
    font-size: 22px;
  }
}
</style>
