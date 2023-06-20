import { createApp } from 'vue';
import './style.css';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'
// @ts-ignore
import App from './App.vue';

const APP = createApp(App);

APP.use(ElementPlus);
APP.mount('#main');
