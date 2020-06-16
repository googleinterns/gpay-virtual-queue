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
  <div class="login">
    <h1 class="header">Sign In</h1>
    <span id="info">Please enter your account credentials here.</span>
    <div class="input-icons">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" v-model="email" placeholder="Email" />
    </div>
    <div class="input-icons">
      <i @click="toggle" :class="{'fa fa-eye icon': isHidden, 'fa fa-eye-slash icon': !isHidden}"></i>
      <input class="input-field" ref="password" type="password" v-model="password" placeholder="Password" />
    </div>
    <p id="alert">{{invalidInputAlert}}</p>
    <router-link to="/forgot-password">Forgot Password</router-link>
    <br>
    <a class="button is-primary" @click="login">Sign in</a>
    <br>
    <br>
    <div>
      Or you can sign in directly with your Google account also.
      <br />
      <button @click="socialLogin" class="social-button">
        <img alt="Google Logo" src="../assets/google-logo.png" />
      </button>
    </div>
    <br>
    <div>
      Don't have an account yet? You can create one
      <router-link to="/signup">here</router-link>.
    </div>
    <div>
      Or go back to
      <router-link to="/about">about</router-link>.
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      invalidInputAlert: "",
      isHidden: true
    };
  },
  methods: {
    login() {
      firebase
        .auth()
        .signInWithEmailAndPassword(this.email, this.password)
        .then(user => {
          this.$router.replace("shop-owner");
        })
        .catch(err => {
          this.invalidInputAlert = "Could not sign in. " + err.message;
        });
    },
    toggle() {
      var passwordRef = this.$refs.password;
      if (passwordRef.type === "password") {
        passwordRef.type = "text";
      } else {
        passwordRef.type = "password";
      }
      this.isHidden = !this.isHidden;
    },
    socialLogin() {
      const provider = new firebase.auth.GoogleAuthProvider();
      firebase
        .auth()
        .signInWithPopup(provider)
        .then(result => {
          this.$router.replace("verify");
        })
        .catch(err => {
          this.invalidInputAlert = "Could not sign in. " + err.message;
        });
      var user = firebase.auth().currentUser;
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
  cursor: pointer;
  margin-bottom: 1%;
  margin-top: 1%;
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
.login {
  margin-top: 2%;
}
.social-button {
  background: white;
  border: 0;
  border-radius: 100%;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
  outline: 0;
  padding: 10px;
  width: 75px;
}
.social-button:active {
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
}
.social-button img {
  width: 100%;
}
</style>
