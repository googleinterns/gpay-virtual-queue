/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="home">
    <h1>Welcome!</h1>
    <p v-if="loggedIn">Your username is {{ userName }}</p>
    <br>
    <p>This page will be common for all our users - customers as well as shop-owners. There will be a navbar having the required navigation links myShops, createShop, myTokens links depending on whether the user is loggedIn or verified.
    <br>
    <div v-if="isVerified">
      <router-link to="/create-shop">Create new shop</router-link>
    </div>
    <div v-if="isVerified">
      <router-link to="/my-shops">My Shops</router-link>
    </div>
    <div>
      <router-link to="/my-tokens">My Tokens</router-link>
    </div>
    <div v-if="isLoggedIn && !isVerified">
      <router-link to="/verify">Verify Me!</router-link>
    </div>
    <div v-if="!isLoggedIn">
      <router-link to="/about">Back</router-link>
    </div>
    <br />
    <button v-if="isLoggedIn" @click="logout">Logout</button>
  </div>
</template>

<script>
import firebase from "firebase";

export default {
  name: "home",

  data() {
    return {
      userName: "",
      uid: "",
      isLoggedIn: false,
      isVerified: false,
    };
  },
  created() {
    var user = firebase.auth().currentUser;
    if (user) {
      this.userName = user.displayName;
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
