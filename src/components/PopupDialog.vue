<!-- src/components/PopupDialog.vue -->
<template>
  <v-dialog v-model="internalDialog" width="400" @update:modelValue="updateDialog">
    <v-card>
      <v-card-title class="text-h6">알림</v-card-title>
      <v-card-text>
        이 작업을 진행하시겠습니까?
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="grey" @click="close">취소</v-btn>
        <v-btn color="primary" @click="confirm">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, } from 'vue'

const props = defineProps({
  modelValue: Boolean
})

const emit = defineEmits(['update:modelValue', 'confirmed'])

const internalDialog = ref(props.modelValue)

// props의 modelValue가 바뀌면 내부 dialog도 갱신
watch(() => props.modelValue, (val) => {
  internalDialog.value = val
})

// 내부 dialog가 바뀌면 부모에 전달
function updateDialog(val) {
  emit('update:modelValue', val)
}

function close() {
  emit('update:modelValue', false)
}

function confirm() {
  emit('confirmed')        // 확인 이벤트 발생
  emit('update:modelValue', false)
}
</script>
