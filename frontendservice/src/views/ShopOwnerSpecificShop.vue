/*
Copyright 2020 Google LLC
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

<template>
  <div>
    <shopOwnerNavbar />
    <table class="table">
      <thead>
        <th>Shop Name</th>
        <th>Shop Address</th>
        <th>Shop Type</th>
        <th>Phone Number</th>
      </thead>
      <tbody>
        <td>{{shopList['shop'].shopName}}</td>
        <td>{{shopList['shop'].address}}</td>
        <td>{{shopList['shop'].shopType}}</td>
        <td>{{shopList['shop'].phoneNumber}}</td>
      </tbody>
    </table>
    <table class="table">
      <thead>
        <th>#</th>
        <th>Token Number</th>
        <th>Delete</th>
      </thead>
      <tbody v-if="renderComponent">
        <tr v-for="(token, index) in tokenList" :key="index">
          <td>{{index+1}}</td>
          <td>{{token.tokenNumber}}</td>
          <td>
            <button @click="deleteToken(token.tokenId)">x</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import shopOwnerNavbar from "../components/NavBar-ShopOwner";
import axios from "axios";
export default {
  name: "ShopOwnerSpecificShop",
  components: {
    shopOwnerNavbar
  },
  props: ["id"],
  data() {
    return {
      shopList: "",
      tokenList: [],
      renderComponent: true
    };
  },
  methods:{
    deleteToken: function(tokenId) {
      // TODO : ikoder - error handling in case of server error.
      axios
        .put("http://penguin.termina.linux.test:8080/token/", {
          tokenId: tokenId,
          tokenStatus: "CANCELLED_BY_SHOP_OWNER"
        });
      this.renderComponent = false;
      var self = this;
      this.$nextTick(() => {
        axios
          .get("http://penguin.termina.linux.test:8080/tokens/" + self.id)
          .then(response => (self.tokenList = response.data['tokenList']));
        self.renderComponent = true;
      });
    }
  },
  created() {
    // TODO : ikoder - error handling in case of server error.
    axios
      .get("http://penguin.termina.linux.test:8080/shops/" + this.id)
      .then(response => (this.shopList = response.data));
    var self = this;
    setInterval(function() {
      axios
        .get("http://penguin.termina.linux.test:8080/tokens/" + self.id)
        .then(response => (self.tokenList = response.data['tokenList']));
    }, 2000 /* milliseconds */);
  }
};
</script>

<style scoped>
.table {
  width: 100%;
}
.table thead th,
.table tbody td {
  text-align: center;
}
.table tbody tr td button:hover {
  cursor: pointer;
  background-color: aquamarine;
}
</style>
