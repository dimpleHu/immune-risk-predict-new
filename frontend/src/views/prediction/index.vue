<template>
  <div class="step-container">
    <!-- 步骤条 -->
    <el-steps :active="activeStep" finish-status="success" class="step-bar">
      <el-step title="信息填写"></el-step>
      <el-step title="症状描述"></el-step>
      <el-step title="化验结果"></el-step>
      <el-step title="风险预测"></el-step>
      <el-step title="详细介绍"></el-step>
      <el-step title="治疗方法"></el-step>
    </el-steps>

    <!-- 信息填写表单 -->
    <el-form :model="form" label-width="80px" v-if="activeStep === 0" class="form-container">
      <el-form-item label="年龄">
        <el-input v-model="form.age"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.gender">
          <el-radio label="male">男性</el-radio>
          <el-radio label="female">女性</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <!-- 症状描述表单 -->
    <el-form v-if="activeStep === 1" class="form-container" @submit.native.prevent>

      <!-- 新增：症状相关图片上传区域 -->
      <div class="symptom-upload-section" style="margin: 15px 0;">
        <h3 style="color: #000000; margin-bottom: 10px;">上传症状相关图片（可选）</h3>
        <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
        :on-remove="handleSymptomRemove"
        :file-list="symptomFileList"
        list-type="picture"
        :limit="3"
        :on-exceed="handleSymptomExceed"
        :on-preview="handleSymptomPreview"
        >
        <el-button size="small" type="primary">点击上传症状图片</el-button>
        <div slot="tip" class="el-upload__tip">
          可上传患处照片、异常部位等图片，支持jpg/png格式，单张不超过2MB
        </div>
        </el-upload>
      </div>

      <h3>你有哪些症状？</h3>
      <el-input
          v-model="symptomInput"
          placeholder="在此输入你的主要症状关键字"
          class="symptom-input"
          @input="handleInput"
          @keyup.enter.native="handleSearch"
          clearable
          @clear="clearSearch"
      >
        <template #append>
          <el-button
              type="primary"
              icon="el-icon-search"
              @click="handleSearch"
          ></el-button>
        </template>
      </el-input>



      <!-- 新增：显示全部症状按钮 -->
      <el-button type="text" @click="showAllSymptoms" style="margin-top: 8px;">
        {{ showAll ? '隐藏全部症状' : '显示全部症状' }}
      </el-button>


      <!-- 分栏容器 -->
      <div class="symptom-columns">
        <!-- 左栏：搜索结果 -->
        <div class="column left" v-if="showResult">
          <div class="column-title">搜索结果</div>
          <div class="result-item" v-for="(item, index) in searchResult" :key="index">
            <span>{{ item.symptomName }}</span>
            <el-button type="text" @click="addSymptom(item)">添加</el-button>
          </div>
        </div>

        <!-- 右栏：全部症状 -->
        <div class="column right" v-if="showAll">
          <div class="column-title">全部症状</div>
          <div class="result-item" v-for="(item, index) in allSymptomsList" :key="index">
            <span>{{ item.symptomName }}</span>
            <el-button type="text" @click="addSymptom(item)">添加</el-button>
          </div>
        </div>
      </div>

      <!-- 已选症状列表 -->
      <div class="symptom-list" v-if="symptoms.length > 0">
        <div class="selected-title">已选症状：</div>
        <div class="symptom-item" v-for="(symptom, index) in symptoms" :key="index">
          {{ symptom.symptomName }}
          <el-button type="text" @click="removeSymptom(index)">删除</el-button>
        </div>
      </div>
    </el-form>

    <!-- 新增：第三步-化验结果表单 -->
    <el-form v-if="activeStep === 2" class="form-container" @submit.native.prevent>
      <h3>请上传或填写化验结果</h3>

      <!-- 1. 图片上传区域 -->
      <div class="lab-section">
        <h4>1. 化验单图片上传（可选）</h4>
        <!-- 图片上传组件：使用模拟接口，后续需替换为真实后端接口 -->
        <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
        :on-remove="handleRemove"
        :file-list="fileList"
        list-type="picture"
        :limit="5"
        :on-exceed="handleExceed"
        >
        <el-button size="small" type="primary">点击上传化验单</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且单张不超过500kb</div>
        </el-upload>
      </div>

      <!-- 2. 快速表单上传区域（常用化验项目） -->
      <div class="lab-section" style="margin-top: 20px;">
        <h4>2. 常用化验项目填写</h4>
        <el-form :model="labForm" label-width="120px" class="inner-form">
          <el-form-item label="抗核抗体（ANA）">
            <el-input
                v-model="labForm.ana"
                placeholder="单位：IU/mL，如：阴性（<1:40）、阳性（≥1:40）"
            ></el-input>
          </el-form-item>
          <el-form-item label="Sm抗体">
            <el-input
                v-model="labForm.smAntibody"
                placeholder="单位：U/mL或AU/mL，如：阴性（<20U/mL）、阳性（≥20U/mL）"
            ></el-input>
          </el-form-item>
          <el-form-item label="dsDNA抗体">
            <el-input
                v-model="labForm.dsDnaAntibody"
                placeholder="单位：IU/mL，如：阴性（<10IU/mL）、阳性（≥10IU/mL）"
            ></el-input>
          </el-form-item>
          <el-form-item label="补体C3">
            <el-input
                v-model="labForm.complementC3"
                placeholder="单位：g/L，如：0.9-1.8g/L"
            ></el-input>
          </el-form-item>
          <el-form-item label="补体C4">
            <el-input
                v-model="labForm.complementC4"
                placeholder="单位：g/L，如：0.1-0.4g/L"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>

      <!-- 3. 自定义表格填写区域（支持多项目录入） -->
      <div class="lab-section" style="margin-top: 20px;">
        <h4>3. 其他化验项目（自定义填写）</h4>
        <!-- 表格：新增/删除行 -->
        <el-table :data="customLabList" border style="width: 100%; margin-bottom: 10px;">
          <el-table-column label="化验项目名称" width="200">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemName" placeholder="如：肌酐、尿酸"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="检测结果" width="180">
            <template slot-scope="scope">
              <el-input v-model="scope.row.result" placeholder="如：80"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="单位" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unit" placeholder="如：μmol/L"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="参考范围" width="200">
            <template slot-scope="scope">
              <el-input v-model="scope.row.reference" placeholder="如：53-106"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  type="text"
                  @click="handleDeleteRow(scope.$index)"
                  style="color: #fa1818;"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 新增行按钮 -->
        <el-button
            type="primary"
            size="small"
            @click="handleAddRow"
            icon="el-icon-plus"
        >
          新增化验项目
        </el-button>
      </div>
    </el-form>


    <!-- 第三步：风险预测 -->
    <div v-if="activeStep === 3 || activeStep === 4" class="risk-prediction-container">
      <div class="flex-container">
        <!-- 左侧疾病列表 -->
        <div class="disease-list" :class="{ 'shrinked': activeDisease }">
          <h2>可能的疾病以及概率</h2>
          <div
              class="disease-item"
              v-for="(disease, index) in sortedDiseases"
              :key="index"
              @click="toggleDiseaseDetail(disease)"
              :class="{ 'active': activeDisease === disease }"
          >
            <div class="disease-name">{{ disease.diseaseName }}</div>
            <div class="disease-probability" v-if="disease.probability">
              <el-progress
                  :percentage="disease.probability"
                  :color="getProgressColor(disease.probability)"
                  :show-text="!activeDisease"
              ></el-progress>
              <span v-if="!activeDisease">{{ disease.probability }}%</span>
            </div>
          </div>
        </div>

        <!-- 右侧疾病详情 -->
        <div
            class="disease-detail"
            v-if="activeDisease"
            :class="{ 'expanded': activeDisease }"
        >
          <div class="detail-header">
            <h3>{{ activeDisease.diseaseName }}</h3>
            <el-button
                type="text"
                @click="toggleDiseaseDetail(null)"
                class="close-button"
            >
              关闭
            </el-button>
          </div>
          <div class="detail-section">
            <h4>症状</h4>
            <p>{{ activeDisease.symptoms || '暂无数据' }}</p>
          </div>
          <div class="detail-section">
            <h4>病情概述</h4>
            <p>{{ activeDisease.description || '暂无数据' }}</p>
          </div>
          <div class="detail-section">
            <h4>风险因素</h4>
            <p>{{ activeDisease.riskfactors || '暂无数据' }}</p>
          </div>
        </div>

        <!-- 右侧信息面板（当没有选中疾病时显示） -->
        <div class="info-panel" v-if="!activeDisease">
          <!-- 个人信息 -->
          <div class="info-card">
            <div class="card-header">
              <i class="el-icon-user"></i>
              <span>信息</span>
              <el-button type="text" size="small" @click="editInfo('age')">修改</el-button>
            </div>
            <div class="info-item">
              <span class="info-label">性别：</span>
              <span class="info-value">{{ form.gender === 'male' ? '男' : '女' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">年龄：</span>
              <span class="info-value">{{ form.age }}</span>
            </div>
          </div>

          <!-- 症状信息 -->
          <div class="info-card">
            <div class="card-header">
              <i class="el-icon-stethoscope"></i>
              <span>我的症状</span>
              <el-button type="text" size="small" @click="goBackToSymptoms">修改</el-button>
            </div>
            <!-- 修改：显示症状名称 -->
            <div class="symptom-item" v-for="(symptom, index) in symptoms" :key="index">
              <span>{{ symptom.symptomName }}</span>
            </div>
          </div>

          <el-button type="primary" size="small" @click="resetPrediction">重新预测</el-button>
        </div>
      </div>
    </div>

    <!-- 新增第五步：治疗方法 -->
    <div v-if="activeStep === 5" class="risk-prediction-container">
      <div class="flex-container">
        <!-- 左侧疾病列表 -->
        <div class="disease-list" :class="{ 'shrinked': activeTreatmentDisease }">
          <h2>可能的疾病以及治疗建议</h2>
          <div
              class="disease-item"
              v-for="(disease, index) in sortedDiseases"
              :key="index"
              @click="toggleTreatmentDiseaseDetail(disease)"
              :class="{ 'active': activeTreatmentDisease === disease }"
          >
            <div class="disease-name">{{ disease.diseaseName }}</div>
          </div>
        </div>

        <!-- 右侧治疗建议详情 -->
        <div
            class="disease-detail"
            v-if="activeTreatmentDisease"
            :class="{ 'expanded': activeTreatmentDisease }"
        >
          <div class="detail-header">
            <h3>{{ activeTreatmentDisease.diseaseName }}</h3>
            <el-button
                type="text"
                @click="toggleTreatmentDiseaseDetail(null)"
                class="close-button"
            >
              关闭
            </el-button>
          </div>
          <div class="detail-suggestion">
            <h4>治疗建议</h4>
            <p>{{ activeTreatmentDisease.suggestions || '暂无数据' }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 按钮区域 -->
    <div class="button-group">
      <el-button v-if="activeStep > 0" @click="prevStep">上一步</el-button>
      <el-button v-if="activeStep < 5" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
import { getSymptoms } from '@/api/symptoms'
import { getDiseasesByIds} from '@/api/disease'
import { addPrediction } from '@/api/prediction'
import {getAllSymptoms} from '@/api/symptoms'

export default {
  name: 'Prediction',
  data() {
    return {
      activeStep: 0,
      form: {
        age: '',
        gender: ''
      },
      // 新增：控制是否显示全部症状
      showAll: false,
      showResult: false, // 控制“搜索结果”左栏显示
      // 新增：存储所有症状列表
      allSymptomsList: [],
      searchResult: [],  // 存储搜索结果
      symptomInput: '',
      symptoms: [],// 修改：存储症状对象数组，包含ID和名称
      searchTimer: null,
      diseases: [],
      isEditing: {
        age: false,
        gender: false
      },
      tempForm: {
        age: '',
        gender: ''
      },
      activeDisease: null,
      diseasesID: '',// 存储匹配到的疾病ID字符串（如 "7,4"）
      activeTreatmentDisease: null,

      // 新增：化验结果相关数据
      fileList: [
        // 模拟已上传文件，实际使用时会清空
        // {name: '血常规.jpg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      labForm: { // 常用化验项目表单
        ana:'',
        smAntibody: '',
        dsDnaAntibody: '',
        complementC3: '',
        complementC4: ''
      },
      customLabList: [ // 自定义化验项目表格数据（初始1行空数据）
        { itemName: '', result: '', unit: '', reference: '' }
      ]
    };
  },
  computed: {
    sortedDiseases() {
      return [...this.diseases].sort((a, b) => {
        if (!a.probability) return 1;
        if (!b.probability) return -1;
        return b.probability - a.probability;
      });
    }
  },
  methods: {
    // 显示全部症状（仅展示右栏，隐藏左栏）
    async showAllSymptoms() {
      try {
        const response = await getAllSymptoms();
        if (response.code === 1) {
          this.allSymptomsList = response.data;
          this.showAll = true;
          this.showResult = false; // 隐藏搜索结果
        } else {
          this.$message.error(response.msg || '获取全部症状失败');
        }
      } catch (error) {
        console.error('获取全部症状失败:', error);
        this.$message.error('网络错误，获取全部症状失败');
      }
    },
    async handleAddPrediction() {
      console.log('开始调用prediction/add API...');

      const formatDate = (date) => {
        const padZero = num => num.toString().padStart(2, '0');
        return `${date.getFullYear()}-${padZero(date.getMonth() + 1)}-${padZero(date.getDate())} ${padZero(date.getHours())}:${padZero(date.getMinutes())}:${padZero(date.getSeconds())}`;
      };

      try {
        const userInfoStr = localStorage.getItem('userInfo')
        if (!userInfoStr) {
          this.$message.warning('请先登录后再进行预测')
          this.$router.push('/login')
          return
        }
        const userInfo = JSON.parse(userInfoStr)

        // 修改：提取症状ID数组并拼接
        const symptomIds = this.symptoms.map(s => String(s.symptomId)).filter(id => id);
        // 新增：提取症状名称数组并拼接
        const symptomNames = this.symptoms.map(s => s.symptomName).join(',');

        // 关键修改：处理部分匹配逻辑
        const userSymptomSet = new Set(symptomIds); // 用户输入的症状ID集合
        const matchedDiseaseIds = new Set(); // 用于收集匹配到的疾病ID（去重）

        // 定义映射关系（按照优先级排序，更具体的组合放在前面）
        const symptomDiseaseMap = {
          "1,2,3,4": "8",
          "1,2,3": "7",
          "1,2,4": "7",
          "1,3,4": "7",
          "2,3,4": "7",
          "2,4": "7",
          "3,4": "7",
          "1,3": "6",
          "1,4": "6",
          "2,3": "6",
          "4": "6",
          "3": "6",
          "1,2": "6",
          "1": "5",
          "2": "5",
          "5,6,7,8": "4",
          "5,6,7": "4",
          "5,6,8": "4",
          "5,7,8": "4",
          "5": "3",
          "5,6": "3",
          "5,7": "3",
          "5,8": "3",
          "6,7,8": "2",
          "6": "1",
          "7": "1",
          "8": "1",
          "6,7": "1",
          "6,8": "1",
          "7,8": "1",

          "9,10,11,12,13": "12",

          "10,11,12,13":"12",
          "9,11,12,13":"12",
          "9,10,12,13": "12",
          "9,10,11,13": "12",
          "9,10,11,12": "12",

          "11,12,13":"11",
          "10,12,13":"11",
          "10,11,13":"11",
          "10,11,12":"11",
          "9,12,13":"11",
          "9,11,13":"11",
          "9,11,12":"11",
          "9,10,12": "11",
          "9,10,13": "11",
          "9,10,11": "11",

          "9,10":"10",
          "9,11":"10",
          "9,12":"11",
          "9,13":"10",
          "10,11":"10",
          "10,12":"10",
          "10,13":"10",
          "11,12":"10",
          "11,13":"10",
          "12,13":"10",

          "9":"10",
          "10":"9",
          "11":"9",
          "12":"10",
          "13":"9",

          "14,15,16,17":"16",
          "15,16,17":"16",
          "14,16,17":"16",
          "14,15,17":"16",
          "14,15,16":"16",

          "14,15":"16",
          "14,16":"16",
          "14,17":"16",
          "15,16":"15",
          "15,17":"15",
          "16,17":"15",


          "14":"14",
          "15":"13",
          "16":"13",
          "17":"13",
        };


        // 遍历所有映射关系，检查用户症状是否包含当前子组合的所有ID
        Object.entries(symptomDiseaseMap).forEach(([subCombination, diseaseId]) => {
          // 将子组合拆分为Set（如 "1,2,3,4" → Set {1,2,3,4}）
          const subSet = new Set(subCombination.split(','));
          // 检查用户症状是否包含子组合的所有ID
          const isIncluded = [...subSet].every(id => userSymptomSet.has(id));

          if (isIncluded) {
            // 包含则添加对应的疾病ID
            matchedDiseaseIds.add(diseaseId);
            console.log(`匹配成功：子组合[${subCombination}] → 疾病ID[${diseaseId}]`);
          }else {
            console.log(`未匹配：子组合[${subCombination}]（用户症状：${[...userSymptomSet].join(',')}）`);
          }
        });

        // 关键修改：按"每4个一组，保留每组最大值"处理
        // 1. 将Set转为数字数组（便于比较大小）
        const diseaseIdNumbers = [...matchedDiseaseIds].map(id => parseInt(id, 10)).filter(id => !isNaN(id));
        console.log("匹配到的疾病ID（数字）：", diseaseIdNumbers);

        // 2. 分组并保留每组最大值（1-4、5-8、9-12...）
        const groupMaxMap = {}; // 键：组索引，值：该组最大ID
        diseaseIdNumbers.forEach(id => {
          // 计算组索引：1-4 → 0，5-8 → 1，9-12 → 2，以此类推
          const groupIndex = Math.floor((id - 1) / 4);
          // 若当前组无值，或当前ID大于组内已有值，则更新
          if (!groupMaxMap[groupIndex] || id > groupMaxMap[groupIndex]) {
            groupMaxMap[groupIndex] = id;
          }
        });
        console.log("每组最大ID：", groupMaxMap);

        // 3. 提取所有组的最大值，转为字符串数组并拼接
        const finalDiseaseIds = Object.values(groupMaxMap).map(id => String(id));
        this.diseasesID = finalDiseaseIds.join(',');


        // 无匹配时的默认值
        if (!this.diseasesID) {
          this.diseasesID = "0";
          console.warn("未找到匹配的疾病ID，症状ID组合：", symptomIds.join(','));
        }

        // 构造请求数据
        const predictionData = {
          age: Number(this.form.age),
          sex: this.form.gender === 'male' ? 0 : 1,
          symptomsID: symptomIds.join(','), // 使用症状ID
          symptoms: symptomNames, // 新增：症状名称拼接字符串
          predictionTime: formatDate(new Date()),
          diseasesID: this.diseasesID,
          userID: userInfo.userID
        };

        console.log('发送的预测数据:', predictionData);

        //this.$message.info('正在保存预测记录...');
        const response = await addPrediction(predictionData);

        if (response.code === 1) {
          //this.$message.success('预测记录保存成功');
          console.log('预测记录保存成功:', response.data);
        } else {
          this.$message.warning(`保存失败: ${response.msg || '未知错误'}`);
        }
      } catch (error) {
        console.error('新增预测记录失败:', error);
        this.$message.error('保存预测记录时发生网络错误');
      }
    },

    // 获取疾病数据
    async fetchDiseases() {
      console.log('开始调用disease/batch API...');
      console.log('用户信息:', this.form);
      console.log('已选症状:', this.symptoms);
      console.log('需要查询的疾病ID:', this.diseasesID);  // 打印要查询的ID

      try {
        // 记录开始时间
        const startTime = new Date().getTime();

        // 调用API
        const response = await getDiseasesByIds(this.diseasesID);

        // 计算请求耗时
        const endTime = new Date().getTime();
        const duration = endTime-startTime;

        console.log(`API调用成功，耗时${duration}ms`);
        console.log('API响应完整数据:', response);

        // 处理响应数据
        if (response && response.data) {
          this.diseases = response.data;
          console.log('处理后的疾病数据:', this.diseases);
          console.log(`共获取到${this.diseases.length}种可能的疾病`);
          // this.$message.success('预测完成');
        } else {
          console.error('API返回数据格式不正确:', response);
          this.$message.error('预测失败：返回数据格式不正确');
        }
      } catch (error) {
        console.error('API调用失败:', error);
        console.error('错误详情:', error.response || error.message || error);
        this.$message.error('预测失败：网络错误');
      }
    },

    nextStep() {
      console.log(`点击下一步，当前步骤: ${this.activeStep}`);

      if (this.activeStep < 5) {
        if (this.activeStep === 0) {
          if (!this.form.age || !this.form.gender) {
            this.$message.warning('请填写完整的年龄和性别信息');
            return;
          }
        } else if (this.activeStep === 1) {
          if (this.symptoms.length === 0) {
            this.$message.warning('请至少选择一个症状');
            return;
          }
          this.handleAddPrediction();
          //this.$message.info('正在进行预测，请稍候...');
          this.fetchDiseases();
        }
        // 步骤2（化验结果）：无强制验证（可选填），直接进入下一步
        else if (this.activeStep === 2) {
         // this.$message.info('正在进行预测，请稍候...');
          this.fetchDiseases(); // 进入风险预测前调用疾病接口
        }
        // 步骤4（详细介绍）：进入治疗方法时默认选中第一个疾病
        else if (this.activeStep === 4) {
          if (this.diseases.length > 0) {
            this.activeTreatmentDisease = this.diseases[0];
          }
        }

        this.activeStep++;
        console.log(`已进入步骤 ${this.activeStep}`);
      }
    },

    prevStep() {
      if (this.activeStep > 0) {
        this.activeStep--;
      }
    },

    handleInput() {
      if (this.searchTimer) {
        clearTimeout(this.searchTimer);
      }
      if (!this.symptomInput.trim()) {
        this.searchResult = [];
        this.showResult = false;
        return;
      }
      this.searchTimer = setTimeout(() => {
        this.handleSearch();
      }, 300);
    },

    async handleSearch() {
      const keyword = this.symptomInput.trim();
      if (!keyword) {
        this.$message.warning('请输入症状关键字');
        return;
      }

      try {
        const response = await getSymptoms({ name: keyword });

        if (response.code === 1) {
          this.searchResult = response.data.map(item => ({
            symptomId: item.symptomId,
            symptomName: item.symptomName
          }));
          this.showResult = this.searchResult.length > 0;

          if (this.searchResult.length === 0) {
            this.$message.info('未找到相关症状，请尝试其他关键字');
          }
        } else {
          this.$message.error(response.msg || '搜索失败');
          this.searchResult = [];
          this.showResult = false;
        }
      } catch (error) {
        console.error('症状搜索失败:', error);
        this.$message.error('网络错误，搜索失败');
      }
    },

    // 修改：添加症状对象而不是名称
    addSymptom(symptom) {
      // 检查是否已添加该症状（根据ID判断）
      const isAlreadyAdded = this.symptoms.some(s => s.symptomId === symptom.symptomId);

      if (!isAlreadyAdded) {
        this.symptoms.push(symptom);
        this.$message.success(`已添加症状：${symptom.symptomName}`);
        console.log(`已添加症状: ${symptom.symptomName} (ID: ${symptom.symptomId})`);
      } else {
        this.$message.info('该症状已在列表中');
      }
    },

    // 移除症状
    removeSymptom(index) {
      const removed = this.symptoms.splice(index, 1);
      this.$message.info(`已移除症状：${removed[0].symptomName}`);
      console.log(`已移除症状: ${removed[0].symptomName} (ID: ${removed[0].symptomId})`);
    },

    resetPrediction() {
      this.activeStep = 1;
      this.symptomInput = '';
      this.showResult = false;
      this.searchResult = [];
      this.activeDisease = null;
      this.symptoms = []; // 重置症状列表
      console.log('已重置预测流程');
    },

    goBackToSymptoms() {
      this.activeStep = 1;
      this.activeDisease = null;
    },

    getProgressColor(percentage) {
      if (percentage > 70) return '#fa1818';
      if (percentage > 50) return '#f17e21';
      if (percentage > 20) return '#e6d83c';
      return '#67c23a';
    },

    editInfo(type) {
      this.tempForm[type] = this.form[type];
      this.isEditing[type] = true;
    },

    saveEdit(type) {
      if (!this.tempForm[type]) {
        this.$message.warning('请输入有效信息');
        return;
      }

      this.form[type] = this.tempForm[type];
      this.isEditing[type] = false;
      this.$message.success('信息已更新');
    },

    cancelEdit(type) {
      this.isEditing[type] = false;
    },
    // 新增：第五步切换疾病方法
    toggleTreatmentDiseaseDetail(disease) {
      this.activeTreatmentDisease = disease;
    },

    toggleDiseaseDetail(disease) {
      this.activeDisease = disease;
      if (disease) {
        this.activeStep = 4;
      }
    },
    // ———— 新增：化验结果相关方法 ————
    // 图片上传-删除文件
    handleRemove(file, fileList) {
      console.log('删除上传文件:', file);
      this.fileList = fileList; // 更新文件列表
      this.$message.info(`已删除文件：${file.name}`);
    },

    // 图片上传-预览文件
    handlePreview(file) {
      console.log('预览上传文件:', file);
      // 可扩展：调用图片预览弹窗
    },

    // 图片上传-超出数量限制
    handleExceed(files, fileList) {
      this.$message.warning(`最多只能上传5张图片，本次选择了${files.length}张，已自动忽略多余文件`);
    },

    // 自定义表格-新增行
    handleAddRow() {
      this.customLabList.push({ itemName: '', result: '', unit: '', reference: '' });
    },

    // 自定义表格-删除行
    handleDeleteRow(index) {
      if (this.customLabList.length <= 1) {
        this.$message.warning('至少保留1行化验项目');
        return;
      }
      this.customLabList.splice(index, 1);
    },

    // 新增：清空搜索结果（原模板遗漏，补充实现）
    clearSearch() {
      this.symptomInput = '';
      this.searchResult = [];
      this.showResult = false;
    }
  },


  beforeDestroy() {
    if (this.searchTimer) {
      clearTimeout(this.searchTimer);
    }
  }
};
</script>

<style scoped>
.step-container {
  padding: 20px;
}

/* 步骤条样式 */
.step-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

/* 表单容器样式 */
.form-container {
  border: 1px solid #dcdcdc;
  padding: 20px;
  border-radius: 5px;
  background-color: #f9f9f9;
  margin-bottom: 20px;
}

/* 按钮区域样式 */
.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}


/* 分栏容器样式 */
.symptom-columns {
  display: flex;
  gap: 20px;
  margin-top: 15px;
}

/* 左右栏通用样式 */
.column {
  flex: 1;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 10px;
  max-height: 300px;
  overflow-y: auto;
  background-color: #fff;
}

.column-title {
  font-weight: bold;
  margin-bottom: 10px;
  padding-bottom: 5px;
  border-bottom: 1px solid #f0f0f0;
}

/* 左栏样式（搜索结果） */
.column.left {
  border-color: #409EFF;
}
.column.left .column-title {
  color: #409EFF;
}

/* 右栏样式（全部症状） */
.column.right {
  border-color: #67c23a;
}
.column.right .column-title {
  color: #67c23a;
}

/* 结果项通用样式 */
.result-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px dashed #f0f0f0;
}
.result-item:last-child {
  border-bottom: none;
}



/* 症状输入框样式 */
.symptom-input {
  width: 300px;
  margin-right: 10px;
  margin-bottom: 10px;
}

.result-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
  padding: 5px 10px;
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #f0f0f0;
}

