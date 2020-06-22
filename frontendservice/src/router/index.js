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

import About from "@/views/About";
import CustomerHome from "@/views/CustomerHome";
import ShopOwnerHome from "@/views/ShopOwnerHome";
import Login from "@/views/Login";
import SignUp from "@/views/SignUp";
import Verify from "@/views/Verify";
import ForgotPassword from "@/views/ForgotPassword";
import CreateShop from "@/views/CreateShop";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
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
      path: "/detailedcookie",
      name: "detailedcookie",
      component: () => import("@/views/detailedcookie"),
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
      path: "/login",
      name: "Login",
      component: Login,
      meta: {
        requiresLogout: true,
      },
    },
    {
      path: "/forgot-password",
      name: "ForgotPassword",
      component: ForgotPassword,
      meta: {
        requiresLogout: true,
      },
    },
    {
      path: "/signup",
      name: "SignUp",
      component: SignUp,
      meta: {
        requiresLogout: true,
      },
    },
    {
      path: "/verify",
      name: "Verify",
      component: Verify,
      meta: {
        requiresUnverified: true,
      },
    },
    {
      path: "/shop-owner/createshop",
      name: "CreateShop",
      component: CreateShop,
      meta: {
        requiresVerified: true,
      },
    },
  ],
});

router.beforeEach((to, from, next) => {
  const currentUser = firebase.auth().currentUser;
  var verified = false;
  if (currentUser) {
    verified = currentUser.emailVerified;
  }

  const requiresLogout = to.matched.some(
    (record) => record.meta.requiresLogout
  );
  const requiresLogin = to.matched.some((record) => record.meta.requiresLogin);
  const requiresUnverified = to.matched.some(
    (record) => record.meta.requiresUnverified
  );
  const requiresVerified = to.matched.some(
    (record) => record.meta.requiresVerified
  );

  if (requiresLogout && currentUser) next("shop-owner");
  else if (requiresLogin && !currentUser) next("customer");
  else if (requiresUnverified && verified) next("shop-owner");
  else if (requiresUnverified && !currentUser) next("customer");
  else if (requiresVerified && !verified) {
    if (currentUser) next("shop-owner");
    else next("customer");
  } else next();
});

export default router;
