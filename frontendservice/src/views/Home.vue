/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="home">
    <h1>Here Ishita's page will be present</h1>
    <p>Welcome! Your UID is {{ uid }}</p>
    <p>Welcome! Your userName is {{ userName }}</p>
    <br />
    <p>
      Lorem Ipsum is simply dummy text of the printing and typesetting industry.
      Lorem Ipsum has been the industry's standard dummy text ever since the
      1500s, when an unknown printer took a galley of type and scrambled it to
      make a type specimen book. It has survived not only five centuries, but
      also the leap into electronic typesetting, remaining essentially
      unchanged. It was popularised in the 1960s with the release of Letraset
      sheets containing Lorem Ipsum passages, and more recently with desktop
      publishing software like Aldus PageMaker including versions of Lorem
      Ipsum.
    </p>
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
