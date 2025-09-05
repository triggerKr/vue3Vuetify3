<template>
  <v-container>
    <v-row align="center" no-gutters>
      <!-- 첫 번째 텍스트 박스 -->
      <v-col cols="auto">
        <PopupGridDialog
          v-model="showAGDialog1"
          :row-data="rowData"
          :column-defs="columnDefs"
          :default-col-def="defaultColDef"
          @select="(rows: any[]) => onSelectedRows(rows, 1)"
        >
          <template #activator="{ props }">
            <v-text-field
              v-bind="props"
              v-model="displayText1"
              label="AG GRID 항목 선택 1"
              placeholder="클릭해서 항목을 선택하세요"
              :readonly="true"
              clearable
              hide-details
              style="max-width: 300px;min-width:300px;"
              :append-inner-icon="showAGDialog1 ? 'mdi-chevron-up' : 'mdi-chevron-down'"
              @click:append-inner="showAGDialog1 = !showAGDialog1"
              @click:clear="onClear(1)"
            />
          </template>
        </PopupGridDialog>
      </v-col>

      <!-- 간격 -->
      <v-col cols="auto" class="ml-4">
        <!-- 두 번째 텍스트 박스 -->
        <PopupGridDialog
          v-model="showAGDialog2"
          :row-data="rowData"
          :column-defs="columnDefs"
          :default-col-def="defaultColDef"
          @select="(rows: any[]) => onSelectedRows(rows, 2)"
        >
          <template #activator="{ props }">
            <v-text-field
              v-bind="props"
              v-model="displayText2"
              label="AG GRID 항목 선택 2"
              placeholder="클릭해서 항목을 선택하세요"
              :readonly="true"
              clearable
              hide-details
              style="max-width: 300px;min-width:300px;"
              :append-inner-icon="showAGDialog2 ? 'mdi-chevron-up' : 'mdi-chevron-down'"
              @click:append-inner="showAGDialog2 = !showAGDialog2"
              @click:clear="onClear(2)"
            />
          </template>
        </PopupGridDialog>
      </v-col>
    </v-row>
  </v-container>

  <v-card color="basil" flat>
    <v-card-text>{{ content }}</v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import PopupGridDialog from '@/components/ag-grid/PopupGridDialog.vue'
import type { ColDef } from 'ag-grid-community'

const showAGDialog1 = ref(false)
const showAGDialog2 = ref(false)

const displayText1 = ref('')         // 1번 선택 결과
const displayText2 = ref('')         // 2번 선택 결과

const selectedRows1 = ref<any[]>([])
const selectedRows2 = ref<any[]>([])

const rowData = [
  { make: '1Toyota',  model: 'Celica',  price: 35000 },
  { make: '2Ford',    model: 'Mondeo',  price: 32000 },
  { make: '3Toyota',  model: 'Celica',  price: 35000 },
  { make: '4Ford',    model: 'Mondeo',  price: 32000 },
  { make: '5Toyota',  model: 'Celica',  price: 35000 },
  { make: '6Ford',    model: 'Mondeo',  price: 32000 },
  { make: '7Toyota',  model: 'Celica',  price: 35000 },
  { make: '8Ford',    model: 'Mondeo',  price: 32000 },
  { make: '9Toyota',  model: 'Celica',  price: 35000 },
  { make: '10Ford',    model: 'Mondeo',  price: 32000 },
  { make: '11Porsche', model: 'Boxter',  price: 72000 }
]

const columnDefs: ColDef[] = [
  {
    headerCheckboxSelection: true,
    checkboxSelection: (params: { data: RowData }) => params.data.make !== 'Ford',
    field: 'make', flex: 2
  },
  { field: 'model', flex: 3 },
  { field: 'price', flex: 1 }
]

const defaultColDef: ColDef = {
  // sortable: true,
  // filter: true,
  // resizable: true
}

// 어떤 입력에서 선택했는지 구분하여 반영
function onSelectedRows(rows: any[], which: 1 | 2) {
  const text = rows.length ? rows.map(r => `${r.make} ${r.model}`).join(', ') : ''
  if (which === 1) {
    selectedRows1.value = rows
    displayText1.value = text
  } else {
    selectedRows2.value = rows
    displayText2.value = text
  }
}

// 각각의 clear 처리
function onClear(which: 1 | 2) {
  if (which === 1) {
    displayText1.value = ''
    // 필요 시 1번 그리드 선택 해제 로직을 PopupGridDialog에 이벤트/메서드로 확장해 연결
  } else {
    displayText2.value = ''
    // 필요 시 2번 그리드 선택 해제 로직도 동일하게 처리
  }
}

const content = ref('그리드 셀렉트 박스 탭 정보 불러오는 중...')
onMounted(() => {
  content.value = '그리드 셀렉트 박스 탭의 내용입니다.'
})
</script>