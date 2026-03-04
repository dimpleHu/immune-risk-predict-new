<!--<template>-->
<!--  <div class="dashboard-container">-->
<!--    &lt;!&ndash; 患病病风险趋势曲线图 &ndash;&gt;-->
<!--    <div class="chart-card">-->
<!--      <div class="chart-header">-->
<!--        <h2 class="chart-title">患病风险趋势</h2>-->
<!--      </div>-->
<!--      <div class="chart-container">-->
<!--        <canvas ref="riskChart"></canvas>-->
<!--      </div>-->
<!--    </div>-->

<!--    &lt;!&ndash; 历史预测记录 &ndash;&gt;-->
<!--    <div class="history-records">-->
<!--      <h3 class="record-title">历史预测记录</h3>-->
<!--      <el-row :gutter="20">-->
<!--        <template v-if="predictionList.length > 0">-->
<!--          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12"-->
<!--                  v-for="prediction in predictionList"-->
<!--                  :key="prediction.predictionID"-->
<!--                  class="order-card-col">-->
<!--            <el-card class="order-card" shadow="hover">-->
<!--              <div class="order-header">-->
<!--                <div class="project-name">预测ID: {{ prediction.predictionID}}</div>-->
<!--              </div>-->
<!--              <div class="info-row">-->
<!--                <i class="el-icon-date"></i>-->
<!--                <span class="info-value">预测时间: {{ formatDate(prediction.predictionTime) }} </span>-->
<!--              </div>-->
<!--              <div class="info-row">-->
<!--                <i class="el-icon-document"></i>-->
<!--                <span class="info-value">症状: {{ prediction.symptoms }} </span>-->
<!--              </div>-->
<!--              <div class="info-row price-row">-->
<!--                <el-button-->
<!--                    type="primary"-->
<!--                    size="small"-->
<!--                    @click="handleViewDetail(prediction.predictionID)"-->
<!--                    class="detail-btn"-->
<!--                >-->
<!--                  查看详情-->
<!--                </el-button>-->
<!--              </div>-->
<!--            </el-card>-->
<!--          </el-col>-->
<!--        </template>-->
<!--        <template v-else>-->
<!--          <div style="text-align: center; color: #999;">暂无历史预测记录</div>-->
<!--        </template>-->
<!--      </el-row>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--&lt;!&ndash;<script>&ndash;&gt;-->
<!--&lt;!&ndash;import { Chart } from 'chart.js';&ndash;&gt;-->
<!--&lt;!&ndash;import { CategoryScale, LinearScale, LineController, PointElement, LineElement } from 'chart.js';&ndash;&gt;-->
<!--&lt;!&ndash;import { Message } from 'element-ui'; // Vue2 Element UI 的消息提示&ndash;&gt;-->

<!--&lt;!&ndash;// 注册 Chart.js的必要组件&ndash;&gt;-->
<!--&lt;!&ndash;Chart.register(CategoryScale, LinearScale, LineController, PointElement, LineElement);&ndash;&gt;-->

