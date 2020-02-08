import Vue from "vue";
import VueRouter from "vue-router";
import Index from "@/pages/Index";
import Login from "@/pages/Login";
import Dashboard from "@/pages/Dashboard";


Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: Index
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/dashboard',
    component: Dashboard
  }
];

const router = new VueRouter({
  mode: "history",
  base: '/',
  routes
});

export default router;
