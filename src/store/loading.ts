import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useLoadingStore = defineStore('loading', () => {

  let loadingCount = 0;
  const isLoading = ref(false)

  const loadingShow = () => {
    loadingCount++
    isLoading.value = true
  }

  const loadingHide = () => {

    if (loadingCount >= 1) {
      loadingCount--
    }
    if (loadingCount === 0){
      isLoading.value = false
    }
  }

  return { isLoading, loadingShow, loadingHide }
})
