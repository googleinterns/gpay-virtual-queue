import Vue from 'vue'
import firebase from "firebase";
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'

Vue.config.productionTip = false

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