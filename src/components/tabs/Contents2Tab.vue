<template>
  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
    <!-- 여기서 더 복잡한 로직과 UI 구성 -->
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

const content = ref('Contents2 탭 정보 불러오는 중...')

onMounted(() => {
  // API 호출하거나 데이터 세팅하는 로직
  content.value = 'Contents2 탭의 내용입니다.'
})
</script>
