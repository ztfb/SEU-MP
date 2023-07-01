<template>
  <div>
    <div class="videocontainer">
      <video
        class="fullscreenvideo"
        id="bgvid"
        playsinline=""
        autoplay=""
        muted=""
        loop=""
      >
        <source src="../assets/video/login-video.mp4" type="video/mp4" />
      </video>
    </div>
    <div class="login-wrap">
      <el-form class="login-container">
        <div class="title-container">
          <img src="../assets/img/logo3.png" class="login-logo" alt="" />
          <h1 class="title">用户登录</h1>
        </div>
        <el-form-item>
          <el-input
            type="text"
            placeholder="用户名"
            v-model="userName"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            type="password"
            placeholder="密码"
            v-model="password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            style="float: left; width: 35%"
            type="primary"
            @click="login"
            >登录</el-button
          >
          <el-button
            style="float: right; width: 35%"
            type="primary"
            @click="register"
            >注册</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "page_login",
  data: function () {
    return {
      userName: "",
      password: "",
    };
  },
  methods: {
    login: function () {
      // 先进行初步的检查工作
      if (
        this.userName === "" ||
        this.userName === null ||
        this.userName === undefined
      ) {
        this.$message.error("用户名不能为空");
      } else if (
        this.password === "" ||
        this.password === null ||
        this.password === undefined
      ) {
        this.$message.error("密码不能为空");
      } else {
        // 此时可以给后端发送注册请求
        this.$http
          .get("/admin/login", {
            params: {
              userName: this.userName,
              password: this.password,
            },
          })
          .then((res) => {
            if (res.data.code === "1") {
              this.$message.error(res.data.msg);
            } else {
              // 存储token
              localStorage.setItem("token", res.data.data.token);
              // 弹窗
              this.$message.success(res.data.msg);
              // 界面路由
              this.$router.push("/main");
            }
          });
      }
    },
    register: function () {
      this.$router.push("/register");
    },
  },
  mounted() {
    var video = document.getElementById("bgvid");

    video.playbackRate = 0.5;
  },
};
</script>

<style scoped>
.login-wrap {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-left: 65%;
  padding-top: 20%;
  /* background-color: #112346; */
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}

.login-container {
  border-radius: 10px;
  margin: 0px auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #808080;
  border: 1px solid #808080;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.fullscreenvideo {
  position: absolute;

  top: 50%;

  left: 50%;

  min-width: 100%;

  min-height: 100%;

  width: auto;

  height: auto;

  z-index: -100;

  -webkit-transform: translateX(-50%) translateY(-50%);

  transform: translateX(-50%) translateY(-50%);

  -webkit-transition: 1s opacity;

  transition: 1s opacity;
}

.videocontainer {
  position: fixed;

  width: 100%;

  height: 100%;

  overflow: hidden;

  z-index: -100;
}

.videocontainer:before {
  content: "";

  position: absolute;

  width: 100%;

  height: 100%;

  display: block;

  z-index: -1;

  top: 0;

  left: 0;
}
.title-container {
  display: flex;
  justify-content: space-between;
}
.login-logo {
  width: 50%;
  height: 50%;
}
</style>
