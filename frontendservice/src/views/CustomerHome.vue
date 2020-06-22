/* Copyright 2020 Google LLC Licensed under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License. You
may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License. */

<template>
  <div v-if="!isLoggedIn">
    <NavBarCustomer></NavBarCustomer>
    <cookieinfo></cookieinfo>
    <cookieackno></cookieackno>
    <div id="searchBarWrap" style="margin: 20px 0">
      <div class="field has-addons">
        <div class="form-group has-feedback">
          <i class="fa fa-search" style="font-size:32px"></i>
          <input id="searchBar" class="input" type="text" placeholder="Find a shop" />
        </div>
      </div>
    </div>
    <div id="wrap" class="control">
      <div class="select">
        <select>
          <option>Select option</option>
          <option>Food</option>
          <option>Furniture</option>
          <option>Electronics</option>
          <option>Stationary</option>
          <option>Jewellery</option>
          <option>Agriculture</option>
        </select>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="table-responsive">
        <table class="table">
          <thead>
            <th>Shop Name</th>
            <th>Shop Type</th>
            <th>Address</th>
            <th>Active Customers</th>
          </thead>
          <tbody>
            <tr v-for="shop in shops" :key="shop.shopId">
              <td>
                <a>
                  <router-link
                    v-bind:to="{name: 'specificShop', params: {Id: shop.shop.shopId} }"
                  >{{shop.shop.shopName}}</router-link>
                </a>
              </td>
              <td>{{shop.shop.shopType}}</td>
              <td>{{shop.shop.address}}</td>
              <td>{{shop.numberOfActiveTokens}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
import NavBarCustomer from "@/components/NavBarCustomer";
import cookieinfo from "@/components/cookieinfo";
import cookieackno from "@/components/cookieackno";
import axios from "axios";
import Cookie from "js-cookie";
export default {
  name: "customerHome",
  components: {
    NavBarCustomer,
    cookieinfo,
    cookieackno
  },
  data() {
    return {
      isLoggedIn: false,
      timer: "",
      shops: []
    };
  },

  methods: {
    apifunction() {
      var user = firebase.auth().currentUser;
      if (user) {
        this.isLoggedIn = true;
      }
      const t = this;
      axios
        .get("http://penguin.termina.linux.test:8085/shops")
        .then(function(res) {
          t.shops = res.data.shops;
        });
    },

    beforeDestroy() {
      clearInterval(this.timer);
    },

    cancelAutoUpdate() {
      clearInterval(this.timer);
    }
  },

  created() {
    this.apifunction();
    this.timer = setInterval(this.apifunction, 1);
  }
};
</script>

<style scoped>
.box {
  opacity: 0.7;
}

.box:hover {
  opacity: 0.9;
}

h1 {
  color: #000;
  font-size: 2rem;
  text-align: center;
}

#searchBar {
  border-color: #000;
  border-radius: 0.7em;
  border-width: 0.2em;
  padding: 0.2em 0.2em 0.2em 0.5em;
  text-align: center;
  width: 40em;
}

#searchBarWrap {
  display: flex;
  justify-content: center;
}

.subtitle {
  color: rgb(19, 15, 15);
  font-size: 1rem;
  text-align: justify;
}

.table {
  width: 100%;
}

.table thead th,
.table tbody tr td {
  text-align: center;
}

.title {
  color: white;
  font-size: 1.5rem;
}

#wrap {
  float: left;
  height: auto;
  margin: 0px;
  padding: 0px;
  text-align: center;
  width: 100%;
}
</style>

