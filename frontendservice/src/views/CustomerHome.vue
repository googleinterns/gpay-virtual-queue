/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="home">
    <h1>Welcome customer!</h1>
    <p>
      This page will be Home page for the customers. 
    </p>
    <p>
      ToDo: Implement CustomerHome page features including NavBar, Search Bar, MyTokens and AllShops.
    </p>  
    <div>
      <router-link to="/my-tokens">My Tokens</router-link>
    </div>
    <div v-if="!isLoggedIn">
      <router-link to="/about">Back</router-link>
    </div>
    <br />
  </div>
</template>

<script>
import firebase from "firebase";

export default {
  name: "customerHome",

  data() {
    return {
      isLoggedIn: false,
    };
  },
  created() {
    var user = firebase.auth().currentUser;
    if (user) {
      this.isLoggedIn = true;
    }
  },
};
</script>

<style scoped>
a {
  margin-top: 1%;
  width: 10%;
  cursor: pointer;
}
</style>
