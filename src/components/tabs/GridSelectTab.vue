<template>
  <v-container>
    <PopupGridDialog
      v-model="showAGDialog"
      :row-data="rowData"
      :column-defs="columnDefs"
      :default-col-def="defaultColDef"
      @select="onSelectedRows"
    >
      <template #activator="{ props }">
        <v-btn v-bind="props" color="primary">AG GRID 팝업 열기</v-btn>
      </template>
    </PopupGridDialog>
  </v-container>

  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import PopupGridDialog from '@/components/ag-grid/PopupGridDialog.vue'
import type { ColDef } from 'ag-grid-community'

const showAGDialog = ref(false)

const rowData = [
  { make: 'Toyota',  model: 'Celica',  price: 35000 },
  { make: 'Ford',    model: 'Mondeo',  price: 32000 },
  { make: 'Porsche', model: 'Boxter',  price: 72000 }
]

const columnDefs: ColDef[] = [
  {
    headerCheckboxSelection: true,
    checkboxSelection: params => params.data.make !== 'Ford',
    field: 'make', flex: 2
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
function onSelectedRows(rows: any[]) {
  selectedRows.value = rows
  console.log('부모에서 받은 선택된 항목:', rows)
}

const content = ref('그리드 셀렉트 박스 탭 정보 불러오는 중...')
onMounted(() => {
  content.value = '그리드 셀렉트 박스 탭의 내용입니다.'
})
</script>