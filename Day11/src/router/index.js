import { createRouter, createWebHistory } from 'vue-router';
import {app , pages} from '@/config';
import Signup from '@/pages/Signup.vue';
import Login from '@/pages/Login.vue';
import AdminPage from '@/pages/AdminPage.vue';
import CustomerPage from '@/pages/CustomerPage.vue';
import { useCounterStore } from '../stores/counter';
import { useUserStore } from '../stores/userStore';

const routes = [
  { 
    path: '/', 
    redirect: '/login' 
  },
  { 
    path:pages.signup, 
    name:app.routerName.signup,
    component: Signup 
  },
  { 
    path:pages.login, 
    name:app.routerName.login,
    component: Login 
  },
  { 
    path:pages.admin, 
    name:app.routerName.admin,
    component: AdminPage 
  },
  { 
    path: pages.customer, 
    name:app.routerName.customer,
    component: CustomerPage 
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from) => {
  const counterStore = useCounterStore();
  const userStore=useUserStore();
  counterStore.increment;
  console.log('Current Count:', counterStore.count);
  counterStore.setUserAndIncrement; 
  console.log(userStore.user);
});


export default router;
