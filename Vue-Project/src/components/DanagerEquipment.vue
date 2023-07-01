<template>
  <div>
    <div style="height: 100%">
      <el-row style="margin: 1px 1px 0px 1px; height: 10%">
        <el-col :span="24">
          <!--新增一个危险源检测设备-->
          <!--查询表单-->
          <div class="point-Top">
            <el-form :inline="true" :model="formInline" class="ins-form">
              <el-form-item
                label="设备类型"
                style="margin-left: 10px"
                class="input_label"
              >
                <el-select
                  v-model="input_type"
                  placeholder="设备类型"
                  class="intput"
                >
                  <el-option
                    v-for="item in inspectionType"
                    :key="item"
                    :label="item"
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="设备区域" class="input_label">
                <el-select
                  v-model="input_area"
                  placeholder="设备区域"
                  class="intput"
                >
                  <el-option
                    v-for="item in inspectionArea"
                    :key="item"
                    :label="item"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="设备编号" class="input_label">
                <el-input
                  v-model="input_number"
                  placeholder="设备编号"
                  class="intput"
                ></el-input>
              </el-form-item>

              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  style=""
                  @click="onSubmit"
                  >查询危险源检测设备</el-button
                >
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col></el-col>
      </el-row>

      <el-row class="point-Bottom">
        <!--地图展示-->
        <el-col :span="18" style="height: 118%">
          <div class="graph" style="padding: 0px 0px 0px 0px; height: 118%">
            <hazard-map
              class="map"
              @click.native="clickMap"
              @getPos="getPosMethod"
              @getDevice="getDeviceMethod"
              @getGeoId="(data) => (this.curGeoId = data)"
              ref="TencentMap"
            ></hazard-map>

            <el-dialog
              :visible.sync="dialogFormVisible"
              title="新增设备"
              width="500px"
            >
              <el-form :model="form" style="padding-right: 60px">
                <el-form-item label="设备类型" :label-width="formLabelWidth">
                  <!-- <el-input v-model="form.type" autocomplete="off"></el-input> -->
                  <el-select
                    v-model="form.type"
                    placeholder="设备类型"
                    class="intput"
                  >
                    <el-option
                      v-for="item in inspectionType"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="设备区域" :label-width="formLabelWidth">
                  <!-- <el-input v-model="form.area" autocomplete="off"></el-input> -->
                  <el-select
                    v-model="form.area"
                    placeholder="设备区域"
                    class="intput"
                  >
                    <el-option
                      v-for="item in inspectionArea"
                      :key="item"
                      :label="item"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="设备编号" :label-width="formLabelWidth">
                  <el-input v-model="form.number" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="设备经度" :label-width="formLabelWidth">
                  <label class="text-item-label" style="margin-left: 10px"
                    >{{ form.lng }}
                  </label>
                </el-form-item>
                <el-form-item label="设备纬度" :label-width="formLabelWidth">
                  <label class="text-item-label" style="margin-left: 10px"
                    >{{ form.lat }}
                  </label>
                </el-form-item>
              </el-form>

              <div slot="footer" class="dialog-footer">
                <el-button @click="submitNewPoint(false)">取 消</el-button>
                <el-button type="primary" @click="submitNewPoint(true)"
                  >确 定</el-button
                >
              </div>
            </el-dialog>

            <el-dialog
              :visible.sync="updateDialogFormVisible"
              title="修改设备信息"
              width="500px"
            >
              <el-form :model="form" style="padding-right: 60px">
                <el-form-item label="设备类型" :label-width="formLabelWidth">
                  <!-- <el-input v-model="form.type" autocomplete="off"></el-input> -->
                  <el-select
                    v-model="form.type"
                    placeholder="设备类型"
                    class="intput"
                  >
                    <el-option
                      v-for="item in inspectionType"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="设备区域" :label-width="formLabelWidth">
                  <!-- <el-input v-model="form.area" autocomplete="off"></el-input> -->
                  <el-select
                    v-model="form.area"
                    placeholder="设备区域"
                    class="intput"
                  >
                    <el-option
                      v-for="item in inspectionArea"
                      :key="item"
                      :label="item"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="设备编号" :label-width="formLabelWidth">
                  <el-input v-model="form.number" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="设备经度" :label-width="formLabelWidth">
                  <label class="text-item-label" style="margin-left: 10px"
                    >{{ form.lng }}
                  </label>
                </el-form-item>
                <el-form-item label="设备纬度" :label-width="formLabelWidth">
                  <label class="text-item-label" style="margin-left: 10px"
                    >{{ form.lat }}
                  </label>
                </el-form-item>
              </el-form>

              <div slot="footer" class="dialog-footer">
                <el-button @click="changeOldPoint(false)">取 消</el-button>
                <el-button type="primary" @click="changeOldPoint(true)"
                  >确 定</el-button
                >
              </div>
            </el-dialog>
          </div>

          <div class="tips-border">
            <label v-show="isAdding" class="tips-label"
              >点击地图上的点开始新增设备</label
            >
          </div>
        </el-col>
        <el-col :span="6" style="height: 100%" class="pointInfo">
          <!-- 显示危险源检测设备信息的卡片 -->

          <el-card class="box-card" style="margin-right: 10px">
            <div slot="header">
              <span>危险源检测设备详细信息</span>
            </div>
            <el-row :span="2">
              <el-col :span="7" style="width: 60%; padding-left: 10%">
                <div class="text-item" style="margin-top: -10px">
                  <label class="text-item-label">设备类型 </label>
                </div>
                <div class="text-item">
                  <label class="text-item-label">设备区域 </label>
                </div>
                <div class="text-item">
                  <label class="text-item-label">设备编号 </label>
                </div>
                <div class="text-item">
                  <label class="text-item-label">设备经度 </label>
                </div>
                <div class="text-item">
                  <label class="text-item-label">设备纬度 </label>
                </div>
                <div class="text-item">
                  <label class="text-item-label">工作情况 </label>
                </div>
                <div class="text-item">
                  <label class="text-item-label">异常次数 </label>
                </div>
              </el-col>

              <el-col :span="7" style="width: 40%; padding-right: 10%">
                <el-row>
                  <div class="text-item" style="margin-top: -10px">
                    <label class="text-item-label">{{ pointInfo.type }} </label>
                  </div>
                </el-row>
                <el-row>
                  <div class="text-item">
                    <label class="text-item-label">{{ pointInfo.area }} </label>
                  </div>
                </el-row>
                <el-row>
                  <div class="text-item">
                    <label class="text-item-label"
                      >{{ pointInfo.number }}
                    </label>
                  </div>
                </el-row>
                <el-row>
                  <div class="text-item">
                    <label class="text-item-label"
                      >{{ pointInfo.lng.toFixed(6) }}°
                    </label>
                  </div>
                </el-row>
                <el-row>
                  <div class="text-item">
                    <label class="text-item-label"
                      >{{ pointInfo.lat.toFixed(6) }}°</label
                    >
                  </div>
                </el-row>
                <el-row>
                  <div class="text-item">
                    <label
                      class="text-item-label"
                      :style="{ color: pointInfo.isWork ? 'blue' : 'red' }"
                    >
                      {{ pointInfo.isWork ? "正常" : "异常" }}
                    </label>
                  </div>
                </el-row>
                <el-row>
                  <div class="text-item">
                    <label class="text-item-label"
                      >{{ pointInfo.abnormalNum ? pointInfo.abnormalNum : 0 }}
                    </label>
                  </div>
                </el-row>
              </el-col>
            </el-row>

            <el-row>
              <hazard-echart class="echart" ref="HazardEchart" @working="setWorking"></hazard-echart>
            </el-row>
            <!-- <div class="text-item" style="display: flex">
              经度
              <el-input placeholder="请输入经度" v-model="pointInfo.longitude">
              </el-input
              >E
            </div>
            <div class="text-item" style="display: flex">
              纬度
              <el-input placeholder="请输入纬度" v-model="pointInfo.latitude">
              </el-input
              >N
            </div> -->

            <div class="cardBottom">
              <!--移除这个危险源检测设备-->
              <el-button
                type="danger"
                icon=""
                @click="deletePoint"
                style="position: relative; left: 12%; scale: 90%"
                >移除该点</el-button
              >
              <!--修改这个危险源检测设备的信息-->
              <el-button
                type="primary"
                icon=""
                @click="changeInfo"
                style="position: relative; right: -22%; scale: 90%"
                >修改信息</el-button
              >
            </div>
          </el-card>

          <el-row :span="1" style="width: 100%">
            <el-col style="width: 50%; margin-left: 20%">
              <el-button type="success" class="addbtn" @click="addNewPoint"
                >{{ isAdding ? "退出添加" : "新增设备" }}
              </el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