<!--&lt;!&ndash;export default {&ndash;&gt;-->
<!--&lt;!&ndash;  name: 'DiseaseRisk',&ndash;&gt;-->
<!--&lt;!&ndash;  data() {&ndash;&gt;-->
<!--&lt;!&ndash;    return {&ndash;&gt;-->
<!--&lt;!&ndash;      predictionList: [], // 历史预测记录&ndash;&gt;-->
<!--&lt;!&ndash;      chart: null,         // Chart.js 实例&ndash;&gt;-->
<!--&lt;!&ndash;      userId: null,        // 当前登录用户 ID&ndash;&gt;-->
<!--&lt;!&ndash;    };&ndash;&gt;-->
<!--&lt;!&ndash;  },&ndash;&gt;-->
<!--&lt;!&ndash;  created() {&ndash;&gt;-->
<!--&lt;!&ndash;    this.getUserInfo();&ndash;&gt;-->
<!--&lt;!&ndash;    // 初始化数据&ndash;&gt;-->
<!--&lt;!&ndash;    this.fetchPredictionData();&ndash;&gt;-->
<!--&lt;!&ndash;  },&ndash;&gt;-->
<!--&lt;!&ndash;  mounted() {&ndash;&gt;-->
<!--&lt;!&ndash;    this.initChart();&ndash;&gt;-->
<!--&lt;!&ndash;  },&ndash;&gt;-->
<!--&lt;!&ndash;  beforeDestroy() {&ndash;&gt;-->
<!--&lt;!&ndash;    if (this.chart) {&ndash;&gt;-->
<!--&lt;!&ndash;      this.chart.destroy();&ndash;&gt;-->
<!--&lt;!&ndash;    }&ndash;&gt;-->
<!--&lt;!&ndash;  },&ndash;&gt;-->
<!--&lt;!&ndash;  methods: {&ndash;&gt;-->
<!--&lt;!&ndash;    // 从 localStorageStorage获取用户信息&ndash;&gt;-->
<!--&lt;!&ndash;    getUserInfo() {&ndash;&gt;-->
<!--&lt;!&ndash;      const userInfoStr = localStorage.getItem('userInfo');&ndash;&gt;-->
<!--&lt;!&ndash;      if (!userInfoStr) {&ndash;&gt;-->
<!--&lt;!&ndash;        Message.warning('请先登录后再查看历史记录');&ndash;&gt;-->
<!--&lt;!&ndash;        this.$router.push('/login');&ndash;&gt;-->
<!--&lt;!&ndash;        return;&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->

<!--&lt;!&ndash;      try {&ndash;&gt;-->
<!--&lt;!&ndash;        const userInfo = JSON.parse(userInfoStr);&ndash;&gt;-->
<!--&lt;!&ndash;        // 假设用户信息中用户ID字段键是 userID，根据实际情况修改&ndash;&gt;-->
<!--&lt;!&ndash;        this.userId = userInfo.userID;&ndash;&gt;-->

<!--&lt;!&ndash;        if (!this.userId) {&ndash;&gt;-->
<!--&lt;!&ndash;          Message.error('用户信息不完整，无法获取历史记录');&ndash;&gt;-->
<!--&lt;!&ndash;          return;&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->

<!--&lt;!&ndash;        // 获取到用户ID后再获取预测记录&ndash;&gt;-->
<!--&lt;!&ndash;        this.fetchPredictionData();&ndash;&gt;-->
<!--&lt;!&ndash;      } catch (error) {&ndash;&gt;-->
<!--&lt;!&ndash;        Message.error('解析用户信息解析析失败，请重新登录');&ndash;&gt;-->
<!--&lt;!&ndash;        console.error('解析析信息解析失败:', error);&ndash;&gt;-->
<!--&lt;!&ndash;        this.$router.push('/login');&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    // 调用后端接口获取数据&ndash;&gt;-->
<!--&lt;!&ndash;    async fetchPredictionData() {&ndash;&gt;-->
<!--&lt;!&ndash;      try {&ndash;&gt;-->
<!--&lt;!&ndash;        const params = {&ndash;&gt;-->
<!--&lt;!&ndash;          page: 1,&ndash;&gt;-->
<!--&lt;!&ndash;          limit: 100,&ndash;&gt;-->
<!--&lt;!&ndash;          userId: this.userId&ndash;&gt;-->
<!--&lt;!&ndash;        };&ndash;&gt;-->
<!--&lt;!&ndash;        console.log('请求参数：', params); // 打印参数，查看 userId 是否有值&ndash;&gt;-->

<!--&lt;!&ndash;        const res = await this.$axios.get('/predictions/userlist', {&ndash;&gt;-->
<!--&lt;!&ndash;          params&ndash;&gt;-->
<!--&lt;!&ndash;        });&ndash;&gt;-->

<!--&lt;!&ndash;        if (res.data.code === 1 && res.data.data) {&ndash;&gt;-->
<!--&lt;!&ndash;          this.predictionList = res.data.data.items;&ndash;&gt;-->
<!--&lt;!&ndash;          this.updateChartData(res.data.data.items);&ndash;&gt;-->
<!--&lt;!&ndash;        } else {&ndash;&gt;-->
<!--&lt;!&ndash;          Message.error('获取数据失败：' + (res.data.msg || '未知错误'));&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->
<!--&lt;!&ndash;      } catch (error) {&ndash;&gt;-->
<!--&lt;!&ndash;        Message.error('网络请求异常：' + error.message);&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    // 转换接口数据为图表可用格式&ndash;&gt;-->
<!--&lt;!&ndash;    updateChartData(records) {&ndash;&gt;-->
<!--&lt;!&ndash;      const diseaseMap = {&ndash;&gt;-->
<!--&lt;!&ndash;        '系统性红斑狼疮': [],&ndash;&gt;-->
<!--&lt;!&ndash;        '类关节炎': [],&ndash;&gt;-->
<!--&lt;!&ndash;        '银屑病': []&ndash;&gt;-->
<!--&lt;!&ndash;      };&ndash;&gt;-->
<!--&lt;!&ndash;      const labels = [];&ndash;&gt;-->

<!--&lt;!&ndash;      records.forEach(record => {&ndash;&gt;-->
<!--&lt;!&ndash;        const time = new Date(record.predictionTime).toLocaleDateString();&ndash;&gt;-->
<!--&lt;!&ndash;        if (!labels.includes(time)) labels.push(time);&ndash;&gt;-->

<!--&lt;!&ndash;        // 根据实际业务逻辑映射疾病ID和风险值&ndash;&gt;-->
<!--&lt;!&ndash;        switch (record.diseasesID) {&ndash;&gt;-->
<!--&lt;!&ndash;          case '1':&ndash;&gt;-->
<!--&lt;!&ndash;            diseaseMap['系统性红斑狼疮'].push(Math.random() * 100);&ndash;&gt;-->
<!--&lt;!&ndash;            break;&ndash;&gt;-->
<!--&lt;!&ndash;          case '2':&ndash;&gt;-->
<!--&lt;!&ndash;            diseaseMap['类关节炎'].push(Math.random() * 100);&ndash;&gt;-->
<!--&lt;!&ndash;            break;&ndash;&gt;-->
<!--&lt;!&ndash;          case '3':&ndash;&gt;-->
<!--&lt;!&ndash;            diseaseMap['银屑病'].push(Math.random() * 100);&ndash;&gt;-->
<!--&lt;!&ndash;            break;&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->
<!--&lt;!&ndash;      });&ndash;&gt;-->

<!--&lt;!&ndash;      this.chart.data.labels = labels;&ndash;&gt;-->
<!--&lt;!&ndash;      this.chart.data.datasets = [&ndash;&gt;-->
<!--&lt;!&ndash;        {&ndash;&gt;-->
<!--&lt;!&ndash;          label: '系统性红斑狼疮',&ndash;&gt;-->
<!--&lt;!&ndash;          data: diseaseMap['系统性红斑狼疮'],&ndash;&gt;-->
<!--&lt;!&ndash;          borderColor: '#3B82F6',&ndash;&gt;-->
<!--&lt;!&ndash;          backgroundColor: 'rgba(59, 130, 246, 0.1)',&ndash;&gt;-->
<!--&lt;!&ndash;          tension: 0.4,&ndash;&gt;-->
<!--&lt;!&ndash;          fill: true&ndash;&gt;-->
<!--&lt;!&ndash;        },&ndash;&gt;-->
<!--&lt;!&ndash;        {&ndash;&gt;-->
<!--&lt;!&ndash;          label: '类关节炎',&ndash;&gt;-->
<!--&lt;!&ndash;          data: diseaseMap['类关节炎'],&ndash;&gt;-->
<!--&lt;!&ndash;          borderColor: '#6366F1',&ndash;&gt;-->
<!--&lt;!&ndash;          backgroundColor: 'rgba(99, 102, 241, 0.1)',&ndash;&gt;-->
<!--&lt;!&ndash;          tension: 0.4,&ndash;&gt;-->
<!--&lt;!&ndash;          fill: true&ndash;&gt;-->
<!--&lt;!&ndash;        },&ndash;&gt;-->
<!--&lt;!&ndash;        {&ndash;&gt;-->
<!--&lt;!&ndash;          label: '银屑病',&ndash;&gt;-->
<!--&lt;!&ndash;          data: diseaseMap['银屑病'],&ndash;&gt;-->
<!--&lt;!&ndash;          borderColor: '#EC4899',&ndash;&gt;-->
<!--&lt;!&ndash;          backgroundColor: 'rgba(236, 72, 153, 0.1)',&ndash;&gt;-->
<!--&lt;!&ndash;          tension: 0.4,&ndash;&gt;-->
<!--&lt;!&ndash;          fill: true&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->
<!--&lt;!&ndash;      ];&ndash;&gt;-->
<!--&lt;!&ndash;      this.chart.update();&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    // 初始化图表&ndash;&gt;-->
<!--&lt;!&ndash;    initChart() {&ndash;&gt;-->
<!--&lt;!&ndash;      const ctx = this.$refs.riskChart.getContext('2d');&ndash;&gt;-->
<!--&lt;!&ndash;      this.chart = new Chart(ctx, {&ndash;&gt;-->
<!--&lt;!&ndash;        type: 'line',&ndash;&gt;-->
<!--&lt;!&ndash;        data: {&ndash;&gt;-->
<!--&lt;!&ndash;          labels: [],&ndash;&gt;-->
<!--&lt;!&ndash;          datasets: []&ndash;&gt;-->
<!--&lt;!&ndash;        },&ndash;&gt;-->
<!--&lt;!&ndash;        options: {&ndash;&gt;-->
<!--&lt;!&ndash;          responsive: true,&ndash;&gt;-->
<!--&lt;!&ndash;          maintainAspectRatio: false,&ndash;&gt;-->
<!--&lt;!&ndash;          interaction: {&ndash;&gt;-->
<!--&lt;!&ndash;            mode: 'index',&ndash;&gt;-->
<!--&lt;!&ndash;            intersect: false,&ndash;&gt;-->
<!--&lt;!&ndash;          },&ndash;&gt;-->
<!--&lt;!&ndash;          plugins: {&ndash;&gt;-->
<!--&lt;!&ndash;            legend: {&ndash;&gt;-->
<!--&lt;!&ndash;              position: 'top',&ndash;&gt;-->
<!--&lt;!&ndash;              labels: {&ndash;&gt;-->
<!--&lt;!&ndash;                usePointStyle: true,&ndash;&gt;-->
<!--&lt;!&ndash;                padding: 20,&ndash;&gt;-->
<!--&lt;!&ndash;                font: {&ndash;&gt;-->
<!--&lt;!&ndash;                  family: 'Inter',&ndash;&gt;-->
<!--&lt;!&ndash;                  size: 12&ndash;&gt;-->
<!--&lt;!&ndash;                }&ndash;&gt;-->
<!--&lt;!&ndash;              }&ndash;&gt;-->
<!--&lt;!&ndash;            },&ndash;&gt;-->
<!--&lt;!&ndash;            tooltip: {&ndash;&gt;-->
<!--&lt;!&ndash;              backgroundColor: 'rgba(255, 255, 255, 0.95)',&ndash;&gt;-->
<!--&lt;!&ndash;              titleColor: '#1E293B',&ndash;&gt;-->
<!--&lt;!&ndash;              bodyColor: '#64748B',&ndash;&gt;-->
<!--&lt;!&ndash;              borderColor: '#E2E8F0',&ndash;&gt;-->
<!--&lt;!&ndash;              borderWidth: 1,&ndash;&gt;-->
<!--&lt;!&ndash;              padding: 12,&ndash;&gt;-->
<!--&lt;!&ndash;              boxPadding: 6,&ndash;&gt;-->
<!--&lt;!&ndash;              usePointStyle: true,&ndash;&gt;-->
<!--&lt;!&ndash;              callbacks: {&ndash;&gt;-->
<!--&lt;!&ndash;                label: function(context) {&ndash;&gt;-->
<!--&lt;!&ndash;                  let label = context.dataset.label || '';&ndash;&gt;-->
<!--&lt;!&ndash;                  if (label) {&ndash;&gt;-->
<!--&lt;!&ndash;                    label += ': ';&ndash;&gt;-->
<!--&lt;!&ndash;                  }&ndash;&gt;-->
<!--&lt;!&ndash;                  if (context.parsed.y !== null) {&ndash;&gt;-->
<!--&lt;!&ndash;                    label += context.parsed.y.toFixed(2) + '%';&ndash;&gt;-->
<!--&lt;!&ndash;                  }&ndash;&gt;-->
<!--&lt;!&ndash;                  return label;&ndash;&gt;-->
<!--&lt;!&ndash;                }&ndash;&gt;-->
<!--&lt;!&ndash;              }&ndash;&gt;-->
<!--&lt;!&ndash;            }&ndash;&gt;-->
<!--&lt;!&ndash;          },&ndash;&gt;-->
<!--&lt;!&ndash;          scales: {&ndash;&gt;-->
<!--&lt;!&ndash;            x: {&ndash;&gt;-->
<!--&lt;!&ndash;              type: 'category',&ndash;&gt;-->
<!--&lt;!&ndash;              grid: {&ndash;&gt;-->
<!--&lt;!&ndash;                display: false&ndash;&gt;-->
<!--&lt;!&ndash;              }&ndash;&gt;-->
<!--&lt;!&ndash;            },&ndash;&gt;-->
<!--&lt;!&ndash;            y: {&ndash;&gt;-->
<!--&lt;!&ndash;              type: 'linear',&ndash;&gt;-->
<!--&lt;!&ndash;              beginAtZero: true,&ndash;&gt;-->
<!--&lt;!&ndash;              max: 100,&ndash;&gt;-->
<!--&lt;!&ndash;              ticks: {&ndash;&gt;-->
<!--&lt;!&ndash;                callback: function(value) {&ndash;&gt;-->
<!--&lt;!&ndash;                  return value + '%';&ndash;&gt;-->
<!--&lt;!&ndash;                }&ndash;&gt;-->
<!--&lt;!&ndash;              }&ndash;&gt;-->
<!--&lt;!&ndash;            }&ndash;&gt;-->
<!--&lt;!&ndash;          }&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->
<!--&lt;!&ndash;      });&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    // 查看详情&ndash;&gt;-->
<!--&lt;!&ndash;    handleViewDetail(predictionID) {&ndash;&gt;-->
<!--&lt;!&ndash;      this.$router.push({ path: '/prediction/detail', query: { id: predictionID } });&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    // 日期格式化&ndash;&gt;-->
<!--&lt;!&ndash;    // formatDate(dateStr) {&ndash;&gt;-->
<!--&lt;!&ndash;    //   const date = new Date(dateStr);&ndash;&gt;-->
<!--&lt;!&ndash;    //   return (&ndash;&gt;-->
<!--&lt;!&ndash;    //       date.getFullYear() + '-' +&ndash;&gt;-->
<!--&lt;!&ndash;    //       (date.getMonth() + 1).toString().padStart(2, '0') + '-' +&ndash;&gt;-->
<!--&lt;!&ndash;    //       date.getDate().toString().padStart(2, '0')&ndash;&gt;-->
<!--&lt;!&ndash;    //   );&ndash;&gt;-->
<!--&lt;!&ndash;      formatDate(dateStr) {&ndash;&gt;-->
<!--&lt;!&ndash;        if (!dateStr) return '';&ndash;&gt;-->
<!--&lt;!&ndash;        const date = new Date(dateStr);&ndash;&gt;-->
<!--&lt;!&ndash;        if (isNaN(date.getTime())) { // 解析失败&ndash;&gt;-->
<!--&lt;!&ndash;          return dateStr;&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->
<!--&lt;!&ndash;        return (&ndash;&gt;-->
<!--&lt;!&ndash;            date.getFullYear() + '-' +&ndash;&gt;-->
<!--&lt;!&ndash;            (date.getMonth() + 1).toString().padStart(2, '0') + '-' +&ndash;&gt;-->
<!--&lt;!&ndash;            date.getDate().toString().padStart(2, '0')&ndash;&gt;-->
<!--&lt;!&ndash;        );&ndash;&gt;-->
<!--&lt;!&ndash;     }&ndash;&gt;-->
<!--&lt;!&ndash;  }&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->
<!--&lt;!&ndash;</script>&ndash;&gt;-->

<!--<script>-->
<!--import { Chart } from 'chart.js';-->
<!--import { CategoryScale, LinearScale, LineController, PointElement, LineElement } from 'chart.js';-->
<!--import { Message } from 'element-ui';-->

<!--Chart.register(CategoryScale, LinearScale, LineController, PointElement, LineElement);-->

<!--export default {-->
<!--  name: 'DiseaseRisk',-->
<!--  data() {-->
<!--    return {-->
<!--      predictionList: [],-->
<!--      chart: null,-->
<!--      userId: null,-->
<!--    };-->
<!--  },-->
<!--  created() {-->
<!--    this.getUserInfo();-->
<!--  },-->
<!--  mounted() {-->
<!--    this.initChart();-->
<!--  },-->
<!--  beforeDestroy() {-->
<!--    if (this.chart) {-->
<!--      this.chart.destroy();-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    getUserInfo() {-->
<!--      const userInfoStr = localStorage.getItem('userInfo');-->
<!--      if (!userInfoStr) {-->
<!--        Message.warning('请先登录');-->
<!--        this.$router.push('/login');-->
<!--        return;-->
<!--      }-->
<!--      try {-->
<!--        const userInfo = JSON.parse(userInfoStr);-->
<!--        this.userId = userInfo.userID;-->
<!--        if (this.userId) {-->
<!--          this.fetchPredictionData();-->
<!--        } else {-->
<!--          Message.error('用户信息异常');-->
<!--        }-->
<!--      } catch (error) {-->
<!--        Message.error('解析用户信息失败');-->
<!--        this.$router.push('/login');-->
<!--      }-->
<!--    },-->

<!--    async fetchPredictionData() {-->
<!--      try {-->
<!--        const res = await this.$axios.get('/predictions/userlist', {-->
<!--          params: {-->
<!--            page: 1,-->
<!--            limit: 100,-->
<!--            userId: this.userId-->
<!--          }-->
<!--        });-->
<!--        if (res.data.code === 1 && res.data.data) {-->
<!--          this.predictionList = res.data.data.items;-->
<!--          this.updateChartData(res.data.data.items);-->
<!--        } else {-->
<!--          Message.error('获取数据失败');-->
<!--        }-->
<!--      } catch (error) {-->
<!--        Message.error('网络异常');-->
<!--      }-->
<!--    },-->

<!--    updateChartData(records) {-->
<!--      const diseaseIdToName = {-->
<!--        '1': '系统性红斑狼疮',-->
<!--        '2': '系统性红斑狼疮',-->
<!--        '3': '系统性红斑狼疮',-->
<!--        '4': '系统性红斑狼疮',-->
<!--        '5': '类风湿性关节炎',-->
<!--        '6': '类风湿性关节炎',-->
<!--        '7': '类风湿性关节炎',-->
<!--        '8': '类风湿性关节炎',-->
<!--        '9': '1型糖尿病',-->
<!--        '10': '1型糖尿病',-->
<!--        '11': '1型糖尿病',-->
<!--        '12': '1型糖尿病',-->
<!--        '13': '银屑病',-->
<!--        '14': '银屑病',-->
<!--        '19': '银屑病',-->
<!--        '20': '银屑病',-->
<!--      };-->

<!--      const diseaseRiskMap = {};-->
<!--      const dateSet = new Set();-->

<!--      records.forEach(record => {-->
<!--        const time = this.formatDate(record.predictionTime);-->
<!--        dateSet.add(time);-->

<!--        const diseaseIds = record.diseasesID.split(',');-->
<!--        const probabilities = record.diseaseProbabilities?.split(',') || [];-->

<!--        diseaseIds.forEach((diseaseId, index) => {-->
<!--          const diseaseName = diseaseIdToName[diseaseId] || `未知(${diseaseId})`;-->
<!--          const probability = +probabilities[index] || 0;-->

<!--          if (!diseaseRiskMap[diseaseName]) {-->
<!--            diseaseRiskMap[diseaseName] = {};-->
<!--          }-->
<!--          diseaseRiskMap[diseaseName][time] = probability;-->
<!--        });-->
<!--      });-->

<!--      const labels = Array.from(dateSet).sort();-->
<!--      const datasets = Object.keys(diseaseRiskMap).map(diseaseName => ({-->
<!--        label: diseaseName,-->
<!--        data: labels.map(date => diseaseRiskMap[diseaseName][date] || 0),-->
<!--        borderColor: this.getRandomColor(),-->
<!--        backgroundColor: `rgba(${this.hexToRgb(this.getRandomColor())}, 0.1)`,-->
<!--        tension: 0.4,-->
<!--        fill: true-->
<!--      }));-->

<!--      if (this.chart) {-->
<!--        this.chart.data.labels = labels;-->
<!--        this.chart.data.datasets = datasets;-->
<!--        this.chart.update();-->
<!--      }-->
<!--    },-->

<!--    initChart() {-->
<!--      const ctx = this.$refs.riskChart.getContext('2d');-->
<!--      this.chart = new Chart(ctx, {-->
<!--        type: 'line',-->
<!--        data: { labels: [], datasets: [] },-->
<!--        options: {-->
<!--          responsive: true,-->
<!--          maintainAspectRatio: false,-->
<!--          interaction: { mode: 'index', intersect: false },-->
<!--          plugins: {-->
<!--            legend: { position: 'top' },-->
<!--            tooltip: {-->
<!--              callbacks: {-->
<!--                label: (context) => `${context.dataset.label}: ${context.parsed.y}%`-->
<!--              }-->
<!--            }-->
<!--          },-->
<!--          scales: {-->
<!--            x: { type: 'category', grid: { display: false } },-->
<!--            y: {-->
<!--              type: 'linear',-->
<!--              beginAtZero: true,-->
<!--              max: 100,-->
<!--              ticks: { callback: v => `${v}%` }-->
<!--            }-->
<!--          }-->
<!--        }-->
<!--      });-->
<!--    },-->

<!--    handleViewDetail(predictionID) {-->
<!--      this.$router.push({ path: '/prediction/detail', query: { id: predictionID } });-->
<!--    },-->

<!--    formatDate(dateStr) {-->
<!--      if (!dateStr) return '';-->
<!--      const date = new Date(dateStr);-->
<!--      return isNaN(date.getTime())-->
<!--          ? dateStr-->
<!--          : `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;-->
<!--    },-->

<!--    getRandomColor() {-->
<!--      return `#${Math.floor(Math.random()*16777215).toString(16)}`;-->
<!--    },-->

<!--    hexToRgb(hex) {-->
<!--      const r = parseInt(hex.slice(1, 3), 16);-->
<!--      const g = parseInt(hex.slice(3, 5), 16);-->
<!--      const b = parseInt(hex.slice(5, 7), 16);-->
<!--      return `${r}, ${g}, ${b}`;-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.dashboard-container {-->
<!--  padding: 20px;-->
<!--  max-width: 1200px;-->
<!--  margin: 0 auto;-->
<!--}-->

<!--.chart-card {-->
<!--  background-color: white;-->
<!--  border-radius: 12px;-->
<!--  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);-->
<!--  padding: 20px;-->
<!--  margin-bottom: 20px;-->
<!--}-->

<!--.chart-header {-->
<!--  margin-bottom: 20px;-->
<!--}-->

<!--.chart-title {-->
<!--  font-size: 18px;-->
<!--  font-weight: 600;-->
<!--  color: #1E293B;-->
<!--}-->

<!--.chart-container {-->
<!--  height: 350px;-->
<!--}-->

<!--.history-records {-->
<!--  background-color: white;-->
<!--  border-radius: 12px;-->
<!--  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);-->
<!--  padding: 20px;-->
<!--}-->

<!--.record-title {-->
<!--  font-size: 16px;-->
<!--  font-weight: 600;-->
<!--  color: #1E293B;-->
<!--  margin-bottom: 16px;-->
<!--}-->

<!--.order-card-col {-->
<!--  margin-bottom: 20px;-->
<!--  padding: 0 10px;-->
<!--}-->

<!--.order-card {-->
<!--  border-radius: 8px;-->
<!--  transition: transform 0.3s;-->
<!--}-->

<!--.order-card:hover {-->
<!--  transform: translateY(-5px);-->
<!--  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);-->
<!--}-->

<!--.order-header {-->
<!--  margin-bottom: 15px;-->
<!--  padding-bottom: 10px;-->
<!--  border-bottom: 1px solid #eee;-->
<!--}-->

<!--.project-name {-->
<!--  font-weight: 600;-->
<!--  font-size: 16px;-->
<!--  color: #333;-->
<!--}-->

<!--.info-row {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  margin-bottom: 12px;-->
<!--  font-size: 14px;-->
<!--}-->

<!--.info-row i {-->
<!--  margin-right: 8px;-->
<!--  width: 20px;-->
<!--  color: #909399;-->
<!--}-->

<!--.info-value {-->
<!--  color: #606266;-->
<!--}-->

<!--.detail-btn {-->
<!--  margin-left: auto;-->
<!--  padding: 6px 12px;-->
<!--  font-size: 12px;-->
<!--}-->
<!--</style>-->


<template>
  <div class="dashboard-container">
    <!-- 患病风险趋势曲线图 + 固定颜色图例 -->
    <div class="chart-card">
      <div class="chart-header">
        <h2 class="chart-title">患病风险趋势</h2>
      </div>
      <div class="chart-container">
        <canvas ref="riskChart"></canvas>
      </div>
      <!-- 新增：疾病-颜色对应说明 -->
      <div class="legend-container">
        <div
            v-for="(color, disease) in diseaseColorMap"
            :key="disease"
            class="legend-item"
        >
          <div class="legend-color" :style="{ backgroundColor: color }"></div>
          <span>{{ disease }}</span>
        </div>
      </div>
    </div>

    <!-- 历史预测记录（保持原逻辑） -->
    <div class="history-records">
      <h3 class="record-title">历史预测记录</h3>
      <el-row :gutter="20">
        <template v-if="predictionList.length > 0">
          <el-col
              :xs="24" :sm="24" :md="12" :lg="12" :xl="12"
              v-for="prediction in predictionList"
              :key="prediction.predictionID"
              class="order-card-col"
          >
            <el-card class="order-card" shadow="hover">
              <div class="order-header">
                <div class="project-name">预测时间: {{ formatDate(prediction.predictionTime) }}</div>
              </div>
              <div class="info-row">
                <i class="el-icon-date"></i>
                <span class="info-value">症状: {{ prediction.symptoms }} </span>
              </div>
              <div class="info-row price-row">
                <el-button
                    type="primary"
                    size="small"
                    @click="handleViewDetail(prediction.predictionID)"
                    class="detail-btn"
                >
                  查看详情
                </el-button>
              </div>
            </el-card>
          </el-col>
        </template>
        <template v-else>
          <div style="text-align: center; color: #999;">暂无历史预测记录</div>
        </template>
      </el-row>
    </div>
  </div>
</template>

<script>
import { Chart } from 'chart.js';
import { CategoryScale, LinearScale, LineController, PointElement, LineElement } from 'chart.js';
import { Message } from 'element-ui';

Chart.register(CategoryScale, LinearScale, LineController, PointElement, LineElement);

export default {
  name: 'DiseaseRisk',
  data() {
    return {
      // 关键：固定疾病与颜色的映射
      diseaseColorMap: {
        "系统性红斑狼疮": "#3B82F6",
        "类风湿性关节炎": "#EC4899",
        "干燥综合征": "#10B981",
        "强直性脊柱炎": "#6366F1"
      },
      predictionList: [],
      chart: null,
      userId: null,
    };
  },
  created() {
    this.getUserInfo();
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.destroy();
    }
  },
  methods: {
    getUserInfo() {
      const userInfoStr = localStorage.getItem('userInfo');
      if (!userInfoStr) {
        Message.warning('请先登录');
        this.$router.push('/login');
        return;
      }
      try {
        const userInfo = JSON.parse(userInfoStr);
        this.userId = userInfo.userID;
        if (this.userId) {
          this.fetchPredictionData();
        } else {
          Message.error('用户信息异常');
        }
      } catch (error) {
        Message.error('解析用户信息失败');
        this.$router.push('/login');
      }
    },

    // async fetchPredictionData() {
    //   try {
    //     const res = await this.$axios.get('/predictions/userlist', {
    //       params: {
    //         page: 1,
    //         limit: 100,
    //         userId: this.userId
    //       }
    //     });
    //     if (res.data.code === 1 && res.data.data) {
    //       this.predictionList = res.data.data.items;
    //       this.updateChartData(res.data.data.items);
    //     } else {
    //       Message.error('获取数据失败');
    //     }
    //   } catch (error) {
    //     Message.error('网络异常');
    //   }
    // },
    async fetchPredictionData() {
      try {
        const res = await this.$axios.get('/predictions/userlist', {
          params: {
            page: 1,
            limit: 100,
            userId: this.userId
          }
        });
        // 1. 先判断接口响应是否正常（避免后端返回非 200 但无 throw 的情况）
        if (res.status !== 200) {
          throw new Error(`接口响应异常，状态码: ${res.status}`);
        }
        // 2. 再判断业务码是否正确
        if (res.data.code === 1 && res.data.data) {
          this.predictionList = res.data.data.items;
          // 图表数据处理单独 try/catch，避免影响列表显示
          try {
            this.updateChartData(res.data.data.items);
          } catch (chartError) {
            console.error('图表数据处理失败:', chartError);
            Message.warning('图表渲染失败，请刷新重试');
          }
        } else {
          Message.error(`获取数据失败：${res.data.msg || '未知业务错误'}`);
        }
      } catch (error) {
        // 3. 精准判断是否为网络错误（Axios 网络错误会有 isAxiosError 属性）
        if (error.isAxiosError) {
          Message.error('网络异常：请检查网络连接或接口地址');
        } else {
          Message.error(`数据处理异常：${error.message}`);
        }
        console.error('接口请求/处理异常:', error);
      }
    },

    updateChartData(records) {
      // 假设后端返回的 diseasesID 是逗号分隔的字符串，需与 diseaseColorMap 中的名称映射
      const diseaseIdToName = {
        '1': '系统性红斑狼疮',
        '2': '系统性红斑狼疮',
        '3': '系统性红斑狼疮',
        '4': '系统性红斑狼疮',
        '5': '类风湿性关节炎',
        '6': '类风湿性关节炎',
        '7': '类风湿性关节炎',
        '8': '类风湿性关节炎',
        '9': '强直性脊柱炎',
        '10': '强直性脊柱炎',
        '11': '强直性脊柱炎',
        '12': '强直性脊柱炎',
        '13': '干燥综合征',
        '14': '干燥综合征',
        '15': '干燥综合征',
        '16': '干燥综合征',
      };

      const diseaseRiskMap = {};
      const dateSet = new Set();

      records.forEach(record => {
        const time = this.formatDate(record.predictionTime);
        dateSet.add(time);

        const diseaseIds = record.diseasesID.split(',');
        const probabilities = record.diseaseProbabilities?.split(',') || [];

        diseaseIds.forEach((diseaseId, index) => {
          const diseaseName = diseaseIdToName[diseaseId] || `未知(${diseaseId})`;
          const probability = +probabilities[index] || 0;

          if (!diseaseRiskMap[diseaseName]) {
            diseaseRiskMap[diseaseName] = {};
          }
          diseaseRiskMap[diseaseName][time] = probability;
        });
      });

      const labels = Array.from(dateSet).sort();
      const datasets = Object.keys(diseaseRiskMap).map(diseaseName => ({
        label: diseaseName,
        data: labels.map(date => diseaseRiskMap[diseaseName][date] || 0),
        // 从固定映射中取色
        borderColor: this.diseaseColorMap[diseaseName] || "#999",
        backgroundColor: `rgba(${this.hexToRgb(this.diseaseColorMap[diseaseName])}, 0.1)`,
        tension: 0.4,
        fill: true
      }));

      if (this.chart) {
        this.chart.data.labels = labels;
        this.chart.data.datasets = datasets;
        this.chart.update();
      }
    },

    initChart() {
      const ctx = this.$refs.riskChart.getContext('2d');
      this.chart = new Chart(ctx, {
        type: 'line',
        data: { labels: [], datasets: [] },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          interaction: { mode: 'index', intersect: false },
          plugins: {
            legend: { position: 'top' },
            tooltip: {
              callbacks: {
                label: (context) => `${context.dataset.label}: ${context.parsed.y}%`
              }
            }
          },
          scales: {
            x: { type: 'category', grid: { display: false } },
            y: {
              type: 'linear',
              beginAtZero: true,
              max: 100,
              ticks: { callback: v => `${v}%` }
            }
          }
        }
      });
    },

    handleViewDetail(predictionID) {
      // this.$router.push({ path: '/prediction/detail', query: { id: predictionID } });
      //this.$router.push({ name: 'HistoryDetail', params: { id: predictionID} })
      console.log('要传递的ID：', predictionID);
      this.$router.push({
        name: 'HistoryDetail', // 必须与路由配置中的 name 一致
        params: { id: predictionID } // id 必须与路由 path 中的 :id 对应
      });

    },

    formatDate(dateStr) {
      if (!dateStr) return '';
      const date = new Date(dateStr);
      return isNaN(date.getTime())
          ? dateStr
          : `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
    },

    hexToRgb(hex) {
      const r = parseInt(hex.slice(1, 3), 16);
      const g = parseInt(hex.slice(3, 5), 16);
      const b = parseInt(hex.slice(5, 7), 16);
      return `${r}, ${g}, ${b}`;
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  box-shadow: none;
}

.dashboard-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.chart-card {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  padding: 20px;
  margin-bottom: 20px;
}

.chart-header {
  margin-bottom: 20px;
}

.chart-title {
  font-size: 18px;
  font-weight: 600;
  color: #1E293B;
}

.chart-container {
  height: 350px;
}

/* 新增：图例样式 */
.legend-container {
  display: flex;
  gap: 20px;
  margin-top: 16px;
  flex-wrap: wrap; /* 适配多疾病换行 */
}
.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
}
.legend-color {
  width: 16px;
  height: 16px;
  border-radius: 2px;
}

.history-records {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  padding: 20px;
}

.record-title {
  font-size: 16px;
  font-weight: 600;
  color: #1E293B;
  margin-bottom: 16px;
}

.order-card-col {
  margin-bottom: 20px;
  padding: 0 10px;
}

.order-card {
  border-radius: 8px;
  transition: transform 0.3s;
  box-shadow: none;
}

.order-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.order-header {
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.project-name {
  font-weight: 600;
  font-size: 16px;
  color: #333;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
  font-size: 14px;
}

.info-row i {
  margin-right: 8px;
  width: 20px;
  color: #909399;
}

.info-value {
  color: #606266;
}

.detail-btn {
  margin-left: auto;
  padding: 6px 12px;
  font-size: 12px;
}

</style>