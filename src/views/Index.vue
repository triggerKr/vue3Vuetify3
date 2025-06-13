<template>
  <div ref="loadingContainer" class="loading-area">
    <MessageDisplay :message="message" />
    <TabMenu v-model="tab" />
    <AgComponet />
    <component :is="currentTabComponent" />

  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import MessageDisplay from '@/components/MessageDisplay.vue'
import TabMenu from '@/components/TabMenu.vue'

import ReleaseTab from '@/components/tabs/ReleaseTab.vue'
import ReworkTab from '@/components/tabs/ReworkTab.vue'
import Contents1Tab from '@/components/tabs/Contents1Tab.vue'
import Contents2Tab from '@/components/tabs/Contents2Tab.vue'
import EquipTab from '@/components/tabs/EquipTab.vue'

// AG Grid 컴포넌트 임포트
import AgComponet from '@/components/AgComponent.vue';


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
 


onMounted(async () => {

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