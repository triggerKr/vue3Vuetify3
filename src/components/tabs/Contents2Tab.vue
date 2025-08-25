<template>
  <div style="padding: 20px;">
    <h3>📊 AG-Grid Vue 3 + `gridOptions` 통합 예시 (로 클릭 토글 선택)</h3>
    <p>커스텀 클래스 추가.</p>

    <ag-grid-vue
      style="width: 100%; height: 500px;"
      class="ag-theme-alpine custom-ag-grid-style"
      :gridOptions="gridOptions"
      :rowData="rowData.value"
      @grid-ready="onGridReady"
    >
    </ag-grid-vue>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { AgGridVue } from 'ag-grid-vue3';

import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';

import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
import type { GridOptions } from 'ag-grid-community';

// AG-Grid 모듈 등록은 main.ts에서 처리한다고 가정합니다.

// --- 데이터 및 타입 정의 ---
interface Car {
  make: string;
  model: string;
  price: number;
}

const rowData = ref<Car[]>([
  { make: 'Tesla', model: 'Model Y', price: 64950 },
  { make: 'Ford', model: 'F-Series', price: 33850 },
  { make: 'Toyota', model: 'Corolla', price: 29600 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Nissan', model: 'GTR', price: 113540 },
  { make: 'BMW', model: 'M3', price: 69900 },
  { make: 'Mercedes', model: 'C-Class', price: 41400 },
  { make: 'Audi', model: 'A4', price: 39100 },
  { make: 'Hyundai', model: 'Sonata', price: 24000 },
  { make: 'Kia', model: 'K5', price: 25000 },
  { make: 'Chevrolet', model: 'Malibu', price: 23000 },
  { make: 'Tesla', model: 'Model S', price: 80000 },
  { make: 'Ford', model: 'Mustang', price: 30000 },
  { make: 'Toyota', model: 'Camry', price: 28000 },
  { make: 'Porsche', model: '911', price: 100000 },
  { make: 'Honda', model: 'Civic', price: 22000 },
]);

// --- gridOptions 정의 ---
const gridOptions = ref<GridOptions>({
  theme: "legacy",
  // ✨ 여러 로우를 동시에 선택할 수 있도록 'multiple'로 설정합니다.
  rowSelection: 'multiple', 
  // ✨ 로우 클릭 선택을 활성화합니다. (true가 기본값이라 생략 가능하지만 명시적으로 작성)
  enableClickSelection: true, 
  // ✨✨ 이 옵션이 핵심입니다! 로우를 단순 클릭 시 선택 상태를 토글(toggle)합니다. ✨✨
  rowMultiSelectWithClick: true, 
  
  columnDefs: [
    {
      headerName: '',
      checkboxSelection: true,        // 체크박스 활성화
      headerCheckboxSelection: true,  // ✨ 헤더 체크박스 활성화 (다중 선택이므로 필요)
      minWidth: 50,
      maxWidth: 50,
      suppressMenu: true,
      pinned: 'left',
      resizable: false,
      filter: false,
      sortable: false,
    },
    {
      field: 'make',
      headerName: '제조사',
      sortable: true,
      filter: true,
      flex: 1
    },
    {
      field: 'model',
      headerName: '모델',
      sortable: true,
      filter: true,
      flex: 1
    },
    {
      field: 'price',
      headerName: '가격',
      sortable: true,
      filter: true,
      cellDataType: 'number',
      valueFormatter: (params: any) => {
        return params.value ? params.value.toLocaleString() + '원' : '';
      },
      flex: 1
    },
  ],

  animateRows: true,
});

// --- 그리드 이벤트 핸들러 ---
const onGridReady = (params: any) => {
  console.log('AG-Grid가 성공적으로 로드되었습니다! 그리드 API:', params.api);
  params.api.sizeColumnsToFit();
  
  // 선택된 로우를 콘솔에 출력하는 예시
  params.api.addEventListener('selectionChanged', () => {
    const selectedRows = params.api.getSelectedRows();
    console.log('선택된 로우:', selectedRows);
  });
};

onMounted(() => {
  console.log('Contents1Tab.vue 컴포넌트가 마운트되었습니다.');
  console.log('rowData의 현재 값 (onMounted):', rowData.value);
});

</script>

<style scoped>
/* ag-theme-alpine 클래스와 함께 custom-ag-grid-style 클래스를 사용합니다. */
.ag-theme-alpine.custom-ag-grid-style {
  /*
    전반적인 그리드 배경색, 테두리, 헤더 배경색 변경
    AG-Grid 33+ 버전의 테마 API는 CSS 변수를 통해 스타일링을 권장합니다.
    (사용 중인 theme: "legacy" 설정은 기존 CSS 임포트 방식을 유지하므로 이 방법이 적합합니다)
  */
  --ag-background-color: #f8f8f8; /* 그리드 전체 배경색 (아주 연한 회색) */
  --ag-border-color: red;         /*  그리드 테두리 색상 */
  --ag-header-background-color: #e0f2f7; /* 헤더 배경색 (연한 하늘색 계열) */
  --ag-odd-row-background-color: #fcfcfc; /* 홀수 행 배경색 */
  --ag-even-row-background-color: #ffffff; /* 짝수 행 배경색 */

  /* 선택된 로우 색상 변경 */
  /* .ag-row-selected 클래스는 선택된 행에 적용됩니다. */
  /* 특정 테마 내에서 오버라이드할 때, 우선순위를 위해 상위 셀렉터를 사용하거나 !important를 사용할 수 있습니다. */
  /* AG-Grid 테마의 변수를 사용하는 것이 더 좋습니다: */
  --ag-selected-row-background-color: #e0f0e0; /* 선택된 로우 배경색 (연한 녹색 계열) */
  --ag-row-hover-color: #e8e8f8; /* 마우스 오버 시 로우 배경색 (연한 보라색 계열) */

  /* 만약 위의 CSS 변수가 적용되지 않는다면, 아래와 같이 직접 클래스를 타겟팅할 수 있습니다. */
  /* 그러나 일반적으로 CSS 변수를 사용하는 것이 권장됩니다. */
  /*
  .ag-row.ag-row-selected {
    background-color: #e0f0e0 !important;
  }
  .ag-row.ag-row-hover {
    background-color: #e8e8f8 !important;
  }
  */
}

/* 추가적으로, 특정 요소의 폰트 크기나 색상 등을 변경하고 싶다면, AG-Grid 문서의 CSS 레퍼런스를 참고하시면 좋습니다. */
/* 예시: 모든 셀의 텍스트 색상을 변경하고 싶을 때 */
/*
.ag-theme-alpine.custom-ag-grid-style .ag-cell {
  color: #333;
}
*/
</style>