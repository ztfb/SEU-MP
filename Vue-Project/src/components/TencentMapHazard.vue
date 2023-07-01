<template>
  <!--为地图创建容器-->
  <div id="container" style="width: 100%; height: 100%"></div>
</template>
<script charset="utf-8" src="https://map.qq.com/api/gljs?v=1.exp&key=RTDBZ-L4NLF-PBVJV-NQUIX-UCDMZ-Z6BH7"></script>
<script>

export default {
  name: "TencentMapHazard",
  data() {
    return {
      hazardURL: require('@/assets/img/hazard.svg'),
      // 地图对象
      map: null,
      // 点标记管理器
      markerLayer: null,
      // 信息窗体
      infoWindow: null,
      // 所有的信息窗体
      infoWindows: []
    };
  },
  mounted() {
    this.initMap();
  },
  methods: {
    // 开启点击展示事件
    open: function () {
      this.map.on("click", this.clickHandler);
    },
    // 关闭点击展示事件
    close: function () {
      this.map.off("click", this.clickHandler);
    },
    // 根据查询结果展示所有符合条件的点
    show: function (fps){
      // 获取原来的点数据
      let points = this.markerLayer.getGeometries()
      //先把所有信息窗体关闭
      for(let i=0; i<points.length;i++)
        this.infoWindows[i].close();
      for(let i=0;i<points.length; i++){
        for(let j=0;j<fps.length;j++){
          if(points[i].id === fps[j].id){
            // 展示信息窗体
            this.infoWindows[i].open();
          }
        }
      }
    },
    //定义事件处理方法
    pclickHandler: function (evt) {
      // 点击一个已有的点
      let point= {
        id: evt.geometry.id,
        latitude: evt.geometry.position.lat,
        longitude: evt.geometry.position.lng,
        type: evt.geometry.properties.type,
        number: evt.geometry.properties.number,
        area: evt.geometry.properties.area,
        AbnormalNum: evt.geometry.properties.abnormalNumber,
        isWork: (evt.geometry.properties.state === '0'?true:false),
      }
      this.$emit('change', point);// 巡检点管理窗口
    },
    clickHandler: function (evt) {
      // 在屏幕上显示效果
      this.markerLayer.remove(["0"]);// 删除id为0的点
      let lat = evt.latLng.getLat().toFixed(6);
      let lng = evt.latLng.getLng().toFixed(6);
      this.markerLayer.add([
        {
          id: "0", //id为零的点为临时电
          styleId: "myStyle2", //指定样式id
          position: new TMap.LatLng(lat, lng), //点标记坐标位置
        },
      ]);
      // 返回经纬度信息
      let position ={
        latitude: lat,
        longitude: lng
      }
      this.$emit('changePosition', position);
    },
    initMap: function () {
      // 定义地图中心点坐标
      let center = new TMap.LatLng(31.887627, 118.819201);
      // 定义map变量，调用 TMap.Map() 构造函数创建地图
      let map = new TMap.Map(document.getElementById("container"), {
        center: center, // 设置地图中心点坐标
        zoom: 18, // 设置地图缩放级别
        viewMode: "2D", // 设置2D地图
        mapStyleId: "style3", // 设置地图样式
        baseMap: {
          type: "vector",
        },
      });
      // 创建并初始化MultiMarker
      let markerLayer = new TMap.MultiMarker({
        map: map, //指定地图容器
        //样式定义
        styles: {
          //创建一个styleId为"myStyle"的样式（styles的子属性名即为styleId）
          // 普通样式
          myStyle1: new TMap.MarkerStyle({
            width: 30, // 点标记样式宽度（像素）
            height: 30, // 点标记样式高度（像素）
            src: this.hazardURL,
            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
            anchor: { x: 15, y: 15 },
          }),
          myStyle2: new TMap.MarkerStyle({
            width: 15, // 点标记样式宽度（像素）
            height: 15, // 点标记样式高度（像素）
            src: this.hazardURL,
            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
            anchor: { x: 7, y: 7 },
          })
        },
        //点标记数据数组
        geometries: [
        ],
      });
      // 根据后端传过的数据初始化markerLayer
      let infoWindows = []
      // 获得所有点信息
      this.$http.get('/hazard/findAll',{
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
          //遍历初始化
          for(let i = 0; i< res.data.length; i++){
            markerLayer.add({
              id: res.data[i].id,
              styleId: "myStyle1",
              position: new TMap.LatLng(res.data[i].latitude, res.data[i].longitude),
              properties: {
                type: res.data[i].type,
                number: res.data[i].number,
                area: res.data[i].area,
                abnormalNumber: res.data[i].abnormalNumber,
                state: res.data[i].state,
                inspectRouteId: res.data[i].inspectRouteId
              }
            })
            let infoWindow=new TMap.InfoWindow({
              content:"符合条件的点", //信息窗口内容
              position: new TMap.LatLng(res.data[i].latitude, res.data[i].longitude),//显示信息窗口的坐标
              map:map
            });
            infoWindow.close()
            infoWindows.push(infoWindow)
          }
          this.infoWindows = infoWindows
        }
      })
      // 初始化infoWindow
      let infoWindow = new TMap.InfoWindow({
        map: map,
        enableCustom: true,
        position: new TMap.LatLng(0, 0),
        offset: { x: 0, y: -32 }, //设置信息窗相对position偏移像素，为了使其显示在Marker的上方
      });
      infoWindow.close(); //初始关闭信息窗关闭
      // 监听marker点击事件
      markerLayer.on("click", this.pclickHandler);
      // 监听标注点击事件
      markerLayer.on("click", function (evt) {
        //设置infoWindow
        infoWindow.open(); //打开信息窗
        infoWindow.setPosition(evt.geometry.position); //设置信息窗位置
        let content = '<div><span>类型：'+evt.geometry.properties.type+'</span></div>'
        +'<div><span>编号：'+evt.geometry.properties.number+'</span></div>'
          +'<div><span>区域：'+evt.geometry.properties.area+'</span></div>'
            +'<div><span>异常次数：'+evt.geometry.properties.abnormalNumber+'</span></div>'
              +'<div><span>工作状态：'+(evt.geometry.properties.state?'异常':'正常')+'</span></div>'
        infoWindow.setContent(content); //设置信息窗内容
      });
      this.map = map;
      this.markerLayer = markerLayer;
      this.infoWindow = infoWindow;
    },
  },
};
</script>

<style scoped></style>
