<!--页面布局-->
<template>
  <div class="app-container">
    <div class="content-wrapper">
      <div class="main-content">
        <div class="service-layout">
          <div class="service-content">
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Layout'
}
</script>

<style lang="scss" scoped>
.app-container {
  min-height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.content-wrapper {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.service-layout {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.service-content {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f5f7fa;
}

@media (max-width: 768px) {
  .service-layout {
    flex-direction: column;
  }
  
  .service-content {
    width: 100%;
  }
}
</style>
