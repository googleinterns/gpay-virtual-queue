/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div>
    <nav class="navbar" role="navigation" aria-label="main navigation">
      <div id="navbarBasicExample" class="spacing">
        <div class="navbar-start">
          <img src="@/assets/google-logo.png" alt="logo" id="google-logo" />
          <router-link class="navbar-item" to="/about">About</router-link>
          <router-link class="navbar-item" to="/customer">Shops</router-link>
        </div>
      </div>
    </nav>
  </div>
</template>

<style>
#google-logo {
  width: 3rem;
}

#logo {
  display: inline-block;
}

#nav {
  background-color: white; /*turns nav bar white*/
  color: white;
  width: 100%;
}

.spacing {
  margin-right: 100px;
}
</style>

<script>
export default {
  name: "NavBarCustomer",
  data() {
    return {
      isLoggedIn: false
    };
  },
  created() {
    var user = firebase.auth().currentUser;
    if (user) {
      this.isLoggedIn = true;
    }
  }
};
</script>
