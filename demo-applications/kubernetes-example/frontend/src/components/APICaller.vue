<template>
  <div>
    <md-button class="md-raised md-primary" v-on:click="callAPI">
      {{ msg }}
      <md-icon>cached</md-icon>
    </md-button>
    <p class="md-body-1">{{ result }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    msg: String,
    url: String
  },
  data: () => ({
    result: undefined
  }),
  methods: {
    callAPI() {
      if (this.result === undefined) {
        this.result = "Fetching...";
      }
      axios
        .get(this.url)
        .then(response => {
          this.result = response.data;
        })
        .catch(() => {
          this.result = "Failed to reach server.";
        });
    }
  }
};
</script>

<style scoped>
  div {
    display: inline-block;
  }
  p {
    min-height: 1.5em;
  }
</style>
