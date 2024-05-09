import './assets/reset.css'
import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus' //导入element-plus
import 'element-plus/dist/index.css' //导入element-plus的样式
import router from './router' //导入路由
import * as ElementPlusIconsVue from '@element-plus/icons-vue' //导入图标
import * as echarts from 'echarts' //导入echarts

const app = createApp(App)
//关闭警告信息
app.config.warnHandler = () => null;

/** 定义一个函数，用于对象链式取值 */
const getObjChainingVal = (obj, keyName) => {
    return tempObj
}
app.config.globalProperties.getObjChainingVal = getObjChainingVal;
/**定义变量$baseUrl，用于全局的路径使用**/
app.config.globalProperties.$baseUrl = import.meta.env.VITE_BASE_API;
app.provide('baseUrl', import.meta.env.VITE_BASE_API);


// 全局挂载 echarts
app.provide('echarts', echarts);

app.use(ElementPlus)
app.use(router);
//引入所有的icon
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')