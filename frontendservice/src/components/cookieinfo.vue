<template>
  <div>
    <div class="notification is-danger is-light">
      <button class="delete" onclick="this.parentElement.style.display='none'"></button>
      If there is no activity of taking tokens for
      <strong>1 day</strong>
      continuously, the information of your previous tokens will be erased from your browser.
      <a>
        <router-link to="/detailedcookie">Click here for the detailed Information</router-link>
      </a>
    </div>
  </div>
</template>




<style>
</style>

<script>
export default {
  name: "cookieinfo"
};
</script>