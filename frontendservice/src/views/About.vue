/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div class="about">
    <h1>Virtual Queues</h1>
    <div class="tile is-ancestor">
      <div class="tile is-parent">
        <article class="tile is-child box one">
          <p class="title">About</p>
          <p class="subtitle">
            This is the first thing any visitor gets to view on the platform.
            This acts like an introduction or more like a welcome page for the
            Virtual Queues platform. Information regarding how the system works
            is mentioned here. It is not mandatory for the user to be authorized
            or logged in to the system for viewing this part of the platform.
            From here, a user can enter the web app as a customer or a
            shop-owner, whichever profile suits their needs. Links for the same
            are available at the bottom of the page.
          </p>
        </article>
      </div>
      <div class="tile is-parent">
        <article class="tile is-child box two">
          <p class="title">Register</p>
          <p class="subtitle">
            Depending on the requirements, a visitor can become a customer or a
            shop-owner. No authentication or verification is required for a
            customer to enter the platform. If the current user is a shop-owner,
            they need to sign up if they are new, else they have to login to
            reveal their identity. Social Sign In (using Google account) is also
            available as an option for the users. But they need to their email
            id verified for making use of all the features that the platform has
            to offer.
          </p>
        </article>
      </div>
      <div class="tile is-parent">
        <article class="tile is-child box three">
          <p class="title">Customers</p>
          <p class="subtitle">
            By default, any visitor who has not logged in to the system is
            treated as a customer. Such users are allowed to view the details of
            all the shops registered on the platform. They can then decide and
            generate a token for themselves for whichever shop they wish to
            visit. They can also view the details of all of their own active
            tokens and the waiting time for their turn. They can also search
            shops using the Search bar in the Home page. Token withdrawl feature
            is also available.
          </p>
        </article>
      </div>
      <div class="tile is-parent">
        <article class="tile is-child box four">
          <p class="title">Shop-owners</p>
          <p class="subtitle">
            Creating an account is necessary for all shop-owners. Besides, they
            also need to verify their account. They can get their shops
            registered on the platform by filling the details in the form. Each
            registered shop-owner can distribute tokens to the customers who
            wish to visit the shop in a first come, first served manner. They
            need to update the current active token number and total number of
            tokens alloted by them in realtime. The token is deleted by the
            shop-owner from the database once the customer exits the shop.
          </p>
        </article>
      </div>
    </div>
    <a v-if="!isLoggedIn" class="button">
      <router-link to="/home">Enter as customer</router-link>
    </a>
    <br />
    <a v-if="!isLoggedIn" class="button">
      <router-link to="/login">Enter as shop-owner</router-link>
    </a>
    <a v-if="isLoggedIn" class="button">
      <router-link to="/home">Continue to Home</router-link>
    </a>
  </div>
</template>

<script>
import firebase from "firebase";

export default {
  name: "about",
  data() {
    return {
      isLoggedIn: false,
      currentUser: false,
      isVerified: false,
    };
  },
  created() {
    var user = firebase.auth().currentUser;
    if (user) {
      this.isLoggedIn = true;
      this.currentUser = firebase.auth().currentUser.email;
      if (user.emailVerified) {
        this.isVerified = true;
      }
    }
  },
};
</script>

<style scoped>
.box {
  opacity: 0.7;
}
.box:hover {
  opacity: 0.9;
}
.title {
  font-size: 1.5rem;
}
.subtitle {
  font-size: 1rem;
  text-align: justify;
}
.one {
  background: rgb(219, 68, 55);
}
.two {
  background: rgb(244, 160, 0);
}
.three {
  background: rgb(66, 133, 244);
}
.four {
  background: rgb(15, 157, 88);
}
h1 {
  color: purple;
  font-size: 3rem;
  text-align: center;
}
p {
  color: white;
}
div {
  padding: 1.25%;
}
a {
  color: white;
  background: purple;
}
</style>
