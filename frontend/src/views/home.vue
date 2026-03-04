<template>
  <div class="home">
    <!-- 原有轮播图及欢迎区域 -->
    <div class="welcome-carousel-container">
      <el-carousel
          class="welcome-carousel"
          :interval="5000"
          height="400px"
          indicator-position="outside"
      >
        <el-carousel-item v-for="(banner, index) in bannerImages" :key="index">
          <div
              class="banner-image"
              :style="{ backgroundImage: `url(${banner})` }"
          ></div>
        </el-carousel-item>
      </el-carousel>
      <div class="fixed-welcome-content">
        <div class="welcome-content">
          <h1>系统性红斑狼疮风险预测平台</h1>
          <p>测免疫风险，护全身安康</p>
          <el-button
              type="primary"
              size="large"
              @click="$router.push('/prediction')"
              class="reserve-btn"
          >
            立即预测
          </el-button>
        </div>
      </div>
    </div>

    <!-- 原有统计数据区域 -->
    <div class="stats-section">
      <el-row :gutter="20">
        <el-col :span="6" v-for="stat in statistics" :key="stat.id">
          <div class="stat-item">
            <div class="stat-number">{{ stat.number }}</div>
            <div class="stat-label">{{ stat.label }}</div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 原有常见免疫病区域 -->
    <div class="section hot-diseases">
      <div class="section-header">
        <h2>常见免疫病</h2>
      </div>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="(disease, index) in hotDiseases" :key="index">
          <el-card class="disease-card" :body-style="{ padding: '0px' }">
            <div style="position: relative; padding-top: 66.67%;">
              <img
                  :src="disease.diseaseImage || defaultImage"
                  class="disease-image"
                  @error="handleImageError"
                  style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;"
              >
            </div>
            <div class="disease-info">
              <h3>{{ disease.diseaseName }}</h3>
              <p class="description">{{ disease.description }}</p>
              <el-button type="primary" size="small" @click="goToProject(disease.diseaseID)">查看详情</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="view-more-container">
        <el-button
            type="text"
            @click="$router.push('/disease')"
            class="view-more-btn">
          查看更多
        </el-button>
      </div>
      <div v-if="hotDiseases.length === 0" style="text-align: center; padding: 20px;">
        <p>暂无相关免疫病数据</p>
      </div>
    </div>

    <!-- 原有预测流程区域 -->
    <div class="section booking-process">
      <h2 class="section-title">预测流程</h2>
      <el-steps :active="5" finish-status="success" align-center>
        <el-step title="信息填写" description="填写个人基础信息"></el-step>
        <el-step title="症状描述" description="详细描述身体症状表现"></el-step>
        <el-step title="风险预测" description="进行系统性红斑狼疮风险评估"></el-step>
        <el-step title="详细介绍" description="查看疾病相关详细内容"></el-step>
        <el-step title="治疗方法" description="获取对应治疗建议方案"></el-step>
      </el-steps>
    </div>

    <!-- 1. 聊天按钮（保留在Home页面） -->
    <div class="chat-button-wrapper">
      <div class="chat-button" @click="drawerVisible = true">
        <div class="custom-icon">
          <img src="@/assets/img/chat-icon.png" alt="聊天图标">
        </div>
      </div>
    </div>

    <!-- 2. 引入独立的ChatDrawer组件 -->
    <ChatDrawer
        :visible="drawerVisible"
        :chat-messages="chatMessages"
        @close="drawerVisible = false"
        @send-message="handleReceiveMessage"
    />
  </div>
</template>
<script>
// 1. 引入ChatDrawer组件
import ChatDrawer from '@/components/ChatDrawer.vue';
import { getDiseases } from '@/api/disease';

