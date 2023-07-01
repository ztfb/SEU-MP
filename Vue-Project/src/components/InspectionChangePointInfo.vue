<template>
  <el-row :gutter="20">
    <el-col :span="16">
      <tencent-map class="graph"></tencent-map>
    </el-col>
    <el-col :span="8">
      <div style="margin: 20px">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>修改巡检点信息</span>
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
            <el-button type="info" @click="Cancel">取消修改</el-button>
            <el-button type="info" @click="Submit" style="margin-left: 150px"
              >提交修改</el-button
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
  name: "page_inspection_change_point_info",
  components: {
    TencentMap,
  },
  data() {
    return {
      POINT_TYPE,
      labelPosition: "top",
      formLabelAlign: {
        id: null,
        type: "",
        number: "",
        area: "",
        longitude: "",
        latitude: "",
      },
    };
  },
  methods: {
    Submit: function () {
      if (this.formLabelAlign.type === "" || this.formLabelAlign.area === "") {
        this.$message.error("保存失败，请保证信息填写完整");
      } else {
        this.$http({
          method: 'put',
          url: '/point/update',
          data: {
            id: this.formLabelAlign.id,
            type: this.formLabelAlign.type,
            number: this.formLabelAlign.number,
            area: this.formLabelAlign.area,
            longitude: this.formLabelAlign.longitude,
            latitude: this.formLabelAlign.latitude,
          },
          headers: {
            'token': localStorage.getItem('token')
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
            // 重定向到原界面
            this.$router.push("/main/pointManage");
            this.$message(res.data.msg);
          }else if(res.data.code === '1')this.$message.error(res.data.msg);
        })
      }
    },
    Cancel: function () {
      this.$confirm("是否取消修改该巡检点信息?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$router.push("/main/pointManage");
          this.$message({
            type: "info",
            message: "已取消修改巡检点的信息",
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
    GetCurrentPoint: function () {
      this.formLabelAlign = this.$route.params.currentPoint;
      console.log(this.formLabelAlign);
    },
  },
  created() {
    this.GetCurrentPoint();
  },
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
  padding-top: 5%;
  padding-left: 2%;
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
