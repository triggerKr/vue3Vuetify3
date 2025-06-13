<template>
<v-container style="height:500px; display: flex; flex-direction: column;">
  <h2>AG Grid</h2>
  <div style="flex: 1 1 auto; width: 100%;">
    <AgGridVue
      class="ag-grid"
      :rowData="rowData"
      :columnDefs="columnDefs"
      :defaultColDef="defaultColDef"
      :animateRows="true"
      :domLayout="'normal'"
    />
  </div>
</v-container>
</template>

<script setup lang="ts"> 
import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
ModuleRegistry.registerModules([AllCommunityModule]);

import { ref ,onMounted} from 'vue';
import { AgGridVue } from 'ag-grid-vue3';
import type { ColDef } from 'ag-grid-community';

// 그리드가 마운트되고 나서 컬럼 사이즈 조절
// const agGrid = ref(null);
// onMounted(() => {
//   if (agGrid.value && agGrid.value.api) {
//     agGrid.value.api.sizeColumnsToFit();
//   }
// });

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
  { field: 'make', flex: 2 },    // 2 비율로 너비 차지
  { field: 'model', flex: 3 },   // 3 비율
  { field: 'price', flex: 1 },   // 1 비율
]);

const defaultColDef: ColDef = {
  sortable: true,
  filter: true,
  resizable: true,
  flex: 1,  // ★ flex 추가
};
</script>

<style scoped>
.ag-grid {
  height: 100%;
  width: 100%;
}
</style>
