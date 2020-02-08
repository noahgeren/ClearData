import Vue from "vue";
import VueRouter from "vue-router";
import Index from "@/pages/Index";


Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: Index
  },
];

const router = new VueRouter({
  mode: "history",
  base: '/',
  routes
});

export default router;
