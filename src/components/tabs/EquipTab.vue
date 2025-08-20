<template>
  <v-card class="ma-4 pa-4">
    <v-card-title>인적 정보 입력</v-card-title>
    <v-card-text style="height: 400px;">
      <!-- 위쪽 폼 -->
      <div v-for="(row, i) in rows" :key="i" class="mb-3 pa-2 border rounded">
        <v-row dense>
          <v-col cols="3" class="d-flex align-center">
            <span class="mr-2 font-bold">이름</span>
            <v-text-field v-model="row.name" variant="outlined" density="compact" hide-details />
          </v-col>
          <v-col cols="3" class="d-flex align-center">
            <span class="mr-2 font-bold">나이</span>
            <v-text-field v-model="row.age" type="number" variant="outlined" density="compact" hide-details />
          </v-col>
          <v-col cols="3" class="d-flex align-center">
            <span class="mr-2 font-bold">직업</span>
            <v-text-field v-model="row.job" variant="outlined" density="compact" hide-details />
          </v-col>
          <v-col cols="3" class="d-flex align-center">
            <span class="mr-2 font-bold">주소</span>
            <v-text-field v-model="row.address" variant="outlined" density="compact" hide-details />
          </v-col>
        </v-row>
      </div>
      <!-- AG Grid -->
      <div v-if="gridReady" class="ag-theme-alpine" style="height: 80%; width: 100%;border:1px solid #ddd; border-radius: 8px;">
          
            <AgGridVue
              ref="agGrid"
              :rowSelection="'multiple'"
              class="ag-grid"
              :rowData="commentData"
              :columnDefs="columnDefs"
              :defaultColDef="defaultColDef"
              :animateRows="true"
              :domLayout="'normal'"
              @grid-ready="onGridReady"
              :header-height="0"
              :getRowHeight="getRowHeight"
            />

      </div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";

import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
ModuleRegistry.registerModules([AllCommunityModule]);

import { AgGridVue } from "ag-grid-vue3";
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-alpine.css";
import { MultiLineCellEditor } from '@/components/ag-grid/MultiLineCellEditor';
const onGridReady = (params) => {
   params.api.sizeColumnsToFit();
};

const getRowHeight = (params) => {
  return 50; // 기본 높이 설정
  // return 28 * (Math.floor(params.data.model.length / 60) + 1);
};

defineOptions({
  components: { AgGridVue }
});
// --- 폼 데이터 ---
const rows = ref([
  { name: "홍길동", age: 30, job: "프로그래머", address: "경기도 동탄" },
  { name: "김철수", age: 40, job: "디자이너", address: "서울시 강남" },
]);

// --- AG Grid 데이터 ---
const commentData = ref([
  { commentLabel: "Comment", comment: "" },
  { commentLabel: "Comment", comment: "" },
  { commentLabel: "Comment", comment: "" },
]);

// --- 컬럼 정의 ---
const columnDefs = ref([
  {
    field: "commentLabel",
    editable: false,
    cellStyle: { fontWeight: "bold" },
    width: 120,
  },
  {
  headerName: 'Comment',
  field: 'comment',
  editable: true,flex: 1,
  // cellEditor: 'agLargeTextCellEditor', // 인라인 편집 가능
  //   cellEditorParams: {
  //       maxLength: 500,      // 최대 글자 수
  //       rows: 5,             // 기본 높이
  //       cols: 50,            // 기본 넓이
  //   },
    autoHeight: true       // 줄 수에 따라 row 높이 자동 조절
  , cellClass: 'cell-wrap-text'
  , cellStyle: {'white-space': 'normal', 'word-break': 'break-word'}
  ,cellEditor: MultiLineCellEditor, // 커스텀 에디터
  cellRenderer: (params) => {
    // 줄바꿈 유지
    const div = document.createElement('div');
    div.style.whiteSpace = 'pre-wrap';
    div.style.wordBreak = 'break-word';
    div.innerText = params.value || '';
    return div;
  },
  }
]);

const defaultColDef = { 
  resizable: true, sortable: false 
};

// --- 그리드 렌더링 컨트롤 ---
const gridReady = ref(false);

onMounted(async () => {
  await nextTick();
  gridReady.value = true;
});
</script>

<style scoped>
.font-bold {
  font-weight: bold;
}
.border {
  border: 1px solid #ddd;
}
.rounded {
  border-radius: 8px;
}
.ag-grid {
  height: 100%;
  width: 100%;
}
.ag-cell {
  white-space: normal !important; /* 여러 줄 표시 */
  line-height: 1.4em;
}

</style>
