<template>
  <el-container class="home-container">
    <el-main>
      <!-- 头部区域 -->
      <div class="hero-section">
        <div class="hero-content">
          <h1>低空飞行体验预约平台</h1>
          <p>探索天际，感受飞行魅力</p>
          <div class="hero-buttons">
            <el-button type="primary" size="large" @click="goToFlightProjects">
              浏览项目 <i class="el-icon-arrow-right"></i>
            </el-button>
            <el-button type="success" size="large" @click="goToMyReservations">
              我的预约 <i class="el-icon-date"></i>
            </el-button>
          </div>
        </div>
      </div>

      <!-- 飞行项目列表 -->
      <div class="projects-section">
        <h2 class="section-title">热门飞行项目</h2>
        <el-row :gutter="20">
          <el-col :span="24" v-if="loading">
            <div class="loading-container">
              <div class="loading-text">
                <i class="el-icon-loading"></i> 正在加载飞行项目...
              </div>
            </div>
          </el-col>
          <template v-else>
            <el-col
                v-for="project in flightProjects"
                :key="project.projectID"
                :xs="24"
                :sm="12"
                :md="8"
            >
              <div class="project-card" @click="viewProjectDetail(project.projectID)">
                <div class="project-image">
                  <img :src="project.projectImage" :alt="project.projectName"/>
                </div>
                <div class="project-info">
                  <h3>{{ project.projectName }}</h3>
                  <p class="project-brief">{{ project.briefIntroduction }}</p>
                  <div class="project-details">
                    <span class="time"><i class="el-icon-time"></i> {{ project.flightTime }}分钟</span>
                    <span class="height"><i class="el-icon-top"></i> {{ project.flightHeight }}</span>
                  </div>
                  <div class="project-footer">
                    <span class="price">¥{{ project.price }}</span>
                    <el-button type="primary" size="small" @click.stop="goToReservation(project.projectID)">
                      立即预约
                    </el-button>
                  </div>
                </div>
              </div>
            </el-col>
          </template>
        </el-row>
      </div>

      <!-- 预约流程 -->
      <div class="process-section">
        <h2 class="section-title">预约流程</h2>
        <el-row :gutter="20">
          <el-col :xs="24" :sm="6">
            <div class="process-step">
              <div class="step-icon">
                <i class="el-icon-search"></i>
              </div>
              <h3>选择项目</h3>
              <p>浏览并选择心仪的飞行体验项目</p>
            </div>
          </el-col>
          <el-col :xs="24" :sm="6">
            <div class="process-step">
              <div class="step-icon">
                <i class="el-icon-date"></i>
              </div>
              <h3>提交预约</h3>
              <p>选择时间并填写预约信息</p>
            </div>
          </el-col>
          <el-col :xs="24" :sm="6">
            <div class="process-step">
              <div class="step-icon">
                <i class="el-icon-wallet"></i>
              </div>
              <h3>支付订单</h3>
              <p>确认订单并完成支付</p>
            </div>
          </el-col>
          <el-col :xs="24" :sm="6">
            <div class="process-step">
              <div class="step-icon">
                <i class="el-icon-success"></i>
              </div>
              <h3>体验飞行</h3>
              <p>按预约时间到场体验</p>
            </div>
          </el-col>
        </el-row>
      </div>

      <!-- 用户评价 -->
      <div class="comments-section" v-if="comments.length > 0">
        <h2 class="section-title">用户评价</h2>
        <el-carousel :interval="4000" type="card" height="200px">
          <el-carousel-item v-for="comment in comments" :key="comment.commentID">
            <div class="comment-card">
              <div class="comment-header">
                <span class="user-name">{{ comment.userName }}</span>
                <el-rate
                    v-model="comment.score"
                    disabled
                    show-score
                    text-color="#ff9900">
                </el-rate>
              </div>
              <p class="comment-content">{{ comment.comments }}</p>
              <div class="comment-footer">
                <span class="comment-time">{{ formatDate(comment.commentTime) }}</span>
                <span class="project-name">{{ comment.projectName }}</span>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      loading: false,
      flightProjects: [
        {
          projectID: 1,
          projectName: '城市观光飞行',
          briefIntroduction: '体验城市上空的独特视角，欣赏城市天际线',
          flightTime: 30,
          flightHeight: '300-500米',
          price: 999,
          projectImage: 'https://img.freepik.com/free-photo/aerial-view-city_23-2148814972.jpg'
        },
        {
          projectID: 2,
          projectName: '山地观光飞行',
          briefIntroduction: '飞越壮丽山脉，体验大自然的震撼',
          flightTime: 45,
          flightHeight: '500-800米',
          price: 1299,
          projectImage: 'https://img.freepik.com/free-photo/beautiful-aerial-shot-mountains_181624-27200.jpg'
        },
        {
          projectID: 3,
          projectName: '特技飞行体验',
          briefIntroduction: '体验各种特技动作，感受极限飞行的刺激',
          flightTime: 20,
          flightHeight: '300-1000米',
          price: 1599,
          projectImage: 'https://img.freepik.com/free-photo/small-plane-flying-cloudy-sky_181624-29891.jpg'
        }
      ],
      comments: [
        {
          commentID: 1,
          userName: '张先生',
          score: 5,
          comments: '非常棒的体验，专业的飞行员和完善的安全措施让人很放心',
          commentTime: '2024-01-15 14:30:00',
          projectName: '城市观光飞行'
        },
        {
          commentID: 2,
          userName: '李女士',
          score: 5,
          comments: '山地观光飞行太震撼了，风景绝美，服务也很贴心',
          commentTime: '2024-01-14 16:20:00',
          projectName: '山地观光飞行'
        },
        {
          commentID: 3,
          userName: '王先生',
          score: 5,
          comments: '特技飞行太刺激了，专业的飞行员技术真的很好',
          commentTime: '2024-01-13 10:15:00',
          projectName: '特技飞行体验'
        }
      ]
    }
  },
  methods: {
    formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0');
      const day = String(d.getDate()).padStart(2, '0');
      const hour = String(d.getHours()).padStart(2, '0');
      const minute = String(d.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day} ${hour}:${minute}`;
    },
    viewProjectDetail(projectId) {
      this.$router.push(`/project-detail/${projectId}`);
    },
    goToFlightProjects() {
      this.$router.push('/flight-projects');
    },
    goToReservation(projectId, event) {
      event.stopPropagation();
      this.$router.push(`/make-reservation/${projectId}`);
    }
  }
}
</script>

<style lang="scss" scoped>
.home-container {
  min-height: 100vh;
  background-color: #f5f7fa;

  .hero-section {
    background: linear-gradient(135deg, #1a365d 0%, #2563eb 100%);
    padding: 80px 20px;
    text-align: center;
    color: white;
    margin-bottom: 40px;

    .hero-content {
      max-width: 800px;
      margin: 0 auto;

      h1 {
        font-size: 42px;
        font-weight: bold;
        margin-bottom: 20px;
      }

      p {
        font-size: 20px;
        margin-bottom: 40px;
        opacity: 0.9;
      }

      .hero-buttons {
        .el-button {
          margin: 0 10px;
          padding: 12px 30px;
        }
      }
    }
  }

  .section-title {
    font-size: 28px;
    color: #1a365d;
    margin-bottom: 30px;
    padding-left: 15px;
    border-left: 4px solid #2563eb;
    display: flex;
    align-items: center;
  }

  .projects-section {
    padding: 40px 20px;

    .project-card {
      background: white;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
      margin-bottom: 20px;
      cursor: pointer;
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 12px 20px rgba(0, 0, 0, 0.1);
      }

      .project-image {
        height: 200px;
        overflow: hidden;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }

      .project-info {
        padding: 20px;

        h3 {
          font-size: 20px;
          color: #1a365d;
          margin-bottom: 10px;
        }

        .project-brief {
          color: #64748b;
          font-size: 14px;
          margin-bottom: 15px;
          height: 40px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }

        .project-details {
          display: flex;
          gap: 20px;
          margin-bottom: 15px;
          color: #64748b;
          font-size: 14px;

          i {
            margin-right: 5px;
          }
        }

        .project-footer {
          display: flex;
          justify-content: space-between;
          align-items: center;

          .price {
            font-size: 24px;
            color: #ef4444;
            font-weight: bold;

            &::before {
              content: '¥';
              font-size: 16px;
            }
          }
        }
      }
    }
  }

  .process-section {
    padding: 60px 20px;
    background: #fff;

    .process-step {
      text-align: center;
      padding: 20px;

      .step-icon {
        width: 80px;
        height: 80px;
        margin: 0 auto 20px;
        background: #eef2ff;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;

        i {
          font-size: 36px;
          color: #2563eb;
        }
      }

      h3 {
        font-size: 18px;
        color: #1a365d;
        margin-bottom: 10px;
      }

      p {
        color: #64748b;
        font-size: 14px;
      }
    }
  }

  .comments-section {
    padding: 40px 20px;

    .comment-card {
      background: white;
      padding: 20px;
      border-radius: 12px;
      height: 100%;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);

      .comment-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;

        .user-name {
          font-size: 16px;
          color: #1a365d;
          font-weight: 500;
        }
      }

      .comment-content {
        color: #64748b;
        font-size: 14px;
        margin-bottom: 15px;
        line-height: 1.6;
      }

      .comment-footer {
        display: flex;
        justify-content: space-between;
        color: #94a3b8;
        font-size: 12px;
      }
    }
  }

  .loading-container {
    padding: 40px;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    text-align: center;

    .loading-text {
      color: #64748b;
      font-size: 16px;

      i {
        color: #2563eb;
        margin-right: 8px;
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .home-container {
    .hero-section {
      padding: 40px 20px;

      .hero-content {
        h1 {
          font-size: 32px;
        }

        p {
          font-size: 16px;
        }

        .hero-buttons {
          .el-button {
            margin: 5px;
            width: 100%;
          }
        }
      }
    }

    .section-title {
      font-size: 24px;
      margin-bottom: 20px;
    }

    .process-section {
      .process-step {
        margin-bottom: 30px;
      }
    }
  }
}
</style>
