<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/payara-logo.png">
    <h1 class="md-display-1">Payara Instance Name Generator</h1>
    <APICaller msg="Generate Name" url="http://localhost:8081/api/name/random" />
  </div>
</template>

<script>
import APICaller from './components/APICaller.vue'

export default {
  name: 'app',
  components: {
    APICaller
  }
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
