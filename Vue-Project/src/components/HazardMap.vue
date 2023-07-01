<template>
  <!--为地图创建容器-->
  <div id="container" style="width: 100%; height: 100%"></div>
</template>
<script
  charset="utf-8"
  src="https://map.qq.com/api/gljs?v=1.exp&key=RTDBZ-L4NLF-PBVJV-NQUIX-UCDMZ-Z6BH7"
></script>
<script>
export default {
  name: "HazardMap",
  data() {
    return {
      hazardURL: require('@/assets/img/hazard.svg'),
      // 地图对象
      map: null,
      // 点标记管理器
      markerLayer: null,
      // 信息窗体
      infoWindow: null,
      // 点击后获取的经纬度
      lat: 0,
      lng: 0,
      //父组件在点击地图时设置的实例属性
      addProp: {},
      //父组件是否允许添加一个点
      addPermit: true,
      cnt: 0,
      kdict: {
        type: "设备类型",
        number: "设备编号",
        area: "设备区域",
        lng: "经度",
        lat: "纬度",
        isWork: "工作情况",
        abnormalNum: "异常次数",
      },
    };
  },
  mounted() {
    this.initMap();
  },
  methods: {
    setCenter(lat, lng) {
      this.map.setCenter(new TMap.LatLng(lat, lng));
    },
    addPoint: function (obj, db_id, lat, lng) {
      // if(this.obj.lat != null) this.lat = this.obj.lat;
      // if(this.obj.lng != null) this.lng = this.obj.lng;
      //改为触发一个新增事件
      this.cnt = this.cnt + 1;
      this.markerLayer.add([
        {
          id: db_id, //id为零的点为临时
          styleId: "myStyle2", //指定样式id
          position: new TMap.LatLng(lat, lng), //点标记坐标位置
          properties: JSON.parse(JSON.stringify(obj)),
        },
      ]);

      // this.markerLayer.add([
      //   {
      //     id: this.markerLayer.geometries.length,
      //     styleId: "myStyle2",
      //     position: new TMap.LatLng(lat, lng),
      //     properties: obj,
      //   }
      // ])
    },
    removePoint: function (id) {
      this.markerLayer.remove([id]);
    },
    getGeoIdFromDb(number) {
      this.markerLayer.geometries.some((item, idx) => {
        if (item.properties.number == number) {
          return item.id;
        }
      });
    },
    updatePointFromDb(number, abnormalNum, isWork) {
      this.markerLayer.geometries.some((item, idx) => {
        if (item.properties.number == number) {
          this.markerLayer.geometries[idx].properties.abnormalNum = abnormalNum;
          this.markerLayer.geometries[idx].properties.isWork = isWork;
          return;
        }
      });
    },
    updatePoint(id, obj) {
      console.log(this.markerLayer.geometries);
      console.log(obj);

      this.markerLayer.geometries.some((item, idx) => {
        if (item.id == id) {
          this.markerLayer.geometries[idx].properties = JSON.parse(
            JSON.stringify(obj)
          );
          return;
        }
      });
    },
    changeMode: function (isAdding) {
      if (isAdding) {
        this.map.on("click", this.clickHandler);
      } else {
        //console.log(this.markerLayer.geometries.length)

        this.map.off("click", this.clickHandler);
      }
    },
    test() {
      console.log("asdasd");
    },
    setFound(ids) {
      console.log(ids);
      this.markerLayer.geometries.forEach((item, idx) => {
        if (ids[item.id]) {
          item.styleId = "myStyle3";
        } else {
          item.styleId = "myStyle2";
        }
        this.removePoint(item.id);
        this.markerLayer.add([
          {
            id: item.id, //id为零的点为临时
            styleId: item.styleId, //指定样式id
            position: item.position, //点标记坐标位置
            properties: item.properties,
          },
        ]);
      });
    },
    //定义事件处理方法
    // pclickHandler: function (evt) {
    //   console.log(evt.geometry.properties.type);
    // },
    clickHandler: function (evt) {
      if (!this.addPermit) {
        return;
      }
      console.log("tencentmap中获取点击");
      // 删除id为0的点
      //this.markerLayer.remove(["-1"]);
      let lat = evt.latLng.getLat().toFixed(6);
      let lng = evt.latLng.getLng().toFixed(6);
      this.lat = lat;
      this.lng = lng;
      console.log(evt.latLng);
      //向父组件发出getPos事件并发送经纬度坐标
      this.$emit("getPos", evt.latLng);
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
          myStyle1: new TMap.MarkerStyle({
            width: 25, // 点标记样式宽度（像素）
            height: 35, // 点标记样式高度（像素）
            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
            anchor: { x: 16, y: 32 },
          }),
          myStyle2: new TMap.MarkerStyle({
            width: 30, // 点标记样式宽度（像素）
            height: 30, // 点标记样式高度（像素）
            src: this.hazardURL,
            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
            anchor: { x: 15, y: 15 },
          }),
          myStyle3: new TMap.MarkerStyle({
            width: 15,
            height: 22,
            anchor: { x: 8, y: 16 },
            src: "https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/markerDefault.png",
          }),
        },
        //点标记数据数组
        geometries: [],
      });
      // 初始化infoWindow
      let infoWindow = new TMap.InfoWindow({
        map: map,
        position: new TMap.LatLng(39.984104, 116.307503),
        offset: { x: 0, y: -32 }, //设置信息窗相对position偏移像素，为了使其显示在Marker的上方
      });
      infoWindow.close(); //初始关闭信息窗关闭
      //Map实例创建后，通过on方法绑定点击事件
      //map.on("click", this.clickHandler);
      // 监听标注点击事件
      markerLayer.on("click", (evt) => {
        //设置infoWindow
        infoWindow.open(); //打开信息窗
        infoWindow.setPosition(evt.geometry.position); //设置信息窗位置
        if (evt.geometry.properties != null) {
          let s = "";
          for (let k in evt.geometry.properties) {
            if (k == "db_id" || k == "isWork") continue;
            let wd = evt.geometry.properties[k];
            console.log(k, wd);
            if (k == "lat" || k == "lng") wd = wd.toFixed(6) + "°";
            s += "<div>" + this.kdict[k] + ":" + wd + "</div>";
          }
          infoWindow.setContent(s); //设置信息窗内容
        }
        this.$emit("getDevice", evt.geometry.properties);
        this.$emit("getGeoId", evt.geometry.id);
      });
      this.map = map;
      this.markerLayer = markerLayer;
      this.infoWindow = infoWindow;
    },
  },
};
</script>

<style scoped></style>