/* 已选症状列表样式 */
.symptom-list {
  margin-top: 20px;
  padding-top: 10px;
  border-top: 1px dashed #e4e7ed;
}

.selected-title {
  font-weight: bold;
  margin-bottom: 10px;
  color: #606266;
}

/* 症状项样式 */
.symptom-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
  padding: 5px 10px;
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #f0f0f0;
}

/* 风险预测容器样式 */
.risk-prediction-container {
  padding: 20px;
  background-color: #f9fafc;
  border-radius: 8px;
  margin-bottom: 20px;
}

/* 左侧疾病列表和右侧信息面板布局 */
.flex-container {
  display: flex;
  justify-content: space-between;
  gap: 20px; /* 添加间距 */
}

/* 调整左侧疾病列表宽度 */
.disease-list {
  width: 60%;
  transition: width 0.3s ease;
}

/* 选中疾病时左侧收缩 */
.disease-list.shrinked {
  width: 35%;
}

/* 调整右侧面板宽度 */
.info-panel, .disease-detail {
  width: 38%;
  transition: width 0.3s ease;
}

/* 选中疾病时右侧展开 */
.disease-detail.expanded {
  width: 63%;
}

/* 疾病列表样式 */
.disease-list h2 {
  margin-bottom: 20px;
}

.disease-item {
  background-color: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.disease-item:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.disease-item.active {
  border-color: #409eff;
  background-color: #f5f7fa;
}

.disease-name {
  font-weight: bold;
}

.disease-probability {
  margin-top: 10px;
}

/* 信息卡片样式 */
.info-card {
  background-color: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 10px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.info-label {
  font-weight: bold;
  color: #606266;
}

/* 疾病详情样式 */
.disease-detail {
  background-color: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 20px;
  overflow: hidden;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  border-bottom: 1px solid #e4e7ed;
  padding-bottom: 10px;
}

.detail-header h3 {
  margin: 0;
}

.close-button {
  color: #909399;
}

.close-button:hover {
  color: #606266;
}

.detail-section {
  margin-bottom: 20px;
}

.detail-section h4 {
  margin: 0 0 10px 0;
  color: #606266;
}

.detail-section p {
  margin: 0;
  color: #303133;
  line-height: 1.6;
  white-space: pre-line; /* 保留文本原有换行 */
  /*text-indent: 2em; 所有段落首行都缩进 2em */
}

.detail-suggestion p{
  margin: 0;
  color: #303133;
  line-height: 1.6;
  white-space: pre-line; /* 保留文本原有换行 */
  text-indent: 2em; /*所有段落首行都缩进 2em */
}


/* 第五步治疗方法页面样式（可根据需要调整，与第四步样式逻辑类似） */
.disease-list h2 {
  color: #303133; /* 保证标题颜色统一 */
}

/* 新增：全部症状列表可添加背景色区分 */
.search-results .result-item {
  background-color: #f9f9f9;
}
.search-results .result-item:hover {
  background-color: #f0f0f0;
}


/* ———— 新增：化验结果相关样式 ———— */
/* 化验区域标题 */
.lab-section h4 {
  color: #409EFF;
  margin-bottom: 15px;
  padding-left: 5px;
  border-left: 3px solid #409EFF;
}

/* 内部表单间距 */
.inner-form .el-form-item {
  margin-bottom: 15px;
}

/* 自定义表格按钮 */
.el-table .el-button--text {
  padding: 5px 0;
}

/* 化验结果信息卡样式 */
.lab-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  padding: 3px 0;
}

.lab-label {
  color: #606266;
  width: 120px;
  display: inline-block;
}

.lab-value {
  color: #303133;
  flex: 1;
  text-align: left;
  padding-left: 10px;
}

/* 上传组件间距 */
.upload-demo {
  margin-bottom: 10px;
}

</style>