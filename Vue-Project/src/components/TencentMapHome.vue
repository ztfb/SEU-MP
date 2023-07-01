<template>
  <!--为地图创建容器-->
  <div id="container" style="width: 100%; height: 100%"></div>
</template>
<script charset="utf-8" src="https://map.qq.com/api/gljs?v=1.exp&key=RTDBZ-L4NLF-PBVJV-NQUIX-UCDMZ-Z6BH7"></script>
<script>
import axios from "axios";
export default {
  name: "TencentMap",
  data() {
    return {
      pointURL: require("@/assets/img/point.svg"),
      employeeURL: require("@/assets/img/employee.svg"),
      // 地图对象
      map: null,
      // 巡检点标记管理器
      markerLayerInspect: null,
      // 人员点管理器
      markerLayerEmployee: null,
      // 巡检点折线管理器
      polylineLayerInspect: null,
      // 巡检人员折线管理器
      polylineLayerEmployee: null,
      // 巡检点信息窗体
      infoWindowInspect: null,
      // 人员信息窗体
      infoWindowEmployee: null,
      // 巡检路线的信息窗
      pathInfoWindow: null,
      // 人员的折线对象，存放的是路径的经纬度对
      pathsEmployee: [],
      // index和id的映射关系
      mapper: [],
      //一次讀取到的人員位置信息
      positions: [],
    };
  },
  mounted() {
    this.initMap();
    //this.getAllPos();
  },
  methods: {
    getAllPos: function () {
      //员工id
      let interval = 12; //查询几秒前的数据

      axios({
        method: "post",
        url: "http://101.132.107.178:8086/api/v2/query?org=SEU",
        headers: {
          Authorization:
            "Token FMLGu8oMjMZgWF_nczsp2NMtGiQeVL4VegkomG-KQ4qtMILD0pqzbG4ay6_wBn75hrfk4nn47insmGRhAU5CSQ==",
          "Content-Type": "application/vnd.flux",
          Accept: "application/csv",
        },
        data:
          'from(bucket:"pos-bucket")' +
          "|> range(start: -" +
          interval +
          "s)" +
          '|> filter(fn: (r) => r._measurement == "pos")' +
          '|> sort(columns: ["_time"], desc: false)',
      })
        .then((rsp) => {
          console.log(rsp)
          let arr = rsp.data.replace(/[\r\n]/g,"").split(",");
          let len = arr.length;
          if(len < 10 ) return;
          let rowLen = (arr[10] == "place" ? 10 : 9)
          console.log("rl", rowLen, arr[9])
          let offset = rowLen; //開始在第一行
          let dict = {};
          while (offset + 9 < len) {
            let id = arr[9 + offset].replace(/[\r\n]/g,"");
            let val = arr[6 + offset].replace(/[\r\n]/g,"");
            let field = arr[7 + offset].replace(/[\r\n]/g,"");
            console.log(id,val,field)
            if (dict[id] == null) dict[id] = {};
            dict[id][field] = val;
            offset += rowLen;
          }
          console.log(dict)
          let posArray = this.positions;
          for (let k in dict) {
            for (let i = 0; i < posArray.length; ++i) {
              if (posArray[i].id == k) {
                posArray[i] = {
                  id: parseInt(k),
                  longitude: parseFloat(dict[k]["lng"]),
                  latitude: parseFloat(dict[k]["lat"]),
                }
                //console.log("命中",k)
                break;
              }
            }
          }
          console.log(posArray)
          this.changePosition(posArray);
        })
        .catch((err) => {
          //未找到数据，说明设备工作异常
          return;
        });
    },
    findIndexById: function (id) {
      for (let i = 0; i < this.mapper.length; i++) {
        if (id === this.mapper[i][1]) return this.mapper[i][0];
      }
    },
    //奇怪的問題，positions的長度必須和this.polylineLayerEmployee長度一樣，才能顯示路綫軌跡，否則人員會移動，但不顯示軌跡
    changePosition: function (positions) {
      // positions是所有巡检人员的位置信息，根据该信息，更新巡检人员的位置，并绘制出路径
      for (let i = 0; i < positions.length; i++) {
        let point = this.markerLayerEmployee.getGeometryById(positions[i].id);
        this.pathsEmployee[this.findIndexById(positions[i].id)].push([
          positions[i].latitude,
          positions[i].longitude,
        ]);
        // 更新点标记
        this.markerLayerEmployee.updateGeometries({
          id: positions[i].id,
          styleId: "myStyle1",
          position: new TMap.LatLng(
            positions[i].latitude,
            positions[i].longitude
          ),
          properties: {
            name: point.properties.name,
            jobNumber: point.properties.jobNumber,
            job: point.properties.job,
            tel: point.properties.tel,
          },
        });
        // 更新路线
        let polyArray = this.pathsEmployee[
          this.findIndexById(positions[i].id)
        ].map((p) => {
          return new TMap.LatLng(p[0], p[1]);
        });

        this.polylineLayerEmployee.updateGeometries({
          id: positions[i].id,
          styleId: "style_blue",
          paths: polyArray,
        });
      }
      // 更新巡检人员的轨迹
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
      let markerLayerInspect = new TMap.MultiMarker({
        map: map, //指定地图容器
        //样式定义
        styles: {
          //创建一个styleId为"myStyle"的样式（styles的子属性名即为styleId）
          // 普通样式
          myStyle1: new TMap.MarkerStyle({
            width: 30, // 点标记样式宽度（像素）
            height: 30, // 点标记样式高度（像素）
            src: this.pointURL,
            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
            anchor: { x: 15, y: 15 },
          }),
        },
        //点标记数据数组
        geometries: [],
      });
      // 创建并初始化MultiMarker
      let markerLayerEmployee = new TMap.MultiMarker({
        map: map, //指定地图容器
        //样式定义
        styles: {
          //创建一个styleId为"myStyle"的样式（styles的子属性名即为styleId）
          // 普通样式
          myStyle1: new TMap.MarkerStyle({
            width: 40, // 点标记样式宽度（像素）
            height: 40, // 点标记样式高度（像素）
            src: this.employeeURL,
            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
            anchor: { x: 20, y: 20 },
          }),
        },
        //点标记数据数组
        geometries: [],
      });
      // 创建并初始化ploylineLayer
      let polylineLayerInspect = new TMap.MultiPolyline({
        id: "inspect", //图层唯一标识
        map: map, //设置折线图层显示到哪个地图实例中
        //折线样式定义
        styles: {
          style_1: new TMap.PolylineStyle({
            color: "#ee3f4d", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_2: new TMap.PolylineStyle({
            color: "#3777FF", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_3: new TMap.PolylineStyle({
            color: "#8b2671", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_4: new TMap.PolylineStyle({
            color: "#45b787", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_5: new TMap.PolylineStyle({
            color: "#d2d97a", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_6: new TMap.PolylineStyle({
            color: "#e7a23f", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_7: new TMap.PolylineStyle({
            color: "#624941", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_8: new TMap.PolylineStyle({
            color: "#f4c7ba", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
          style_9: new TMap.PolylineStyle({
            color: "#b78d12", //线填充色
            width: 4, //折线宽度
            lineCap: "round", //线端头方式
          }),
        },
        //折线数据定义
        geometries: [],
      });
      // 创建并初始化ploylineLayer
      let polylineLayerEmployee = new TMap.MultiPolyline({
        id: "employee", //图层唯一标识
        map: map, //设置折线图层显示到哪个地图实例中
        //折线样式定义
        styles: {
          style_blue: new TMap.PolylineStyle({
            color: "#3777FF", //线填充色
            width: 4, //折线宽度
            lineCap: "butt", //线端头方式
            dashArray: [5, 5],
          }),
        },
        //折线数据定义
        geometries: [],
      });
      // 根据后端传过的数据初始化markerLayer
      // 获得所有点信息
      this.$http
        .get("/point/findAll", {
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
            //遍历初始化
            for (let i = 0; i < res.data.length; i++) {
              markerLayerInspect.add({
                id: res.data[i].id,
                styleId: "myStyle1",
                position: new TMap.LatLng(
                  res.data[i].latitude,
                  res.data[i].longitude
                ),
                properties: {
                  type: res.data[i].type,
                  number: res.data[i].number,
                  area: res.data[i].area,
                  abnormalNumber: res.data[i].abnormalNumber,
                  state: res.data[i].state,
                  inspectRouteId: res.data[i].inspectRouteId,
                },
              });
            } // end for
          } // end else
        });
      // 获得所有人员信息
      this.$http
        .get("/employee/findAll", {
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
            // 获取所有人员位置
            this.$http
              .get("/employee/findAllp", {
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
                  //一個注釋標簽POSITIONSTEST
                  for (let i = 0; i < resp.data.length; i++) {
                    this.positions.push({
                      id: resp.data[i].inspectEmployeeId,
                      longitude: resp.data[i].longitude,
                      latitude: resp.data[i].latitude,
                    });
                  }
                  console.log(this.positions);

                  //遍历初始化
                  for (let i = 0; i < res.data.length; i++) {
                    for (let j = 0; j < resp.data.length; j++) {
                      if (res.data[i].id === resp.data[j].inspectEmployeeId) {
                        // 添加点
                        markerLayerEmployee.add({
                          id: res.data[i].id,
                          styleId: "myStyle1",
                          position: new TMap.LatLng(
                            resp.data[j].latitude,
                            resp.data[j].longitude
                          ),
                          properties: {
                            name: res.data[i].name,
                            jobNumber: res.data[i].jobNumber,
                            job: res.data[i].job,
                            tel: res.data[i].tel,
                          },
                        });
                        // 初始化路线
                        // this.pathsEmployee.push([
                        //   [resp.data[j].latitude, resp.data[j].longitude],
                        // ]);
                        //开始的时候不加入点，只加入空列表占位
                        this.pathsEmployee.push([]);

                        this.mapper.push([i, res.data[i].id]); // 建立映射关系
                        let polyArray = [
                          [resp.data[j].latitude, resp.data[j].longitude],
                        ];
                        
                        polyArray = polyArray.map((p) => {
                          return new TMap.LatLng(p[0], p[1]);
                        });
                        polylineLayerEmployee.add({
                          id: res.data[i].id,
                          styleId: "style_blue",
                          paths: polyArray,
                        });
                      }
                    } //end for
                  } // end for
                }
              });
          } // end else
        });
      // 根据后端穿过来的数据初始化paths
      this.$http
        .get("/route/findAll", {
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
            for (let i = 0; i < res.data.length; i++) {
              let polyArray = []; // 创建一个用来存放该路线上所有点的数组
              for (let j = 0; j < markerLayerInspect.geometries.length; j++) {
                if (
                  markerLayerInspect.geometries[j].properties.inspectRouteId ===
                  res.data[i].id
                ) {
                  polyArray.push([
                    markerLayerInspect.geometries[j].position.lat,
                    markerLayerInspect.geometries[j].position.lng,
                  ]);
                }
              }
              //转为LatLng数组
              polyArray = polyArray.map((p) => {
                return new TMap.LatLng(p[0], p[1]);
              });
              // 创建该巡检路线
              polylineLayerInspect.add({
                // 这里使用巡检路线的名字作为唯一标识
                id: res.data[i].routeName,
                styleId: "style_" + (i + 1).toString(),
                paths: polyArray,
              });
            }
          }
        });
      // 初始化infoWindow
      let infoWindowInspect = new TMap.InfoWindow({
        map: map,
        enableCustom: true,
        position: new TMap.LatLng(0, 0),
        offset: { x: 0, y: -32 }, //设置信息窗相对position偏移像素，为了使其显示在Marker的上方
      });
      // 初始化infoWindow
      let infoWindowEmployee = new TMap.InfoWindow({
        map: map,
        enableCustom: true,
        position: new TMap.LatLng(0, 0),
        offset: { x: 0, y: -32 }, //设置信息窗相对position偏移像素，为了使其显示在Marker的上方
      });
      // 初始化pathInfoWindow
      let pathInfoWindow = new TMap.InfoWindow({
        map: map,
        enableCustom: true,
        position: new TMap.LatLng(0, 0),
        offset: { x: 0, y: -32 }, //设置信息窗相对position偏移像素，为了使其显示在Marker的上方
      });
      infoWindowInspect.close(); //初始关闭信息窗关闭
      infoWindowEmployee.close();
      pathInfoWindow.close(); //初始关闭信息窗关闭
      // 监听标注点击事件
      markerLayerInspect.on("click", function (evt) {
        //设置infoWindow
        infoWindowInspect.open(); //打开信息窗
        infoWindowInspect.setPosition(evt.geometry.position); //设置信息窗位置
        let content =
          "<div><span>类型：" +
          evt.geometry.properties.type +
          "</span></div>" +
          "<div><span>编号：" +
          evt.geometry.properties.number +
          "</span></div>" +
          "<div><span>区域：" +
          evt.geometry.properties.area +
          "</span></div>" +
          "<div><span>异常次数：" +
          evt.geometry.properties.abnormalNumber +
          "</span></div>" +
          "<div><span>工作状态：" +
          (evt.geometry.properties.state ? "异常" : "正常") +
          "</span></div>";
        infoWindowInspect.setContent(content); //设置信息窗内容
      });
      // 监听标注点击事件
      markerLayerEmployee.on("click", function (evt) {
        //设置infoWindow
        infoWindowEmployee.open(); //打开信息窗
        infoWindowEmployee.setPosition(evt.geometry.position); //设置信息窗位置
        let content =
          "<div><span>姓名：" +
          evt.geometry.properties.name +
          "</span></div>" +
          "<div><span>工号：" +
          evt.geometry.properties.jobNumber +
          "</span></div>" +
          "<div><span>职务：" +
          evt.geometry.properties.job +
          "</span></div>" +
          "<div><span>电话：" +
          evt.geometry.properties.tel +
          "</span></div>";
        infoWindowEmployee.setContent(content); //设置信息窗内容
      });
      polylineLayerInspect.on("click", function (res) {
        var res = res && res.geometry;
        if (res) {
          pathInfoWindow.open();
          pathInfoWindow.setPosition(res.paths[0]);
          pathInfoWindow.setContent(res.id);
        }
      });
      this.map = map;
      this.markerLayerInspect = markerLayerInspect;
      this.markerLayerEmployee = markerLayerEmployee;
      this.polylineLayerInspect = polylineLayerInspect;
      this.polylineLayerEmployee = polylineLayerEmployee;
      this.infoWindowInspect = infoWindowInspect;
      this.infoWindowEmployee = infoWindowEmployee;
      this.pathInfoWindow = pathInfoWindow;
    },
  },
};
</script>

<style scoped></style>
