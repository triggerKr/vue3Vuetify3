<template>
   <v-btn @click="showDialog = true">항목 선택 팝업</v-btn>
  <WaferDialog v-model="showDialog" 
   @select="handleSelectedItems"
  />
  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
    <!-- 여기서 더 복잡한 로직과 UI 구성 -->
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import WaferDialog from '@/components/WaferDialog.vue'

const content = ref('Release 탭 정보 불러오는 중...')

onMounted(() => {
  // API 호출하거나 데이터 세팅하는 로직
  content.value = 'Release 탭의 내용입니다.'
})


const showDialog = ref(false)
const openDialog = () => {
  showDialog.value = true
}
function handleSelectedItems(selected:any) {
  debugger;
  console.log('선택된 항목들:', selected)
}
</script>
