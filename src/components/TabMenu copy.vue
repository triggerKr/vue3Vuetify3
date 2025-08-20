<template>
  <v-tabs v-model="localTab" bg-color="transparent" color="basil" grow>
    <v-tab v-for="item in items" :key="item" :value="item">
      {{ item }}
    </v-tab>
  </v-tabs>

  <!-- 탭 콘텐츠 영역 -->
  <v-window v-model="localTab" class="mt-4">
    <v-window-item
      v-for="item in items"
      :key="item"
      :value="item"
    >
      <div class="pa-4">
        {{ item }} X탭의 내용입니다.
      </div>
    </v-window-item>
  </v-window>
</template>

<script setup lang="ts">
import { ref, watch, defineProps } from 'vue'

const props = defineProps<{ modelValue: string }>()
const emit = defineEmits(['update:modelValue'])

const localTab = ref(props.modelValue)

watch(localTab, (val) => {
  emit('update:modelValue', val)
})

watch(
  () => props.modelValue,
  (val) => {
    if (val !== localTab.value) localTab.value = val
  }
)

const items = ['Release', 'Rework', 'Contents1', 'Contents2', 'Equip']
</script>
