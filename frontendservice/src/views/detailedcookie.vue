/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div>
    <NavBarCustomer></NavBarCustomer>
    <div class="tile is-ancestor">
      <div class="tile is-parent">
        <article class="tile is-child box three">
          <p class="title">Cookies in the Application</p>
          <br />
          <p class="subtitle">
            Instead of providing sign up and login features for the customers,
            we have cosidered to include cookies which can store the token ids when the customers take
            the tokens in multiple shops.
            These token ids stored in the cookies will be utilized by the Application to give real time updates on
            the number of customers ahead so that they can plan their time accordingly.
            These cookies will be expired in 1 day if there is no activity of taking tokens by the customers.
            And no real time update can be given on the token Ids in the expired cookies.
            New cookie will be set later again when a fresh token.
            No information of the user other than the token Ids generated upon taking the token is stored in our cookies.
          </p>
        </article>
      </div>
    </div>
  </div>
</template>

<script>
import NavBarCustomer from "@/components/NavBarCustomer";
export default {
  components: {
    NavBarCustomer
  },
  name: "detailedcookie",
  data() {},
  created() {}
};
</script>

<style scoped>
.box {
  opacity: 0.7;
}

.box:hover {
  opacity: 0.9;
}

div {
  padding: 1.25%;
}

.subtitle {
  align-items: center;
  display: flex;
  font-size: 1rem;
  justify-content: center;
  text-align: justify;
}

.three {
  background: rgb(66, 133, 244);
}

.title {
  align-items: center;
  display: flex;
  font-size: 1.5rem;
  justify-content: center;
  text-align: center;
}

p {
  color: white;
}
</style>
