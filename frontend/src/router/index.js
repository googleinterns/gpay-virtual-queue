import firebase from "firebase";
import Vue from "vue";
import VueRouter from "vue-router";

//ToDo: kschauhan - uncomment the below statements when their respective files are added

import Home from "@/views/Home";
// import Login from "@/views/Login";
// import SignUp from "@/views/SignUp";
import About from "@/views/About";
// import CreateShop from "@/views/CreateShop";
// import MyTokens from "@/views/MyTokens";
// import MyShops from "@/views/MyShops";
// import Verify from "@/views/Verify";
// import ForgotPassword from "@/views/ForgotPassword";

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
        },
        // {
        //     path: "/login",
        //     name: "Login",
        //     component: Login,
        //     meta: {
        //         goToHome: true,
        //     }
        // },
        // {
        //     path: "/forgot-password",
        //     name: "ForgotPassword",
        //     component: ForgotPassword,
        //     meta: {
        //         goToHome: true,
        //     }
        // },
        // {
        //     path: "/sign-up",
        //     name: "SignUp",
        //     component: SignUp,
        //     meta: {
        //         goToHome: true,
        //     }
        // },
        // {
        //     path: "/verify",
        //     name: "Verify",
        //     component: Verify,
        // },
        {
            path: "/home",
            name: "Home",
            component: Home,
        },
        // {
        //     path: "/my-tokens",
        //     name: "MyTokens",
        //     component: MyTokens,
        // },
        // {
        //     path: "/create-shop",
        //     name: "CreateShop",
        //     component: CreateShop,
        //     meta: {
        //         requiresAuth: true,
        //     },
        // },
        // {
        //     path: "/my-shops",
        //     name: "MyShops",
        //     component: MyShops,
        //     meta: {
        //         requiresAuth: true,
        //     },
        // },
    ],
});

// router.beforeEach((to, from, next) => {
//     const currentUser = firebase.auth().currentUser;
//     var verified = false; 
//     if (currentUser) {
//         verified = currentUser.emailVerified; 
//     }
//     const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
//     const goToHome = to.matched.some((record) => record.meta.goToHome); 

//     if (requiresAuth && !verified) next("login");
//     else if (goToHome && currentUser) next("home");
//     else next();
// });

export default router;