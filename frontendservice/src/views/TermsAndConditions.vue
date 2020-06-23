/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div>
    <shopOwnerNavbar />
    <h1 id="terms-and-conditions">Terms and Conditions</h1>
    <!-- TODO : ikoder - Add more Terms and Conditions. -->
    <article class="message is-info">
      <div class="message-body">Restriction on app abuses</div>
    </article>
    <article class="message is-info">
      <div class="message-body">Retention of right to terminate/ban users</div>
    </article>
    <article class="message is-info">
      <div class="message-body">Disclaimer of warranties and liabilities</div>
    </article>
  </div>
</template>

<script>
import shopOwnerNavbar from "../components/NavBar-ShopOwner";
export default {
  name: "TermsAndConditions",
  components: {
    shopOwnerNavbar
  }
};
</script>

<style scoped>
#terms-and-conditions {
  font-size: 2rem;
  margin: 2%;
  text-align: center;
}
</style>
