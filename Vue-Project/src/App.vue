<template>
  <div id="app">
    <router-view style="height: 100%"></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
};
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
}
#app {
  height: 100vh;
}
</style>
