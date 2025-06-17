<template>
  <v-dialog v-model="internalDialog" persistent max-width="600">
    <v-card style="width: 600px; height: 500px;">
      <!-- Header -->
      <div class="popup-header d-flex justify-space-between align-center">
        <span class="text-subtitle-1 font-weight-medium text-grey-darken-4">
          항목 선택
        </span>
        <v-btn icon @click="closePopup" density="compact">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </div>

      <!-- Content -->
      <v-card-text class="py-2 px-4" style="overflow: hidden;">
        <v-row>
          <v-col cols="6" class="pa-0">
            <v-checkbox
              v-for="n in 20"
              :key="'left-' + n"
              v-model="leftChecks"
              :label="'왼쪽 항목 ' + n"
              :value="'L' + n"
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
              :value="'R' + n"
              density="compact"
              class="my-1"
              hide-details
            />
          </v-col>
        </v-row>
      </v-card-text>

      <!-- Footer Buttons -->
      <v-card-actions class="justify-end px-4 py-2">
        <v-btn size="small" variant="text" @click="closePopup">그냥 닫기</v-btn>
        <v-btn size="small" color="blue" @click="confirmSelection">선택</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'

// props 정의 및 참조
const props = defineProps({
  modelValue: Boolean
})

// emit 정의
const emit = defineEmits(['update:modelValue', 'select'])

// 내부 dialog 상태
const internalDialog = ref(false)

// props와 내부 상태 동기화
watch(() => props.modelValue, val => (internalDialog.value = val))
watch(internalDialog, val => emit('update:modelValue', val))

// 체크박스 상태
const leftChecks = ref([])
const rightChecks = ref([])

function closePopup() {
  internalDialog.value = false
}

function confirmSelection() {
  const selected = [...leftChecks.value, ...rightChecks.value]
  emit('select', selected)
  internalDialog.value = false
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
