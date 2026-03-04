<template>
  <div class="dashboard">
    <h2>用户仪表盘</h2>
    <div class="user-info">
      <h3>用户信息</h3>
      <div>
        <label for="nickname">昵称:</label>
        <input type="text" id="nickname" v-model="userInfo.nickname" @change="updateUserInfo" />
      </div>
      <div>
        <label for="avatar">头像:</label>
        <input type="file" id="avatar" @change="uploadAvatar" />
      </div>
      <div>
        <label for="contact">联系方式:</label>
        <input type="text" id="contact" v-model="userInfo.contact" @change="updateUserInfo" />
      </div>
    </div>
    <div class="my-appointments">
      <h3>我的预约</h3>
      <ul>
        <li v-for="appointment in appointments" :key="appointment.id">
          {{ appointment.serviceName }} - {{ appointment.time }}
        </li>
      </ul>
    </div>
    <div class="my-reviews">
      <h3>我的评价</h3>
      <ul>
        <li v-for="review in reviews" :key="review.id">
          {{ review.serviceName }} - {{ review.rating }}星
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {
        nickname: '',
        avatar: '',
        contact: ''
      },
      appointments: [],
      reviews: []
    };
  },
  created() {
    this.fetchUserInfo();
    this.fetchAppointments();
    this.fetchReviews();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const response = await this.$api.getUserInfo();
        this.userInfo = response.data;
      } catch (error) {
        console.error('获取用户信息失败', error);
      }
    },
    async updateUserInfo() {
      try {
        await this.$api.updateUserInfo(this.userInfo);
        alert('用户信息更新成功');
      } catch (error) {
        console.error('更新用户信息失败', error);
      }
    },
    uploadAvatar(event) {
      const file = event.target.files[0];
      // 处理文件上传逻辑
    },
    async fetchAppointments() {
      try {
        const response = await this.$api.getAppointments();
        this.appointments = response.data;
      } catch (error) {
        console.error('获取预约信息失败', error);
      }
    },
    async fetchReviews() {
      try {
        const response = await this.$api.getReviews();
        this.reviews = response.data;
      } catch (error) {
        console.error('获取评价信息失败', error);
      }
    }
  }
};
</script>

<style scoped>
/* 样式可以根据需要添加 */
</style>