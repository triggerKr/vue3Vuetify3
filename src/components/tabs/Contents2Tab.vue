<template>
  <v-container fluid>
    <!-- 제목 -->
    <v-card class="ma-4 pa-4">
      <v-card-title>
        사용자 관리
      </v-card-title>

      <!-- 조회조건 -->
      <v-card class="pa-4 mb-4" variant="outlined">
        <v-row dense>
          <v-col cols="3">
            <v-text-field
              v-model="search.name"
              label="이름"
              variant="outlined"
              density="compact"
              clearable
            />
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="search.age"
              label="나이"
              type="number"
              variant="outlined"
              density="compact"
              clearable
            />
          </v-col>
          <v-col cols="3" class="d-flex align-center">
            <v-btn color="primary" @click="doSearch">조회</v-btn>
          </v-col>
        </v-row>
      </v-card>

      <!-- 리스트 (AG Grid) -->
      <v-card class="pa-2">
        <div class="ag-theme-alpine" style="height: 400px; width: 100%;">
          <AgGridVue
            :columnDefs="columnDefs"
            :rowData="rowData"
            :defaultColDef="defaultColDef"
            :pagination="true"
            :paginationPageSize="10"
            :animateRows="true"
            @grid-ready="onGridReady"
          />
        </div>
      </v-card>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { AgGridVue } from "ag-grid-vue3";
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-alpine.css";

// 조회조건
const search = ref({
  name: "",
  age: "",
});

// 컬럼 정의
const columnDefs = ref([
  { headerName: "ID", field: "id", width: 90 },
  { headerName: "이름", field: "name", flex: 1 },
  { headerName: "나이", field: "age", width: 100, filter: "agNumberColumnFilter" },
]);

// 기본 컬럼 속성
const defaultColDef = {
  resizable: true,
  sortable: true,
  filter: true,
  minWidth: 100,
};

// 샘플 데이터
const allData = [
  { id: 1, name: "홍길동", age: 25 },
  { id: 2, name: "이순신", age: 31 },
  { id: 3, name: "강감찬", age: 42 },
  { id: 4, name: "을지문덕", age: 37 },
  { id: 5, name: "세종대왕", age: 53 },
];

const rowData = ref([...allData]);

// 조회 기능
const doSearch = () => {
  rowData.value = allData.filter((row) => {
    const matchName = search.value.name
      ? row.name.includes(search.value.name)
      : true;
    const matchAge = search.value.age
      ? row.age === Number(search.value.age)
      : true;
    return matchName && matchAge;
  });
};

const onGridReady = (params: any) => {
  console.log("Grid Ready!", params);
};
</script>
