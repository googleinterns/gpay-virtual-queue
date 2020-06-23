/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div id="footer">
    <div class="notification is-success is-light">
      <button class="delete" onclick="this.parentElement.style.display='none'"></button>
      We use
      <strong>Cookies</strong>
      that allow our website to recognize your device
      and store details about your tokens.
      The cookie is set once you take a token.
      <a>
        <router-link to="/detailedcookie">Click here for the detailed Information</router-link>
      </a>
    </div>
  </div>
</template>

<style>
#footer {
  bottom: 0;
  position: fixed;
  width: 100%;
}
</style>

<script>
export default {
  name: "cookieackno"
};
</script>
