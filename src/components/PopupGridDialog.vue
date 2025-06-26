<template>
  <v-dialog v-model="internalDialog" max-width="1000px" persistent>
    <!-- <v-card style="height: 600px; display: flex; flex-direction: column;"> -->
    <v-card
        elevation="50"
  style="height: 600px; display: flex; flex-direction: column;"

    >
      <v-card-title class="text-h6">AG Grid 팝업</v-card-title>

      <v-card-text style="flex: 1 1 auto; padding: 0;">
        <div class="ag-theme-alpine grid-container">
          <AgGridVue
            ref="agGrid"
            :rowSelection="'multiple'"
            :rowData="props.rowData"
            :columnDefs="props.columnDefs"
            :defaultColDef="props.defaultColDef"
            :animateRows="true"
            @grid-ready="onGridReady"
            style="width: 100%; height: 500px;"
            :isRowSelectable="isRowSelectable"
          />
        </div>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="grey" @click="close">닫기</v-btn>
        <v-btn color="primary" @click="confirm">선택 확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineEmits, defineProps, nextTick } from 'vue'
import { AgGridVue } from 'ag-grid-vue3'
import type { ColDef, GridApi, ColumnApi } from 'ag-grid-community'

// 👇 컴포넌트 등록
defineOptions({
  components: { AgGridVue }
})

const isRowSelectable = (node: RowNode) => {
  // 예: make가 'Ford'인 행은 선택 불가능
  return node.data.make !== 'Ford';
}

// props와 emits 정의
/* -- 여기부터 변경된 부분 -- */
const props = defineProps<{
  modelValue: boolean
  rowData: any[]                  // <--- 새로 추가된 prop
  columnDefs: ColDef[]            // <--- 새로 추가된 prop
  defaultColDef: ColDef           // <--- 새로 추가된 prop
}>()
/* -- 여기까지 변경된 부분 -- */


const emit = defineEmits(['update:modelValue', 'select'])
const internalDialog = ref(props.modelValue)

watch(() => props.modelValue, async (val) => {
  internalDialog.value = val

  // 팝업 열릴 때 grid 리사이즈 처리
  if (val && gridApi.value) {
    await nextTick()
    // setTimeout(() => {
    //   gridApi.value!.sizeColumnsToFit()
    // }, 100)
  }
})

watch(internalDialog, (val) => {
  emit('update:modelValue', val)
})

// AG Grid 관련 참조
const gridApi = ref<GridApi | null>(null)
const columnApi = ref<ColumnApi | null>(null)

const onGridReady = (params: { api: GridApi; columnApi: ColumnApi }) => {
  gridApi.value = params.api
  columnApi.value = params.columnApi
  setTimeout(() => {
    params.api.sizeColumnsToFit()
  }, 0)
}
const close = () => {
  internalDialog.value = false
}

const confirm = () => {
  if (gridApi.value) {
    const selected = gridApi.value.getSelectedRows()
    console.log('선택된 행:', selected)
    alert(`선택된 행 수: ${selected.length}`)
    emit('select', selected) // 부모에게 emit
    internalDialog.value = false // 닫기까지 자동 처리하려면 이 줄도 유지

  }
}

// 샘플 row 및 컬럼
// const rowData = ref([
//   { make: 'Toyota', model: 'Celica', price: 35000 },
//   { make: 'Ford', model: 'Mondeo', price: 32000 },
//   { make: 'Porsche', model: 'Boxter', price: 72000 },
// ])

// const columnDefs = ref<ColDef[]>([
//   {
//     headerCheckboxSelection: true,
//     checkboxSelection: true,
//     field: 'make',
//     flex: 2
//   },
//   { field: 'model', flex: 3 },
//   { field: 'price', flex: 1 }
// ])

// const defaultColDef: ColDef = {
//   sortable: true,
//   filter: true,
//   resizable: true
// }
</script>

<style scoped>
.grid-container {
  flex: 1 1 auto;  /* flex-grow 시켜서 남은 공간 모두 차지 */
  min-height: 0;   /* flexbox 내부 스크롤 문제 방지 */
  width: 100%;
}
.v-card-text {
  flex: 1 1 auto;
  padding: 0;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>