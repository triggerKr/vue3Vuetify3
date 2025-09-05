<!-- AgGridCombobox.vue -->
<script setup>
import { ref, watch, nextTick, computed } from 'vue'
import { AgGridVue } from 'ag-grid-vue3'

// 스타일은 전역(main.ts 등)에서 한 번만 import 하세요.
// import 'ag-grid-community/styles/ag-grid.css'
// import 'ag-grid-community/styles/ag-theme-alpine.css'

const props = defineProps({
  modelValue: { type: [String, Number, Object], default: null },
  label: { type: String, default: '' },
  rows: { type: Array, default: () => [] },
  columns: { type: Array, default: () => [] }, // [{ field, headerName }, ...]
  valueField: { type: String, default: 'id' },
  labelField: { type: String, default: 'name' },
  menuHeight: { type: Number, default: 300 },
})

const emit = defineEmits(['update:modelValue', 'select'])

const open = ref(false)
const search = ref('')
const gridApi = ref(null)

const columnDefs = computed(() => {
  if (props.columns?.length) return props.columns
  const first = props.rows?.[0]
  return first ? Object.keys(first).map(k => ({ field: k })) : []
})

function onGridReady(params) {
  gridApi.value = params.api
  nextTick(() => params.api.sizeColumnsToFit())
}

function onRowClicked(e) {
  const row = e.data
  const value = props.valueField ? row?.[props.valueField] : row
  const label = props.labelField ? row?.[props.labelField] : (value ?? '')
  emit('update:modelValue', value)
  emit('select', row)
  search.value = label ?? ''
  open.value = false
}

// rows가 바뀌면 컬럼 폭만 다시 맞춰줍니다.
watch(() => props.rows, async () => {
  await nextTick()
  gridApi.value?.sizeColumnsToFit()
})

// 메뉴를 열 때 현재 검색어/폭 반영 + 선택 상태 동기화
watch(open, async v => {
  if (!v) return
  await nextTick()
  if (search.value) gridApi.value?.setQuickFilter(search.value)
  gridApi.value?.sizeColumnsToFit()
  syncGridSelectionFromModel()
})

// 검색어 → Quick Filter (디바운스)
let qfTimer
watch(search, v => {
  clearTimeout(qfTimer)
  qfTimer = setTimeout(() => {
    gridApi.value?.setQuickFilter(v || '')
  }, 120)
})

function onClear() {
  search.value = ''
  emit('update:modelValue', null)
  gridApi.value?.deselectAll()
}

// 외부 v-model이 바뀔 때 입력 박스 라벨과 그리드 선택 상태를 동기화
watch(() => props.modelValue, () => {
  syncInputFromModel()
  syncGridSelectionFromModel()
}, { immediate: true })

function syncInputFromModel() {
  if (props.modelValue == null) {
    // 선택 해제 상태
    // 사용자가 검색 중일 수 있으므로 입력값은 비우되 강제하지 않으려면 아래 줄을 주석 처리 가능
    search.value = ''
    return
  }
  const row = (props.rows || []).find(r => {
    const v = props.valueField ? r?.[props.valueField] : r
    return v === props.modelValue
  })
  if (row) {
    const label = props.labelField ? row?.[props.labelField] : props.modelValue
    search.value = label ?? ''
  }
}

function syncGridSelectionFromModel() {
  if (!gridApi.value) return
  const target = props.modelValue
  gridApi.value.forEachNode(node => {
    const v = props.valueField ? node.data?.[props.valueField] : node.data
    node.setSelected(v === target)
  })
}
</script>

<template>
  <v-menu
    v-model="open"
    :close-on-content-click="false"
    location="bottom start"
    transition="fade-transition"
    offset="4"
  >
    <template #activator="{ props: actProps }">
      <v-text-field
        v-bind="actProps"
        v-model="search"
        :label="label"
        clearable
        @focus="open = true"
        @click="open = true"
        @click:clear="onClear"
        :append-inner-icon="open ? 'mdi-chevron-up' : 'mdi-chevron-down'"
        @click:append-inner="open = !open"
      />
    </template>

    <v-card elevation="2" class="pa-0" style="width: 100%; max-width: none;">
      <div class="ag-theme-alpine" :style="{ width: '100%', height: menuHeight + 'px' }">
        <AgGridVue
          :columnDefs="columnDefs"
          :rowData="rows"
          rowSelection="single"
          :suppressCellFocus="true"
          :animateRows="true"
          @grid-ready="onGridReady"
          @row-clicked="onRowClicked"
        />
      </div>
    </v-card>
  </v-menu>
</template>