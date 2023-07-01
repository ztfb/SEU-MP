<template>
  <el-row :gutter="20">
    <el-col :span="16">
      <tencent-map ref="tencentMap" class="graph" @changePosition="positionChange($event)"></tencent-map>
    </el-col>
    <el-col :span="8">
      <div style="margin: 20px">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>新建巡检点</span>
            <!-- <el-button style="float: right; padding: 3px 0" type="text"
              >操作按钮</el-button
            > -->
          </div>
          <el-form
            :label-position="labelPosition"
            label-width="80px"
            :model="formLabelAlign"
          >
            <el-form-item label="巡检点类型">
              <el-input v-model="formLabelAlign.type"></el-input>
            </el-form-item>
            <el-form-item label="巡检点编号">
              <el-input v-model="formLabelAlign.number"></el-input>
            </el-form-item>
            <el-form-item label="巡检点区域">
              <el-select
                v-model="formLabelAlign.area"
                placeholder="请选择巡检点区域"
              >
                <el-option
                  v-for="item in POINT_TYPE.POINT_TYPE"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="巡检点经度">
              <el-input-number
                v-model="formLabelAlign.longitude"
                :precision="6"
                :step="0.000001"
                :max="180"
                :min="0"
              ></el-input-number>
            </el-form-item>
            <el-form-item label="巡检点纬度">
              <el-input-number
                v-model="formLabelAlign.latitude"
                :precision="6"
                :step="0.000001"
                :max="180"
                :min="0"
              ></el-input-number>
            </el-form-item>
          </el-form>
          <div class="commit-buttons">
            <el-button type="info" @click="Cancel">取消创建</el-button>
            <el-button type="info" @click="Submit" style="margin-left: 150px"
              >提交创建</el-button
            >
          </div>
        </el-card>
        <el-button type="info" class="return-button" @click="Return"
          >返回</el-button
        >
      </div>
    </el-col>
  </el-row>
</template>

<script>
import TencentMap from "./TencentMap.vue";
import POINT_TYPE from "../assets/data.js";
export default {
  name: "page_inspection_add_new_point",
  components: {
    TencentMap,
  },
  data() {
    return {
      POINT_TYPE,
      labelPosition: "top",
      formLabelAlign: {
        type: "",
        number: "",
        area: "",
        longitude: "",
        latitude: "",
      },
    };
  },
  methods: {
    positionChange: function (value){
      this.formLabelAlign.longitude = value.longitude;
      this.formLabelAlign.latitude = value.latitude;
    },
    Submit: function () {
      if (
        this.formLabelAlign.type === "" ||
        this.formLabelAlign.area === "" ||
        this.formLabelAlign.number === ""
      ) {
        this.$message.error("新建失败，请保证信息填写完整");
      } else {
        this.$http({
          method: 'post',
          url: '/point/add',
          headers: {
            'token': localStorage.getItem('token')
          },
          data: {
            type: this.formLabelAlign.type,
            number: this.formLabelAlign.number,
            area: this.formLabelAlign.area,
            longitude: this.formLabelAlign.longitude,
            latitude: this.formLabelAlign.latitude,
          }
        }).then((res) => {
          if(res.data === 'token无效'){
            // 清空本地token
            localStorage.setItem('token','');
            // 强制跳转登录界面
            this.$router.push('/login')
            // 弹出提示款
            this.$message.error('登录过期，请重新登录')
          }else if(res.data.code === '0'){
            this.$router.push("/main/pointManage");
            this.$message(res.data.msg);
          }else if(res.data.code === '1'){
            this.$message(res.data.msg);
          }
        })
      }
    },
    Cancel: function () {
      this.$confirm("是否取消创建该巡检点信息?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$router.push("/main/pointManage");
          this.$message({
            type: "info",
            message: "已取消创建巡检点的信息",
          });
        })
        .catch(() => {
          //do nothing
        });
    },
    Return: function () {
      this.$router.push("/main/pointManage");
      this.$message({
        type: "info",
        message: "已返回上一级",
      });
    },
  },
  mounted() {
    // 挂载时打开地图的点击事件
    this.$refs.tencentMap.open();
  }
};
</script>

<style scoped>
.el-row {
  width: 100%;
  height: 100%;
}
.el-col {
  border-radius: 4px;
  height: 90%;
}
.graph {
  margin-top: 5%;
  margin-left: 2%;
}

.commit-buttons {
  display: flex;
}

.return-button {
  margin-left: 23%;
  margin-top: 5%;
  width: 200px;
}
</style>
