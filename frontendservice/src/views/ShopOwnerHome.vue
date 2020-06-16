/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="home">
    <h1 v-if="isLoggedIn">Welcome! {{ name }}</h1>
    <p>
      This page will be Home page for the shop-owners. 
    </p>
    <p>
      ToDo: Implement ShopOwnerHome page features including NavBar, Search Bar, MyShops and AddShop.
    </p> 
    <div v-if="isVerified">
      <router-link to="/create-shop">Create new shop</router-link>
    </div>
    <div v-if="isVerified">
      <router-link to="/my-shops">My Shops</router-link>
    </div>
    <div v-if="isLoggedIn && !isVerified">
      <router-link to="/verify">Verify Me!</router-link>
    </div>
    <div v-if="!isLoggedIn">
      <router-link to="/about">Back</router-link>
    </div>
    <br />
    <a class="button is-danger" v-if="isLoggedIn" @click="logout">Logout</a>
  </div>
</template>

<script>
import firebase from "firebase";

export default {
  name: "shopOwnerHome",

  data() {
    return {
      name: "",
      uid: "",
      isLoggedIn: false,
      isVerified: false,
    };
  },
  created() {
    var user = firebase.auth().currentUser;
    if (user) {
      this.name = user.displayName;
      this.uid = user.uid;
      this.isLoggedIn = true;
      if (user.emailVerified) {
        this.isVerified = true;
      }
    }
  },
  methods: {
    logout: function() {
      firebase
        .auth()
        .signOut()
        .then(() => {
          this.$router.replace("login");
        });
    },
  },
};
</script>

<style scoped>
button {
  margin-top: 1%;
  width: 10%;
  cursor: pointer;
}
</style>
