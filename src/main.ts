import { createApp } from 'vue'
import App from './App.vue'
import 'vuetify/styles'
import vuetify from './plugins/vuetify'
import router from './plugins/router'
import pinia from './store'
import { loadFonts } from './plugins/webfontloader'
import { LoadingPlugin } from 'vue-loading-overlay';

//import 'ag-grid-community/styles/ag-grid.css'
import 'ag-grid-community/styles/ag-theme-alpine.css'; // 테마 중 하나

await loadFonts()

createApp(App)
  .use(vuetify)
  .use(router)
  .use(pinia)
  .use(LoadingPlugin)
  .mount('#app')
