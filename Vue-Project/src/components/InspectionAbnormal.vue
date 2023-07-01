<template>
  <div class="inspection-abnormal-box">
    <el-row class="search-external-box">
      <!----------------------------搜索框---------------------------------->
      <el-card class="search-internal-card">
        <el-form :inline="true" :model="serarchInfo" class="search-form">
          <el-form-item label="巡检点区域">
            <el-select
              v-model="serarchInfo.area"
              placeholder="请选择巡检点区域"
            >
              <el-option
                v-for="item in equipmentArea"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="巡检点类型">
            <el-input v-model="serarchInfo.type" placeholder="请输入巡检点类型">
            </el-input>
          </el-form-item>
          <el-form-item label="巡检点编号">
            <el-input
              v-model="serarchInfo.equipmentNum"
              placeholder="请输入巡检点编号"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="巡检人员名称">
            <!--change在失去焦点或者用户按下回车键         input在输入值发生变化时改变（注意是单向的，只有从空变为非空才有用）-->
            <el-input v-model="serarchInfo.name" placeholder="输入巡检人员名称">
            </el-input>
          </el-form-item>
          <el-form-item label="巡检人员编号">
            <!--change在失去焦点或者用户按下回车键         input在输入值发生变化时改变（注意是单向的，只有从空变为非空才有用）-->
            <el-input
              v-model="serarchInfo.employeeNum"
              placeholder="输入巡检人员编号"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="巡检点状态">
            <el-select
              v-model="serarchInfo.status"
              clearable
              placeholder="请选择巡检点状态"
            >
              <el-option
                v-for="item in equipmentType"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="Reset">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="SerachAbnormalInfo"
              >查询</el-button
            >
          </el-form-item>
        </el-form>
      </el-card>
    </el-row>

    <el-row class="table-external-box">
      <!-------------------------------表格------------------------------------------->
      <el-card class="table-internal-card">
        <el-table :data="abnormalData" style="width: 100%" max-height="630">
          <el-table-column label="上报时间" width="200">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span>{{ scope.row.time }}</span>
            </template>
          </el-table-column>
          <el-table-column label="巡检点区域" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.area }}</span>
            </template>
          </el-table-column>
          <el-table-column label="巡检点类型" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.type }}</span>
            </template>
          </el-table-column>
          <el-table-column label="巡检点编号" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.equipmentNum }}</span>
            </template>
          </el-table-column>
          <el-table-column label="上报人员姓名" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="上报人员工号" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.employeeNum }}</span>
            </template>
          </el-table-column>
          <el-table-column label="巡检点状态" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.status }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="250">
            <template slot-scope="scope">
              <el-button size="mini" @click="CheckDetail(scope.row)"
                >详细信息</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="UpdateStatus(scope.$index)"
                >更新状态</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-row>
    <!-----------------------表单详细信息------------------------------->
    <el-dialog
      title="巡检点异常详细信息"
      :visible.sync="detailDialogVisible"
      width="30%"
    >
      <div class="detail-info-box">
        <p>
          异常上报时间<span class="detail-info-text">{{
            detailInfo.time
          }}</span>
        </p>
        <p>
          巡检点区域<span class="detail-info-text">{{ detailInfo.area }}</span>
        </p>
        <p>
          巡检点类型<span class="detail-info-text">{{ detailInfo.type }}</span>
        </p>
        <p>
          巡检点编号<span class="detail-info-text">{{
            detailInfo.equipmentNum
          }}</span>
        </p>
        <p>
          巡检人员名称<span class="detail-info-text">{{
            detailInfo.name
          }}</span>
        </p>
        <p>
          巡检人员工号<span class="detail-info-text">{{
            detailInfo.employeeNum
          }}</span>
        </p>
        <p>
          当前状态<span class="detail-info-text">{{ detailInfo.status }}</span>
        </p>
        <p>备注</p>
        <el-input type="textarea" autosize v-model="detailInfo.remark">
        </el-input>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailDialogVisible = false"
          >关闭</el-button
        >
      </span>
    </el-dialog>
    <!---------------------表单，更新状态------------------------------------------>
    <el-dialog title="更新状态" :visible.sync="statusDialogVisible">
      <el-form :model="statusForm">
        <el-form-item label="巡检点状态" :label-width="formLabelWidth">
          <el-select v-model="statusForm.status" placeholder="请更新巡检点状态">
            <el-option
              v-for="item in equipmentType"
              :key="item"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="statusDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="SubmitStatus">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "page_inspection_abnormal",
  data() {
    return {
      //搜索框中的相关信息
      serarchInfo: {
        area: "",
        type: "",
        equipmentNum: "",
        name: "",
        employeeNum: "",
        status: "",
      },
      equipmentArea: [
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
      equipmentType: ["已经报废", "维修完成", "正在维修"],
      //表中的数据
      abnormalData: [
      ],
      detailDialogVisible: false,
      detailInfo: {
        id: "", //数据库中的主键
        time: "", //异常上报时间
        area: "", //巡检点区域
        type: "", //巡检点类型
        equipmentNum: "", //巡检点编号
        name: "", //巡检人员名称
        employeeNum: "", //巡检人员工号
        status: "", //巡检设备当前状态
        remark: "", //备注
      },
      statusDialogVisible: false,
      statusForm: {
        status: "",
      },
      currentRowNum: -1,
      formLabelWidth: "120px",
    };
  },
  methods: {
    //用户点击重置
    Reset: function () {
      //1.清空serarchInfo
      this.serarchInfo = {
        area: "",
        type: "",
        equipmentNum: "",
        name: "",
        employeeNum: "",
        status: "",
      };
    },
    SerachAbnormalInfo: function () {
      this.abnormalData=[];// 清空
      // 先根据前三个数据查询查符合条件的设备
      this.$http
          .get("/point/find", {
            headers: {
              'token': localStorage.getItem('token')
            },
            params: {
              type: this.serarchInfo.type,
              area: (this.serarchInfo.area === '请选择'?'':this.serarchInfo.area),
              number:this.serarchInfo.equipmentNum
            },
          }).then((res) => {
        if(res.data === 'token无效'){
          // 清空本地token
          localStorage.setItem('token','');
          // 强制跳转登录界面
          this.$router.push('/login')
          // 弹出提示款
          this.$message.error('登录过期，请重新登录')
        }else{
          //此时再发起另一个请求
          this.$http
              .get("/employee/find", {
                headers: {
                  'token': localStorage.getItem('token')
                },
                params: {
                  name: this.serarchInfo.name,
                  jobNumber: this.serarchInfo.employeeNum
                },
              }).then((resp) => {
            if(resp.data === 'token无效'){
              // 清空本地token
              localStorage.setItem('token','');
              // 强制跳转登录界面
              this.$router.push('/login')
              // 弹出提示款
              this.$message.error('登录过期，请重新登录')
            }else{
              //利用得到的points和employees以及status得到所有符合条件的日志信息
              if(res.data.length!=0&&resp.data.length!=0){
                for(let i=0;i<res.data.length;i++){
                  for(let j=0;j<resp.data.length;j++){
                    this.$http.get('/pointinfo/findLogs',{
                      headers: {
                        'token': localStorage.getItem('token')
                      },
                      params: {
                        pid: res.data[i].id,
                        eid: resp.data[j].id,
                        status: this.serarchInfo.status
                      },
                    }).then((respo) => {
                      if(respo.data === 'token无效'){
                        // 清空本地token
                        localStorage.setItem('token','');
                        // 强制跳转登录界面
                        this.$router.push('/login')
                        // 弹出提示款
                        this.$message.error('登录过期，请重新登录')
                      }else if(respo.data!==null && respo.data.length!=0){
                        for(let k=0;k<respo.data.length;k++){
                          this.abnormalData.push({
                            id:respo.data[k].id,
                            time:respo.data[k].reportTime,
                            area: res.data[i].area,
                            type: res.data[i].type,
                            equipmentNum: res.data[i].number,
                            name: resp.data[j].name,
                            employeeNum: resp.data[j].jobNumber,
                            status: respo.data[k].status,
                            remark: respo.data[k].description
                          })
                        }

                      }
                    })
                  }
                }// end for
              }else if(res.data.length!=0){
                for(let i=0;i<res.data.length;i++){
                  this.$http.get('/pointinfo/findLogs',{
                    headers: {
                      'token': localStorage.getItem('token')
                    },
                    params: {
                      pid: res.data[i].id,
                      eid: 0,
                      status: this.serarchInfo.status
                    },
                  }).then((respo) => {
                    if(respo.data === 'token无效'){
                      // 清空本地token
                      localStorage.setItem('token','');
                      // 强制跳转登录界面
                      this.$router.push('/login')
                      // 弹出提示款
                      this.$message.error('登录过期，请重新登录')
                    }else if(respo.data!==null && respo.data.length!=0){
                      for(let k=0;k<respo.data.length;k++){
                        this.$http.get('/employee/findById',{
                          headers: {
                            'token': localStorage.getItem('token')
                          },
                          params: {
                            id:respo.data[k].inspectEmployeeId
                          }
                        }).then((respon) => {
                          if(respon.data === 'token无效'){
                            // 清空本地token
                            localStorage.setItem('token','');
                            // 强制跳转登录界面
                            this.$router.push('/login')
                            // 弹出提示款
                            this.$message.error('登录过期，请重新登录')
                          }else{
                            this.abnormalData.push({
                              id:respo.data[k].id,
                              time:respo.data[k].reportTime,
                              area: res.data[i].area,
                              type: res.data[i].type,
                              equipmentNum: res.data[i].number,
                              name: respon.data.name,
                              employeeNum: respon.data.jobNumber,
                              status: respo.data[k].status,
                              remark: respo.data[k].description
                            })
                          }

                        })

                      }

                    }
                  })
                }// end for
              }else if(resp.data.length!=0){
                for(let j=0;j<resp.data.length;j++){
                  this.$http.get('/pointinfo/findLogs',{
                    headers: {
                      'token': localStorage.getItem('token')
                    },
                    params: {
                      pid: 0,
                      eid: resp.data[j].id,
                      status: this.serarchInfo.status
                    },
                  }).then((respo) => {
                    if(respo.data === 'token无效'){
                      // 清空本地token
                      localStorage.setItem('token','');
                      // 强制跳转登录界面
                      this.$router.push('/login')
                      // 弹出提示款
                      this.$message.error('登录过期，请重新登录')
                    }else if(respo.data!==null && respo.data.length!=0){
                      for(let k=0;k<respo.data.length;k++){
                        this.$http.get('/point/findById',{
                          headers: {
                            'token': localStorage.getItem('token')
                          },
                          params: {
                            id:respo.data[k].inspectPointId
                          }
                        }).then((respon) => {
                          if(respon.data === 'token无效'){
                            // 清空本地token
                            localStorage.setItem('token','');
                            // 强制跳转登录界面
                            this.$router.push('/login')
                            // 弹出提示款
                            this.$message.error('登录过期，请重新登录')
                          }else{
                            this.abnormalData.push({
                              id:respo.data[k].id,
                              time:respo.data[k].reportTime,
                              area: respon.data.area,
                              type: respon.data.type,
                              equipmentNum: respon.data.number,
                              name: resp.data[j].name,
                              employeeNum: resp.data[j].jobNumber,
                              status: respo.data[k].status,
                              remark: respo.data[k].description
                            })
                          }

                        })

                      }

                    }
                  })
                }// end for
              }else {
                this.$http.get('/pointinfo/findLogs',{
                  headers: {
                    'token': localStorage.getItem('token')
                  },
                  params: {
                    pid: 0,
                    eid: 0,
                    status: this.serarchInfo.status
                  },
                }).then((respo) => {
                  if(respo.data === 'token无效'){
                    // 清空本地token
                    localStorage.setItem('token','');
                    // 强制跳转登录界面
                    this.$router.push('/login')
                    // 弹出提示款
                    this.$message.error('登录过期，请重新登录')
                  }else if(respo.data!==null && respo.data.length!=0){
                    for(let k=0;k<respo.data.length;k++){
                      this.$http.get('/point/findById',{
                        headers: {
                          'token': localStorage.getItem('token')
                        },
                        params: {
                          id:respo.data[k].inspectPointId
                        }
                      }).then((respon) => {
                        if(respon.data === 'token无效'){
                          // 清空本地token
                          localStorage.setItem('token','');
                          // 强制跳转登录界面
                          this.$router.push('/login')
                          // 弹出提示款
                          this.$message.error('登录过期，请重新登录')
                        }else{
                          this.$http.get('/employee/findById',{
                            headers: {
                              'token': localStorage.getItem('token')
                            },
                            params: {
                              id: respo.data[k].inspectEmployeeId
                            }
                          }).then((respons) => {
                            if(respons.data === 'token无效'){
                              // 清空本地token
                              localStorage.setItem('token','');
                              // 强制跳转登录界面
                              this.$router.push('/login')
                              // 弹出提示款
                              this.$message.error('登录过期，请重新登录')
                            }else{
                              this.abnormalData.push({
                                id:respo.data[k].id,
                                time:respo.data[k].reportTime,
                                area: respon.data.area,
                                type: respon.data.type,
                                equipmentNum: respon.data.number,
                                name: respons.data.name,
                                employeeNum: respons.data.jobNumber,
                                status: respo.data[k].status,
                                remark: respo.data[k].description
                              })
                            }

                          })
                        }

                      })

                    }

                  }
                })
              }

            }
          })
        }
      })
    },
    CheckDetail: function (rowData) {
      this.detailInfo = {
        id: "", //数据库中的主键
        time: "", //异常上报时间
        area: "", //巡检点区域
        type: "", //巡检点类型
        equipmentNum: "", //巡检点编号
        name: "", //巡检人员名称
        employeeNum: "", //巡检人员工号
        status: "", //巡检设备当前状态
        remark: "", //备注
      };
      this.detailDialogVisible = true;
      this.detailInfo = JSON.parse(window.JSON.stringify(rowData));
      console.log(this.detailInfo);
    },
    UpdateStatus: function (rowNum) {
      //将原来的表单信息清空
      this.statusForm.status = "";
      this.statusDialogVisible = true;
      this.currentRowNum = rowNum;
    },
    SubmitStatus: function () {
      //如果两个信息相同，提示用户
      if (
        this.abnormalData[this.currentRowNum].status === this.statusForm.status
      ) {
        this.$message({
          message: "不可提交修改设备相同的状态",
          type: "warning",
        });
      } else {
        this.$http.get('/pointinfo/updateResult',{
          headers: {
            'token': localStorage.getItem('token')
          },
          params: {
            id: this.abnormalData[this.currentRowNum].id,
            status: this.statusForm.status
          }
        }).then((res) => {
          if(res.data === 'token无效'){
            // 清空本地token
            localStorage.setItem('token','');
            // 强制跳转登录界面
            this.$router.push('/login')
            // 弹出提示款
            this.$message.error('登录过期，请重新登录')
          }else this.$message.success(res.data.msg);
        })
        //*************************************后端接口***********************************************//
        //用户现在需要修改设备的状态
        //个人感觉不需要考虑管理者这里困难出现的操作，因为更新状态的时候管理者也会出错
        //将在数据库中巡检任务的唯一id号为this.abnormalData[this.currentRowNum].id的设备状态修改成this.statusForm.status

        //******如果巡检任务的状态从正在维修变为了维修完成，数据大屏上应该出现一个滚动条信息（XXX设备以及维修完成）********//
        //*******也就是后端还要记录一下操作者改变状态的时间，数据大屏调用接口的时候通过时间来显示（每20s调用一次接口，每次记录当前滚动条中的最晚时间，每次调用时，新增时间在上次最晚时间之后的信息）

        //前端修改
        this.abnormalData[this.currentRowNum].status = this.statusForm.status;
        //关闭页面
        this.statusDialogVisible = false;
      }
    },
  },
};
</script>

<style scoped>
.inspection-abnormal-box {
  height: 100%;
  width: 100%;
}
.search-external-box {
  width: 100%;
}
.table-external-box {
  width: 100%;
}
.search-internal-card {
  margin: 2% 2% 2% 2%;
  padding-top: 2%;
  padding-left: 4%;
  height: 40%;
}
.search-internal-card ::v-deep .el-card__body {
  height: 100%;
  padding: 0;
}
</style>
