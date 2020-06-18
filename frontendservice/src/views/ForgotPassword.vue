/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="forgotPassword">
    <h1 class="header">Forgot Password</h1>
    <span id="info">Enter the email address of the registered account whose password needs to be reset.</span>
    <div class="input-icons">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" v-model="email" placeholder="Email" />
    </div>
    <p id="alert">{{emailAlert}}</p>
    <a class="button is-primary" @click="reset">Send password reset mail</a>
    <br>
    <br>
    <div>
      Or go back to
      <router-link to="/login">login</router-link>.
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
export default {
  name: "forgotPassword",
  data() {
    return {
      email: "",
      emailAlert: ""
    };
  },
  methods: {
    reset() {
      this.emailAlert = "";
      if(this)
      firebase
        .auth()
        .sendPasswordResetEmail(this.email)
        .then(user => {
          alert("Password reset mail sent to " + this.email + ". Reset your password and login.");
          this.$router.replace("login");
        })
        .catch(error => {
          this.emailAlert = "Could not send  mail to " + this.email + ". " + error.message;
        });
    }
  }
};
</script>

<style scoped>
#alert {
  color: red;
  font-size: 0.8rem;
  margin-bottom: 1%;
}
.button {
  margin-bottom: 1%;
  margin-top: 1%;
}
.forgotPassword {
  margin-top: 2%;
}
.header {
  color: #00d1b2;
  font-size: 2rem;
}
.icon {
  background: #00d1b2;
  color: white;
  height: 3rem;
  min-width: 5%;
  padding: 1rem;
  width: 3rem;
}
#info {
  color: rgb(0, 0, 0);
  display: block;
  margin-top: 1%;
}
.input-field {
  outline: none;
  padding: 2%;
  width: 100%;
}
.input-field:focus {
  border: 2px solid #00d1b2;
}
.input-icons {
  display: flex;
  margin: 15px auto;
  width: 40%;
}
</style>
