<template>
  <div>
    <el-container style="height: 100%">
      <el-aside width="auto">
        <common-aside class="my-aside"></common-aside>
      </el-aside>
      <el-container>
        <el-header style="width: 100%; height: 5vh">
          <common-header></common-header>
        </el-header>
        <router-view></router-view>
      </el-container>
    </el-container>
  </div>
</template>

<style>
.el-header {
  background-color: black;
  padding: 0;
}
.el-main {
  padding-top: 0;
}
</style>

<script>
import CommonAside from "../src/components/CommonAside.vue";
import CommonHeader from "../src/components/CommonHeader.vue";
export default {
  name: "page_main",
  components: {
    CommonAside,
    CommonHeader,
  },
  data() {
    return {};
  },
};
</script>
