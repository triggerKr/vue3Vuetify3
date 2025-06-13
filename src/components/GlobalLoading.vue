<template>
  <div v-if="isLoading" class="overlay">
    <div class="spinner"></div>
  </div>
</template>

<script setup lang="ts">
import { useLoadingStore } from '../store/loading'
const { isLoading, show,hide } = useLoadingStore()

import { onMounted, onUnmounted } from 'vue'  
onMounted(() => {
  debugger;
  show() // 컴포넌트가 마운트될 때 로딩 표시
})
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
}

.spinner {
  width: 60px;
  height: 60px;
  border: 6px solid #ccc;
  border-top: 6px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
