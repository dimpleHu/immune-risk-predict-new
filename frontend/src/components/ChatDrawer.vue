<template>
  <el-drawer
      :visible.sync="visible"
      direction="rtl"
      :before-close="handleBeforeClose"
      size="35%"
      :with-header="false"
      :border="false"
      class="custom-drawer"
  >
    <div class="chat-container">
      <!-- 聊天头部（保持原有风格） -->
      <div class="chat-header">
        <div class="header-content">
          <h3>在线咨询</h3>
          <p>工作日 9:00-18:00 在线</p>
        </div>
        <el-button
            icon="el-icon-close"
            circle
            size="mini"
            class="close-btn"
            @click="handleClose"
        ></el-button>
      </div>

      <!-- 聊天内容区（新增加载状态、错误提示） -->
      <div class="chat-messages">
        <div class="message-date">今天</div>

        <!-- 初始欢迎消息 -->
        <div class="message-item system-message">
          <div class="avatar system-avatar">
            <i class="el-icon-user"></i>
          </div>
          <div class="message-content">
            您好！我是免疫病风险预测咨询助手，可为您解答免疫病相关问题~
          </div>
        </div>

        <!-- 聊天记录列表 -->
        <div
            v-for="(msg, index) in chatMessages"
            :key="index"
            class="message-item"
            :class="msg.type === 'user' ? 'user-message' : 'system-message'"
        >
          <div class="avatar" :class="msg.type === 'user' ? 'user-avatar' : 'system-avatar'">
            <i :class="msg.type === 'user' ? 'el-icon-user-solid' : 'el-icon-user'"></i>
          </div>
          <div class="message-content">
            <!-- 普通消息 -->
            <span v-if="!msg.isError">{{ msg.content }}</span>
            <!-- 错误消息（红色提示） -->
            <span v-else class="error-text">{{ msg.content }}</span>
            <!-- 流式加载中（小loading图标） -->
            <span v-if="msg.isLoading" class="loading-icon">
              <i class="el-icon-loading el-icon--small"></i>
            </span>
          </div>
        </div>
      </div>

      <!-- 输入区域（禁用状态、错误提示） -->
      <div class="chat-input-area">
        <el-input
            v-model="inputValue"
            placeholder="请输入您的问题（例如：自身免疫病有哪些症状？）..."
            @keyup.enter.native="handleSendStreamMessage"
            class="message-input"
            :autosize="{ minRows: 1, maxRows: 4 }"
            :disabled="isGlobalLoading"
            :placeholder-class="isGlobalLoading ? 'disabled-placeholder' : ''"
        ></el-input>
        <el-button
            type="primary"
            class="send-button"
            @click="handleSendStreamMessage"
            :disabled="!inputValue.trim() || isGlobalLoading"
            :loading="isGlobalLoading"
        >
          <i class="el-icon-paper-plane"></i>
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
// 1. 引入封装好的DeepSeek流式接口（和预测记录接口风格一致）
import { deepSeekChatStream } from '@/api/deepseek'

