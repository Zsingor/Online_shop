import { createRouter, createWebHistory } from 'vue-router'
import Main from "@/views/MainView.vue";
import Login from "@/views/LoginView.vue";
import Register from "@/views/RegisterView.vue";

const routes = [
    {
        path: '/',
        name: 'main',
        component: Main
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router