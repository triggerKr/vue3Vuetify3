<script setup lang="ts">
import { ref, onMounted } from 'vue';

import {
    getHello,
    postEcho,
    postUserMap,
    postUserDto,
    getUserById,
    searchKeyword,
    postUserList,
    deleteUser,
    putUserDto
} from '../apiService';
const helloMsg = ref('');
const echoMsg = ref('');
const userResponse = ref<any>(null);
const userDtoResponse = ref<any>(null);
const getUser = ref<any>(null);
const searchResult = ref<any>(null);
const userListResponse = ref<any>(null);
const deleteResult = ref<any>(null);

async function runTests() {
  helloMsg.value = await getHello();

  echoMsg.value = await postEcho('안녕하세요, 태원님!');

  userResponse.value = await postUserMap({ name: '태원', age: 45 });

  userDtoResponse.value = await postUserDto({ name: '태원', age: 45 });

  getUser.value = await getUserById(1);       // 성공 케이스
  // getUser.value = await getUserById(99);   // 실패 케이스 (404)

  searchResult.value = await searchKeyword('Vue3');

  userListResponse.value = await postUserList([
    { name: '홍길동', age: 30 },
    { name: '김철수', age: 25 },
  ]);

  deleteResult.value = await deleteUser(1);
}

onMounted(() => {
  runTests();
});
</script>

<template>
  <div>
    <h2>API 테스트 결과</h2>

    <p><strong>1. /hello GET:</strong> </p>

    <p><strong>2. /echo POST:</strong> {{ echoMsg }}</p>

    <p><strong>3. /user POST (Map):</strong> {{ userResponse }}</p>

    <p><strong>4. /user/dto POST:</strong> {{ userDtoResponse }}</p>

    <p><strong>5. /user/{id} GET:</strong> {{ getUser }}</p>

    <p><strong>6. /search GET:</strong> {{ searchResult }}</p>

    <p><strong>7. /users POST (List):</strong> {{ userListResponse }}</p>

    <p><strong>8. /user/{id} DELETE:</strong> {{ deleteResult }}</p>

    <p><strong>9. /user/{id} GET (실패 케이스):</strong> {{ getUser }}</p>

    <p><strong>10. /search GET (실패 케이스):</strong> {{ searchResult }}</p>
  </div>
</template>
