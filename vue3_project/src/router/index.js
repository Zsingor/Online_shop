import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/HomeView.vue'
import Login from '../views/LoginView.vue'
import Register from '../views/RegisterView.vue'
import Product from "@/views/ProductView.vue";
import Detail from "@/views/DetailView.vue";
import Shopcart from "@/views/ShopcartView.vue";
import Person from "@/views/PersonView.vue";
import Order from "@/views/OrderView.vue";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    },
    {
        path: '/product',
        name: 'product',
        component: Product
    },
    {
        path: '/detail',
        name: 'detail',
        component: Detail
    },
    {
        path: '/shopcart',
        name: 'shopcart',
        component: Shopcart
    },
    {
        path: '/person',
        name: 'person',
        component: Person
    },
    {
        path: '/order',
        name: 'order',
        component: Order
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router