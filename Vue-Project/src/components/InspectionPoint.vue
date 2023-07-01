<template>
  <div style="height: 100%">
    <el-row class="point-Top" style="margin: 30px 110px 30px 130px">
      <el-col :span="24">
        <!--查询表单-->
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="巡检点类型">
            <el-input v-model="input_type" placeholder="巡检点类型"></el-input>
          </el-form-item>
          <el-form-item label="巡检点区域">
            <el-select v-model="input_area" placeholder="巡检点区域">
              <el-option
                v-for="item in inspectionArea"
                :key="item"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="巡检点编号">
            <el-input
              v-model="input_number"
              placeholder="巡检点编号"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询巡检点</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row class="point-Bottom">
      <!--地图展示-->
      <el-col :span="18" style="height: 100%">
        <div class="graph" style="padding: 0px 10px 10px 10px; height: 750px">
          <tencent-map ref="tencentMap" @change="pointChange($event)" :key="refreshKey"></tencent-map>
        </div>
      </el-col>
      <el-col :span="6" style="height: 100%" class="pointInfo">
        <!-- 显示巡检点信息的卡片 -->

        <el-card class="box-card" style="margin-right: 10px">
          <div slot="header">
            <span>巡检点详细信息</span>
          </div>
          <div class="text-item">
            <label class="text-item-label">巡检点类型 </label>
            <div class="text-item-info">{{ pointInfo.type }}</div>
          </div>
          <div class="text-item">
            <label class="text-item-label">巡检点编号 </label>
            <div class="text-item-info">{{ pointInfo.number }}</div>
          </div>
          <div class="text-item">
            <label class="text-item-label">巡检点区域 </label>
            <div class="text-item-info">{{ pointInfo.area }}</div>
          </div>
          <div class="text-item">
            <label class="text-item-label">巡检点经度 </label>
            <div class="text-item-info">{{ pointInfo.longitude }}</div>
          </div>
          <div class="text-item">
            <label class="text-item-label">巡检点纬度 </label>
            <div class="text-item-info">{{ pointInfo.latitude }}</div>
          </div>
          <div class="text-item">
            <label class="text-item-label">工作情况 </label>
            <div class="text-item-info">
              {{ pointInfo.isWork ? "正常" : "异常" }}
            </div>
          </div>
          <div class="text-item">
            <label class="text-item-label">异常次数 </label>
            <div class="text-item-info">{{ pointInfo.AbnormalNum }}</div>
          </div>
          <div class="cardBottom">
            <!--移除这个巡检点-->
            <el-button type="info" icon="el-icon-search" @click="deletePoint"
              >移除该点</el-button
            >
            <!--修改这个巡检点的信息-->
            <el-button
              type="info"
              icon="el-icon-search"
              @click="changeInfo"
              style="margin-left: 20%"
              >修改信息</el-button
            >
          </div>
        </el-card>

        <div>
          <!--新增一个巡检点-->
          <el-button
            type="info"
            icon="el-icon-search"
            style="margin: 30px 92px"
            @click="addNewPoint"
            >新增巡检点</el-button
          >
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.text-item {
  padding-top: 20px;
  padding-bottom: 20px;
  display: flex;
  justify-content: space-between;
}
.text-item-info {
  margin-right: 20px;
}
</style>

<script>
import TencentMap from "./TencentMap.vue";

export default {
  name: "page_inspection_point",
  components: {
    TencentMap,
  },
  data() {
    return {
      // 用于刷新子组件的key
      refreshKey: 0,
      /*查询框内容*/
      input_type: "",
      input_area: "",
      input_number: "",
      inspectionArea: [
        "请选择",
        "东一区",
        "东二区",
        "西一区",
        "西二区",
        "南一区",
        "南二区",
        "北一区",
        "北二区",
      ],

      pointInfo: {
        id: null,
        type: "",
        number: "",
        area: "",
        /*先纬度N再经度E*/
        longitude: 0,
        latitude: 0,
        isWork: true,
        AbnormalNum: 0,
      },

      formInline: {
        user: "",
        region: "",
      },
    };
  },
  methods: {
    // 事件处理函数
    async pointChange(value) {// 当子组件值发生变化时，父组件值也发生相应变化
      this.pointInfo = value
    },
    chooseType: function (ans) {
      this.choose_1 = ans;
      console.log(ans);
    },
    chooseArea: function (ans) {
      this.choose_2 = ans;
      console.log(ans);
    },
    searchPoint: function () {
      console.log(
        "查询" + this.choose_1 + this.choose_2 + this.input + "的设备"
      );
    },
    deletePoint: function () {
      let _this=this;
      this.$confirm("此操作将永久删除该巡检点, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(
        function (response) {
          //*****************向后端发起请求，删除巡检点**************************************//
          //可以直接用this.point.number获得设备编号
          if(_this.pointInfo.id === null || _this.pointInfo.id === '' || _this.pointInfo.id === undefined){
            //如果用户什么都没有点，不做任何事
            _this.$message.error('没有选择任何巡检点')
          }else {
            // 删除巡检点
            _this.$http.delete('/point/delete',{
              headers: {
                'token': localStorage.getItem('token')
              },
              params: {
                id: _this.pointInfo.id
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
                // 强制刷新子组件
                _this.refreshKey++;
                // 弹窗提示
                _this.$message.success(res.data.msg);
                // 清空
                let temp= {
                  id: null,
                      type: "",
                      number: "",
                      area: "",
                      /*先纬度N再经度E*/
                      longitude: 0,
                      latitude: 0,
                      isWork: true,
                      AbnormalNum: 0,
                };
                _this.pointInfo = temp;
              }
            })
          }
          console.log('点击确定'+response)
        },
        function (err) {
          console.log("点击了取消" + err);
        }
      );
    },
    changeInfo: function () {
      let _this=this;
      if(this.pointInfo.id === null || this.pointInfo.id === '' || this.pointInfo.id === undefined){
        this.$message.error('没有选择任何巡检点')
      }else{
        this.$router.push({
          name: "page_inspection_change_point_info",
          params: {
            currentPoint: {
              id: _this.pointInfo.id,
              type: _this.pointInfo.type,
              number: _this.pointInfo.number,
              area: _this.pointInfo.area,
              longitude: _this.pointInfo.longitude,
              latitude: _this.pointInfo.latitude,
            },
          },
        });
      }
    },
    addNewPoint: function () {
      this.$router.push({
        //跳转到新增巡检点的界面
        path: "/main/addNewPoint",
        name: "page_inspection_add_new_point",
      });
    },
    onSubmit() {
      if (
        this.input_type === "" &&
        this.input_area === "" &&
        this.input_number === ""
      ) {
        this.$message({
          message: "请至少填入一项查询信息",
          type: "warning",
        });
      } else {
        console.log(this.input_type);
        this.$http
            .get("/point/find", {
              headers: {
                'token': localStorage.getItem('token')
              },
              params: {
                type: this.input_type,
                area: (this.input_area === '请选择'?'':this.input_area),
                number:this.input_number
              },
            }).then((res) => {
            if(res.data === 'token无效'){
            // 清空本地token
            localStorage.setItem('token','');
            // 强制跳转登录界面
            this.$router.push('/login')
            // 弹出提示款
            this.$message.error('登录过期，请重新登录')
            }else this.$refs.tencentMap.show(res.data)
        })
        /*******************接口，传给后端三个参数，返回一个巡检点对象*************************/
        // this.input_type
        // this.input_area
        // this.input_number
        //把对象赋给pointInfo对象（124行）
      }
    },
  },
};
</script>

<style scoped></style>
