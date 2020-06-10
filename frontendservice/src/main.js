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

import Vue from "vue";
import firebase from "firebase";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";

Vue.config.productionTip = false;

let app = "";
const config = {
  apiKey: "AIzaSyCT6lcX1aPbtXEkbq-4_w-04g1h-5wlHJ0",
  authDomain: "demofirebaseproj-b2df6.firebaseapp.com",
  databaseURL: "https://demofirebaseproj-b2df6.firebaseio.com",
  projectId: "demofirebaseproj-b2df6",
  storageBucket: "demofirebaseproj-b2df6.appspot.com",
  messagingSenderId: "322994385961",
  appId: "1:322994385961:web:c99328b8cc852cf8f89838",
  measurementId: "G-4ZZWFCM4J4",
};

firebase.initializeApp(config);

firebase.auth().onAuthStateChanged(() => {
  if (!app) {
    /* eslint-disable no-new */
    app = new Vue({
      router,
      render: (h) => h(App),
    }).$mount("#app");
  }
});
