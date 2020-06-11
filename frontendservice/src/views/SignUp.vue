/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="sign-up">
    <h1>Sign Up</h1>
    <span>New to Virtual Queues? Create an account now!</span>
    <div class="input-icons">
      <i class="fa fa-user icon"></i>
      <input class="input-field" type="text" v-model="name" placeholder="Name" />
    </div>
    <p>{{nameAlert}}</p>
    <div class="input-icons">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" v-model="email" placeholder="Email" />
    </div>
    <p>{{emailAlert}}</p>
    <div class="input-icons">
      <i @click="toggle" :class="{'fa fa-eye icon': isHidden, 'fa fa-eye-slash icon': !isHidden}"></i>
      <input class="input-field" ref="password" type="password" v-model="password" placeholder="Password" />
    </div>
    <p>{{passwordAlert}}</p>
    <a class="button is-primary" @click="createUser">Sign Up</a>
    <p>{{firebaseAlert}}</p>
    <div>
      If you have already registered, go to <router-link to="/login">login</router-link>.
    </div>
    <div>
      Or go back to <router-link to="/about">about</router-link>.
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
export default {
  name: "signUp",
  data() {
    return {
      email: "",
      password: "",
      name: "",
      isHidden: true,
      nameAlert: "",
      emailAlert: "",
      passwordAlert: "",
      firebaseAlert: ""
    };
  },
  methods: {
    toggle() {
      var x = this.$refs.password;
      if (x.type === "password") {
        x.type = "text";
      } else {
        x.type = "password";
      }
      this.isHidden = !this.isHidden;
    },
    createUser: function() {
      this.nameAlert = "";
      this.emailAlert = "";
      this.passwordAlert = "";
      this.firebaseAlert = "";
      var emailRegex = new RegExp(/\w+@\w+.\w+/g); //////check thissss
      var passwordRegex1 = new RegExp(/[0-9]/g);
      var passwordRegex2 = new RegExp(/(!|@|#|%|&|_|-)/g);
      var emailResult = this.email.match(emailRegex);
      var passwordResult1 = this.password.match(passwordRegex1);
      var passwordResult2 = this.password.match(passwordRegex2);

      if (this.name.length <= 3 && this.name.length >= 0) {
        this.nameAlert = "Name must be of atleast 3 characters.";
      } else if (this.name.length > 30) {
        this.nameAlert = "Name can have atmost 30 characters only.";
      }
      if (this.email.length == 0 || !emailResult) {
        this.emailAlert = "Invalid email address entered.";
      }
      if (this.password.length >= 0 && this.password.length < 6) {
        this.passwordAlert = "Password length must be greater than 5 characters.";
      } else if (!passwordResult1 && !passwordResult2) {
        this.passwordAlert = "Password must contain a digit or special character (!, @, #, %, &, _, -).";
      }
      if (this.nameAlert == "" && this.emailAlert == "" && this.passwordAlert == "") {
        firebase
          .auth()
          .createUserWithEmailAndPassword(this.email, this.password)
          .then(
            user => {
              firebase
                .auth()
                .currentUser.updateProfile({
                  displayName: this.name
                })
                .then(function() {
                })
                .catch(function(error) {
                  this.firebaseAlert = "Could not create user. " + err.message;
                });

              this.$router.replace("verify");
            },
            err => {
              this.firebaseAlert = "Could not create user. " + err.message;
            }
          );
      }
    }
  }
};
</script>

<style scoped>
.sign-up {
  margin-top: 2%;
  margin-bottom: 1%;
}
.input-icons i {
  position: absolute;
}
.icon {
  padding-top: 1.4rem;
  padding-left: 0.2rem;
  color: #00d1b2;
  min-width: 5%;
  text-align: center;
}
.input-field {
  padding: 1%;
  text-align: center;
}
input {
  margin: 1%;
  width: 40%;
}
h1 {
  color: #00d1b2;
  font-size: 2rem;
}
a {
  margin-top: 1%;
  margin-bottom: 1%;
  width: 10%;
  cursor: pointer;
}
span {
  display: block;
  color: rgb(0, 0, 0);
  margin-top: 1%;
}
p {
  margin-bottom: 1%;
  font-size: 0.8rem;
  color: red;
}
</style>