.echart {
  padding-top: 5px;
  margin: auto;
  /* border-style:solid;
  border-color: red; 
  border-width: 1px 1px 1px 1px; */
}

.cardBottom {
  /* border-style:solid;
  border-color: red;
  border-width: 1px 1px 1px 1px; */
  margin-bottom: -10px;
}

.map {
}

.tips-border {
  border-style: solid;
  border-color: greenyellow;
  border-width: 1px 1px 1px 1px;
}
.tips-label {
  margin: 10% 0 0 40%;
  line-height: 1.5em;
  font-family: Tahoma, Geneva, sans-serif;
  font-size: 20px;
  color: #6f6f6f;
}
.point-Bottom {
  height: 60%;
}
.addbtn {
  margin-left: 30%;
  margin-top: 5%;
}
.el-form-item {
  margin: 0 20px 10px 20px;
}
.point-Top {
  border-style: solid;
  border-color: gray;
  border-width: 1px 1px 1px 1px;
  padding-top: 10px;
  padding-bottom: -10px;
  position: inherit;
  /* margin-right: 25%; */
}
.text-item {
  padding-top: 2px;
  padding-bottom: 2px;
  white-space: nowrap;
}
.text-item-label {
  width: 40px;
}
.ins-form {
  width: 100%;
  display: flex;
}
.ins-form el-form-item:nth-child(1) {
  width: 25%;
}
.ins-form el-form-item:nth-child(2) {
  width: 25%;
}
.ins-form el-form-item:nth-child(3) {
  width: 25%;
}
.ins-form el-form-item:nth-child(4) {
  width: 25%;
}
</style>

