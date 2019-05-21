<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/payara-logo.png">
    <h1 class="md-display-1">Payara Instance Name Generator</h1>
    <APICaller msg="Generate Name" :url="backendUrl" />
  </div>
</template>

<script>
import APICaller from './components/APICaller.vue'

export default {
  name: 'app',
  components: {
    APICaller
  },
  data: () => ({
    backendUrl: process.env.VUE_APP_BACKEND_SERVICE_URL + "/api/name/random"
  }),
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  margin-top: 60px;
}
</style>
