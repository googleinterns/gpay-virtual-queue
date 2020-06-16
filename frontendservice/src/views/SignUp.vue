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
    <span id="info">New to Virtual Queues? Create an account now!</span>
    <div class="input-icons">
      <i class="fa fa-user icon"></i>
      <input class="input-field" type="text" v-model="name" placeholder="Name" />
    </div>
    <p id="alert">{{nameAlert}}</p>
    <div class="input-icons">
      <i class="fa fa-envelope icon"></i>
      <input class="input-field" type="text" v-model="email" placeholder="Email" />
    </div>
    <p id="alert">{{emailAlert}}</p>
    <div class="input-icons">
      <i @click="toggle" :class="{'fa fa-eye icon': isHidden, 'fa fa-eye-slash icon': !isHidden}"></i>
      <input class="input-field" ref="password" type="password" v-model="password" placeholder="Password" />
    </div>
    <p id="alert">{{passwordAlert}}</p>
    <a class="button is-primary" @click="createUser">Sign Up</a>
    <p id="alert">{{firebaseAlert}}</p>
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
      var passwordRef = this.$refs.password;
      if (passwordRef.type === "password") {
        passwordRef.type = "text";
      } else {
        passwordRef.type = "password";
      }
      this.isHidden = !this.isHidden;
    },
    getNameInputAlertIfInvalid() {
      var nameAlert = "";
      if (this.name.length <= 3 && this.name.length >= 0) {
        nameAlert = "Name must be of atleast 4 characters.";
      } else if (this.name.length > 30) {
        nameAlert = "Name can have atmost 30 characters only.";
      }
      return nameAlert;
    },
    getEmailInputAlertIfInvalid() {
      var emailAlert = "";
      // Regex to check if the input email follows correct format like *@*.*
      // eg - virtualqueues1@gmail.com is valid
      var emailRegex = new RegExp(/\w+@\w+.\w+/g);
      var emailResult = this.email.match(emailRegex);
      if (this.email.length == 0 || !emailResult) {
        emailAlert = "Invalid email address entered.";
      }  
      return emailAlert;
    },
    getPasswordInputAlertIfInvalid() {
      var passwordAlert = "";
      // Regex to check if the input password contains any digit
      // eg - xyz123 is valid
      var passwordRegex1 = new RegExp(/[0-9]/g);
      // Regex to check if the input password contains any special character (!, @, #, %, &, _ or -)
      // eg - abcde! is valid
      var passwordRegex2 = new RegExp(/(!|@|#|%|&|_|-)/g);
      var passwordResult1 = this.password.match(passwordRegex1);
      var passwordResult2 = this.password.match(passwordRegex2);
      if (this.password.length >= 0 && this.password.length < 6) {
        passwordAlert = "Password length must be greater than 5 characters.";
      } else if (!passwordResult1 && !passwordResult2) {
        passwordAlert = "Password must contain a digit or special character (!, @, #, %, &, _, -).";
      }
      return passwordAlert;
    },
    createUser: function() {
      this.nameAlert = "";
      this.emailAlert = "";
      this.passwordAlert = "";
      this.firebaseAlert = "";
      this.nameAlert = this.getNameInputAlertIfInvalid();
      this.emailAlert = this.getEmailInputAlertIfInvalid();
      this.passwordAlert = this.getPasswordInputAlertIfInvalid();

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
h1 {
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
.sign-up {
  margin-bottom: 1%;
  margin-top: 2%;
  text-align: center;
}
</style>