export default {
  name: 'ChatDrawer',
  props: {
    visible: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  data() {
    return {
      inputValue: '', // 输入框内容
      chatMessages: [], // 聊天记录列表
      isGlobalLoading: false, // 全局加载状态（控制输入框+按钮禁用）
      streamCancel: null // 流式请求取消函数（用于关闭连接）
    }
  },
  methods: {
    // 关闭抽屉前的确认（和预测记录页面的删除确认逻辑一致）
    handleBeforeClose(done) {
      this.$confirm('确认关闭咨询窗口吗？未完成的对话将中断')
          .then(_ => {
            // 中断可能存在的流式连接
            if (this.streamCancel) {
              this.streamCancel()
            }
            this.resetChatState() // 重置聊天状态
            done()
            this.$emit('close')
          })
          .catch(_ => {})
    },

    // 直接关闭抽屉
    handleClose() {
      if (this.streamCancel) {
        this.streamCancel()
      }
      this.resetChatState()
      this.$emit('close')
    },

    // 重置聊天状态（复用逻辑）
    resetChatState() {
      this.inputValue = ''
      this.isGlobalLoading = false
      this.streamCancel = null
      // 保留初始欢迎消息，清空后续记录
      this.chatMessages = this.chatMessages.filter(msg => msg.isInit)
    },

    // 发送流式消息（核心逻辑，和预测记录的新增接口调用风格一致）
    handleSendStreamMessage() {
      const userInput = this.inputValue.trim()
      if (!userInput) {
        this.$message.warning('请输入您的问题')
        return
      }

      // 1. 添加用户消息到记录（和预测记录的"新增成功"提示逻辑一致）
      const userMsg = {
        type: 'user',
        content: userInput,
        isInit: false,
        isLoading: false,
        isError: false
      }
      this.chatMessages.push(userMsg)
      this.inputValue = '' // 清空输入框
      this.isGlobalLoading = true // 开启全局加载

      // 2. 添加系统消息占位（用于实时渲染流式内容）
      const systemMsgIndex = this.chatMessages.length
      this.chatMessages.push({
        type: 'system',
        content: '',
        isInit: false,
        isLoading: true, // 显示加载中
        isError: false
      })
      this.scrollToBottom() // 滚动到底部

      // 3. 调用流式接口（和预测记录的API调用风格一致：try-catch+错误处理）
      const streamPromise = deepSeekChatStream({
        message: userInput // 传递用户输入参数
      })

      // 4. 处理流式响应（核心：分块接收+实时更新）
      streamPromise.then(response => {
        let fullContent = '' // 累计流式内容

        // 定义取消流式连接的函数（复用）
        this.streamCancel = () => {
          response.data.destroy() // 中断流
          this.updateSystemMessage(systemMsgIndex, '对话已中断', false, false)
          this.isGlobalLoading = false
        }

        // 接收流数据分块
        response.data.on('data', (chunk) => {
          const chunkStr = chunk.toString().trim()
          // 解析SSE格式（data: 内容\n\n）
          const dataMatch = chunkStr.match(/data: (.*)/)
          if (dataMatch && dataMatch[1]) {
            fullContent += dataMatch[1]
            // 实时更新系统消息内容
            this.updateSystemMessage(systemMsgIndex, fullContent, true, false)
            this.scrollToBottom()
          }
        })

        // 流结束（和预测记录的"查询成功"逻辑一致）
        response.data.on('end', () => {
          this.updateSystemMessage(systemMsgIndex, fullContent, false, false)
          this.isGlobalLoading = false
          this.streamCancel = null
          this.$message.success('对话完成')
        })

        // 流错误（和预测记录的"接口报错"处理逻辑一致）
        response.data.on('error', (error) => {
          console.error('流式对话错误：', error)
          const errorMsg = error.message || '对话请求失败，请稍后重试'
          this.updateSystemMessage(systemMsgIndex, errorMsg, false, true)
          this.isGlobalLoading = false
          this.streamCancel = null
          this.$message.error(errorMsg)
        })

      }).catch(error => {
        // 接口初始化失败（和预测记录的"新增失败"处理一致）
        console.error('流式请求初始化错误：', error)
        this.updateSystemMessage(systemMsgIndex, '系统错误，请稍后重试', false, true)
        this.isGlobalLoading = false
        this.$message.error('请求失败：' + (error.message || '未知错误'))
      })
    },

    // 更新系统消息（复用逻辑，避免重复代码）
    updateSystemMessage(index, content, isLoading, isError) {
      this.$set(this.chatMessages, index, {
        ...this.chatMessages[index],
        content,
        isLoading,
        isError
      })
    },

    // 滚动到聊天底部（复用逻辑）
    scrollToBottom() {
      this.$nextTick(() => {
        const container = document.querySelector('.chat-messages')
        if (container) {
          container.scrollTop = container.scrollHeight
        }
      })
    }
  },

  // 初始化聊天记录（只添加初始欢迎消息）
  created() {
    this.chatMessages = [{
      type: 'system',
      content: '您好！我是免疫病风险预测咨询助手，可为您解答免疫病相关问题~',
      isInit: true, // 标记为初始消息，关闭时不删除
      isLoading: false,
      isError: false
    }]
  },

  // 组件销毁时中断连接（防止内存泄漏，和预测记录的组件销毁逻辑一致）
  beforeDestroy() {
    if (this.streamCancel) {
      this.streamCancel()
    }
  }
}
</script>

<style scoped>
/* 仅保留抽屉相关样式，删除原页面无关样式 */
.custom-drawer {
  padding: 0;
  box-shadow: -5px 0 25px rgba(0, 0, 0, 0.05);
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #f9f9f9;
}

.chat-header {
  background: linear-gradient(135deg, #409EFF, #66b1ff);
  color: white;
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.header-content h3 {
  margin: 0 0 5px 0;
  font-size: 18px;
  font-weight: 500;
}

.header-content p {
  margin: 0;
  font-size: 12px;
  opacity: 0.9;
}

.close-btn {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  transition: all 0.2s ease;
}

.close-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
  color: white;
  transform: rotate(90deg);
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f9f9f9;
}

.message-date {
  text-align: center;
  color: #999;
  font-size: 12px;
  margin: 10px 0;
}

.message-item {
  margin-bottom: 18px;
  display: flex;
  animation: fadeIn 0.3s ease;
}

.system-message {
  align-items: flex-start;
}

.user-message {
  flex-direction: row-reverse;
  align-items: flex-start;
}

.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px;
  margin-top: 3px;
  flex-shrink: 0;
}

.user-message .avatar {
  margin-right: 0;
  margin-left: 10px;
}

.system-avatar {
  background-color: #e6f7ff;
  color: #409EFF;
}

.user-avatar {
  background-color: #409EFF;
  color: white;
}

.message-content {
  max-width: 70%;
  padding: 10px 15px;
  border-radius: 18px;
  line-height: 1.5;
  position: relative;
}

.system-message .message-content {
  background-color: white;
  border: 1px solid #e5e6eb;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.user-message .message-content {
  background-color: #409EFF;
  color: white;
  box-shadow: 0 1px 2px rgba(64, 158, 255, 0.2);
}

.chat-input-area {
  padding: 15px;
  background-color: white;
  border-top: 1px solid #eee;
  display: flex;
  gap: 10px;
}

.message-input {
  flex: 1;
  border-radius: 20px;
  border-color: #e5e6eb;
  transition: all 0.2s ease;
}

.message-input:focus {
  border-color: #409EFF;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.send-button {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #409EFF;
  transition: all 0.2s ease;
}

.send-button:hover {
  background-color: #337ab7;
  transform: scale(1.1);
}

.send-button:disabled {
  background-color: #c0c4cc;
  cursor: not-allowed;
  transform: none;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 10px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #aaa;
}

/* 适配移动端 */
@media (max-width: 768px) {
  .system-message {
    background-color: #f5f7fa;
    color: #303133;
    align-self: flex-start;
  }
  .user-message {
    background-color: #409EFF;
    color: white;
    align-self: flex-end;
  }
}

/* 错误消息文本（和预测记录的错误提示颜色一致） */
.error-text {
  color: #f56c6c;
}

/* 加载中图标（和预测记录的loading图标风格一致） */
.loading-icon {
  margin-left: 8px;
  color: #409eff;
  animation: spin 1s linear infinite;
}

/* 禁用状态的占位符（和预测记录的禁用输入框风格一致） */
.disabled-placeholder {
  color: #c0c4cc;
}

/* 加载动画（复用Element UI的loading动画） */
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

/* 确保聊天记录的加载状态和预测记录的列表加载对齐 */
.message-item .message-content {
  display: flex;
  align-items: center;
}
</style>