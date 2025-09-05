<template>
  <v-container>
    <PopupGridDialog
      v-model="showAGDialog"
      :row-data="rowData"
      :column-defs="columnDefs"
      :default-col-def="defaultColDef"
      @select="onSelectedRows"
    >
      <!-- 버튼 대신 텍스트 박스를 activator로 사용 -->
      <template #activator="{ props }">
        <v-text-field
          v-bind="props"
          v-model="displayText"
          label="AG GRID 항목 선택"
          placeholder="클릭해서 항목을 선택하세요"
          :readonly="true"
          clearable
          hide-details
          max-width="300px"
          :append-inner-icon="showAGDialog ? 'mdi-chevron-up' : 'mdi-chevron-down'"
          @click:append-inner="showAGDialog = !showAGDialog"
          @click:clear="onClear"
        />
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
const displayText = ref('')          // 선택 결과 표시용
const selectedRows = ref<any[]>([])  // 선택 데이터 보관

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

function onSelectedRows(rows: any[]) {
  selectedRows.value = rows
  // 표시 문자열은 필요에 맞게 변경하세요.
  // 예: 다중 선택 시 "n개 선택"
  displayText.value = rows.length
    ? rows.map(r => `${r.make} ${r.model}`).join(', ')
    : ''
  // 선택 후 팝업은 PopupGridDialog 내부 confirm에서 닫힙니다.
}

function onClear() {
  displayText.value = ''
  // 그리드 선택도 함께 해제하고 싶다면,
  // PopupGridDialog에 선택 해제용 이벤트/메서드 노출을 추가하는 방식을 권장합니다.
}

const content = ref('그리드 셀렉트 박스 탭 정보 불러오는 중...')
onMounted(() => {
  content.value = '그리드 셀렉트 박스 탭의 내용입니다.'
})
</script>