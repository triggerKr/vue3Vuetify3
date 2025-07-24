import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vuetify from 'vite-plugin-vuetify'
import path from 'path'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [
    vue(),
    vuetify({ styles: 'sass' })
  ],
    resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src') // '@'를 'src'로 매핑
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8084', // Spring Boot 서버 주소
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    },
    port: 3000, // 개발 서버 포트
    host: 'localhost', // 호스트 이름
    strictPort: true // 지정된 포트가 사용 중이면 오류 발생
  }
})
