<template>
<v-container style="height:500px; display: flex; flex-direction: column;">
  <h2>AG Grid</h2>
  <div style="flex: 1 1 auto; width: 100%;">
    <AgGridVue
      ref="agGrid"
      :rowSelection="'multiple'"
      class="ag-grid"
      :rowData="rowData"
      :columnDefs="columnDefs"
      :defaultColDef="defaultColDef"
      :animateRows="true"
      :domLayout="'normal'"
      @grid-ready="onGridReady"
    />
  </div>
  <v-btn style="margin-top: 10px;" @click="test">클릭</v-btn>
</v-container>
</template>

<script setup lang="ts"> 
import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
ModuleRegistry.registerModules([AllCommunityModule]);

import { ref ,onMounted} from 'vue';
import { AgGridVue } from 'ag-grid-vue3';
import type { ColDef } from 'ag-grid-community';
import type { GridReadyEvent } from 'ag-grid-community';
type GridApi = /*unresolved*/ any
type ColumnApi = /*unresolved*/ any
type GridReadyParams = {
  api: GridApi;
  columnApi: ColumnApi;
};
const agGrid = ref<InstanceType<any> | null>(null);
const gridApi = ref<GridApi | null>(null);
const columnApi = ref<ColumnApi | null>(null);

const onGridReady = (params: GridReadyParams) => {
debugger;
  if (!agGrid.value) {  
  gridApi.value = params.api;
  columnApi.value = params.columnApi;
  }
};
const test = () => {

  if (gridApi.value) {
    const selectedRows = gridApi.value.getSelectedRows();
    console.log('선택된 행:', selectedRows);
  } else {
    console.error('Grid API가 초기화되지 않았습니다.');
  }
};

defineOptions({
  components: { AgGridVue }
});

const rowData = ref([
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
]);

const columnDefs = ref<ColDef[]>([
  { headerCheckboxSelection: true,   // 헤더에 전체 선택 체크박스
    checkboxSelection: true,        // 각 row에 체크박스
    field: 'make',
    flex: 2
  },
  { field: 'model', flex: 3 },   // 3 비율
  { field: 'price', flex: 1 },   // 1 비율
]);

const defaultColDef: ColDef = {
  sortable: true,
  filter: true,
  resizable: true,
  flex: 1 
}
</script>

<style scoped>
.ag-grid {
  height: 100%;
  width: 100%;
}
</style>
