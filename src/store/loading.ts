import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useLoadingStore = defineStore('loading', () => {

  let loadingCount = 0;
  const isLoading = ref(false)

  function show() {
    loadingCount++
// sourcery skip: avoid-function-declarations-in-blocks
    isLoading.value = true
  }

  function hide() {
    loadingCount--
    if (loadingCount === 0) {
      isLoading.value = false
    }
  }

  return { isLoading, show, hide }
})
