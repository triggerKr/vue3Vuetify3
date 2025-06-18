<template>
   <v-btn @click="showDialog = true">항목 선택 팝업</v-btn>
  <WaferDialog v-model="showDialog" 
   @select="handleSelectedItems"
  />
  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
    <!-- 여기서 더 복잡한 로직과 UI 구성 -->
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import WaferDialog from '@/components/WaferDialog.vue'

const content = ref('Release 탭 정보 불러오는 중...')

onMounted(() => {
  // API 호출하거나 데이터 세팅하는 로직
  content.value = 'Release 탭의 내용입니다.'
})


const showDialog = ref(false)
const openDialog = () => {
  showDialog.value = true
}
function handleSelectedItems(selected:any) {
  debugger;
  console.log('선택된 항목들:', selected)

// 자식 페이지에서 예: Vue 컴포넌트 또는 plain JS에서도 가능
const payload = {
  type: 'form-submitted',
  name: '태원님',
  age: 45
};

// 부모에게 postMessage 전송
window.parent.postMessage(payload, 'http://127.0.0.1:2001'); // 부모 origin 명시

}


    // 메시지 수신 처리
    window.addEventListener('message', (event) => {
      // 보안: 부모의 origin을 반드시 확인
      if (event.origin !== 'http://127.0.0.1:2001') return;
debugger;
      const data = event.data;

      if (data.type === 'navigate') {
        const page = data.page;
        const app = document.getElementById('app');

        // 페이지 전환 시뮬레이션
        switch (page) {
          case 'home':
            if( app)
            app.innerText = '🏠 Home 페이지입니다';
            break;
          case 'about':
            if( app)
            app.innerText = 'ℹ️ About 페이지입니다';
            break;
          case 'contact':
            if( app)
            app.innerText = '📞 Contact 페이지입니다';
            break;
          default:
            if( app)
            app.innerText = '❓ 알 수 없는 페이지';
        }
      }
    });
</script>
