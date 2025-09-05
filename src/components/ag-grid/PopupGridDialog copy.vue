<template>
  <v-dialog 
    v-model="internalDialog"
    max-width="300px"
    location="bottom start"
    origin="top start"
    location-strategy="connected"
    :scrim="false"
    :close-on-content-click="false"
    scroll-strategy="reposition">

    <!-- 버튼을 부모에서 주입 
     v-dialog의 activator 슬롯을 받아서, 우리 컴포넌트의 activator 슬롯으로 전달
     -->
    <template #activator="{ props: act }">
      <slot name="activator" v-bind="{ props: act }" />
    </template>

    <v-card elevation="50"
      style="height: 600px; 
      display: flex; 
      flex-direction: column;">
      <!-- <v-card-title class="text-h6">AG Grid 팝업</v-card-title> -->

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
        <v-btn @click="close">닫기</v-btn>
        <v-btn @click="confirm">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, nextTick } from 'vue'
import { AgGridVue } from 'ag-grid-vue3'
import type { ColDef, GridApi, RowNode, IRowNode } from 'ag-grid-community'

// 이 선언이 있어야 Volar가 "우리 컴포넌트에 activator 슬롯이 있다"라고 인식합니다.
defineSlots<{
  activator?: (slotProps: { props: Record<string, any> }) => any
  default?: () => any
}>()

defineOptions({ components: { AgGridVue } })

const props = defineProps<{
  modelValue: boolean
  rowData: any[]
  columnDefs: ColDef[]
  defaultColDef: ColDef
}>()

const emit = defineEmits(['update:modelValue', 'select'])

const internalDialog = ref(props.modelValue)
watch(() => props.modelValue, async (val) => {
  internalDialog.value = val
  if (val && gridApi.value) {
    await nextTick()
    gridApi.value.sizeColumnsToFit()
  }
})
watch(internalDialog, (val) => emit('update:modelValue', val))

const gridApi = ref<GridApi | null>(null)
const columnApi = ref<any | null>(null)

const onGridReady = (params: { api: GridApi; columnApi: any }) => {
  gridApi.value = params.api
  columnApi.value = params.columnApi
  setTimeout(() => params.api.sizeColumnsToFit(), 0)
}

const isRowSelectable = (node: IRowNode) => node.data.make !== 'Ford'

const close = () => { internalDialog.value = false }
const confirm = () => {
  if (!gridApi.value) return
  const selected = gridApi.value.getSelectedRows()
  emit('select', selected)
  internalDialog.value = false
}
</script>

<style scoped>
.grid-container { flex: 1 1 auto; min-height: 0; width: 100%; }
.v-card-text { flex: 1 1 auto; padding: 0; height: 100%; display: flex; flex-direction: column; }
</style>