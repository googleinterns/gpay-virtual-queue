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
    <cookieinfo></cookieinfo>
    <cookieackno></cookieackno>
    <div class="card">
      <div class="card-content">
        <p class="title">{{shopinfo.shop.shopName}}</p>
        <p
          v-if="flag"
          class="title"
        >Number of People ahead in the queue are {{tokeninfo.peopleAhead}}</p>
        <p v-else class="title">Number of people currently in queue are {{shopinfo.customerQueue}}</p>
        <p class="subtitle">{{shopinfo.shop.shopType}}</p>
        <p class="subtitle">{{shopinfo.shop.address}}</p>
        <p class="subtitle">{{shopinfo.shop.phoneNumber}}</p>
      </div>
      <footer class="card-footer">
        <p class="card-footer-item">
          <button
            class="button is-primary is-medium"
            v-if="flag"
            v-on:click="getshopinfo()"
          >Refresh token</button>
          <button class="button is-primary is-medium" v-else v-on:click="gettoken()">Take token</button>
        </p>
        <p class="card-footer-item">
          <button
            class="button is-danger is-medium"
            v-on:click="deletetoken()"
            v-if="flag"
          >Cancel token</button>
        </p>
      </footer>
    </div>
  </div>
</template>

<script>
import NavBarCustomer from "@/components/NavBarCustomer";
import cookieinfo from "@/components/cookieinfo";
import cookieackno from "@/components/cookieackno";
import axios from "axios";

export default {
  name: "specificShop",
  components: {
    NavBarCustomer,
    cookieinfo,
    cookieackno
  },
  data() {
    return {
      shopid: this.$route.params.Id,
      shopinfo: "",
      timer: "",
      flag: false
    };
  },

  methods: {
    getshopinfo() {
      const t = this;
      axios
        .get("http://penguin.termina.linux.test:8085/shops/" + this.shopid)
        .then(function(res) {
          t.shopinfo = res.data;
          //TODO: write code related to tokenInfo here.
        });
    },

    gettoken() {
      //TODO: write http client request to get token.
    },

    deletetoken() {
      //TODO: write http client request to delete token.
    },

    cancelAutoUpdate() {
      clearInterval(this.timer);
    },

    beforeDestroy() {
      clearInterval(this.timer);
    }
  },

  created() {
    this.getshopinfo();
    this.timer = setInterval(this.getshopinfo, 1);
  }
};
</script>

<style scoped>
body {
  padding: 40px;
}

#button {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1 {
  margin: 40px 0 0;
  font: 30px Helvetica, Sans-Serif;
}

#mid {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.table {
  width: 100%;
  float: left;
}
.table thead th,
.table tbody tr td {
  text-align: center;
}
</style>