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
        >Number of People ahead in the queue are: {{tokeninfo.customersAhead}}</p>
        <p
          v-else
          class="title"
        >Number of people currently in queue are {{shopinfo.customersInQueue}}</p>
        <p class="subtitle">{{shopinfo.shop.shopType}}</p>
        <p class="subtitle">{{shopinfo.shop.address}}</p>
        <p class="subtitle">{{shopinfo.shop.phoneNumber}}</p>
      </div>
      <footer class="card-footer">
        <p class="card-footer-item">
          <button
            class="button is-primary is-medium"
            v-if="flag"
            v-on:click="getShopInfo()"
          >Refresh token</button>
          <button class="button is-primary is-medium" v-else v-on:click="getToken()">Take token</button>
        </p>
        <p class="card-footer-item">
          <button
            class="button is-danger is-medium"
            v-on:click="deleteToken()"
            v-if="flag"
          >Cancel token</button>
        </p>
      </footer>
      <div v-if="flag==true">
        <div v-if="tokeninfo.customersAhead == 0">
          <shopTurn></shopTurn>
        </div>
      </div>
      <div v-if="flag">
        <div class="panel panel-default">
          <div class="table-responsive">
            <table class="table">
              <caption>
                <h1 style="margin: 20px 0" class="is-vcentered">Your Token information</h1>
              </caption>
              <thead>
                <th id="head">Token Number</th>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <a>{{tokeninfo.token.tokenNumber}}</a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBarCustomer from "@/components/NavBarCustomer";
import cookieinfo from "@/components/cookieinfo";
import cookieackno from "@/components/cookieackno";
import axios from "axios";
import shopTurn from "@/components/shopTurn";
import Cookie from "js-cookie";

export default {
  name: "specificShop",
  components: {
    NavBarCustomer,
    cookieinfo,
    cookieackno,
    shopTurn
  },
  data() {
    return {
      shopid: this.$route.params.Id,
      shopinfo: "",
      timer: "",
      flag: false,
      tokeninfo: null,
      allCookieList: [],
      cookieValue: "",
      token: null
    };
  },

  methods: {
    getShopInfo() {
      const t = this;
      axios
        .get("http://penguin.termina.linux.test:8085/shops/" + this.shopid)
        .then(function(res) {
          t.shopinfo = res.data;
          t.allCookieList = JSON.parse(Cookie.get("tokenid"));
          var i;
          for (i = 0; i < t.allCookieList.length; i++) {
            t.cookieValue = t.allCookieList[i].toString();
            axios({
              method: "GET",
              url:
                "http://penguin.termina.linux.test:8085/token/" + t.cookieValue
            }).then(function(res) {
              if (res.data.token.shopId == t.shopid) {
                t.flag = true;
                t.tokeninfo = res.data;
              }
            });
          }
        });
    },

    getToken() {
      const t = this;
      axios({
        method: "POST",
        url: "http://penguin.termina.linux.test:8085/tokens/" + this.shopid
      }).then(function(res) {
        t.token = res.data.token;
        t.allCookieList.push(t.token.tokenId);
        Cookie.set("tokenid", JSON.stringify(t.allCookieList), {
          expires: 1
        });
        t.flag = true;
        t.getshopinfo();
      });
    },

    deleteToken() {
      const t = this;
      axios
        .delete(
          "http://penguin.termina.linux.test:8085/token?tokenId=" +
            t.tokeninfo.token.tokenId
        )
        .then(function(res) {
          t.allCookieList.splice(
            t.allCookieList.indexOf(t.tokeninfo.token.tokenId)
          );
          Cookie.set("tokenid", JSON.stringify(t.allCookieList));
          t.flag = false;
          t.getshopinfo();
        });
    },

    cancelAutoUpdate() {
      clearInterval(this.timer);
    },

    beforeDestroy() {
      clearInterval(this.timer);
    }
  },

  created() {
    this.getShopInfo();
    this.timer = setInterval(this.getShopInfo, 1);
  }
};
</script>

<style scoped>
body {
  padding: 40px;
}

#button {
  color: #2c3e50;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  margin-top: 60px;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  -webkit-font-smoothing: antialiased;
}

h1 {
  font: 30px Helvetica, Sans-Serif;
  margin: 40px 0 0;
}

#mid {
  color: #2c3e50;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  margin-top: 60px;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  -webkit-font-smoothing: antialiased;
}
.table {
  float: left;
  width: 100%;
}
.table thead th,
.table tbody tr td {
  text-align: center;
}
</style>
