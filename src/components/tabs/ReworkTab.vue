<template>
  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

const content = ref('Rework 탭 정보 불러오는 중...')

onMounted(() => {
  // API 호출하거나 데이터 세팅하는 로직
  content.value = 'Rework 탭의 내용입니다.'
})
</script>
