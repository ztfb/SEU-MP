import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/components/Login";
import Register from "@/components/Register";
import Main from "../views/Main";
import Home from "@/components/Home";
import InspectionPoint from "@/components/InspectionPoint";
import InspectionRoute from "@/components/InspectionRoute";
import InspectionTaskManagement from "@/components/InspectionTaskManagement";
import InspectionTaskSchedule from "@/components/InspectionTaskSchedule";
import InspectionAbnormal from "@/components/InspectionAbnormal";
import DanagerEquipment from "@/components/DanagerEquipment";
import DanagerAbnormal from "@/components/DanagerAbnormal";
import Employee from "@/components/Employee";
import Print from "@/components/Print";

//郑孝骥新增：修改巡检信息页面以及新增巡检点
import PointInfoChange from "@/components/InspectionChangePointInfo.vue";
import AddNewPoint from "@/components/InspectionAddNewPoint.vue";
Vue.use(VueRouter)


const routes = [
    {
        // 设置进入页面时的重定向
        path: '/',
        redirect: () => {
            localStorage.removeItem("token");
            let token = localStorage.getItem('token')
            if (token === null || token === '' || token === undefined) {
                return '/login'
            } else {
                return '/main'
            }
        }
    },
    {
        path: '/login',
        name: 'page_login',
        component: Login,
        children: [
            {
                path: '*',
                redirect: '/login'
            }
        ]
    },
    {
        path: '/register',
        name: 'page_register',
        component: Register,
        children: [
            {
                path: '*',
                redirect: '/register'
            }
        ]
    },
    {
        path: '/main',
        name: 'page_main',
        redirect: '/main/home',
        component: Main,
        children: [
            {
                path: 'home',
                name: 'page_home',
                component: Home
            },
            {
                path: 'pointManage',
                name: 'page_inspection_point',
                component: InspectionPoint,
            },
            //郑孝骥新增：修改巡检信息页面
            {
                path: 'pointInfoChange',
                name: 'page_inspection_change_point_info',
                component: PointInfoChange
            },
            //郑孝骥新增：新增巡检点界面
            {
                path: 'addNewPoint',
                name: 'page_inspection_add_new_point',
                component: AddNewPoint
            },
            {
                path: 'routeManage',
                name: 'page_inspection_route',
                component: InspectionRoute
            },
            {
                path: 'taskManage',
                name: 'page_inspection_task_management',
                component: InspectionTaskManagement
            },
            {
                path: 'taskSchedule',
                name: 'page_inspection_task_schedule',
                component: InspectionTaskSchedule
            },
            {
                path: 'inspectionAbnormal',
                name: 'page_inspection_abnormal',
                component: InspectionAbnormal
            },
            {
                path: 'equipmentManage',
                name: 'page_danger_equipment',
                component: DanagerEquipment
            },
            {
                path: 'abnormalManage',
                name: 'page_danger_abnormal',
                component: DanagerAbnormal
            },
            {
                path: 'employeeManage',
                name: 'page_employee',
                component: Employee
            },
            {
                path: 'print',
                name: 'page_print',
                component: Print
            },
        ]
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})
router.beforeEach((to, from, next) => {
     // 默认是以登录状态
     let flag = true
     let token = localStorage.getItem('token')
     if (token === null || token === '' || token === undefined) {
         flag = false
     }
     if (flag) {
         if (to.name === 'page_login') next('/main')
         else next()
     } else {
         // 没有登录时可以进入登录或者注册页
         if (to.name === 'page_login' || to.name === 'page_register')next()
         else next('/login')
     }
})
export default router
