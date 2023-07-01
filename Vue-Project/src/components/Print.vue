<template>
<div>
  <el-row>
    <el-col :span="4"><el-button @click="findAll1">巡检点日志查询</el-button></el-col>
    <el-col :span="4"><el-button @click="findAll2">危险源日志查询</el-button></el-col>
    <el-col :span="4">
      <download-excel class = "export-excel-wrapper" :header="title" :data="tableData" :fields = "json_fields" name = "异常报表">
        <el-button>导出为excel表格</el-button>
      </download-excel>
    </el-col>
  </el-row>
  <el-row>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          prop="reportTime"
          label="上报时间"
          width="180">
      </el-table-column>
      <el-table-column
          prop="area"
          label="设备区域"
          width="180">
      </el-table-column>
      <el-table-column
          prop="type"
          label="设备类型">
      </el-table-column>
      <el-table-column
          prop="number"
          label="设备编号">
      </el-table-column>
      <el-table-column
          prop="name"
          label="上报人员姓名">
      </el-table-column>
      <el-table-column
          prop="jobNumber"
          label="上报人员工号">
      </el-table-column>
      <el-table-column
          prop="status"
          label="设备状态">
      </el-table-column>
      <el-table-column
          prop="remark"
          label="备注">
      </el-table-column>
    </el-table>
  </el-row>
</div>

</template>

<script>
export default {
  name: "page_print",
  data() {
    return {
      title: '东南石化生产异常报表',
      json_fields: {
        '上报时间': 'reportTime',
        '设备区域': 'area',
        '设备类型': 'type',
        '设备编号': 'number',
        '上报人员姓名': 'name',
        '上报人员工号': 'jobNumber',
        '设备状态': 'status',
        '备注': 'remark'
      },
      tableData: [
      ]
    }
  },
  methods: {
    findAll1: function (){
      this.tableData=[];// 先清空
      this.$http.get('/pointinfo/findAll',{
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
        }else if(res.data!==null && res.data.length!=0){
          for(let k=0;k<res.data.length;k++){
            this.$http.get('/point/findById',{
              headers: {
                'token': localStorage.getItem('token')
              },
              params: {
                id:res.data[k].inspectPointId
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
                this.$http.get('/employee/findById',{
                  headers: {
                    'token': localStorage.getItem('token')
                  },
                  params: {
                    id: res.data[k].inspectEmployeeId
                  }
                }).then((respo) => {
                  if(respo.data === 'token无效'){
                    // 清空本地token
                    localStorage.setItem('token','');
                    // 强制跳转登录界面
                    this.$router.push('/login')
                    // 弹出提示款
                    this.$message.error('登录过期，请重新登录')
                  }else{
                    this.tableData.push({
                      reportTime:res.data[k].reportTime,
                      area: resp.data.area,
                      type: resp.data.type,
                      number: resp.data.number,
                      name: respo.data.name,
                      jobNumber: respo.data.jobNumber,
                      status: res.data[k].status,
                      remark: res.data[k].description
                    })
                  }

                })
              }

            })

          }// end for
        }
      })
    },
    findAll2: function (){
      this.tableData=[];
      this.$http.get('/hazardinfo/findAll',{
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
        }else if(res.data!==null && res.data.length!=0){
          for(let k=0;k<res.data.length;k++){
            this.$http.get('/hazard/findById',{
              headers: {
                'token': localStorage.getItem('token')
              },
              params: {
                id:res.data[k].hazardSourceId
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
                this.tableData.push({
                  reportTime:res.data[k].reportTime,
                  area: resp.data.area,
                  type: resp.data.type,
                  number: resp.data.number,
                  status: res.data[k].status,
                  remark: res.data[k].exceptionDescription
                })
              }

            })

          }// end for
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
