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
    <ul>
      <li>
        <strong>About</strong>
        <p>
          This is the first thing any visitor gets to view on the platform. This
          acts like an introduction or more like a welcome page for the Virtual
          Queues platform. Information regarding how the system works is
          mentioned here. It is not mandatory for the user to be authorized or
          logged in to the system for viewing this part of the platform. From
          here, a user can enter the web app as a customer or a shop-owner,
          whichever profile suits their needs. Links for the same are available
          at the bottom of the page.
        </p>
      </li>
      <li>
        <strong>Login and Sign Up</strong>
        <p>
          Depending on the requirements, a visitor can become a customer or a
          shop-owner. No authentication or verification is required for a
          customer to enter the platform. If the current user is a shop-owner,
          they need to sign up if they are new, else they have to login to
          reveal their identity. Social Sign In (using Google account) is also
          available as an option for the users. But there are certain steps that
          a user needs to complete first for making use of all the features that
          the platform has to offer to the shop-owners. They need to get their
          entered email id verified before they want to register their shops on
          the platform. After that, all the implemented features of the platform
          will be made available to them.
        </p>
      </li>
      <li>
        <strong>Features for Customers</strong>
        <p>
          As mentioned above, the customers do not need to login to enter the
          platform. By default, any visitor who has not logged in to the system
          is treated as a customer automatically. Such users are allowed to view
          the details of all the shops registered on the platform. They can then
          decide and generate a token for themselves for whichever shop they
          wish to visit. They can also view the details of all of their own
          active tokens and the waiting time for their turn so that they can
          leave for shopping at the appropriate time depending on the distance
          from the shop, traffic on road and some other such factors. They can
          also search for their desired shops using the Search bar in the Home
          page. Incase of change in plans, customers can also withdraw their
          tokens which will update the entire database in realtime and the
          changes will be reflected in every users' page.
        </p>
      </li>
      <li>
        <strong>Features for Shop-owners</strong>
        <p>
          Shop-owners are the users for whom creating an account is necessary to
          unlock all the features. Besides, they also need to verify their
          account. This is important to ensure that fake shops do not get
          registered on the platform and every shop-owner is an authentic user.
          They can get their shops registered on the platform by filling the
          necessary details like shop name, shop type, phone number and address.
          Each registered shop-owner can distribute tokens to the customers who
          wish to visit the shop in a first come, first served manner. They also
          get to view all of their own registered shops and the allocated tokens
          separately. They need to update the current active token number and
          total number of tokens alloted by them in realtime so that the
          customers can have an idea of their current position in the virtual
          queue. The token is deleted by the shop-owner from the database once
          the customer exits the shop.
        </p>
      </li>
    </ul>
    <div>
      <div v-if="!isLoggedIn">
        <router-link to="/home">Enter as customer</router-link>
      </div>
      <div v-if="!isLoggedIn">
        <router-link to="/login">Enter as shop-owner</router-link>
      </div>
      <div v-if="isLoggedIn">
        <router-link to="/home">Continue to Home</router-link>
      </div>
    </div>
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
h1 {
  color: purple;
}
div {
  padding-right: 10px;
}
li {
  color: blue;
}
ul {
  text-align: justify;
}
p {
  color: green;
}
</style>
