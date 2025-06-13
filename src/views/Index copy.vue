<template>
  <v-card>
    <v-tabs v-model="tab">
      <v-tab
        v-for="item in items"
        :key="item"
        :value="item"
      >
        {{ item }}
      </v-tab>
    </v-tabs>

    <v-window v-model="tab" class="pa-4" style="min-height: 400px;">
      <v-window-item
        v-for="item in items"
        :key="item"
        :value="item"
      >
        <v-card flat>
          <v-card-text height="200" class="d-flex align-center justify-center">
            {{ item }} content here
          </v-card-text>
        </v-card>
      </v-window-item>
    </v-window>
  </v-card>
</template>

<style>
.min-40 {
  min-width: 40px;
}
</style>

<script setup lang='ts'>
import { ref } from 'vue'

const tab = ref('Tab 1')
const items = ['Tab 1', 'Tab 2', 'Tab 3']
const activeTab = ref('Tab 1')
const updateTab = (value: string) => {
  activeTab.value = value
}
</script>
