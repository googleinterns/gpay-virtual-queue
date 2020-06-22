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