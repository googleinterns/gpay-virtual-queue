/*
Copyright 2020 Google LLC
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import firebase from "firebase";
import Vue from "vue";
import VueRouter from "vue-router";

//ToDo: kschauhan - import vue components, edit paths and router when more .vue files are pushed

import About from "@/views/About";
import CustomerHome from "@/views/CustomerHome";
import ShopOwnerHome from "@/views/ShopOwnerHome";
import SignUp from "@/views/SignUp";

Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    {
      path: "*",
      redirect: "/about",
    },
    {
      path: "/",
      redirect: "/about",
    },
    {
      path: "/about",
      name: "About",
      component: About,
    },
    {
      path: "/customer",
      name: "CustomerHome",
      component: CustomerHome,
      meta: {
        requiresLogout: true,
      },
    },
    {
      path: "/shop-owner",
      name: "ShopOwnerHome",
      component: ShopOwnerHome,
      meta: {
        requiresLogin: true,
      },
    },
    {
      path: "/sign-up",
      name: "SignUp",
      component: SignUp,
      meta: {
        requiresLogout: true,
      }
    },
  ],
});

router.beforeEach((to, from, next) => {
  const currentUser = firebase.auth().currentUser;
  var verified = false;
  if (currentUser) {
    verified = currentUser.emailVerified;
  }

  const requiresLogout = to.matched.some((record) => record.meta.requiresLogout);
  const requiresLogin = to.matched.some((record) => record.meta.requiresLogin);

  if (requiresLogout && currentUser) next("shop-owner");
  else if (requiresLogin && !currentUser) next("customer");
  else next();
});

export default router;
