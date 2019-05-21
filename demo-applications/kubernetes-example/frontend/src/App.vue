<template>
  <div id="app">
    <div id="content">
      <img alt="Vue logo" src="./assets/payara-logo.png">
      <h1 class="md-display-1">Payara Instance Name Generator</h1>
      <APICaller msg="Generate Name" :url="backendUrl" />
    </div>
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
    backendUrl: process.env.VUE_APP_BACKEND_SERVICE_URL + "/api/greeting"
  }),
}
</script>

<style>
html,body,#app {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
