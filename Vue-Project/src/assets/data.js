//菜单栏的信息
const menuInfo = [
    {
      label: '巡检管理',
      icon: 'el-icon-view',
      name: 'inspection',
      children: [
        {
          path: 'pointManage',
          name: 'page_inspection_point',
          label: '巡检点管理',
          icon: '',
          url: '',
        },
        {
          path: 'routeManage',
          name: 'page_inspection_route',
          label: '巡检路线管理',
          icon: '',
          url: '',
        },
        {
          path: 'taskManage',
          name: 'page_inspection_task_management',
          label: '巡检任务管理',
          icon: '',
          url: '',
        },
        {
          path: 'taskSchedule',
          name: 'page_inspection_task_schedule',
          label: '巡检任务安排',
          icon: '',
          url: '',
        },
        {
          path: 'inspectionAbnormal',
          name: 'page_inspection_abnormal',
          label: '巡检异常管理',
          icon: '',
          url: '',
        },
      ],
    },
    {
      label: '危险源管理',
      icon: 'el-icon-warning',
      name: 'danger',
      children: [
        {
          path: 'equipmentManage',
          name: 'page_danger_equipment',
          label: '设备管理',
          icon: '',
          url: '',
        },
        {
          path: 'abnormalManage',
          name: 'page_danger_abnormal',
          label: '异常管理',
          icon: '',
          url: '',
        },
      ],
    },
    {
      path: 'employeeManage',
      name: 'page_employee',
      label: '人员管理',
      icon: 'el-icon-user-solid',
      url: '',
    },
    {
      path: 'print',
      name: 'page_print',
      label: '报表导出',
      icon: 'el-icon-document',
      url: '',
    },
  ]

const POINT_TYPE = [
  "请选择",
  "东一区",
  "东二区",
  "西一区",
  "西二区",
  "南一区",
  "南二区",
  "北一区",
  "北二区",
]
export default{
    menuInfo,
    POINT_TYPE
}
