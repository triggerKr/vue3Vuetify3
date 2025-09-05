<template>
  <!-- 상단 메뉴 탭 -->
  <v-tabs v-model="model">
    <v-tab 
      v-for="item in items"
      :key="item.value"
      :text="item.label"
      :value="item.value"
    />
  </v-tabs>

  <!-- 아래 윈도우(탭과 같은 v-model 공유) -->
  <v-window v-model="model" class="mt-2">
    <v-window-item value="ReleaseTab"><ReleaseTab /></v-window-item>
    <v-window-item value="ReworkTab"><ReworkTab /></v-window-item>
    <v-window-item value="Contents1Tab"><Contents1Tab /></v-window-item>
    <v-window-item value="Contents2Tab"><Contents2Tab /></v-window-item>
    <v-window-item value="EquipTab"><EquipTab /></v-window-item>
    <v-window-item value="GridSelectTab"><GridSelectTab /></v-window-item>
    <v-window-item value="GridSelectTab2"><GridSelectTab2 /></v-window-item>
    <v-window-item value="GridSelectTab3"><GridSelectTab3 /></v-window-item>
  </v-window>
</template>

<script setup lang="ts">
import { defineModel } from 'vue'
import ReleaseTab from '@/components/tabs/ReleaseTab.vue'
import ReworkTab from '@/components/tabs/ReworkTab.vue'
import Contents1Tab from '@/components/tabs/Contents1Tab.vue'
import Contents2Tab from '@/components/tabs/Contents2Tab.vue'
import GridSelectTab from '@/components/tabs/GridSelectTab.vue'
import GridSelectTab2 from '@/components/tabs/GridSelectTab2.vue'
import GridSelectTab3 from '@/components/tabs/GridSelectTab3.vue'
import EquipTab from '@/components/tabs/EquipTab.vue'

// 부모 v-model과 연결 (기본값 보장)  // defineModel로 v-model 브리지(기본값 포함)
const model = defineModel<string>({ default: 'ReleaseTab' })

const items = [
  { label: 'Release',   value: 'ReleaseTab' },
  { label: 'Rework',    value: 'ReworkTab' },
  { label: 'Contents1', value: 'Contents1Tab' },
  { label: 'Contents2', value: 'Contents2Tab' },
  { label: 'GridSelectTab', value: 'GridSelectTab' },
  { label: 'GridSelectTab2', value: 'GridSelectTab2' },
  { label: 'GridSelectTab3', value: 'GridSelectTab3' },
  { label: 'Equip',     value: 'EquipTab' },
] as const
</script>