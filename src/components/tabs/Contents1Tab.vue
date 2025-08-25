<template>
  <div style="padding: 20px;">
    <h3>AG-Grid</h3>
    <p>XODNJS</p>
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

import type { GridOptions } from 'ag-grid-community';

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
]);

// --- gridOptions 정의 ---
const gridOptions = ref<GridOptions>({
  theme: "legacy", // 이게 중요함... 옛 버전 기능 사용.. v33 이상한것 많음.
  
  columnDefs: [
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
  ]
  ,animateRows: true
  ,rowSelection: { type: 'multiple' },
});

// --- 그리드 이벤트 핸들러 ---
const onGridReady = (params: any) => {
  debugger;
  console.log('AG-Grid가 성공적으로 로드되었습니다! 그리드 API:', params.api);
  params.api.sizeColumnsToFit();
};

onMounted(() => {
  console.log('Contents1Tab.vue 컴포넌트가 마운트되었습니다.');
});

</script>

<style scoped>
</style>