import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Room from '../views/Room.vue'
import User from '../views/User.vue'
import Contract from '../views/Contract.vue'
import Bill from '../views/Bill.vue'
import TenantHome from '../views/TenantHome.vue'
import TenantBill from '../views/TenantBill.vue'
import Register from '../views/Register.vue'
import TenantMarket from '@/views/TenantMarket.vue'
import Community from '../views/Community.vue'
import AppService from '../views/AppService.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'login', component: Login },
    {
      path: '/layout',
      name: 'layout',
      component: Layout,
      children: [ 
        { path: '/room', component: Room },
        { path: '/user', component: User },
        { path: '/contract', component: Contract },
        { path: '/bill', component: Bill },
        { path: '/tenant-home', component: TenantHome },
        { path: '/tenant-bill', component: TenantBill },
        { path: '/register', component: Register },
        { path: '/tenant-market', component: TenantMarket },
        { path: '/community', component: Community },
        { path: '/service', component: AppService } // 全新的综合工单路由
      ]
    }
  ]
})

export default router