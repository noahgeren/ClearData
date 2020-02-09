import Vue from "vue";
import VueRouter from "vue-router";
import Index from "@/pages/Index";
import Login from "@/pages/Login";
import Dashboard from "@/pages/Dashboard";
import NotFound from "@/pages/NotFound";


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
    path: '/dashboard/:id',
    component: Dashboard
  },
  {
    path: '*',
    component: NotFound
  }
];

const router = new VueRouter({
  mode: "history",
  base: '/',
  routes
});

export default router;
