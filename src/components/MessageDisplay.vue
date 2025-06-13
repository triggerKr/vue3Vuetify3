<template>
  <v-card color="basil">
    <v-card-title class="text-center justify-center py-6">
      <h1 class="font-weight-bold text-h2 text-basil">
        {{ message }}
      </h1>
    </v-card-title>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{ message: string }>()
</script>
