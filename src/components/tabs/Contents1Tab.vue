<template>
  <div style="padding: 20px;">
    <h3>📊 AG-Grid Vue 3 + `gridOptions` 통합 예시 (체크박스 및 로우 클릭 해결)</h3>
    <p>체크박스 선택만 작동하도록 로우 클릭 선택 동작을 비활성화했습니다.</p>

    <ag-grid-vue
      style="width: 100%; height: 500px;"
      class="ag-theme-alpine"
      :gridOptions="gridOptions"
      :rowData="rowData"
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
  rowSelection: 'multiple', // 로우 다중 선택 활성화
  enableClickSelection: false, // ✨✨ 로우 클릭 시 선택 동작 비활성화! ✨✨
  
  columnDefs: [
    {
      headerName: '',
      checkboxSelection: true,
      headerCheckboxSelection: true,
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
</style>