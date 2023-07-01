<template>
  <el-row class="main-box">
    <el-col :span="24" class="tree-external-box">
      <!------------------------------------树形结构------------------------------------------->
      <el-card class="tree-external-box-card">
        <el-input placeholder="输入关键字进行过滤" v-model="filterText">
        </el-input>
        <el-tree
          class="filter-tree"
          node-key="id"
          default-expand-all
          :data="Classdata"
          :props="defaultProps"
          :filter-node-method="filterNode"
          ref="tree"
          :expand-on-click-node="false"
          @node-click="ChooseClass"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span>
              <el-button
                type="text"
                v-if="node.label === '东南石化'"
                size="mini"
                @click="AddNewClass()"
              >
                添加班组
              </el-button>
              <el-button
                v-if="node.label !== '东南石化'"
                type="text"
                size="mini"
                @click="() => DeleteClass(node, data)"
              >
                删除班组
              </el-button>
            </span>
          </span>
        </el-tree>
      </el-card>
      <!--------------------------------------输入班组名称的对话框---------------------------------------------------->
    </el-col>
    <el-dialog title="新建班组" :visible.sync="dialogForAddClassVisible">
      <el-form :model="addClassForm">
        <el-form-item label="班组名称" :label-width="formLabelWidth">
          <el-input v-model="addClassForm.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogForAddClassVisible = false">取 消</el-button>
        <el-button type="primary" @click="SubmitNewClass">确 定</el-button>
      </div>
    </el-dialog>
    <el-col :span="24" class="table-external-box">
      <el-row class="search-box">
        <el-card class="search-box-card">
          <!----------------------------------------搜索框----------------------------------------------------->
          <el-form :inline="true" :model="SearchInfo" class="search-box-form">
            <el-form-item label="巡检人员名称">
              <el-input v-model="SearchInfo.name" placeholder="巡检人员名称">
              </el-input>
            </el-form-item>
            <el-form-item label="巡检人员工号">
              <el-input v-model="SearchInfo.number" placeholder="巡检人员工号">
              </el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-select v-model="SearchInfo.gender" placeholder="请选择">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="SearchEmployee">查询</el-button>
            </el-form-item>
          </el-form>
          <!----------------------------------------搜索框----------------------------------------------------->
        </el-card>
      </el-row>
      <el-row class="button-box">
        <el-button class="add-employee-button" @click="AddNewEmployee"
          >添加人员</el-button
        >
      </el-row>
      <el-row class="table-box">
        <el-card class="table-box-card">
          <!----------------------------------------表格数据----------------------------------------------->
          <!-----------------------------
          id:"0",//数据库中的主键
          name:"郑孝骥",//姓名
          className: "巡检1班",//班组名称
          number:"213202853",//工号
          age: 19,//年龄
          phone: "13996601802",//联系电话
          jobDuty: "学生",//职务
          gender: "男",//性别
          userName: "硝基苯",//app用户名
          userPassword: "123456789",//app密码
          times_should: 233,//应巡检次数
          times_actual: 231,//实际巡检次数
          times_abnormal: 88,//发现异常数
          ------------------------------->
          <el-table
            :data="
              tableData.slice(
                (currentPage - 1) * tablePageSize,
                currentPage * tablePageSize
              )
            "
            style="width: 100%"
            max-height="750"
          >
            <el-table-column label="姓名" width="150">
              <template slot-scope="scope">
                <span>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column label="工号" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.number }}</span>
              </template>
            </el-table-column>
            <el-table-column label="性别" width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.gender }}</span>
              </template>
            </el-table-column>
            <el-table-column label="所属班组" width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.className }}</span>
              </template>
            </el-table-column>
            <el-table-column label="联系方式" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.phone }}</span>
              </template>
            </el-table-column>
            <el-table-column label="职务" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.jobDuty }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="220">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="SearchDetail(scope.$index)"
                  >详情</el-button
                >
                <el-button
                  size="mini"
                  type="success"
                  @click="EditInfo(scope.$index)"
                  >编辑</el-button
                >
                <el-button
                  size="mini"
                  type="danger"
                  @click="DeleteInfo(scope.$index)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="tablePageSize"
            layout="total, prev, pager, next"
            :total="tableTotalData"
          >
          </el-pagination>
        </el-card>
        <!---------------------------表单（新增、修改、详情）---------------------------------------------->
        <el-dialog
          title="巡检人员详细信息"
          :visible.sync="dialogForAddEmployeeVisible"
        >
          <el-form :model="EmployeeForm">
            <el-form-item label="姓名" :label-width="formLabelWidth">
              <el-input
                v-model="EmployeeForm.name"
                :disabled="isDetail"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item label="工号" :label-width="formLabelWidth">
              <el-input
                v-model="EmployeeForm.number"
                autocomplete="off"
                :disabled="isDetail || isEditInfo"
              ></el-input>
            </el-form-item>
            <el-form-item label="所属班组" :label-width="formLabelWidth">
              <el-select
                v-model="EmployeeForm.className"
                placeholder="请选择所属班组"
                :disabled="isDetail"
              >
                <el-option
                  v-for="item in Classdata[0].children"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="年龄" :label-width="formLabelWidth">
              <el-input-number
                v-model="EmployeeForm.age"
                :min="18"
                :max="100"
                label="填入用户的年龄"
                :disabled="isDetail"
              ></el-input-number>
            </el-form-item>
            <el-form-item label="联系方式" :label-width="formLabelWidth">
              <el-input
                v-model="EmployeeForm.phone"
                autocomplete="off"
                :disabled="isDetail"
              ></el-input>
            </el-form-item>
            <el-form-item label="职务" :label-width="formLabelWidth">
              <el-input
                v-model="EmployeeForm.jobDuty"
                autocomplete="off"
                :disabled="isDetail"
              ></el-input>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth">
              <el-select
                v-model="EmployeeForm.gender"
                placeholder="请选择性别"
                :disabled="isDetail"
              >
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="APP用户名" :label-width="formLabelWidth">
              <el-input
                v-model="EmployeeForm.userName"
                autocomplete="off"
                :disabled="isDetail"
              ></el-input>
            </el-form-item>
            <el-form-item label="APP密码" :label-width="formLabelWidth">
              <el-input
                v-model="EmployeeForm.userPassword"
                autocomplete="off"
                :disabled="isDetail"
              ></el-input>
            </el-form-item>
            <!-------------------------------------只能查看-------------------------------------------------------->
            <el-form-item
              label="应巡检次数"
              :label-width="formLabelWidth"
              v-if="isDetail"
            >
              <el-input
                v-model="EmployeeForm.times_should"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              label="实际巡检次数"
              :label-width="formLabelWidth"
              v-if="isDetail"
            >
              <el-input
                v-model="EmployeeForm.times_actual"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              label="发现设备异常数"
              :label-width="formLabelWidth"
              v-if="isDetail"
            >
              <el-input
                v-model="EmployeeForm.times_abnormal"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button
              type="primary"
              @click="dialogForAddEmployeeVisible = false"
              v-show="isDetail"
              >关闭</el-button
            >
            <el-button
              @click="dialogForAddEmployeeVisible = false"
              v-show="!isDetail"
              >取 消</el-button
            >
            <el-button type="primary" @click="SubmitEmployee" v-show="!isDetail"
              >保存</el-button
            >
          </div>
        </el-dialog>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "page_employee",
  data() {
    return {
      filterText: "",
      currentId: 100,
      Classdata: [
        {
          id: 1,
          label: "东南石化",
          children: [
          ],
        },
      ],
      defaultProps: {
        id: "id",
        children: "children",
        label: "label",
      },
      SearchInfo: {
        name: "",
        gender: "",
        number: "",
      },
      //前端缓存的信息
      tableData: [
      ],
      //用户新增班组弹出输入框
      dialogForAddClassVisible: false,
      //用户新增巡检人员输入框
      dialogForAddEmployeeVisible: false,
      //用户当前打开的班级信息页面
      currentClass: "",
      addClassForm: {
        name: "",
      },
      EmployeeForm: {
        id: "", //数据库中的主键
        name: "", //姓名
        className: "", //班组名称
        number: "", //工号
        age: 0, //年龄
        phone: "", //联系电话
        jobDuty: "", //职务
        gender: "", //性别
        userName: "", //app用户名
        userPassword: "", //app密码
        times_should: 0, //应巡检次数
        times_actual: 0, //实际巡检次数
        times_abnormal: 0, //发现异常数
      },
      formLabelWidth: "120px",
      //下面两个变量用于判断用户是否进行了查看详情/修改信息
      isDetail: false,
      isEditInfo: false,
      //用户点击按钮所在表格中的行
      currentIndex: -1,
      //分页
      currentPage: 1,
      tablePageSize: 9,
      tableTotalData: 10,
    };
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    SearchEmployee() {
      //****************************************************************************************//
      if (
        this.SearchInfo.name === "" &&
        this.SearchInfo.number === "" &&
        this.SearchInfo.gender === ""
      ) {
        this.$message.error("请输入信息之后再进行查询！");
      } else {
        if(this.currentClass === '东南石化'||this.currentClass===''){
          this.$http.get('/employee/find2',{
            headers:{
              'token': localStorage.getItem('token')
            },
            params: {
              name:this.SearchInfo.name,
              jobNumber: this.SearchInfo.number,
              sex: this.SearchInfo.gender
            }
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else{
              this.tableData=[];
              for(let i=0;i<res.data.length;i++){
                this.tableData.push({
                  id: res.data[i].id,
                  name: res.data[i].name,
                  className: res.data[i].className,
                  number: res.data[i].jobNumber,
                  age: res.data[i].age,
                  phone:res.data[i].tel,
                  jobDuty: res.data[i].job,
                  gender: res.data[i].sex,
                  userName: res.data[i].userName,
                  userPassword: res.data[i].password,
                  times_should: res.data[i].inspectNumber,
                  times_actual: res.data[i].inspectFinishNumber,
                  times_abnormal: res.data[i].exceptionFoundNumber
                })
              }
              this.tableTotalData = this.tableData.length
            }
          })
        }else{
          this.$http.get('/employee/find3',{
            headers:{
              'token': localStorage.getItem('token')
            },
            params: {
              name:this.SearchInfo.name,
              jobNumber: this.SearchInfo.number,
              sex: this.SearchInfo.gender,
              className: this.currentClass
            }
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else{
              this.tableData=[];
              for(let i=0;i<res.data.length;i++){
                this.tableData.push({
                  id: res.data[i].id,
                  name: res.data[i].name,
                  className: res.data[i].className,
                  number: res.data[i].jobNumber,
                  age: res.data[i].age,
                  phone:res.data[i].tel,
                  jobDuty: res.data[i].job,
                  gender: res.data[i].sex,
                  userName: res.data[i].userName,
                  userPassword: res.data[i].password,
                  times_should: res.data[i].inspectNumber,
                  times_actual: res.data[i].inspectFinishNumber,
                  times_abnormal: res.data[i].exceptionFoundNumber
                })
              }
              this.tableTotalData = this.tableData.length
            }
          })
        }
      }
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    SearchDetail: function (index) {
      this.isDetail = true;
      this.isEditInfo = false;
      this.dialogForAddEmployeeVisible = true;
      this.currentIndex = index;
      this.EmployeeForm = JSON.parse(
        window.JSON.stringify(this.tableData[index])
      );
    },
    EditInfo: function (index) {
      this.isEditInfo = true;
      this.isDetail = false;
      this.dialogForAddEmployeeVisible = true;
      this.currentIndex = index;
      //编辑表为深拷贝
      this.EmployeeForm = JSON.parse(
        window.JSON.stringify(this.tableData[index])
      );
    },
    AddNewEmployee: function (index) {
      this.isEditInfo = false;
      this.isDetail = false;
      this.dialogForAddEmployeeVisible = true;
      this.currentIndex = index;
      this.EmployeeForm = {
        id: "", //数据库中的主键
        name: "", //姓名
        className: "", //班组名称
        number: "", //工号
        age: 0, //年龄
        phone: "", //联系电话
        jobDuty: "", //职务
        gender: "", //性别
        userName: "", //app用户名
        userPassword: "", //app密码
        times_should: 0, //应巡检次数
        times_actual: 0, //实际巡检次数
        times_abnormal: 0, //发现异常数
      };
    },
    SubmitEmployee: function () {
      //********************************************************************************************************//
      //用户点击提交，两种情况
      //考虑数据存在空的情况
      if (
        this.EmployeeForm.name === "" ||
        this.EmployeeForm.number === "" ||
        this.EmployeeForm.className === "" ||
        this.EmployeeForm.age === 0 ||
        this.EmployeeForm.number === "" ||
        this.EmployeeForm.jobDuty === "" ||
        this.EmployeeForm.gender === "" ||
        this.EmployeeForm.userName === "" ||
        this.EmployeeForm.userPassword === ""
      ) {
        this.$message.eroor("请输入完整的信息！");
      } else {

        if (this.isEditInfo) {
          this.$http({
            method: 'put',
            url: '/employee/update',
            headers: {
              'token': localStorage.getItem('token')
            },
            data: {
              id: this.EmployeeForm.id,
              name: this.EmployeeForm.name,
              jobNumber: this.EmployeeForm.number,
              className: this.EmployeeForm.className,
              age: this.EmployeeForm.age,
              tel: this.EmployeeForm.phone,
              job: this.EmployeeForm.jobDuty,
              sex: this.EmployeeForm.gender,
              userName: this.EmployeeForm.userName,
              password: this.EmployeeForm.userPassword
            }
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else if(res.data.code === '1')this.$message.error(res.data.msg);
            else if(res.data.code === '0'){
              this.tableData[this.currentIndex] = this.EmployeeForm
              this.dialogForAddEmployeeVisible = false;
              this.isEditInfo = false;
              this.isDetail = false;
              this.$message.success(res.data.msg);
            }
          })

        } else {
          this.$http({
            method: 'post',
            url: '/employee/add',
            headers: {
              'token': localStorage.getItem('token')
            },
            data: {
              name: this.EmployeeForm.name,
              jobNumber: this.EmployeeForm.number,
              className: this.EmployeeForm.className,
              age: this.EmployeeForm.age,
              tel: this.EmployeeForm.phone,
              job: this.EmployeeForm.jobDuty,
              sex: this.EmployeeForm.gender,
              userName: this.EmployeeForm.userName,
              password: this.EmployeeForm.userPassword
            }
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else if(res.data.code === '1')this.$message.error(res.data.msg);
            else if(res.data.code === '0'){
              this.tableData[this.currentIndex] = this.EmployeeForm
              this.dialogForAddEmployeeVisible = false;
              this.isEditInfo = false;
              this.isDetail = false;
              this.$message.success(res.data.msg);
            }
          })
        }
      }
    },
    DeleteInfo: function (index) {
      let that = this;
      this.$confirm("此操作将永久删除该员工的信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          that.$http.delete('/employee/delete',{
            headers: {
              'token': localStorage.getItem('token')
            },
            params: {
              id: that.tableData[index].id
            }
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              this.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else if(res.data.code === '0'){
              that.tableData.splice(index, 1);
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            }
          })

        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //用户点击树形结构的某一个节点调用该函数
    ChooseClass: function (needData, data1, data2) {
      if (needData.label === "东南石化") {
        this.currentClass = needData.label; //东南石化赋值
        this.$http.get('/employee/findAll',{
          headers: {
            'token': localStorage.getItem('token')
          }
        }).then((res) => {
          if (res.data === "token无效") {
            // 清空本地token
            localStorage.setItem("token", "");
            // 强制跳转登录界面
            this.$router.push("/login");
            // 弹出提示款
            this.$message.error("登录过期，请重新登录");
          } else{
            this.tableData=[];
            for(let i=0;i<res.data.length;i++){
              this.tableData.push({
                id: res.data[i].id,
                name: res.data[i].name,
                className: res.data[i].className,
                number: res.data[i].jobNumber,
                age: res.data[i].age,
                phone:res.data[i].tel,
                jobDuty: res.data[i].job,
                gender: res.data[i].sex,
                userName: res.data[i].userName,
                userPassword: res.data[i].password,
                times_should: res.data[i].inspectNumber,
                times_actual: res.data[i].inspectFinishNumber,
                times_abnormal: res.data[i].exceptionFoundNumber
              })
            }
            this.tableTotalData = this.tableData.length
          }
        })

      } //用户点击的是子节点（班组）
      else {
        this.currentClass = needData.label; //班组信息赋值
        this.$http.get('/employee/finds',{
          headers: {
            'token': localStorage.getItem('token')
          },
          params: {
            className: this.currentClass
          }
        }).then((res) => {
          if (res.data === "token无效") {
            // 清空本地token
            localStorage.setItem("token", "");
            // 强制跳转登录界面
            this.$router.push("/login");
            // 弹出提示款
            this.$message.error("登录过期，请重新登录");
          } else{
            this.tableData=[];
            for(let i=0;i<res.data.length;i++){
              this.tableData.push({
                id: res.data[i].id,
                name: res.data[i].name,
                className: res.data[i].className,
                number: res.data[i].jobNumber,
                age: res.data[i].age,
                phone:res.data[i].tel,
                jobDuty: res.data[i].job,
                gender: res.data[i].sex,
                userName: res.data[i].userName,
                userPassword: res.data[i].password,
                times_should: res.data[i].inspectNumber,
                times_actual: res.data[i].inspectFinishNumber,
                times_abnormal: res.data[i].exceptionFoundNumber
              })
            }
            this.tableTotalData = this.tableData.length
          }
        })
      }
    },
    AddNewClass: function () {
      this.dialogForAddClassVisible = true;
    },
    //用户在新增班组界面填写了确认
    SubmitNewClass: function () {
      //***********************************用户想要创建班组名称为this.addClassForm.name的班组**********************************************//
      //如果内容为空，提示
      if (this.addClassForm.name === "") {
        this.$message.error("请输入班组名称！");
      } else {
        this.$http({
          method: 'post',
          url: 'class/add',
          headers: {
            'token': localStorage.getItem('token')
          },
          data: {
            className: this.addClassForm.name
          }
        }).then((res) => {
          if(res.data === 'token无效'){
            // 清空本地token
            localStorage.setItem('token','');
            // 强制跳转登录界面
            this.$router.push('/login')
            // 弹出提示款
            this.$message.error('登录过期，请重新登录')
          }else if(res.data.code === '1'){
            this.$message.error(res.data.msg);
          }else if(res.data.code === '0'){
            this.$message.success(res.data.msg);
            this.Classdata[0].children.push({
              id: this.currentId++,
              label: this.addClassForm.name,
            });
            this.dialogForAddClassVisible = false;
          }
        })
      }
    },
    DeleteClass: function (node, data) {
      let that = this;
      this.$confirm("此操作将永久删除该班组, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(()=>{
          that.$http.delete('/class/delete',{
            headers: {
              'token': localStorage.getItem('token')
            },
            params: {
              className: that.currentClass
            }
          }).then((res) => {
            if (res.data === "token无效") {
              // 清空本地token
              localStorage.setItem("token", "");
              // 强制跳转登录界面
              that.$router.push("/login");
              // 弹出提示款
              this.$message.error("登录过期，请重新登录");
            } else if(res.data.code === '1')that.$message.error(res.data.msg);
            else if(res.data.code === '0'){
              const parent = node.parent;
              const children = parent.data.children || parent.data;
              const index = children.findIndex((d) => d.id === data.id);
              children.splice(index, 1);
              that.$message.success(res.data.msg);
            }
          })
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
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
            for(let i=0;i<res.data.length;i++){
              this.Classdata[0].children.push({
                id: i+1,
                label: res.data[i].className,
                children: null
              })
            }
          }
        })
  },
};
</script>

<style scoped>
.main-box {
  height: 95vh;
  width: 100%;
}
.tree-external-box {
  height: 95vh;
  width: 15%;
}
.table-external-box {
  height: 95vh;
  width: 85%;
}
.search-box {
  height: 20vh;
}
.button-box {
  height: 5vh;
}
.table-box {
  height: 70vh;
}
.add-employee-button {
  margin-left: 8%;
  margin-top: 2%;
}
.tree-external-box-card {
  height: 94.8vh;
}
.search-box-card {
  height: 70%;
  width: 95%;
  margin-left: 2.5%;
  margin-top: 2%;
  border-radius: 20px;
}
.table-box-card {
  height: 92%;
  width: 95%;
  margin-left: 2.5%;
  margin-top: 2.5%;
}
.search-box-form {
  margin-top: 2.5%;
 /* margin-left: 5%; */
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
