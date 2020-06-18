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

<template>
  <div class="verify">
    <h1 class="header">Verification status</h1>
    <div>
      <span id="info" v-if="!status">Your account has not been verified.</span>
      <span id="info" v-else>You have already verified your account!</span>
    </div>
    <span id="info">An email will be sent to {{email}} for verification.</span>
    <br>
    <a class="button is-primary" v-if="isLoggedIn && !status" @click="verify">Send a verification mail</a>
    <br>
    <a class="button is-danger" v-if="isLoggedIn" @click="logout">Logout</a>
    <br>
    <br>
    <div v-if="isLoggedIn">
      <router-link to="/shop-owner">Continue to Home</router-link>
    </div>
    <div>
      Or go back to
      <router-link to="/about">About</router-link>.
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
export default {
  name: "verify",
  data() {
    return {
      email: "",
      status: false,
      isLoggedIn: false
    };
  },
  created() {
    var user = firebase.auth().currentUser;
    if (user) {
      this.email = user.email;
      this.status = user.emailVerified;
      this.isLoggedIn = true;
    }
  },
  methods: {
    verify: function() {
      firebase
        .auth()
        .currentUser.sendEmailVerification()
        .then(user => {
          alert("Verification email sent! Kindly verify to be an authorized shop-owner."); 
          this.$router.replace("shop-owner");
        })
        .catch(error => {
          alert("Oops. Could not send verification mail. Deleting the user. Please create a new valid account. " + error.message);
          var user = firebase.auth().currentUser;
          user.delete();
        });
    },
    logout: function() {
      firebase
        .auth()
        .signOut()
        .then(() => {
          this.$router.replace("login");
        });
    }
  }
};
</script>

<style scoped>
.button {
  cursor: pointer;
  margin-bottom: 1%;
  margin-top: 1%;
}
.header {
  color: #00d1b2;
  font-size: 2rem;
}
#info {
  display: block;
  color: rgb(0, 0, 0);
  margin-top: 1%;
}
.verify {
  margin-top: 2%;
}
</style>
