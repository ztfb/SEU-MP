<template>
  <div class="mainWindow">
    <el-row style="height: 100%">
      <el-col :span="16" class="graph-margin-box" style="height: 100%">
        <div class="graph-padding-box">
          <tencent-map class="graph" ref="tencentMap" :key="refreshKey" @change2="pointChange2($event)"></tencent-map>
        </div>
      </el-col>
      <el-col :span="8" style="height: 100%">
        <el-row class="chooseRouteBox">
          <el-tabs type="border-card" style="height: 100%; border-radius: 20px">
            <el-tab-pane label="编辑巡检路线" style="height: 55px">
              <el-radio-group v-model="editRouteNum">
                <el-radio
                  v-for="(item, index) in Routes"
                  :key="item[0]"
                  :label="index"
                  @change="chooseEditRoute"
                  >{{ item[0] }}</el-radio
                >
              </el-radio-group>
            </el-tab-pane>
            <el-tab-pane label="显示巡检路线" style="height: 55px">
              <el-checkbox-group v-model="showRoutesList">
                <el-checkbox
                  v-for="(item, index) in Routes"
                  :key="item[0]"
                  :label="index"
                  @change="chooseShowRoute"
                  >{{ item[0] }}</el-checkbox
                >
              </el-checkbox-group>
            </el-tab-pane>
          </el-tabs>
        </el-row>

        <!--巡检路线上的巡检点信息-->
        <el-card
          class="box-card-external"
          :body-style="{ padding: '1% 5% 1% 5%' }"
        >
          <div slot="header" class="clearfix">
            <span>巡检路线详情</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              @click="deleteRoute"
              >移除该巡检路线</el-button
            >
          </div>
          <el-form
            :label-position="labelPosition"
            label-width="100px"
            :model="formLabelAlign"
          >
            <el-form-item label="巡检路线名称" style="margin-bottom: 10px">
              <el-input
                v-model="formLabelAlign.name"
                :disabled="true"
                style="width: 200px"
              ></el-input>
            </el-form-item>
            <el-form-item label="巡检点编号" style="margin-bottom: 10px">
              <el-select
                v-model="choosePointNumber"
                @change="ChoosePointFromRoute"
                placeholder="请选择"
                style="width: 200px"
              >
                <el-option
                  v-for="point in formLabelAlign.points"
                  :key="point"
                  :label="point"
                  :value="point"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <el-card class="box-card-internal">
            <div slot="header" class="clearfix">
              <span>巡检点详情</span>
              <el-button
                style="float: right; padding: 3px 0"
                type="text"
                @click="deletePointFromRoute"
                >移除该巡检点</el-button
              >
            </div>
            <div class="text-item">
              <label class="text-item-label">巡检点类型 </label>
              <div class="text-item-info">{{ currentPoint.type }}</div>
            </div>
            <div class="text-item">
              <label class="text-item-label">巡检点编号 </label>
              <div class="text-item-info">{{ currentPoint.number }}</div>
            </div>
            <div class="text-item">
              <label class="text-item-label">巡检点区域 </label>
              <div class="text-item-info">{{ currentPoint.area }}</div>
            </div>
            <div class="text-item">
              <label class="text-item-label">巡检点经度 </label>
              <div class="text-item-info">{{ currentPoint.longitude }}</div>
            </div>
            <div class="text-item">
              <label class="text-item-label">巡检点纬度 </label>
              <div class="text-item-info">{{ currentPoint.latitude }}</div>
            </div>
            <div class="text-item">
              <label class="text-item-label">工作情况 </label>
              <div
                class="text-item-info"
                v-show="this.currentPoint.isWork === null"
              >
                未知
              </div>
              <div
                class="text-item-info"
                v-show="this.currentPoint.isWork !== null"
              >
                {{ currentPoint.isWork ? "正常" : "异常" }}
              </div>
            </div>
            <div class="text-item">
              <label class="text-item-label">异常次数 </label>
              <div class="text-item-info">{{ currentPoint.AbnormalNum }}</div>
            </div>
            <div class="text-item">
              <label class="text-item-label">最近一次巡检时间 </label>
              <div class="text-item-info">
                {{ currentPoint.latestInsectionTime }}
              </div>
            </div>
          </el-card>
          <div class="twoButtons">
            <label class="currentChoosePoint">
              {{currentChoosePointNumber}}
            </label>
            <el-button
              type="primary"
              plain
              style="margin-left: 15%"
              @click="ClickAddPointToRoute"
              >将该巡检点加入此巡检路线</el-button
            >
            <el-dialog
              title="提示"
              :visible.sync="dialogVisible"
              width="30%"
              :before-close="HandleClose"
            >
              <span>确认将该巡检点加入此巡检路线？</span>
              <span slot="footer" class="dialog-footer"></span>
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="AddNewPointToRoute"
                >确 定</el-button
              >
            </el-dialog>
          </div>
        </el-card>
        <div class="bottomButtons">
          <el-button
            type="primary"
            style="width: 30%; margin-left: 30%; margin-right: 30%"
            @click="AddNewRoute"
            plain
            >新增巡检路线</el-button
          >
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TencentMap from "./TencentMap.vue";
export default {
  name: "page_inspection_route",
  components: {
    TencentMap,
  },
  data() {
    return {
      refreshKey: 0,
      //当前用户正在编辑的巡检路线在数组中的编号，-1代表未选择
      editRouteNum: -1,
      Routes: [
      ],
      //当前用户选择的多选框中，需要显示的巡检路线
      //里面的值为选择显示的路径在Routes中的下标
      showRoutesList: [],
      //用户当前选中的巡检点在this.Routes[editRouteNum]中的下标
      choosePointIndex: -1,
      //用户当前选中的巡检点编号（唯一标识）
      choosePointNumber: "",
      labelPosition: "right",
      //在表单中选择的选项，巡检路线的名称，巡检点的信息
      formLabelAlign: {
        name: "",
        points: [],
      },
      //当前展示在页面上的巡检点信息
      currentPoint: {
        type: "",
        number: "",
        area: "",
        longitude: null,
        latitude: null,
        abnormalNumber: null,
        isWork: null,
        //最近一次巡检时间
        latestInsectionTime: "",
      },
      //对话框显示
      dialogVisible: false,
      currentChoosePoint: {
        type: "",
        number: "",
        area: "",
        longitude: null,
        latitude: null,
        abnormalNumber: null,
        isWork: null,
        //最近一次巡检时间
        latestInsectionTime: "",
      },
      currentChoosePointNumber : "请选择巡检点"
    };
  },
  methods: {
    //****************************************需要用到地图api***************************************************//
    //当用户任意点击一个巡检点的时候，该巡检点的信息会出现在地图上
    //前端这个地方么有写函数
    async pointChange2(value) {
      this.currentChoosePoint=value
      this.currentChoosePointNumber=value.number
    },
    chooseEditRoute: function () {
      //先清空原来表单的信息
      this.formLabelAlign.name = "";
      this.formLabelAlign.points = [];
      //还要清空显示给用户的巡检点的值
      this.choosePointIndex = -1;
      this.choosePointNumber = "";
      (this.currentPoint = {
        type: "",
        number: "",
        area: "",
        longitude: null,
        latitude: null,
        AbnormalNum: null,
        isWork: null,
        //最近一次巡检时间
        latestInsectionTime: "",
      }),
        this.$message("编辑" + this.Routes[this.editRouteNum][0]);
      //把当前选中的巡检路线的相关信息同步到下方编辑表单中
      this.formLabelAlign.name = this.Routes[this.editRouteNum][0];
      for (let i = 1; i < this.Routes[this.editRouteNum].length; i++) {
        this.formLabelAlign.points.push(
          this.Routes[this.editRouteNum][i].number
        );
      }
    },
    //每次用户选择一条巡检路线，都需要显示出来
    chooseShowRoute: function () {
      let routeName=[];
      for(let i = 0 ; i < this.showRoutesList.length ; i++)
      {
        routeName.push(this.Routes[this.showRoutesList[i]][0]);
      }
      this.$refs.tencentMap.showPath(routeName)
    },
    ChoosePointFromRoute: function () {
      //前端要利用this.choosePointNumber更新this.choosePointIndex
      for (let i = 0; i < this.formLabelAlign.points.length; i++) {
        if (this.formLabelAlign.points[i] == this.choosePointNumber) {
          this.choosePointIndex = i;
        }
      }
      this.currentPoint =
        this.Routes[this.editRouteNum][1 + this.choosePointIndex];
      console.log(this.currentPoint);
    },
    //当用户点击移除巡检点时被触发
    deletePointFromRoute: function () {
      this.$confirm("确认删除巡检路线上删除该巡检点?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //用户此时想删除巡检路线（在Routes数组中的下标为editRouteNum）中，巡检点编号为choosePointNumber的巡检点
          if (this.choosePointNumber === "") {
            this.$message.error("请选择你要删除的巡检点");
          } else {
            this.$http.delete('route/update1',{
              headers: {
                'token': localStorage.getItem('token')
              },
              params: {
                routeName: this.Routes[this.editRouteNum][0],
                pointNumber: this.choosePointNumber
              }
            }).then((res) => {
              if(res.data === 'token无效'){
                // 清空本地token
                localStorage.setItem('token','');
                // 强制跳转登录界面
                this.$router.push('/login')
                // 弹出提示款
                this.$message.error('登录过期，请重新登录')
              }else if(res.data.code === '0'){// 移除成功
                //首先在formLabelAlign.points中找到名字为this.choosePointNumber的下标
                //从this.formLabelAlign.points中删除
                //显示给用户的信息为空
                this.choosePointNumber = "";
                this.formLabelAlign.points.splice(this.choosePointIndex, 1);
                //从Routes中删除这个巡检点的所有信息
                //因为下标0为巡检路线的名称，所以删除下标为choosePointIndex+1的地方
                this.Routes[this.editRouteNum].splice(this.choosePointIndex + 1, 1);
                this.refreshKey++;// 强制刷新子组件
                // 将路线显示改为未选中状态
                this.showRoutesList=[];
              }
            })
          }
          //然后让显示给用户的数据为空
          this.currentPoint = {
            type: "",
            number: "",
            area: "",
            longitude: null,
            latitude: null,
            abnormalNumber: null,
            isWork: null,
            //最近一次巡检时间
            latestInsectionTime: "",
          };
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    deleteRoute: function () {
      //==================================删除某条巡检路线==============================================
      //前端已经做好了判断，要删除所有巡检点之后才可以删除巡检路线
      //要被删除的巡检路线在数组Route中的下标为editRouteNum
      //若用户没有选择巡检路线，即editRouteNum=-1，出现错误弹窗
      //被删除的巡检路线的名称为        this.Routes[this.editRouteNum][0]
      if (this.editRouteNum === -1) {
        this.$message.error("请选择要删除的巡检路线！");
      } //如果用户已经选择了某条巡检路线，则需要查看这条巡检路线是否还有巡检点，如果没有巡检点了才可以删除
      else {
        if (this.Routes[this.editRouteNum].length !== 1) {
          this.$message({
            message: "请删除该条巡检路线上所有的巡检点后，再删除该巡检点",
            type: "warning",
          });
        } else {
          this.$http.delete('route/delete',{
            headers: {
              'token': localStorage.getItem('token')
            },
            params: {
              routeName: this.Routes[this.editRouteNum][0]
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
              this.$message({
                message: "已经删除" + this.Routes[this.editRouteNum][0],
                type: "success",
              });

              //在前端的Routes数组中删除该巡检路线
              this.Routes.splice(this.editRouteNum, 1);
              this.formLabelAlign.name = "";
            }
          })
        }
      }
    },
    AddNewRoute: function () {
      let that = this;
      if (that.Routes.length === 9) {
        this.$message({
          message: "巡检路线数量已经超过9条",
          type: "warning",
        });
      } else {
        this.$prompt("请输入新建的巡检路线名称", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          inputValidator: function (userInput) {
            for (let i = 0; i < that.Routes.length; i++) {
              if (userInput === that.Routes[i][0]) {
                return false;
              }
            }
            return true;
          },
          inputErrorMessage: "该名称已被占用",
        })
          .then(({ value }) => {
            if(value === '' || value === null || value === undefined)this.$message.error('路线名不能为空')
            else{
              this.$http({
                method: 'post',
                url: 'route/add',
                params: {
                  routeName: value
                },
                headers: {
                  'token': localStorage.getItem('token')
                },
              }).then((res) => {
                if(res.data === 'token无效'){
                  // 清空本地token
                  localStorage.setItem('token','');
                  // 强制跳转登录界面
                  this.$router.push('/login')
                  // 弹出提示款
                  this.$message.error('登录过期，请重新登录')
                }else if(res.data.code === '0'){
                  //******************名称为value,已经检查过与其他巡检路线不重名****************************
                  //向routes中插入巡检路线，名称为value
                  that.Routes.push([value]);
                  this.$message({
                    type: "success",
                    message: "已经新建巡检路线，名称为" + value + " ，可以进行编辑了",
                  });
                }
              })
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消创建巡检路线",
            });
          });
      }
    },
    //点击"在最后一个巡检点后增加巡检点"按钮
    ClickAddPointToRoute: function () {
      //只有选中了一条巡检路线
      //而且在地图上选择了一个本来不在这个巡检路线上的巡检点才可以跳出弹框
      if (this.formLabelAlign.name !== "") {
        //***********************************需要地图api***************************************************//
        //需要获取到用户目前在地图上选择的点的编号,我这里假设为point_Added_Number
        let point_Added_Number = "SSS1111"; //测试当前用户选中了巡检路线7上的一个巡检点
        //如果用户选中的当前巡检路线上已经包含了地图上选中的点
        if (
          this.formLabelAlign.points.find((pointNumber) => {
            return pointNumber === point_Added_Number;
          })
        ) {
          this.$message.error("选择的巡检点已在当前巡检路线，请勿重复添加！");
        } else {
          //加入用户选中的巡检点到巡检路线中，在此之前弹窗让用户再次确认一下。
          //如果用户点击了确认，请在另一个函数AddNewPointToRoute中将这个巡检点添加到某条巡检路线中
          this.dialogVisible = true;
        }
      } else {
        this.$message.error("请选择一条巡检路线！");
      }
    },
    //用户进入弹框后，点击弹框外部
    HandleClose: function (done) {
      this.$confirm("确认返回？")
        .then((_) => {
          done();
          console.log("确认");
        })
        .catch((_) => {
          console.log("取消");
        });
    },
    AddNewPointToRoute: function () {
      this.dialogVisible = false;
      this.$http({
        method: 'put',
        url: 'route/update2',
        headers: {
          'token': localStorage.getItem('token')
        },
        params: {
          routeName: this.Routes[this.editRouteNum][0],
          pointNumber: this.currentChoosePointNumber
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
          this.refreshKey++;// 强制刷新子组件
          // 将路线显示改为未选中状态
          this.showRoutesList=[];
          this.currentPoint=this.currentChoosePoint
          this.formLabelAlign.points.push(this.currentPoint.number);
          this.choosePointNumber = this.currentPoint.number;
          this.Routes[this.editRouteNum].push(this.currentPoint);
          console.log(this.Routes[this.editRouteNum]);
        }else {
          this.$message.error(res.data.msg)
        }
      })
    },
  },
  //********************************后端接口*************************************************************//
  mounted() {
    this.$http.get('route/findAll',{
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
      }else{
        this.$http.get('/point/findAll',{
          headers: {
            'token': localStorage.getItem('token')
          }
        }).then((resp) => {
          if(resp.data === 'token无效'){
            // 清空本地token
            localStorage.setItem('token','');
            // 强制跳转登录界面
            this.$router.push('/login')
            // 弹出提示款
            this.$message.error('登录过期，请重新登录')
          }else{
            for(let i=0;i<res.data.length;i++){
              let points = [];
              for(let j=0;j<resp.data.length;j++){
                if(res.data[i].id === resp.data[j].inspectRouteId)points.push(resp.data[j])
              }
              points.unshift(res.data[i].routeName);
              this.Routes.push(points)
            }
          }
        })
      }
    })
    //保证巡检路线个数少于9！！！！
    //如果要修改最大巡检路线数，请同步修改AddNewRoute方法中的判断条件
  },
};
</script>

<style scoped>
.mainWindow {
  height: 100%;
  margin-top: 3%;
}
.graph-padding-box {
  height: 820px;
  width: 97%;
  margin-left: 3%;
  margin-right: 3%;
}
.graph ::v-deep canvas {
  border-radius: 20px;
}
.chooseRouteBox {
  height: 25%;
  padding-left: 3%;
  padding-right: 3%;
  padding-bottom: 5%;
}
.editRouteBox {
  height: 75%;
}
.el-form {
  padding: 10px 10px 10px 20px;
}
.box-card-external {
  margin: 3%;
}
.text-item {
  display: flex;
  justify-content: space-between;
}
.text-item-info {
  margin-right: 20px;
}
.box-card-external ::v-deep el-card__body {
  padding: 1%;
}
.twoButtons {
  display: flex;
  justify-content: space-between;
  align-content: center;
  padding-top: 5%;
}
</style>
