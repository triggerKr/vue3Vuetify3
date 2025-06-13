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
      '@': path.resolve(__dirname, 'src') // ✅ '@'를 'src'로 매핑
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8084', // Spring Boot 서버 주소
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
