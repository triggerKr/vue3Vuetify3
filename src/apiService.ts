import api from './api';

// 1. GET /hello
export async function getHello() {
  const res = await api.get('/hello');
  return res.data;
}

// 2. POST /echo (text/plain)
export async function postEcho(message: string) {
  const res = await api.post('/echo', message, {
    headers: { 'Content-Type': 'text/plain' },
  });
  return res.data;
}

// 3. POST /user (Map 방식 JSON)
export async function postUserMap(user: { name: string; age: number }) {
  try {
    const res = await api.post('/user', user);
    return res.data;
  } catch (error: any) {
    if (error.response) return error.response.data;
    throw error;
  }
}

// 4. POST /user/dto (User DTO 방식)
export async function postUserDto(user: { name: string; age: number }) {
  try {
    const res = await api.post('/user/dto', user);
    return res.data;
  } catch (error: any) {
    if (error.response) return error.response.data;
    throw error;
  }
}

// 5. GET /user/{id}
export async function getUserById(id: number) {
  try {
    const res = await api.get(`/user/${id}`);
    return res.data;
  } catch (error: any) {
    if (error.response) return error.response.data;
    throw error;
  }
}

// 6. GET /search?keyword=xxx
export async function searchKeyword(keyword: string) {
  const res = await api.get('/search', { params: { keyword } });
  return res.data;
}

// 7. POST /users (List<User>)
export async function postUserList(users: { name: string; age: number }[]) {
  try {
    const res = await api.post('/users', users);
    return res.data;
  } catch (error: any) {
    if (error.response) return error.response.data;
    throw error;
  }
}

// 8. DELETE /user/{id}
export async function deleteUser(id: number) {
  try {
    const res = await api.delete(`/user/${id}`);
    return res.data;
  } catch (error: any) {
    if (error.response) return error.response.data;
    throw error;
  }
}

// 9. PUT /user/{id} (User DTO 방식)
export async function putUserDto(id: number, user: { name: string; age: number }) {
  try {
    const res = await api.put(`/user/${id}`, user);
    return res.data;
  } catch (error: any) {
    if (error.response) return error.response.data;
    throw error;
  }
}   