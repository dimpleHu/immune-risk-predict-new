<template>
  <div class="disease-detail-container">
    <el-card class="detail-card">
      <div class="detail-header">
        <!-- 新增：返回箭头按钮 -->
        <el-button
            type="text"
            icon="el-icon-arrow-left"
            @click="$router.go(-1)"
            class="back-btn"
        >
        </el-button>
        <h2>{{ disease.diseaseName }}</h2>
        <div class="meta-info">
<!--          <span>疾病类型：{{ disease.diseaseType }}</span>-->
<!--          <span>发生概率：{{ disease.probability }}%</span>-->
              <span>患病率:30-70/10 万人</span>
              <span>中国每年新发患者为19万到20万，截至2024年1月，中国的SLE患者已超过100万。</span>
        </div>
      </div>
      <div class="detail-body">
        <!-- 替换原生img为el-image，实现大图预览 -->
        <div class="image-container">
          <el-image
              :src="disease.diseaseImage"
              :alt="disease.diseaseName"
              :preview-src-list="previewImageList"
              fit="contain"
              style="width: 100%; height: auto; border-radius: 8px"
          >
              <!-- 加载失败时的占位提示 -->
              <div slot="error" class="image-error">
                <i class="el-icon-picture-outline"></i>
                <p>图片加载失败</p>
              </div>
          </el-image>
        </div>
        <div class="info-section">
          <div class="description">
            <h3>疾病描述</h3>
            <p>{{ disease.description }}</p>
          </div>
          <div class="symptoms">
            <h3>症状表现</h3>
            <p>{{ disease.symptoms }}</p>
          </div>
          <div class="risk-factors">
            <h3>风险因素</h3>
            <p>{{ disease.riskfactors }}</p>
          </div>
          <div class="suggestions">
            <h3>建议措施</h3>
            <p>{{ disease.suggestions }}</p>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getDiseaseDetail } from '@/api/disease';

export default {
  name: 'DiseaseDetail',
  data() {
    return {
      disease: {},
      baseUrl: process.env.VUE_APP_BASE_API || 'http://localhost:8085',
      previewImageList: []  // 存储预览图片列表（支持单图/多图）
    };
  },
  created() {
    const diseaseId = this.$route.params.id; // 获取路由参数中的疾病 ID
    this.fetchDiseaseDetail(diseaseId);
  },
  methods: {
    fetchDiseaseDetail(id) {
      getDiseaseDetail(id).then(response => { // 调用获取详情接口，需根据实际接口调整
        if (response.code === 1 && response.data) {
          const mainImage = `${this.baseUrl}/static/${response.data.diseaseImage}`;

          // 新增：校验并处理otherImages字段
          const otherImagesRaw = response.data.otherImages;
          const otherImagesArr = typeof otherImagesRaw === 'string'
              ? otherImagesRaw.split(',')
              : Array.isArray(otherImagesRaw)
                  ? otherImagesRaw
                  : [];

          // 构建预览图列表
          this.previewImageList = [
            mainImage,
            ...otherImagesArr.map(img => `${this.baseUrl}/static/${img}`)
          ];

          // 赋值疾病数据
          this.disease = {
            ...response.data,
            diseaseImage: mainImage,
            diseaseType: response.data.diseaseType || '未知类型'
          };
        } else {
          this.$message.error('获取疾病详情失败');
        }
      }).catch(error => {
        console.error('获取疾病详情出错:', error);
        this.$message.error('获取疾病详情失败，请稍后再试');
      });
    }
  }
};
</script>

<style scoped>

.detail-header {
  margin-bottom: 20px;
  display: flex; /* 横向布局 */
  align-items: center; /* 垂直居中 */
  gap: 15px; /* 元素间间距 */
}

.back-btn {
  color: #000000; /* 箭头颜色 */
  font-size: 16px;
  padding: 0; /* 清除默认内边距 */
  font-weight: bold; /* 加粗文字 */
}
.back-btn:hover {
  color: #66b1ff; /* hover加深颜色 */
}

.disease-detail-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.detail-card {
  background-color: #fff;
  border-radius: 12px;
  padding: 20px;
}

.detail-header {
  margin-bottom: 20px;
}

.detail-header h2 {
  margin: 0;
  font-size: 28px;
  color: #303133;
}

.meta-info {
  margin-top: 10px;
  display: flex;
  gap: 20px;
  color: #606266;
}

.detail-body {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.image-container {
  flex: 1;
  min-width: 300px;
}

.image-container img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.info-section {
  flex: 2;
  min-width: 500px;
}

.info-section > div {
  margin-bottom: 20px;
}

.info-section h3 {
  margin: 0 0 10px 0;
  font-size: 20px;
  color: #303133;
}

.info-section p {
  color: #606266;
  line-height: 1.6;
}

/* 图片加载失败样式：居中显示图标和文字 */
.image-error {
  width: 100%;
  height: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #f5f5f5;
  border-radius: 8px;
}

.image-error.el-icon-picture-outline {
  font-size: 32px;
  color: #999;
  margin-bottom: 8px;
}

.image-error p {
  color: #999;
  margin: 0;
}
</style>