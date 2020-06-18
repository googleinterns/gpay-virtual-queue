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
    <form @submit.prevent="createShop()">
      <h1 class="heading">Add a Shop!</h1>
      <div class="field">
        <label class="label">Shop Name</label>
        <div class="control">
          <input class="input" type="text" placeholder="Type Shop Name" id="shopName" required />
        </div>
      </div>
      <div class="field">
        <label class="label">Address</label>
        <div class="control">
          <input class="input" type="text" placeholder="Type Address" id="address" required />
        </div>
      </div>
      <div>
        <label class="label">Phone Number</label>
        <vue-tel-input v-model="phone" @onInput="onInput" id="phoneNumber"></vue-tel-input>
      </div>
      <div class="field">
        <label class="label">Shop Type</label>
        <div class="control">
          <div class="select">
            <select id="shopType" required>
              <option>Stationary</option>
              <option>Food</option>
              <option>Furniture</option>
              <option>Electronics</option>
              <option>Jewellery</option>
              <option>Agriculture</option>
            </select>
          </div>
        </div>
      </div>
      <div class="field">
        <div class="control">
          <label class="checkbox">
            <input type="checkbox" id="conditions-check" />
            I agree to the
            <a href="#">terms and conditions</a>
          </label>
        </div>
        <div v-if="hidden" id="msg"></div>
      </div>
      <div class="field is-grouped">
        <div class="control">
          <button class="button is-link" type="submit" value="submit">Submit</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import shopOwnerNavbar from "../components/NavBar-ShopOwner";
import Vue from "vue";
import VueTelInput from "vue-tel-input";
import firebase from "firebase";

Vue.use(VueTelInput);

export default {
  name: "CreateShop",
  components: {
    shopOwnerNavbar
  },
  data() {
    return {
      hidden: true,
      isCorrect: false,
      phoneNumber: 0
    };
  },
  methods: {
    createShop: function() {
      var shopName = document.getElementById("shopName").value;
      var address = document.getElementById("address").value;
      var shopType = document.getElementById("shopType").value;
      var checkBox = document.getElementById("conditions-check");
      var phoneNo = this.phoneNumber;
      console.log(document.getElementById("phoneNumber"));
      // Regex check only permit # . characters, digits and space
      // eg - Haridwar123 is invalid.
      var addressRegex = new RegExp(/^[#.a-zA-Z0-9 ]+$/i);
      // Regex check only permit characters, digits and space
      // eg - Ishita Sweets is invalid.
      var nameRegex = new RegExp(/^[a-zA-Z0-9 ]+$/i);

      if (!checkBox.checked) {
        var checkBoxError = document.createElement("div");
        checkBoxError.innerHTML = "Please check this to continue.";
        document.getElementById("msg").appendChild(checkBoxError);
      }
      if (!addressRegex.test(address)) {
        var addressError = document.createElement("div");
        addressError.innerHTML = "Invalid address.";
        document.getElementById("msg").appendChild(addressError);
      }
      if (!nameRegex.test(shopName)) {
        var nameError = document.createElement("div");
        nameError.innerHTML = "Invalid Shop Name.";
        document.getElementById("msg").appendChild(nameError);
      }
      if (!this.isCorrect) {
        var phoneNumberError = document.createElement("div");
        phoneNumberError.innerHTML = "Invalid Phone Number.";
        document.getElementById("msg").appendChild(phoneNumberError);
      }
      if (checkBox.checked && addressRegex.test(address) && nameRegex.test(shopName) && this.isCorrect) {
        var self = this;
        axios
          .post("http://penguin.termina.linux.test:8080/shop", {
            shopOwnerId: firebase.auth().currentUser.uid,
            shopName: shopName,
            address: address,
            phoneNumber: phoneNo,
            shopType: shopType
          })
          .then(function(response) {
            // Todo: Exception handling.
            // Todo: Clear phone number.
            document.getElementById("msg").innerHTML = "Shop Added!";
            document.getElementById("shopName").value = "";
            document.getElementById("address").value = "";
          });
      }
    },
    onInput: function({ number, isValid }) {
      this.isCorrect = isValid;
      this.phoneNumber = number.international;
    }
  }
};
</script>

<style scoped>
.control {
  width: 100%;
}
form {
  padding: 2%;
}
.heading {
  font-size: 3rem;
}
#msg {
  color: red;
}
</style>
