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
import Home from "@/views/Home";

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
      path: "/home",
      name: "Home",
      component: Home,
    },
  ],
});

export default router;