export default {
  name: 'Home',
  // 2. 注册组件
  components: {
    ChatDrawer
  },
  data() {
    return {
      // 原有数据保持不变
      bannerImages: [
        require('@/assets/img/banners/banner1.jpg'),
        require('@/assets/img/banners/banner2.jpg'),
        require('@/assets/img/banners/banner3.jpg')
      ],
      statistics: [
        { id: 1, number: '1000+', label: '免疫风险检测案例' },
        { id: 2, number: '50+', label: '免疫风险因素覆盖' },
        { id: 3, number: '90%+', label: '风险预测精准度' },
        { id: 4, number: '98%', label: '用户满意度' }
      ],
      hotDiseases: [],
      defaultImage: 'https://via.placeholder.com/300x200',
      baseUrl: process.env.VUE_APP_BASE_API || 'http://localhost:8085',

      // 3. 与ChatDrawer通信的数据
      drawerVisible: false, // 控制抽屉显示/隐藏（传给组件）
      chatMessages: [] // 聊天记录（双向同步，传给组件）
    }
  },
  methods: {
    // 原有方法保持不变
    goToProject(id) {
      this.$router.push(`/disease/${id}`)
    },
    handleImageError(e) {
      e.target.src = this.defaultImage;
    },
    async fetchDiseasesWithIdDivisibleByFour() {
      try {
        console.log('开始获取diseaseID能被四整除的疾病数据');
        const response = await getDiseases();
        const diseasesData = response.data;
        if (Array.isArray(diseasesData)) {
          this.hotDiseases = diseasesData.map(disease => ({
            diseaseImage: this.baseUrl + '/static/' + disease.diseaseImage,
            diseaseName: disease.diseaseName,
            description: disease.description,
            diseaseID: disease.diseaseID
          }));
        } else {
          console.error('响应数据不是数组格式:', diseasesData);
          this.$message.error('获取疾病数据失败：响应数据格式错误');
        }
      } catch (error) {
        console.error('获取疾病数据失败:', error);
        this.$message.error('获取疾病数据失败：' + (error.message || '未知错误'));
      }
    },

    // 4. 接收ChatDrawer组件传来的消息（用户发送/系统回复）
    handleReceiveMessage(newMsg) {
      // 将新消息添加到聊天记录中（同步给组件）
      this.chatMessages.push(newMsg);
    }
  },
  created() {
    // 原有初始化逻辑不变
    this.fetchDiseasesWithIdDivisibleByFour();
  }
}
</script>

<style scoped>
/* 仅保留Home页面原有样式，删除原抽屉相关样式 */
.home {
  padding: 0;
  width: 100%;
  overflow-x: hidden;/* 防止水平滚动条出现 */
  position: relative; /* 为固定聊天按钮提供定位上下文 */
}

/* 轮播容器包装 */
.welcome-carousel-container {
  position: relative;
  width: 100%;
  margin-top: 0;
  border-radius: 10px; /* 圆角 */
  overflow: hidden; /* 确保内部元素遵守圆角 */
}
/*轮播器的图片*/
.banner-image {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 添加半透明遮罩增强文字可读性 */
.banner-image::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1;
}

/* 固定在轮播图上的欢迎内容 */
.fixed-welcome-content {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
  z-index: 2;
}

.welcome-content {
  text-align: center;
  color: white;
  padding: 40px;
  max-width: 800px;
  margin: 0 auto;
  position: relative;
  z-index: 2;
  pointer-events: auto;
}

.welcome-content h1 {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 20px;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.5);
}

.welcome-content p {
  font-size: 20px;
  margin-bottom: 30px;
  opacity: 0.9;
  text-shadow: 0 1px 4px rgba(0, 0, 0, 0.5);
}

/*预约按钮*/
.reserve-btn {
  font-weight: bold;
  padding: 14px 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  pointer-events: auto;
}

.reserve-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.4);
}

/* 轮播指示器样式 - 使用安全的深度选择器 */
.welcome-carousel-container ::v-deep .el-carousel__indicators {
  position: absolute;
  bottom: 30px;
  left: 0;
  right: 0;
  z-index: 3;
}

.welcome-carousel-container ::v-deep .el-carousel__indicator {
  padding: 0 8px;
}

.welcome-carousel-container ::v-deep .el-carousel__button {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.6);
  transition: all 0.3s ease;
}

.welcome-carousel-container ::v-deep .el-carousel__indicator.is-active .el-carousel__button {
  width: 30px;
  border-radius: 5px;
  background-color: #fff;
}

.stats-section {
  background: white;
  padding: 40px 20px;
  margin-top: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
  box-sizing: border-box;
}

