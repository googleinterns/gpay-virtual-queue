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
    <h1>Sign In</h1>
    <div class="input-icons">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" v-model="email" placeholder="Email" />
    </div>
    <div class="input-icons">
      <i @click="toggle" :class="{'fa fa-eye icon': isHidden, 'fa fa-eye-slash icon': !isHidden}"></i>
      <input class="input-field" ref="password" type="password" v-model="password" placeholder="Password" />
    </div>
    <p>{{invalidInputAlert}}</p>
    <router-link to="/forgot-password">Forgot Password</router-link>
    <br />
    <a class="button is-primary" @click="login">Sign in</a>
    <br />
    <br />
    <div>
      Or you can sign in directly with your Google account also.
      <br />
      <button @click="socialLogin" class="social-button">
        <img alt="Google Logo" src="../assets/google-logo.png" />
      </button>
    </div>
    <br />
    <div>
      Don't have an account yet? You can create one
      <router-link to="/sign-up">here</router-link>.
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

  ////////////////change this so that an already logged in user is redirected from here to home page
  // created() {
  //   var user = firebase.auth().currentUser;
  //   if (user) {
  //     firebase
  //       .auth()
  //       .signOut()
  //       .then(() => {
  //         // this.$router.replace("login");
  //       });
  //   }
  // },
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
          // alert("Oops. Could not sign in. " + err.message);
          // email = "";
          // password = "";
        });
    },
    toggle() {
      // alert(this.$refs.password);
      // var x = document.getElementById("password");
      var x = this.$refs.password;
      if (x.type === "password") {
        x.type = "text";
      } else {
        x.type = "password";
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
          // alert("Oops. " + err.message);
        });

      var user = firebase.auth().currentUser;
      // user.emailVerified = true; ///////////////////////////check
    }
  }
};
</script>

<style scoped>
/* "scoped" attribute limit the CSS to this component only */
h1 {
  color: #00d1b2;
  font-size: 2rem;
}
span {
  display: block;
  color: rgb(0, 0, 0);
  margin-top: 1%;
  /* font-size: 11px; */
}
.login {
  margin-top: 2%;
}
/* input {
  margin: 1%;
  width: 40%; */
  /* padding: 15px; */
/* } */
a {
  margin-top: 1%;
  margin-bottom: 1%;
  width: 10%;
  cursor: pointer;
}
p {
  margin-bottom: 1%;
  font-size: 0.8rem;
  color: red;
}
p a {
  text-decoration: underline;
  cursor: pointer;
}

.input-field:focus {
  border: 2px solid #00d1b2;
}

/* Style the input container */
.input-icons {
  display: flex;
  width: 40%;
  margin: 15px auto;
}

/* Style the form icons */
.icon {
  padding: 1rem;
  background: #00d1b2;
  color: white;
  min-width: 5%;
  height: 3rem;
  width: 3rem;
}

/* Style the input fields */
.input-field {
  width: 100%;
  padding: 2%;
  outline: none;
  /* text-align: center;  */
}

.social-button {
  width: 75px;
  background: white;
  padding: 10px;
  border-radius: 100%;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
  outline: 0;
  border: 0;
}
.social-button:active {
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
}
.social-button img {
  width: 100%;
}
</style>
