import Vue from "vue";
import VueRouter from "vue-router";
import Index from "@/pages/Index";
import Login from "@/pages/Login";


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
];

const router = new VueRouter({
  mode: "history",
  base: '/',
  routes
});

export default router;
