import { createApp } from 'vue'
import { createPinia } from 'pinia'

// 引入 Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/global-glass.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
// 告诉 Vue 使用 Element Plus
app.use(ElementPlus)

app.mount('#app')