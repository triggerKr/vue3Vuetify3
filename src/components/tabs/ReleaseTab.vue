<template>
   <v-btn @click="showDialog = true">d항목 선택 팝업</v-btn>
  <WaferDialog v-model="showDialog" 
   @select="handleSelectedItems"
  />
   <v-btn @click="showDialog = true">항목 선택 팝업</v-btn>
  <WaferDialog v-model="showDialog" @select="handleSelectedItems"/>

  <v-container>
    <v-btn color="primary" @click="showPopup = true">팝업 열기</v-btn>
    <PopupDialog v-model="showPopup" @confirmed="onConfirmed" />
  </v-container>

  <v-container>
    <v-btn color="primary" @click="showAGDialog = true">AG Grid 팝업 열기</v-btn>
    <PopupGridDialog     
      v-model="showAGDialog"
      :rowData="rowData"
      :columnDefs="columnDefs"
      :defaultColDef="defaultColDef"
      @select="onSelectedRows"
    />
  </v-container>
  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
    <!-- 여기서 더 복잡한 로직과 UI 구성 -->
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import WaferDialog from '@/components/WaferDialog.vue'
import PopupGridDialog from '@/components/PopupGridDialog.vue'

const showAGDialog = ref(false)
const rowData = [
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 }
]

const columnDefs: ColDef[] = [
  {
    headerCheckboxSelection: true,
    checkboxSelection: (params) => {
      // 예: make가 'Ford'인 행은 체크박스 제거 (disabled처럼)
      return params.data.make !== 'Ford';
    },
    field: 'make',
    flex: 2
  },
  { field: 'model', flex: 3 },
  { field: 'price', flex: 1 }
]

const defaultColDef: ColDef = {
  sortable: true,
  filter: true,
  resizable: true
}

const selectedRows = ref<any[]>([])

const onSelectedRows = (rows: any[]) => {
  debugger;
  selectedRows.value = rows
  console.log('부모에서 받은 선택된 항목:', rows)
}


const content = ref('Release 탭 정보 불러오는 중...')

onMounted(() => {
  // API 호출하거나 데이터 세팅하는 로직
  content.value = 'Release 탭의 내용입니다.'
})

import PopupDialog from '@/components/PopupDialog.vue'
import { ColDef } from 'ag-grid-community'

const showPopup = ref(false)

function onConfirmed() {
  alert("확인 버튼 클릭됨!")
}

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
