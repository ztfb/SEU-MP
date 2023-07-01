<template>
  <el-row>
    <!---------------------------------树形控件---------------------------------------------->
    <el-col :span="4" class="tree-component-external">
      <div class="tree-component-internal">
        <el-card style="height: 100%">
          <el-input placeholder="输入关键字进行过滤" v-model="filterText">
          </el-input>
          <el-tree
            class="filter-tree"
            :data="classData"
            :props="defaultProps"
            default-expand-all
            :filter-node-method="filterNode"
            ref="tree"
            @node-click="ClickNode"
          >
          </el-tree>
        </el-card>
      </div>
    </el-col>
    <el-col :span="20" class="schedule-external-box">
      <!---------------------------------提示信息---------------------------------------------->
      <el-row class="show-route-for-class-box">
        <el-card class="tips-for-manager-card">
          <div class="tips-for-manager-span">
            <span v-show="chooseClassIndex !== 0" class="kill-span"
              >班级<span class="showClass">{{
                " " + chooseClassName + " "
              }}</span
              >被分配到的巡检路线为<span class="showRouteName">{{
                " " + allocatedRouteName + " "
              }}</span></span
            >
            <span class="kill-span" v-show="chooseClassIndex === 0"
              >请选择要分配任务的班组</span
            >
          </div>
          <el-button
            plain
            class="add-new-task-button"
            :disabled="
              allocatedRouteName === '' || allocatedRouteName === '暂定'
            "
            @click="AddNewTask"
            >新增任务</el-button
          >
        </el-card>
      </el-row>

      <el-row class="user-operation-box">
        <!---------------------------------表单---------------------------------------------->
        <el-dialog title="巡检任务详情" :visible.sync="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="巡检路线" :label-width="formLabelWidth">
              <el-input
                v-model="form.route"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="巡检班组" :label-width="formLabelWidth">
              <el-input
                v-model="form.className"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="巡检人员" :label-width="formLabelWidth">
              <el-select
                v-model="form.employeeName"
                filterable
                placeholder="请选择巡检人员"
                :disabled="isSearching"
                @change="ChooseNameFromForm"
              >
                <el-option
                  v-for="item in employeeData[chooseClassIndex]"
                  :key="item.number"
                  :label="item.name"
                  :value="item.name"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="巡检工号" :label-width="formLabelWidth">
              <el-select
                v-model="form.employeeNumber"
                filterable
                placeholder="请选择巡检人员工号"
                :disabled="isSearching || !hasChoosedName"
              >
                <el-option
                  v-for="item in satisfyNumbers"
                  :key="item.number"
                  :label="item.number"
                  :value="item.number"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="巡检时间" :label-width="formLabelWidth">
              <el-time-select
                :disabled="isSearching"
                placeholder="起始时间"
                v-model="form.startTime"
                :picker-options="{
                  start: '00:00',
                  step: '01:00',
                  end: '24:00',
                  maxTime: form.endTime,
                }"
              >
              </el-time-select>
              <span style="padding-left: 3%; padding-right: 3%">至</span>
              <el-time-select
                :disabled="isSearching"
                placeholder="结束时间"
                v-model="form.endTime"
                :picker-options="{
                  start: '00:00',
                  step: '01:00',
                  end: '24:00',
                  minTime: form.startTime,
                }"
              >
              </el-time-select>
            </el-form-item>
          </el-form>
          <!--管理员在查看详细信息时，表单上仅显示一个关闭按钮-->
          <div slot="footer" class="dialog-footer" v-show="isSearching">
            <el-button @click="dialogFormVisible = false">关闭</el-button>
          </div>
          <!--当用户进行修改和新增信息时，保单上显示提交和退出两个按钮-->
          <div slot="footer" class="dialog-footer" v-show="!isSearching">
            <el-button @click="CancleAndLeaveTheForm">返回</el-button>
            <el-button type="primary" @click="SubmitEdit">提交</el-button>
          </div>
        </el-dialog>
        <!---------------------------------表格---------------------------------------------->
        <el-card>
          <el-table
            :key="refreshKey"
            v-loading="loading"
            element-loading-text="拼命加载中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :data="taskData[chooseClassIndex]"
            style="width: 100%"
            max-height="580"
          >
            <el-table-column label="巡检班组" width="120">
              <template slot-scope="scope">
                <i class="el-icon-time"></i>
                <span style="margin-left: 10px">{{ scope.row.className }}</span>
              </template>
            </el-table-column>
            <el-table-column label="巡检路线" width="130">
              <template slot-scope="scope">
                <i class="el-icon-time"></i>
                <span style="margin-left: 10px">{{ scope.row.route }}</span>
              </template>
            </el-table-column>
            <el-table-column label="巡检时间" width="300">
              <template slot-scope="scope">
                <el-slider
                  v-model="scope.row.time"
                  range
                  show-stops
                  disabled
                  :max="24"
                >
                </el-slider>
              </template>
            </el-table-column>
            <el-table-column label="巡检人员名称" width="130">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{
                  scope.row.employeeName
                }}</span>
              </template>
            </el-table-column>
            <el-table-column label="巡检人员工号" width="120">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{
                  scope.row.employeeNumber
                }}</span>
              </template>
            </el-table-column>

            <el-table-column label="巡检起始时间" width="80">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.startTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="巡检结束时间" width="80">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.endTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="210" fixed="right">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="DeleteInfo(scope.$index, scope.row)"
                  >删除</el-button
                >
                <el-button
                  size="mini"
                  type="success"
                  @click="showTaskInfo(scope.row)"
                  >查看</el-button
                >

                <el-button size="mini" @click="editRow(scope.row, scope.$index)"
                  >编辑</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "page_inspection_task_schedule",
  data() {
    return {
      // 强制刷新key
      refreshKey: 0,
      //用户目前选中的班组名称
      chooseClassName: "",
      //用户当前选中的班组索引，这个索引有俩方面
      //1.和classData中的班组id号相同
      //2.和TaskData数组中存储了某个班组的巡检任务数组的索引一致
      //默认为0是为了用户进入页面时表格不显示任何信息
      chooseClassIndex: 0,
      //班组目前被分配到的巡检路线
      allocatedRouteName: "",
      filterText: "",
      classData: [
        {
          id: 0,
          label: "东南石化",
          children: [],
        },
      ],
      defaultProps: {
        children: "children",
        label: "label",
      },
      employeeData: [[]],
      taskData: [
        [], //←下标为0时置空，让表格最开始不渲染数据
      ],
      loading: true,
      dialogFormVisible: false,
      //用户当前是否在查看某一项巡检任务的具体信息
      isSearching: false,
      //用户是否点击了表单中的人员这个下拉框
      hasChoosedName: false,
      //表单中符合管理员选择名字之后对应的工号
      satisfyNumbers: [],
      formLabelWidth: "120px",
      //表单数据
      form: {
        route: "",
        className: "",
        time: [],
        employeeName: "",
        employeeNumber: "",
        startTime: "",
        endTime: "",
      },
      //用户在表格中选中了第几行，未选择为-1
      currentRow: -1,
    };
  },
  methods: {
    filterNode: function (value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //点击左侧班组调用该函数
    ClickNode: function (needData, nodeData, componentData) {
      //将后续需要的数据进行赋值，方便渲染表格，确定能否新增表格
      //用户当前选择的班级在taskData中的索引
      this.chooseClassIndex = needData.id;
      //用户当前选择的班级名称
      this.chooseClassName = needData.label;
      //用户当前选择的班级被分配到的路径名称
      this.allocatedRouteName = needData.route;
    },
    //删除表格中某一项
    DeleteInfo: function (index, row) {
      this.$confirm("此操作将永久删除该巡检任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http({
            method: "put",
            url: "/task/deleteItem",
            headers: {
              token: localStorage.getItem("token"),
            },
            data: {
              startTime: row.startTime,
              endTime: row.endTime,
            },
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else if (res.data.code === "0") {
              //用户点击了确定，直接删除
              //1. 前端删除
              this.taskData[this.chooseClassIndex].splice(index, 1);
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
      console.log(index, row);
    },
    //查看表格中某一行的具体数据
    showTaskInfo: function (item) {
      this.dialogFormVisible = true;
      this.isSearching = true;
      this.form = item;
    },
    //在表单中，新建，修改信息时
    ChooseNameFromForm: function () {
      //用户已经选择了名字，可以解除对工号选择框的禁用
      //但在那之前需要先完成搜索，把数据填入到this.satisfyNumbers中
      //先清空
      this.satisfyNumbers = [];
      this.form.employeeNumber = "";
      this.satisfyNumbers = this.employeeData[this.chooseClassIndex].filter(
        (item) => {
          //只保留本班巡检人员中，姓名与用户选择一致的巡检人员的工号
          return item.name === this.form.employeeName;
        }
      );
      //解除对表单中选择工号框的禁用，还需要在表单被提交或者被取消后进行修改
      this.hasChoosedName = true;
    },
    //用户点击关闭表单时
    CancleAndLeaveTheForm: function () {
      this.dialogFormVisible = false;
      //对选择工号框进行禁用
      this.hasChoosedName = false;
    },
    editRow: function (item, index) {
      this.dialogFormVisible = true;
      //显示信息
      //父组件传给表单的值是浅拷贝，但是需要用户点击确定了才可以修改父组件数据
      //这里必须将浅拷贝改为深拷贝
      //**************************************//
      this.form = JSON.parse(window.JSON.stringify(item));
      //编辑信息的时候不是搜索状态
      this.isSearching = false;
      //记录一下当前行的索引，提交表单时直接修改
      this.currentRow = index;
    },
    //点击了新增巡检路线
    AddNewTask: function () {
      this.dialogFormVisible = true;
      //与点击了编辑某一行的任务不同，新增时不会记录当前数据的行数，而且表单只有
      //“巡检路线”和“巡检班组”这两项
      //用currentRow这个变量的值表示当前为新增的状态
      this.currentRow = -1;
      //新增任务的时候不是搜索状态
      this.isSearching = false;
      //更新form的值
      this.form = {
        route: this.allocatedRouteName,
        className: this.chooseClassName,
        time: [],
        employeeName: "",
        employeeNumber: "",
        startTime: "",
        endTime: "",
      };
    },
    //用户提交表单
    SubmitEdit: function () {
      //对表单的数据进行检查
      //如果巡检人员或巡检人员工号未填写，提示一下
      if (this.form.employeeName === "" || this.form.employeeNumber === "") {
        this.$message.error("请将巡检人员相关信息填写完整之后再提交！");
      } else {
        //因为同一个班时间不会冲突，所以这里只需要检查时间有没有冲突就可以了，不用关心人员
        //如果起始时间或者结束时间为空，提示错误
        if (this.form.startTime === "" || this.form.endTime === "") {
          this.$message.error("请填写起始时间与结束时间的详细信息！");
        }
        //用户也有可能在编辑状态下没有修改任何信息
        else if (
          this.currentRow !== -1 &&
          this.form.employeeName ===
            this.taskData[this.chooseClassIndex][this.currentRow]
              .employeeName &&
          this.form.employeeNumber ===
            this.taskData[this.chooseClassIndex][this.currentRow]
              .employeeNumber &&
          this.form.startTime ===
            this.taskData[this.chooseClassIndex][this.currentRow].startTime &&
          this.form.endTime ===
            this.taskData[this.chooseClassIndex][this.currentRow].endTime
        ) {
          this.$message.error("提交失败，并未修改任何信息！");
        } else {
          this.form.time = [];
          //检查时间是否有冲突
          this.form.time.push(parseInt(this.form.startTime.substr(0, 2)));
          this.form.time.push(parseInt(this.form.endTime.substr(0, 2)));
          //找到的第一个产生冲突的任务
          let firstConfiltTask = null;
          //遍历当前班组的每一个任务，检查当前时间是否冲突
          for (
            let i = 0;
            i < this.taskData[this.chooseClassIndex].length;
            i++
          ) {
            //在编辑信息状态下，如果和当前行currentRow相同，直接跳过
            if (i === this.currentRow) {
              continue;
            }
            //对于遍历的每一个巡检任务，两种情况下是可以修改的
            //1. 新增的任务开始时间大于我的结束时间
            //2. 新增任务的结束时间小于我的开始时间
            else if (
              this.form.time[0] >=
                this.taskData[this.chooseClassIndex][i].time[1] ||
              this.form.time[1] <=
                this.taskData[this.chooseClassIndex][i].time[0]
            ) {
              //这种情况下可以插入
              //但是还要考虑其他的任务，必须跟每一个任务都不冲突才可以，这里不做改动
            } else {
              //找到了第一个产生冲突的任务
              firstConfiltTask = this.taskData[this.chooseClassIndex][i];
              break;
            }
          }
          //如果有产生冲突的，给出提示
          if (firstConfiltTask !== null) {
            this.$message.error(
              "当前任务与开始于" +
                firstConfiltTask.startTime +
                "，结束于" +
                firstConfiltTask.endTime +
                "的巡检任务产生冲突，请重新安排时间！"
            );
          } else {
            //没有冲突，可以插入了！
            //前端先插入
            //如果是新增状态,直接push到尾部就好
            if (this.currentRow === -1) {
              this.$http({
                method: "post",
                url: "/task/addItem",
                headers: {
                  token: localStorage.getItem("token"),
                },
                data: {
                  startTime: this.form.startTime + ":00",
                  endTime: this.form.endTime + ":00",
                  inspectEmployeeNumber: this.form.employeeNumber,
                  inspectEmployeeName: this.form.employeeName,
                  inspectClassName: this.form.className,
                  inspectRouteName: this.form.route,
                },
              }).then((res) => {
                if (res.data === "token无效") {
                  // 清空本地token
                  localStorage.setItem("token", "");
                  // 强制跳转登录界面
                  this.$router.push("/login");
                  // 弹出提示款
                  this.$message.error("登录过期，请重新登录");
                } else if (res.data.code === "0") {
                  //前端修改数据
                  this.form.startTime += ":00";
                  this.form.endTime += ":00";
                  this.taskData[this.chooseClassIndex].push(this.form);
                  //修改后表单为空
                  this.form = {
                    route: "",
                    className: "",
                    time: [],
                    employeeName: "",
                    employeeNumber: "",
                    startTime: "",
                    endTime: "",
                  };
                  this.$message.success(res.data.msg);
                  this.refreshKey++; // 强制刷新
                }
              });
            } else {
              this.$http({
                method: "put",
                url: "/task/updateItem",
                headers: {
                  token: localStorage.getItem("token"),
                },
                data: {
                  id: this.taskData[this.chooseClassIndex][this.currentRow].id,
                  startTime: this.form.startTime + ":00",
                  endTime: this.form.endTime + ":00",
                  inspectEmployeeNumber: this.form.employeeNumber,
                  inspectEmployeeName: this.form.employeeName,
                  inspectClassName: this.form.className,
                  inspectRouteName: this.form.route,
                },
              }).then((res) => {
                if (res.data === "token无效") {
                  // 清空本地token
                  localStorage.setItem("token", "");
                  // 强制跳转登录界面
                  this.$router.push("/login");
                  // 弹出提示款
                  this.$message.error("登录过期，请重新登录");
                } else if (res.data.code === "0") {
                  //前端修改信息
                  this.form.startTime += ":00";
                  this.form.endTime += ":00";
                  this.taskData[this.chooseClassIndex][this.currentRow] =
                    this.form;
                  //修改后表单为空
                  this.form = {
                    route: "",
                    className: "",
                    time: [],
                    employeeName: "",
                    employeeNumber: "",
                    startTime: "",
                    endTime: "",
                  };
                  this.$message.success(res.data.msg);
                  this.refreshKey++; // 强制刷新
                }
              });
            }
            //对选择工号框进行禁用
            this.hasChoosedName = true;
            this.dialogFormVisible = false;
          }
        }
      }
    },
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  mounted() {
    // 查找所有的班
    this.$http
      .get("/class/findAll", {
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
        } else {
          // 查找所有的巡检任务
          this.$http
            .get("/task/findTask", {
              headers: {
                token: localStorage.getItem("token"),
              },
            })
            .then((resp) => {
              if (resp.data === "token无效") {
                // 清空本地token
                localStorage.setItem("token", "");
                // 强制跳转登录界面
                this.$router.push("/login");
                // 弹出提示款
                this.$message.error("登录过期，请重新登录");
              } else {
                let childrenData = [];
                // 此时res.data为所有班的信息,resp.data为所有巡检任务信息
                for (let i = 0; i < res.data.length; i++) {
                  let routeName = "暂定"; // 默认名
                  for (let j = 0; j < resp.data.length; j++) {
                    if (
                      res.data[i].className === resp.data[j].inspectClassName
                    ) {
                      routeName = resp.data[j].inspectRouteName;
                      break;
                    }
                  }
                  childrenData.push({
                    id: i + 1,
                    label: res.data[i].className,
                    route: routeName,
                  });
                }
                this.classData[0].children = childrenData;
              }
            });
          // 查找所有的人
          this.$http
            .get("/employee/findAll", {
              headers: {
                token: localStorage.getItem("token"),
              },
            })
            .then((resp) => {
              if (resp.data === "token无效") {
                // 清空本地token
                localStorage.setItem("token", "");
                // 强制跳转登录界面
                this.$router.push("/login");
                // 弹出提示款
                this.$message.error("登录过期，请重新登录");
              } else {
                for (let i = 0; i < res.data.length; i++) {
                  let employeeArray = [];
                  for (let j = 0; j < resp.data.length; j++) {
                    if (res.data[i].className === resp.data[j].className)
                      employeeArray.push({
                        name: resp.data[j].name,
                        number: resp.data[j].jobNumber,
                      });
                  }
                  this.employeeData.push(employeeArray);
                }
              }
            });
          // 查找所有的子任务
          this.$http
            .get("/task/findItems", {
              headers: {
                token: localStorage.getItem("token"),
              },
            })
            .then((resp) => {
              if (resp.data === "token无效") {
                // 清空本地token
                localStorage.setItem("token", "");
                // 强制跳转登录界面
                this.$router.push("/login");
                // 弹出提示款
                this.$message.error("登录过期，请重新登录");
              } else {
                for (let i = 0; i < res.data.length; i++) {
                  let itemArray = [];
                  for (let j = 0; j < resp.data.length; j++) {
                    if (res.data[i].className === resp.data[j].inspectClassName)
                      itemArray.push({
                        id: resp.data[j].id,
                        route: resp.data[j].inspectRouteName,
                        className: resp.data[j].inspectClassName,
                        employeeName: resp.data[j].inspectEmployeeName,
                        employeeNumber: resp.data[j].inspectEmployeeNumber,
                        startTime: resp.data[j].startTime,
                        endTime: resp.data[j].endTime,
                        time: [
                          parseInt(resp.data[j].startTime.substr(0, 2)),
                          parseInt(resp.data[j].endTime.substr(0, 2)),
                        ],
                      });
                  }
                  this.taskData.push(itemArray);
                }
              }
            });
        }
      });
    // 初始化完成后让this.loading = false停止表格的加载动画
    this.loading = false;
  },
};
</script>

<style scoped>
.show-route-for-class-box {
  display: flex;
  width: 100%;
  padding-left: 10%;
  padding-right: 10%;
}
.el-row {
  height: 100%;
}
.tree-component-external {
  height: 100%;
}
.tree-component-internal {
  height: 100%;
}
.filter-tree {
  height: 100%;
  margin-top: 2%;
}
.tips-for-manager-span {
  width: 50%;
  margin-left: 8%;
  margin-right: 8%;
}
.tips-for-manager-card {
  width: 100%;
  border-radius: 20px;
  margin-top: 2%;
}
.tips-for-manager-card ::v-deep .el-card__body {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.add-new-task-button {
  margin-left: 10%;
  margin-right: 10%;
}
.user-operation-box {
  margin-top: 5%;
  margin-left: 2%;
  margin-right: 2%;
}
.kill-span {
  display: inline-block;
  margin-top: 3%;
}
</style>
