<template>
  <el-row>
    <el-col :span="16">
      <div class="graph-external-box">
        <tencent-map class="graph" ref="tencentMap"></tencent-map>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="operating-external-box">
        <el-card class="box-card" style="height: 150px">
          <div slot="header" class="clearfix">
            <span>巡检路线排班</span>
            <el-button @click="showNoPath">不显示巡检路线</el-button>
            <el-button @click="showAllPath">显示全部巡检路线</el-button>
          </div>
          <el-radio-group v-model="chooseRouteName" @change="ChooseRoute">
            <el-radio v-for="route in Routes" :key="route" :label="route">{{
              route
            }}</el-radio>
          </el-radio-group>
        </el-card>

        <div class="operation-internal-box">
          <div class="choose-class-box">
            <label for="selectClass">巡检班级</label>
            <span style="padding-left: 10%"></span>
            <el-select
              id="selectClass"
              v-model="chooseClassName"
              :disabled="changeClass"
              placeholder="请选择"
            >
              <el-option
                v-for="className in Classes"
                :key="className"
                :label="className"
                :value="className"
              >
              </el-option>
            </el-select>
          </div>

          <div id="two-buttons-task-manage">
            <el-button @click="ChangeClassSchedule" plain
              >修改班组安排</el-button
            >
            <el-button @click="SaveChange" plain>保存班组安排</el-button>
          </div>
          <el-card class="box-card-table">
            <div slot="header" class="clearfix">
              <span>排班详情</span>
            </div>
            <el-table :data="MappingTable" stripe max-height="380">
              <el-table-column
                width="200"
                property="routeName"
                label="巡检路线"
              ></el-table-column>
              <el-table-column
                width="200"
                property="className"
                label="巡检班组"
              ></el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import TencentMap from "./TencentMap.vue";
export default {
  name: "page_inspection_task_management",
  components: { TencentMap },
  data() {
    return {
      chooseRouteName: "",
      chooseClassName: "",
      //选择班组的选择框，默认不被激活
      changeClass: true,

      lastClassName: "",
      //班组与巡检路线的映射信息
      MappingTable: [
      ],
      Classes: [
      ],
      Routes: [
      ],
    };
  },
  methods: {
    showNoPath: function (){
      this.$refs.tencentMap.showNoPath();
    },
    showAllPath: function (){
      this.$refs.tencentMap.showAllPath();
    },
    ChooseRoute: function () {
      console.log("点击单选框了！！！");
      //换了巡检路线，下拉框重新换成不可编辑的状态
      this.changeClass = true;
      //下拉框的值也应该变为这条巡检路线配分配给的班组
      this.chooseClassName = this.MappingTable.find((item) => {
        return item.routeName === this.chooseRouteName;
      }).className;
      this.lastClassName = this.chooseClassName;
      // 展示信息窗体
      this.$refs.tencentMap.openInfoWindow(this.chooseRouteName)
    },
    ChangeClassSchedule: function () {
      if (this.chooseRouteName == "") {
        this.$message.error("请选择巡检路线!");
      } else {
        this.changeClass = false;
      }
    },
    SaveChange: function () {
      //如果没有选中巡检路线，提示
      if (this.chooseRouteName == "") {
        this.$message.error("请选择巡检路线，并修改巡检班组之后再进行保存！");
      } else {
        this.$confirm("确认保存当前安排?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            //检查chooseClassNum有没有其他被安排的任务，如果有的话，不向后端调用接口，如果没有，再修改前端和后端的数据
            let ConfirmInspectionClass = true;
            if (
              this.MappingTable.find((item) => {
                return (item.className === this.chooseClassName && this.chooseClassName !== '暂定');
              })
            ) {
              //在MappingTable里面找到了班组，说明班组已经被安排任务了
              ConfirmInspectionClass = false;
            } else {
              //没有找到，说明班组还没安排任务
              ConfirmInspectionClass = true;
            }

            if (ConfirmInspectionClass) {
              //chooseClassNum没有被安排任务
              //先在前端保存
              this.MappingTable.find((item) => {
                return item.routeName === this.chooseRouteName;
              }).className = this.chooseClassName;
              this.$http({
                method: 'put',
                url: 'task/updateTask',
                headers: {
                  'token': localStorage.getItem('token')
                },
                params: {
                  routeName: this.chooseRouteName,
                  className: (this.chooseClassName === '暂定'?'':this.chooseClassName)
                }
              })
              //************************************后端接口**************************************//
              //用户想把巡检路线（名称为this.chooseRouteName）分配给巡检班组（名称为：this.chooseClassName）
              /***********************************************************************************/
              this.$message({
                type: "success",
                message: "保存成功!",
              });
              //保存之后，要恢复到不可编辑的状态
              this.changeClass = true;
            } else {
              this.$message.error("此班组已被安排了巡检路线，请重新选择");
              this.changeClass = true;
              this.chooseClassName = this.lastClassName;
            }
          })
          .catch(() => {
            //用户点击了取消，可能还是想要修改一下，保险起见，将选择框关闭
            this.changeClass = true;
            this.chooseClassName = this.lastClassName;
          });
      }
    },
  },
  mounted() {
    // 查找所有的巡检路线
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
        for(let i=0;i<res.data.length;i++)
          this.Routes.push(res.data[i].routeName);
      }
    })
    // 查找所有的班
    this.$http.get('class/findAll',{
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
        for(let i=0;i<res.data.length;i++)
          this.Classes.push(res.data[i].className);
        this.Classes.push('暂定')
      }
    })
    // 查找所有的映射关系
    this.$http.get('task/findTask',{
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
        for(let i=0;i<res.data.length;i++)
          this.MappingTable.push({
            routeName: res.data[i].inspectRouteName,
            className: ((res.data[i].inspectClassName === null || res.data[i].inspectClassName === '' ||
                res.data[i].inspectClassName === undefined)?'暂定':res.data[i].inspectClassName)
          })
      }
    })
  },
};
</script>

<style scoped>
.el-row {
  height: 100%;
}
.el-col {
  height: 100%;
}
.graph-external-box {
  height: 800px;
  padding: 5% 3% 3% 3%;
}
.graph ::v-deep canvas {
  border-radius: 20px;
}
.operating-external-box {
  padding-top: 10%;
  padding-right: 3%;
}
#two-buttons-task-manage {
  display: flex;
  justify-content: space-between;
  padding-left: 9%;
  padding-right: 19%;
  padding-top: 5%;
}
.operation-internal-box {
  padding-top: 5%;
}
.choose-class-box {
  padding-left: 10%;
}
.box-card-table {
  margin-top: 10%;
}
</style>
