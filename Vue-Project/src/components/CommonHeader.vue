<template>
  <header class="my-header" style="width: 100%">
    <div class="l-content">
      <el-button
        type="info"
        @click="handleMenu"
        icon="el-icon-menu"
        round
      ></el-button>
    </div>
    <div class="r-content">
      <!-- 右边的图片 -->
      <el-dropdown trigger="hover" class="pic">
        <img :src="userImg" alt="" class="userImg" />
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-user-solid"
            >用户信息</el-dropdown-item
          >
          <el-dropdown-item icon="el-icon-s-promotion" @click.native="logout"
            >登出</el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </header>
</template>
<style>
header {
  /*盒子布局*/
  display: flex;
  height: 100%;
  /*让两个盒子中间用空格隔开*/
  justify-content: space-between;
  /*让两个盒子均居中排列*/
  align-items: center;
}
/*左边的容器盒子*/
.l-content {
  /*内部元素居中分布*/
  display: flex;
  align-content: center;
}
.pic {
  margin-left: 95%;
}
.userImg {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.r-content {
  width: 100%;
}
.my-header {
  background: #a2cbf7;
}
</style>
<script>
export default {
  data() {
    return {
      userImg: require("../assets/logo.png"),
    };
  },
  methods: {
    handleMenu: function () {
      /*要改变vuex中的状态，只可以用mutation中的方法*/
      this.$store.commit("collapseMenu");
    },
    logout: function () {
      this.$http
        .delete("/admin/logout", {
          headers: {
            token: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          if (res.data === "token无效") {
            // 清空本地token
            localStorage.setItem("token", "");
            // 强制跳转登录界面
            this.$router.push("/login");
            // 弹出提示款
            this.$message.error("登录过期，请重新登录");
          } else if (res.data.code === "0") {
            // 清空token
            localStorage.setItem("token", "");
            // 强制跳转
            this.$router.push("/login");
            // 打印信息
            this.$message.success(res.data.msg);
          }
        });
    },
  },
  computed: {},
};
</script>
