import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Exchanges from '../components/Exchanges.vue';
import ExchangeDetail from '../components/ExchangeDetail.vue';
import TradingSessions from '../components/TradingSessions.vue';
import TradingSessionDetail from '../components/TradingSessionDetail.vue';
import ExchangeInstruments from '../components/ExchangeInstruments.vue';
import ExchangeInstrumentDetail from '../components/ExchangeInstrumentDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/exchanges',
    name: 'Exchanges',
    component: Exchanges
  },
  {
    path: '/exchange/:id',
    name: 'ExchangeDetail',
    component: ExchangeDetail,
    props: true
  },
  {
    path: '/tradingSessions',
    name: 'TradingSessions',
    component: TradingSessions
  },
  {
    path: '/tradingSession/:id',
    name: 'TradingSessionDetail',
    component: TradingSessionDetail,
    props: true
  },
  {
    path: '/exchangeInstruments',
    name: 'ExchangeInstruments',
    component: ExchangeInstruments
  },
  {
    path: '/exchangeInstrument/:id',
    name: 'ExchangeInstrumentDetail',
    component: ExchangeInstrumentDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
