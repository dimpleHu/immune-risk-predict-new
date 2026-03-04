<template>
    <el-card class="serviceModel-card" shadow="hover" @click="viewDetail">
      <img :src="serviceModel.image" alt="serviceModel-image" class="serviceModel-image" />
      <div class="serviceModel-info">
        <h3>{{ serviceModel.name }}</h3>
        <p>{{ serviceModel.description }}</p>
        <span class="price">￥{{ serviceModel.price }}</span>
      </div>
    </el-card>
  </template>

  <script setup>
  const props = defineProps({
    serviceModel: {
      type: Object,
      required: true,
    },
  });

  const emit = defineEmits(['view-detail']);

  const viewDetail = () => {
    emit('view-detail', props.serviceModel.id);
  };
  </script>

  <style scoped>
  .serviceModel-card {
    cursor: pointer;
    transition: transform 0.2s, box-shadow 0.2s;
  }

  .serviceModel-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }

  .serviceModel-image {
    width: 100%;
    height: 150px;
    object-fit: cover;
  }

  .serviceModel-info {
    padding: 15px;
  }

  .serviceModel-info h3 {
    margin: 0 0 10px;
    font-size: 18px;
  }

  .serviceModel-info p {
    margin: 0 0 10px;
    color: #666;
    font-size: 14px;
  }

  .price {
    font-size: 16px;
    color: #409eff;
    font-weight: bold;
  }
  </style>
