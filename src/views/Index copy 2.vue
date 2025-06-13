<template>
  <v-card color="basil">
    <v-card-title class="text-center justify-center py-6">
      <h1 class="font-weight-bold text-h2 text-basil">
       {{message}}
      </h1>
    </v-card-title>

    <v-tabs
      v-model="tab"
      bg-color="transparent"
      color="basil"
      grow
    >
      <v-tab
        v-for="item in items"
        :key="item"
        :text="item"
        :value="item"
      ></v-tab>
    </v-tabs>

    <v-tabs-window v-model="tab">
      <v-tabs-window-item
        v-for="item in items"
        :key="item"
        :value="item"
      >
        <v-card
          color="basil"
          flat
        >
          <v-card-text>{{ text }}</v-card-text>
        </v-card>
      </v-tabs-window-item>
    </v-tabs-window>
  </v-card>
</template>

<style>
/* Helper classes */
.bg-basil {
  background-color: #FFFBE6 !important;
}
.text-basil {
  color: #356859 !important;
}
</style>
<script setup lang="ts">
  import { ref , onMounted } from 'vue'
  import api from '../api'
  //import { useAuthStore } from '@/stores/auth'  
const message = ref('안녕하세요?')

onMounted(async () => {
  try {
 
    const response = await api.get<any>('/hello')
    console.log('응답:', response.data);

    message.value = response.data.message
  } catch (error) {
    console.error('API 호출 에러:', error)
  }
})


  const tab = ref('Appetizers')

  const items = [
    'Appetizers',
    'Entrees',
    'Deserts',
    'Cocktails',
  ]
  const text = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'
</script>