<script>
import HazardEchart from "./HazardEchart.vue";
import HazardMap from "./HazardMap.vue";
export default {
  name: "page_danger_equipment",
  components: {
    HazardMap,
    HazardEchart,
  },

  data() {
    return {
      updateDialogFormVisible: false,
      dialogFormVisible: false,
      form: {
        db_id: 0,
        type: "",
        area: "",
        number: "",
        lng: 0.0,
        lat: 0.0,
        isWork: false,
        abnormalNum: 0,
      },
      formLabelWidth: "120px",
      // 新增按钮信息
      isAdding: false,
      /*查询框内容*/
      input_type: "",
      input_area: "",
      input_number: "",
      inspectionType: ["", "温度检测", "压强检测", "湿度检测", "储气罐"],
      inspectionArea: [
        "",
        "东一区",
        "东三区",
        "西一区",
        "西二区",
        "南一区",
        "南二区",
        "北一区",
        "北二区",
      ],

      pointInfo: {
        db_id: 0,
        type: "type",
        area: "area",
        number: "number",
        lng: 0,
        lat: 0,
        isWork: false,
        abnormalNum: 0,
      },

      formInline: {
        user: "",
        region: "",
      },
      curGeoId: 0,

      devices: [],

      cnt: 1,
    };
  },
  mounted() {
    //console.log("asd")
    /*******************接口，获得所有设备，返回数组*************************/
    //从后端获取所有点
    this.$http({
      method: "get",
      url: "/hazard/findAll",
      headers: {
        token: localStorage.getItem("token"),
      },
      params: {},
      data: {},
    })
      .then((res) => {
        if (res.data === "token无效") {
          清空本地token;
          localStorage.setItem("token", "");
          // 强制跳转登录界面
          this.$router.push("/login");
          // 弹出提示款
          this.$message.error("登录过期，请重新登录");
        } else {
          this.devices = res.data;
          //修改devices的字段
          for (let device of this.devices) {
            // console.log(this.$refs.TencentMap)
            this.$refs.TencentMap.addPoint(
              device,
              device.db_id,
              device.lat,
              device.lng
            );
          }
          //this.pointInfo = JSON.parse(JSON.stringify(this.devices[0]));
        }
      })
      .catch((err) => {
        console.log(err);
      });
    //假设获得了一个设备数组
    //  this.devices = [
    //   {
    //     type: "asd",
    //     number: 123,
    //     area: "seu",
    //     lat: 31.887627,
    //     lng: 118.819201,
    //     isWork: true,
    //     abnormalNum: 9
    //   },
    // ]

    // console.log(this.pointInfo.lat)
  },
  methods: {
    setWorking(flag){
      this.pointInfo.isWork = flag
    },
    setupEchart(data){
        let child = this.$refs.HazardEchart

        child.data = []
        child.echart.setOption({
          series: [
            {
              data: [],
            },
          ],
        })
        child.device_id = data.number // 根据设备编号找id
        this.$refs.HazardEchart.requestData()
        if(this.$refs.HazardEchart.isWork){
          this.pointInfo.isWork = true
        }
        
    },
    changeInfo: function () {
      this.updateDialogFormVisible = true;
      this.form = JSON.parse(JSON.stringify(this.pointInfo));
    },
    changeOldPoint(flag) {
      //更新时点击取消
      if (!flag) {
        this.updateDialogFormVisible = false;
        // this.$message({
        //     type: 'info',
        //     message: '取消修改'
        //   });
      } else {
        if (
          this.form.type === "" ||
          this.form.number === "" ||
          this.form.area === ""
        ) {
          this.$message({
            message: "请将设备信息填写完整",
            type: "warning",
          });
          return;
        }
        this.updateDialogFormVisible = false;
        let form = this.form;
        this.pointInfo.type = form.type;
        this.pointInfo.number = form.number;
        this.pointInfo.area = form.area;
        this.$refs.TencentMap.updatePoint(this.curGeoId, this.pointInfo);
        /*******************接口，传一个新设备主键id，返回是否成功*************************/
        this.$http({
          method: "put",
          url: "/hazard/update",
          headers: {
            token: localStorage.getItem("token"),
          },
          params: {},
          data: form,
        })
          .then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else {
              //接下来的业务
              this.$message({
                type: "success",
                message: "修改成功",
              });
            }
          })
          .catch((err) => {
            this.$message({
              type: "warning",
              message: "修改失败",
            });
          });

        //不改变位置信息，只改变区域和类型

        // //先删除地图上的点
        //   this.$refs.TencentMap.removePoint(this.curGeoId);
        //   //再重新添加
        //   this.$refs.TencentMap.addProp = form
        //   this.$refs.TencentMap.addPoint(form, form.lat, form.lng)
      }
    },
    addNewPoint: function () {
      this.isAdding = !this.isAdding;
      //console.log(this.isAdding)
      this.$refs.TencentMap.changeMode(this.isAdding);

      // this.$router.push({
      //   //跳转到新增危险源检测设备的界面
      //   path: "addNewPoint",
      //   name: "page_add_new_point",
      // });
    },
    submitNewPoint: function (flag) {
      //新增时点击取消
      if (!flag) {
        this.dialogFormVisible = false;
        // this.$message({
        //     type: 'info',
        //     message: '取消添加'
        //   });
      }
      //新增时点击确定
      else {
        if (
          this.form.type === "" ||
          this.form.number === "" ||
          this.form.area === ""
        ) {
          this.$message({
            message: "请将设备信息填写完整",
            type: "warning",
          });
          return;
        } else {
          this.dialogFormVisible = false;
          let form = this.form;
          form.abnormalNum = 0;
          form.isWork = false;

          /*******************接口，传一个新设备，返回是否成功*************************/
          //保存在后端

          this.$http({
            method: "post",
            url: "/hazard/add",
            headers: {
              token: localStorage.getItem("token"),
            },
            params: {},
            data: form,
          })
            .then((res) => {
              if (res.data === "token无效") {
                // 清空本地token
                localStorage.setItem("token", "");
                // 强制跳转登录界面
                this.$router.push("/login");
                // 弹出提示款
                this.$message.error("登录过期，请重新登录");
              } else {
                //接下来的业务
                console.log(res);
                this.pointInfo = JSON.parse(JSON.stringify(res.data.data));
                console.log(this.pointInfo);
                this.$refs.TencentMap.addPoint(
                  this.pointInfo,
                  this.pointInfo.db_id,
                  form.lat,
                  form.lng
                );
                this.setupEchart(this.pointInfo)
                this.$message({
                  type: "success",
                  message: "添加成功",
                });
              }
            })
            .catch((err) => {
              console.log(err);
              this.$message({
                type: "warning",
                message: "添加失败",
              });
            });

          // Object.keys(this.form).forEach(key => (this.form[key] = ''));
        }
      }
    },
    clickMap: function (evt) {
      if (this.isAdding) {
        this.dialogFormVisible = true;
        this.form.area = "";
        this.form.type = "";
        this.form.number = "";
        this.form.db_id = 0;
        // this.$prompt('请输入邮箱', '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        //   inputErrorMessage: '邮箱格式不正确'
        // }).then(({ value }) => {
        //    this.$refs.TencentMap.addPermit = true;
        //   this.$message({
        //     type: 'success',
        //     message: '你的邮箱是: ' + value
        //   });
        // }).catch(() => {
        //    this.$refs.TencentMap.addPermit = false;
        //   this.$message({
        //     type: 'info',
        //     message: '取消添加'
        //   });
        // });
      }
    },
    deletePoint: function () {
      this.$confirm("此操作将永久该危险源检测设备, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(
        (response) => {
          console.log("点击了确定" + response);
          /*******************接口，传一个设备数据库主键id，返回是否成功*************************/
          //再在后端删除
          this.$http({
            method: "delete",
            url: "/hazard/delete",
            headers: {
              token: localStorage.getItem("token"),
            },
            params: {
              id: this.pointInfo.db_id,
            },
            data: {},
          })
            .then((res) => {
              if (res.data === "token无效") {
                // 清空本地token
                localStorage.setItem("token", "");
                // 强制跳转登录界面
                this.$router.push("/login");
                // 弹出提示款
                this.$message.error("登录过期，请重新登录");
              } else {
                //接下来的业务

                this.$refs.TencentMap.removePoint(this.curGeoId);

                this.pointInfo.type = "已删除";
                this.pointInfo.area = "已删除";
                this.pointInfo.number = "已删除";
                this.pointInfo.db_id = 0;
                this.pointInfo.isWork = false;
                this.pointInfo.abnormalNum = 0;

                this.$message({
                  type: "success",
                  message: "删除成功",
                });
              }
            })
            .catch((err) => {
              console.log(err);
              this.$message({
                type: "warning",
                message: "删除失败",
              });
            });
        },
        (err) => {}
      );
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
        /*******************接口，传给后端三个参数，返回一个危险源检测设备对象*************************/
        // this.input_type
        // this.input_area
        // this.input_number
        //把对象赋给pointInfo对象（124行）
        console.log("查询！");

        this.$http({
          method: "get",
          url: "/hazard/find",
          headers: {
            token: localStorage.getItem("token"),
          },
          params: {
            type: this.input_type,
            area: this.input_area,
            number: this.input_number,
          },
          data: {},
        })
          .then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else {
              //接下来的业务
              console.log(res.data);
              let ids = {};
              for (let item of res.data) {
                let id = item.db_id;
                let lat = item.lat;
                let lng = item.lng;
                ids[id] = true;
              }
              console.log(ids);
              if (Object.keys(ids).length === 0) {
                this.$message({
                  type: "info",
                  message: "未查询到设备",
                });
                return;
              }
              this.$refs.TencentMap.setFound(ids);
              this.$message({
                type: "success",
                message: "查询成功，以地图上的蓝色标记为表示",
              });
            }
          })
          .catch((err) => {
            this.$message({
              type: "warning",
              message: "查询失败",
            });
          });

        //   //假设已经获取到了一个对象
        //   let device = {
        //   db_id: 0,
        //   type: "asd",
        //   number: 123,
        //   area: "seu",
        //   lat: 31.887627,
        //   lng: 118.819201,
        //   isWork: true,
        //   abnormalNum: 9,
        // }

        //   //将中心定位到该点位置
        //   this.$refs.TencentMap.setCenter(device.lat, device.lng)

        //   //更新form，pointInfo，还有this.$refs.TencentMap
        //   this.$refs.TencentMap.updatePointFromDb(device.number, device.abnormalNum, device.isWork)
        //   this.form = device
        //   this.pointInfo = device
        //   this.curGeoId = this.$refs.TencentMap.getGeoIdFromDb(device.number)
        // }
      }
    },
    getDeviceMethod(data) {
      this.setupEchart(data)
      this.pointInfo.db_id = data.db_id;
      this.pointInfo.type = data.type;
      this.pointInfo.number = data.number;
      this.pointInfo.area = data.area;
      this.pointInfo.lat = data.lat;
      this.pointInfo.lng = data.lng;
      this.pointInfo.isWork = data.isWork ? 0 : data.isWork;
      this.pointInfo.abnormalNum = data.abnormalNum ? 0 : data.abnormalNum;
    },
    getPosMethod(data) {
      this.form.lat = data.lat;
      this.form.lng = data.lng;
    },
  },
};
</script>

