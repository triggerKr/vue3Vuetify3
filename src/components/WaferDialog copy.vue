<template>
  <v-btn @click="dialog = true">팝업 열기</v-btn>

  <v-dialog v-model="dialog" persistent max-width="600">
    <v-card style="width: 600px; height: 500px;">
      <!-- 개선된 헤더 -->
<div class="popup-header d-flex justify-space-between align-center">
  <span class="text-subtitle-1 font-weight-medium text-grey-darken-4">항목 선택</span>
  <v-btn icon @click="dialog = false" density="compact">
    <v-icon>mdi-close</v-icon>
  </v-btn>
</div>


      <!-- 본문 -->
      <v-card-text class="py-2 px-4" style="overflow: hidden;">
        <v-row>
          <v-col cols="6" class="pa-0">
            <v-checkbox
              v-for="n in 20"
              :key="'left-' + n"
              v-model="leftChecks"
              :label="'왼쪽 항목 ' + n"
              :value="n"
              density="compact"
              class="my-1"
              hide-details
            />
          </v-col>
          <v-col cols="6" class="pa-0">
            <v-checkbox
              v-for="n in 20"
              :key="'right-' + n"
              v-model="rightChecks"
              :label="'오른쪽 항목 ' + n"
              :value="n"
              density="compact"
              class="my-1"
              hide-details
            />
          </v-col>
        </v-row>
      </v-card-text>

      <!-- 하단 버튼 -->
      <v-card-actions class="justify-end px-4 py-2">
        <v-btn size="small" variant="text" @click="dialog = false">닫기</v-btn>
        <v-btn size="small" color="primary" @click="onConfirm">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref } from 'vue'

const dialog = ref(false)
const leftChecks = ref([])
const rightChecks = ref([])

function onConfirm() {
  console.log('왼쪽 선택:', leftChecks.value)
  console.log('오른쪽 선택:', rightChecks.value)
  dialog.value = false
}
</script>

<style scoped>
.popup-header {
  background-color: #f5f5f5;
  border-bottom: 1px solid #ccc;
  padding: 10px 16px;
  height: 48px;
}
</style>
