import { createApp } from 'vue'
import App from './App.vue'
import 'vuetify/styles'
import vuetify from './plugins/vuetify'
import router from './plugins/router'
import pinia from './store'

import { LoadingPlugin } from 'vue-loading-overlay';

// AG-Grid 모듈 등록 (추가!)
import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
// 이 코드를 main.ts에 두면 AG-Grid를 사용하는 모든 컴포넌트에서 문제가 발생하지 않습니다.
ModuleRegistry.registerModules([AllCommunityModule]);

createApp(App)
  .use(router)
  .use(vuetify)
  .use(pinia)
  .use(LoadingPlugin)
  .mount('#app')
