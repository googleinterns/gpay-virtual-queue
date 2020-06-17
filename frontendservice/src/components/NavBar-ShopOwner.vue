/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <nav class="navbar">
    <div class="navbar-brand">
      <img src="../assets/google-logo.png" class="navbar-item" id="google-logo" />
    </div>
    <div class="navbar-menu is-active">
      <div class="navbar-start" >
        <a class="navbar-item" href="/shop-owner/shops">My Shops</a>
        <a class="navbar-item" href="/shop-owner/about">About</a>
      </div>
      <div class="navbar-end">
        <div class="navbar-item">
          <a class="button is-primary">
            <strong>Add Shop</strong>
          </a>
        </div>
        <div class="navbar-item">
          <a class="button is-danger">
            <strong>Logout</strong>
          </a>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "shop-owner-navbar"
};
</script>

<style scoped>
#google-logo {
  width: 4rem;
}
</style>
