<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h2>用户注册</h2>
        <p class="subtitle">自身免疫病风险预测平台</p>
      </div>

      <el-form
        ref="registerForm"
        :model="registerForm"
        :rules="rules"
        label-position="top"
        @submit.native.prevent>

        <el-form-item label="手机号" prop="phoneNumber">
          <el-input
            v-model="registerForm.phoneNumber"
            prefix-icon="el-icon-mobile-phone"
            placeholder="请输入手机号"
            maxlength="11"
            clearable />
        </el-form-item>

        <el-form-item label="用户昵称" prop="userName">
          <el-input
            v-model="registerForm.userName"
            prefix-icon="el-icon-user"
            placeholder="请输入用户昵称"
            clearable />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerForm.password"
            :type="passwordVisible ? 'text' : 'password'"
            prefix-icon="el-icon-lock"
            placeholder="请输入密码">
            <i
              slot="suffix"
              :class="['el-input__icon', passwordVisible ? 'el-icon-view' : 'el-icon-hide']"
              @click="passwordVisible = !passwordVisible">
            </i>
          </el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            :type="confirmPasswordVisible ? 'text' : 'password'"
            prefix-icon="el-icon-lock"
            placeholder="请再次输入密码">
            <i
              slot="suffix"
              :class="['el-input__icon', confirmPasswordVisible ? 'el-icon-view' : 'el-icon-hide']"
              @click="confirmPasswordVisible = !confirmPasswordVisible">
            </i>
          </el-input>
        </el-form-item>

        <el-button
          type="primary"
          class="register-button"
          :loading="loading"
          @click="handleRegister">
          {{ loading ? '注册中...' : '注册' }}
        </el-button>
      </el-form>

      <div class="register-footer">
        <span>已有账号？</span>
        <router-link to="/login" class="login-link">立即登录</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from '@/api/user';

export default {
  name: 'Register',
  data() {
    // 校验密码是否一致
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    };

    // 手机号验证
    const validatePhoneNumber = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入手机号'));
      } else if (!/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error('请输入正确的手机号'));
      } else {
        callback();
      }
    };

    return {
      registerForm: {
        phoneNumber: '',
        userName: '',
        password: '',
        confirmPassword: ''
      },
      passwordVisible: false,
      confirmPasswordVisible: false,
      loading: false,
      rules: {
        phoneNumber: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: validatePhoneNumber, trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' },
          { min: 2, max: 20, message: '用户昵称长度在2到20个字符之间', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    // 处理注册
    async handleRegister() {
      try {
        // 表单验证
        await this.$refs.registerForm.validate();

        this.loading = true;

        // 准备注册数据
        const userData = {
          phoneNumber: this.registerForm.phoneNumber,
          userName: this.registerForm.userName,
          password: this.registerForm.password
        };

        // 发送注册请求
        const response = await register(userData);

        if (response.code === 1) {
          this.$message({
            type: 'success',
            message: response.data || '注册成功，请登录',
            duration: 1500
          });
          // 跳转到登录页
          setTimeout(() => {
            this.$router.push('/login');
          }, 1500);
        } else {
          this.$message.error(response.msg || '注册失败，请重试');
        }
      } catch (error) {
        console.error('注册失败:', error);
        if (error.response) {
          switch (error.response.status) {
            case 400:
              this.$message.error('注册信息不完整或格式错误');
              break;
            case 409:
              this.$message.error('该手机号已被注册');
              break;
            default:
              this.$message.error(error.response.data?.msg || '注册失败，请重试');
          }
        } else {
          this.$message.error('网络错误，请稍后重试');
        }
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 180px);
  padding: 40px 20px;
  background-color: #f5f7fa;
}

.register-card {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  max-width: 450px;
  width: 100%;
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
}

.register-header h2 {
  font-size: 24px;
  color: #303133;
  margin-bottom: 8px;
}

.subtitle {
  color: #909399;
  font-size: 16px;
  margin: 0;
}

.register-button {
  width: 100%;
  margin-top: 10px;
  height: 46px;
  font-size: 16px;
  border-radius: 4px;
}

.register-footer {
  margin-top: 30px;
  text-align: center;
  color: #606266;
}

.login-link {
  color: #409EFF;
  text-decoration: none;
  margin-left: 5px;
  font-weight: bold;
}

.login-link:hover {
  text-decoration: underline;
}
</style>
