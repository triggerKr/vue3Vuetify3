<template>
  <div ref="loadingContainer" class="loading-area">
    <MessageDisplay :message="message" />
    <TabMenu v-model="tab" />
    <AgTest />
    <component :is="currentTabComponent" />

    <!-- 수동으로 로딩 오버레이 표시 -->
    <div v-if="isLoading" class="custom-overlay">
      <div class="spinner"></div>
    </div>


  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import api from '../api'
import MessageDisplay from '@/components/MessageDisplay.vue'
import TabMenu from '@/components/TabMenu.vue'

import ReleaseTab from '@/components/tabs/ReleaseTab.vue'
import ReworkTab from '@/components/tabs/ReworkTab.vue'
import Contents1Tab from '@/components/tabs/Contents1Tab.vue'
import Contents2Tab from '@/components/tabs/Contents2Tab.vue'
import EquipTab from '@/components/tabs/EquipTab.vue'
import AgTest from '@/components/AgTest.vue';




// import { useLoading } from 'vue-loading-overlay'
// import type { Ref } from 'vue'

const isLoading = ref(false)
// 🔹 로딩 컨테이너 ref
const loadingContainer = ref<HTMLElement | null>(null)

// 🔹 로딩 인스턴스 ref
//const $loading = useLoading()
//const loader = ref<ReturnType<typeof $loading.show> | null>(null)

// 🔹 로딩 함수
function showLoadingOverlay() {
  // loader.value = $loading.show({
  //   container: loadingContainer.value as HTMLElement, // 여기 중요!
  //   width: 100,
  //   height: 100,
  //   loader: 'bars',
  //   canCancel: false,
  // })

  isLoading.value = true;
}

function hideLoadingOverlay() {
  //loader.value?.hide()
  isLoading.value = false;
}

const message = ref('안녕하세요?')
const tab = ref('ReleaseTab')


const currentTabComponent = computed(() => {
  switch (tab.value) {
    case 'ReleaseTab': return ReleaseTab
    case 'ReworkTab': return ReworkTab
    case 'Contents1Tab': return Contents1Tab
    case 'Contents2Tab': return Contents2Tab
    case 'EquipTab': return EquipTab
    default: return ReleaseTab
  }
})
debugger;
// import { useLoadingStore } from '../store/loading'
// const loading = useLoadingStore()





onMounted(async () => {
  try {

//isLoading.value = true;
// debugger;
// loading.show()
    //showLoadingOverlay()
    const response = await api.get<any>('/hello')
    message.value = response.data.message
  } catch (error) {
    console.error('API 호출 에러:', error)
  } finally {
    //hideLoadingOverlay()
  }
})




</script>
<style scoped>
.loading-wrapper {
  position: relative;
  min-height: 500px;
}

.custom-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* 반투명 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* 위로 띄우기 */
  pointer-events: auto; /* 아래 요소 클릭 방지 */
}
/* 
.spinner {
  background: white;
  padding: 20px;
  border-radius: 10px;
  font-weight: bold;
} */
.spinner {
  width: 50px;
  height: 50px;
  border: 6px solid #ccc;
  border-top-color: #3498db; /* 회전 색상 */
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>