.stat-item {
  text-align: center;
  padding: 20px;
}

.stat-number {
  font-size: 36px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 10px;
}

.stat-label {
  font-size: 16px;
  color: #606266;
}

.section {
  max-width: 1200px;
  margin: 80px auto;
  padding: 0 20px;
  box-sizing: border-box;
}

/* 标题区域样式 */
.section-header {
  display: flex;
  flex-direction: column; /* 垂直布局 */
  align-items: center; /* 居中所有内容 */
  margin-bottom: 30px;
  position: relative; /* 为查看更多按钮提供定位上下文 */
}

.section h2 {
  font-size: 28px;
  color: #303133;
  margin: 0 auto 20px; /* 底部间距 */
  text-align: center;
  position: relative;
  padding-bottom: 15px;
  font-weight: 600; /* 中等字重 */
}

/* 标题下划线装饰 */
.section h2:after {
  content: '';
  display: block;
  position: absolute;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background: #409EFF;
  border-radius: 2px;
}

/* 查看更多按钮容器 */
.view-more-container {
  display: flex;
  justify-content: flex-end; /* 右侧对齐 */
  margin-top: 30px;
  margin-right: 10px; /* 右侧偏移量 */
}

/* 查看更多按钮样式 */
.view-more-btn {
  color: #26b5bf; /* 蓝绿色调 */
  font-size: 16px;
  font-weight: 500;
  padding: 8px 15px;
  transition: all 0.3s ease;
  position: relative;
}

.disease-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  border: none;
  border-radius: 8px;
  overflow: hidden;
}

.disease-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0,0,0,.1);
}

.disease-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.disease-info {
  padding: 20px;
}

.disease-info h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
}

.description {
  margin: 10px 0;
  font-size: 14px;
  color: #606266;
  height: 40px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.section-title {
  font-size: 32px;
  color: #303133;
  text-align: center;
  margin-bottom: 40px;
  position: relative;
}

.section-title:after {
  content: '';
  display: block;
  width: 50px;
  height: 3px;
  background: #409EFF;
  margin: 15px auto 0;
}

.booking-process {
  background: #f5f7fa;
  padding: 60px 40px;
  border-radius: 12px;
  margin: 80px auto;
}

.booking-process :deep(.el-step__title) {
  font-size: 16px;
  color: #303133;
}

.booking-process :deep(.el-step__description) {
  font-size: 14px;
  color: #606266;
}

/* 聊天按钮样式（保留，控制抽屉显示） */
.chat-button-wrapper {
  position: fixed;
  bottom: 30px;
  right: 30px;
  z-index: 999;
}

.chat-button {
  width: 65px;
  height: 65px;
  border-radius: 50%;
  color: white;
  box-shadow: 0 4px 15px rgba(64, 158, 255, 0.4);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.chat-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.1);
  transform: scale(0);
  border-radius: 50%;
  transition: transform 0.3s ease;
}

.chat-button:hover::before {
  transform: scale(2);
}

.chat-button:hover {
  transform: translateY(-5px) scale(1.05);
  box-shadow: 0 8px 25px rgba(64, 158, 255, 0.5);
}

.custom-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 3px;
}

.custom-icon img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .welcome-content h1 {
    font-size: 32px;
  }

  .welcome-content p {
    font-size: 16px;
  }

  .reserve-btn {
    padding: 10px 30px;
  }

  .welcome-carousel-container ::v-deep .el-carousel {
    height: 300px !important;
  }

  .stats-section {
    margin-top: 0;
    padding: 20px;
  }

  .stat-number {
    font-size: 28px;
  }

  .stat-label {
    font-size: 14px;
  }

  .section {
    margin: 40px auto;
    padding: 0 15px;
  }

  .section h2 {
    font-size: 24px;
  }

  .section-title {
    font-size: 24px;
    margin-bottom: 30px;
  }

  .booking-process {
    padding: 30px 15px;
    margin: 40px auto;
  }

  .booking-process ::v-deep .el-step__title {
    font-size: 14px;
  }

  .booking-process ::v-deep .el-step__description {
    font-size: 12px;
  }
}
</style>