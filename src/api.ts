// src/api.ts
import axios from 'axios'
import { API_BASE_URL } from './constants'

const api = axios.create({
  baseURL: API_BASE_URL, 
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // 쿠키 인증이 필요한 경우 사용
})

export default api
