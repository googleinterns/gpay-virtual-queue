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
  position: fixed;
  bottom: 0;
  width: 100%;
}
</style>

<script>
export default {
  name: "cookieackno"
};
</script>