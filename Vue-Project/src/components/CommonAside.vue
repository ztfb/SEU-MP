<template>
  <el-menu
    default-active="1-4-1"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
    :collapse="isCollapse"
    :collapse-transition="false"
    text-color="black"
    active-text-color="#ffd04b"
  >
    <!-- <h3>石化安全生产管理系统</h3> -->
    <div class="block" style="display: flex">
      <img
        src="../../src/assets/img/logo3.png"
        alt=""
        style="height: 100%; width: 100%"
      />
      <div v-show="!isCollapse">
        <p style="line-height: 1px">admin</p>
        <p style="line-height: 1px">在线</p>
        <p style="line-height: 1px">登出</p>
      </div>
    </div>

    <el-menu-item index="1" @click="openHomePage">
      <i class="el-icon-menu"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-submenu v-for="item in hasChildren" :index="item.name" :key="item.name">
      <template slot="title">
        <i class="el-icon-view"></i>
        <span slot="title">{{ item.label }}</span>
      </template>
      <el-menu-item
        v-for="subItem in item.children"
        :index="subItem.path"
        :key="subItem.name"
        @click="openPage(subItem)"
        >{{ subItem.label }}</el-menu-item
      >
    </el-submenu>

    <el-menu-item
      v-for="item in noChildren"
      :index="item.name + ''"
      :key="item.path"
      @click="openPage(item)"
    >
      <i class="el-icon-user-solid"></i>
      <span slot="title">{{ item.label }}</span>
    </el-menu-item>
  </el-menu>
</template>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
  overflow: hidden;
}
.el-menu {
  height: 100%;
  border: none;
}
.el-menu-vertical-demo {
  background-image: url("../assets/img/aside-background_3.jpg");
}
</style>

<script>
import ALL_DATA from "../assets/data.js";

export default {
  data() {
    return {
      ALL_DATA,
      squareUrl:
        "https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png",
      sizeList: ["large", "medium", "small"],
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    openHomePage: function () {
      //当前路径等于跳转路径，不执行
      if (this.$route.fullPath === "/home") {
        //do nothing
      } else {
        this.$router.push({
          path: "/home",
          name: "page_home",
        });
      }
    },
    openPage: function (item) {
      //当前路径等于跳转路径，不执行
      if (this.$route.fullPath === item.path) {
        //do nothing
      } else {
        // console.log(item.path);
        this.$router.push({
          path: item.path,
          name: item.name,
        });
      }
    },
  },
  computed: {
    hasChildren: function () {
      return this.ALL_DATA.menuInfo.filter((item) => item.children);
    },
    noChildren: function () {
      return this.ALL_DATA.menuInfo.filter((item) => !item.children);
    },
    /*拿到在vuex中定义的状态*/
    isCollapse: function () {
      return this.$store.state.tab.isCollapse;
    },
  },
  mounted() {
    console.log(this.isCollapse);
  },
};
</script>
