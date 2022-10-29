import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store' // './store/index' 简写为 './store' 默认不写就是/index
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import 'dayjs/locale/zh-cn'
import locale from 'element-plus/lib/locale/lang/zh-cn';

import * as echarts from 'echarts'

import './assets/css/global.css'

// Vue3项目， app类似于Vue2的vm，app比vm更轻
const app = createApp(App)
    .use(store)
    .use(router)
    .use(ElementPlus,{ locale })
    .mount('#app')

app.echarts = echarts