<template>
  <div class="prediction-detail-container">
    <el-card class="detail-card" shadow="hover">
      <div slot="header" class="card-header">
        <!-- 新增：返回箭头按钮 -->
        <el-button
            type="text"
            icon="el-icon-arrow-left"
            @click="$router.go(-1)"
            class="back-btn"
        >
        </el-button>
        <span>预测详情</span>
      </div>
      <div class="detail-info" v-if="predictionDetail">
        <div class="info-item">
          <label>预测ID：</label>
          <span>{{ predictionDetail.predictionID }}</span>
        </div>
        <div class="info-item">
          <label>用户ID：</label>
          <span>{{ predictionDetail.userID }}</span>
        </div>
        <div class="info-item">
          <label>预测时间：</label>
          <span>{{ formatDate(predictionDetail.predictionTime) }}</span>
        </div>
        <div class="info-item">
          <label>年龄：</label>
          <span>{{ predictionDetail.age }}</span>
        </div>
        <div class="info-item">
          <label>性别：</label>
          <span>{{ predictionDetail.sex === 0 ? '男' : '女' }}</span>
        </div>
        <div class="info-item">
          <label>疾病：</label>
          <span>{{this.diseaseNames}}</span>
        </div>
        <div class="info-item">
          <label>症状：</label>
          <span>{{ predictionDetail.symptoms }}</span>
        </div>
      </div>
      <div v-else class="empty-tip">
        暂无详情数据
      </div>
    </el-card>
  </div>
</template>

<script>
import { getPredictionById } from '@/api/prediction';
import { getDiseaseDetail } from '@/api/disease';
import { Message } from 'element-ui';

export default {
  name: 'HistoryDetail',
  data() {
    return {
      predictionDetail: null,
      diseaseNames: '' // 存储疾病名称
    };
  },
  created() {
    this.fetchPredictionDetail();
  },
  methods: {
    async fetchPredictionDetail() {
      const predictionID = this.$route.params.id;
      console.log('详情页接收的ID：', predictionID);
      try {
        const res = await getPredictionById(predictionID);
        if (res.code === 1 && res.data) {
          this.predictionDetail = res.data;
          // 获取疾病名称
          await this.fetchDiseaseNames();
        } else {
          Message.error(res.msg || '获取详情失败');
        }
      } catch (error) {
        Message.error('网络异常，获取详情失败');
      }
    },
    // 获取疾病名称
    async fetchDiseaseNames() {
      if (!this.predictionDetail || !this.predictionDetail.diseasesID) {
        this.diseaseNames = '无疾病信息';
        return;
      }

      try {
        // 分割疾病ID字符串
        const diseaseIds = this.predictionDetail.diseasesID.split(',');

        // 查询每个疾病ID对应的名称
        const names = [];
        for (const id of diseaseIds) {
          if (id.trim()) {
            const res = await getDiseaseDetail(id.trim());
            if (res.code === 1 && res.data) {
              names.push(res.data.diseaseName);
            }
          }
        }

        this.diseaseNames = names.join('、') || '未知疾病';
        console.log('疾病名字:', this.diseaseNames);
      } catch (error) {
        console.error('获取疾病名称失败:', error);
        this.diseaseNames = '获取疾病名称失败';
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return '';
      const date = new Date(dateStr);
      return isNaN(date.getTime())
          ? dateStr
          : `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;
    }
  }
}
</script>

<style scoped>
.prediction-detail-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.detail-card {
  border-radius: 8px;
}

.card-header {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.detail-info {
  margin-top: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  font-size: 14px;
}

.info-item label {
  width: 80px;
  color: #606266;
}

.info-item span {
  color: #333;
}

.empty-tip {
  text-align: center;
  color: #999;
  padding: 20px 0;
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
</style>