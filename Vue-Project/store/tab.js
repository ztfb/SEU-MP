//在该文件定义是否展开的state属性
export default {
    /*使用vuex进行事件传递需要的状态，定义在此*/
    state: {
        isCollapse: false//默认不展开
    },
    /*改变上述state的方法，定义在mutation中*/
    /*mutation里面的方法接受两个参数：state和传入参数 */
    mutations: {
        collapseMenu(state){
            state.isCollapse = !state.isCollapse;
        }
    }
}