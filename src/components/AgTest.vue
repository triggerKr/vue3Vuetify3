<template>
  <v-container>
    <h2>AG Grid</h2>
    <div class="ag-theme-alpine" style="height: 500px; width: 100%;">
      <AgGridVue
        class="ag-grid"
        :rowData="rowData"
        :columnDefs="columnDefs"
        :defaultColDef="defaultColDef"
        :animateRows="true"
        :domLayout="'autoHeight'"
      />
    </div>
  </v-container>
</template>

<script setup lang="ts"> 
import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
ModuleRegistry.registerModules([AllCommunityModule]);

import { ref } from 'vue';
import { AgGridVue } from 'ag-grid-vue3';
import type { ColDef } from 'ag-grid-community';

defineOptions({
  components: { AgGridVue }
});

const rowData = ref([
  { make: 'Toyota', model: 'Celica', price: 35000 },
  { make: 'Ford', model: 'Mondeo', price: 32000 },
  { make: 'Porsche', model: 'Boxter', price: 72000 },
]);

const columnDefs = ref<ColDef[]>([
  { field: 'make' },
  { field: 'model' },
  { field: 'price' },
]);

const defaultColDef: ColDef = {
  sortable: true,
  filter: true,
  resizable: true,
};
</script>

<style scoped>
.ag-grid {
  width: 100%;
}
</style>
