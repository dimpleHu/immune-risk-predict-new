<template>
  <div class="diseases-container">
    <div class="diseases-list">
      <el-row :gutter="20">
        <el-col :span="24" v-for="disease in diseaseList" :key="disease.diseaseID">
          <el-card class="disease-card" :body-style="{ padding: '0px' }">
            <div class="disease-content">
              <div class="disease-image-container">
                <img :src="disease.diseaseImage" class="disease-image" :alt="disease.diseaseName">
              </div>
              <div class="disease-info">
                <h3>{{ disease.diseaseName }}</h3>
                <div class="disease-meta">
                  <span class="disease-type">{{ disease.diseaseType }}</span>
                </div>
                <p class="description">{{ disease.description }}</p>
                <div class="action-area">
                  <el-button type="primary" size="small" @click="handleViewDetails(disease)">查看详情</el-button>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getDiseases } from '@/api/disease';

export default {
  name: 'DiseasePage',
  data() {
    return {
      diseaseList: [],
      baseUrl: process.env.VUE_APP_BASE_API || 'http://localhost:8085'
    };
  },
  methods: {
    handleViewDetails(disease) {
      this.$router.push({ path: `/disease/${disease.diseaseID}` });
    },
    getList() {
      getDiseases().then(response => {
        if (response.code === 1 && response.data) {
          const rawData = response.data.items || response.data;
          this.diseaseList = rawData.map(disease => ({
            ...disease,
            diseaseImage: `${this.baseUrl}/static/${disease.diseaseImage}`
          }));
        } else {
          this.$message.error(response.msg || '获取疾病列表失败');
          this.diseaseList = [];
        }
      }).catch(error => {
        console.error('获取疾病列表失败:', error);
        this.$message.error('获取疾病列表失败');
        this.diseaseList = [];
      });
    }
  },
  created() {
    this.getList();
  }
};
</script>

<style scoped>
.diseases-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.diseases-list {
  margin-bottom: 20px;
}

.disease-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
}

.disease-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.disease-content {
  display: flex;
  height: 240px;
}

.disease-image-container {
  width: 360px;
  flex-shrink: 0;
  overflow: hidden;
}

.disease-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.disease-card:hover.disease-image {
  transform: scale(1.05);
}

.disease-info {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.disease-info h3 {
  margin: 0;
  font-size: 24px;
  color: #303133;
  font-weight: bold;
}

.disease-meta {
  margin: 10px 0;
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.disease-type {
  background-color: #e8f4fd;
  color: #1890ff;
  padding: 3px 10px;
  border-radius: 4px;
  font-size: 14px;
}

.description {
  margin: 15px 0;
  font-size: 16px;
  color: #606266;
  line-height: 1.6;
  flex-grow: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.action-area {
  display: flex;
  justify-content: flex-end;
  margin-top: auto;
  padding-top: 15px;
  border-top: 1px solid #ebeef5;
}

.action-area.el-button {
  padding: 12px 25px;
  font-size: 14px;
  border-radius: 8px;
}

@media (max-width: 992px) {
  .disease-content {
    flex-direction: column;
    height: auto;
  }
  .disease-image-container {
    width: 100%;
    height: 200px;
  }
}
</style>