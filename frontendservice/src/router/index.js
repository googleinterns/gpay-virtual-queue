import firebase from "firebase";
import Vue from "vue";
import VueRouter from "vue-router";

//ToDo: kschauhan - import vue components, edit paths and router when more .vue files are pushed

import About from "@/views/About";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [{
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
            component: About
        }
    ],
});

export default router;