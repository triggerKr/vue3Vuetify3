<template>
  <TabMenu v-model="tab" />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import TabMenu from '@/components/TabMenu.vue'
const tab = ref('ReleaseTab')

onMounted(async () => {

})

</script>
<style scoped>

</style>