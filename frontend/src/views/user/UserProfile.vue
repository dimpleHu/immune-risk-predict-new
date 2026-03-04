<template>
  <div class="user-profile">
    <el-tabs v-model="activeTab">
      <!-- 个人信息标签页 -->
      <el-tab-pane label="个人信息" name="profile">
        <el-card class="profile-card">
          <div slot="header" class="card-header">
            <span>个人信息</span>
          </div>

          <el-form
              ref="userForm"
              :model="userForm"
              :rules="rules"
              label-width="100px"
              disabled>

            <el-form-item label="用户编号">
              <el-input v-model="userForm.userID" disabled></el-input>
            </el-form-item>

            <el-form-item label="用户昵称" prop="userName">
              <el-input v-model="userForm.userName"></el-input>
            </el-form-item>

            <el-form-item label="手机号码" prop="phoneNumber">
              <el-input v-model="userForm.phoneNumber"></el-input>
            </el-form-item>
          </el-form>
        </el-card>

        <el-card class="password-card">
          <div slot="header" class="card-header">
            <span>修改密码</span>
          </div>

          <el-form
              ref="passwordForm"
              :model="passwordForm"
              :rules="passwordRules"
              label-width="100px">

            <el-form-item label="原密码" prop="oldPassword">
              <el-input
                  type="password"
                  v-model="passwordForm.oldPassword"
                  show-password>
              </el-input>
            </el-form-item>

            <el-form-item label="新密码" prop="newPassword">
              <el-input
                  type="password"
                  v-model="passwordForm.newPassword"
                  show-password>
              </el-input>
            </el-form-item>

            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                  type="password"
                  v-model="passwordForm.confirmPassword"
                  show-password>
              </el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="handleUpdatePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getUserInfo, updatePassword } from '@/api/user'

export default {
  name: 'UserProfile',
  data() {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      activeTab: 'profile',
      userForm: {
        userID: '',
        userName: '',
        phoneNumber: ''
      },
      rules: {
        userName: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        phoneNumber: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      },
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 从本地存储获取用户ID
    const userInfo = localStorage.getItem('userInfo')
    if (userInfo) {
      try {
        const { userID } = JSON.parse(userInfo)
        this.userForm.userID = userID
        this.getUserInfo()
      } catch (error) {
        console.error('解析用户信息失败:', error)
        this.$message.error('获取用户信息失败')
        this.$router.push('/login')
      }
    } else {
      this.$message.error('请先登录')
      this.$router.push('/login')
    }
  },
  methods: {
    async getUserInfo() {
      try {
        if (!this.userForm.userID) {
          this.$message.error('用户ID不存在')
          return
        }

        const res = await getUserInfo(this.userForm.userID)
        if (res.code === 1) {
          const userInfo = res.data
          this.userForm = {
            userID: userInfo.userID,
            userName: userInfo.userName,
            phoneNumber: userInfo.phoneNumber
          }
        } else {
          this.$message.error(res.msg || '获取用户信息失败')
        }
      } catch (error) {
        console.error('获取用户信息失败:', error)
        this.$message.error('获取用户信息失败')
      }
    },
    async handleUpdatePassword() {
      try {
        await this.$refs.passwordForm.validate()
        const res = await updatePassword(this.userForm.userID, {
          oldPassword: this.passwordForm.oldPassword,
          newPassword: this.passwordForm.newPassword
        })
        if (res.code === 1) {
          this.$message.success('密码修改成功')
          this.$refs.passwordForm.resetFields()
        }
      } catch (error) {
        console.error('密码修改失败:', error)
        this.$message.error('密码修改失败')
      }
    }
  }
}
</script>

<style scoped>
/* 保留个人信息相关样式，删除其他模块样式 */
.user-profile {
  padding: 20px;
  display: flex;
  justify-content: center;
  min-height: calc(100vh - 120px);
  background-color: #f5f7fa;
}

.el-tabs {
  width: 100%;
  max-width: 1000px;
}

.profile-card,
.password-card {
  margin-bottom: 20px;
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.profile-card:hover,
.password-card:hover {
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
}

.card-header span {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.el-form {
  margin: 30px auto;
  width: 80%;
}

.el-form-item {
  margin-bottom: 25px;
}

.el-input {
  width: 100%;
}

.el-button {
  padding: 12px 25px;
  font-size: 14px;
}

/* 标签页样式 */
.el-tabs__item {
  font-size: 16px;
  padding: 0 25px;
  height: 50px;
  line-height: 50px;
}

.el-tabs__item.is-active {
  font-weight: 600;
}
</style>