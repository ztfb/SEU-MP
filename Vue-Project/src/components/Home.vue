<template>
  <div class="container">
    <el-row class="el-row-all">
      <!-- 左 -->
      <el-col :span="6" class="left-main-chart">
        <el-card class="left1" shadow="hover">
          <video
            class="back-video2"
            src=" https://thingsboard.io/images/dashboards/districts/energy-consumed-bg.mp4"
            loop=""
            muted=""
            autoplay=""
          ></video>
          <div class="value2">
            <div class="headValue">设备详情</div>
            <div class="inValue">
              <div class="v1">正常运行设备&nbsp;98.7%</div>
              <div class="v2">异常运行设备&nbsp;0.97%</div>
              <div class="v3">维修运行设备&nbsp;1.08%</div>
              <div>累计巡检次数</div>
              <div>15200次</div>
            </div>
          </div>
        </el-card>
        <el-card class="left2" shadow="hover">
          <div
            ref="chart3"
            id="chart33"
            style="width: 380px; height: 190px; margin-top: 30px"
          ></div>
        </el-card>
        <el-card class="left3" shadow="hover">
          <div class="inValue">实时预警</div>
          <dv-scroll-board
            :config="config5"
            style="width: 300px; height: 300px"
          />
        </el-card>
      </el-col>
      <!-- 中 -->
      <el-col :span="12" class="mid-main-chart">
        <el-row class="mid1">
          <tencent-map-home ref="tencentMap"></tencent-map-home>
        </el-row>
        <el-row class="mid2">
          <el-col :span="12" class="mid21">
            <el-card class="mid21-card" shadow="hover">
              <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
              <div
                ref="chart2"
                id="chart22"
                style="width: 320px; height: 200px"
              ></div>
            </el-card>
          </el-col>
          <el-col :span="12" class="mid22">
            <el-card class="mid22-card" shadow="hover">
              <div
                ref="chart1"
                id="chart21"
                style="width: 270px; height: 190px"
              ></div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <!-- 右 -->
      <el-col :span="6" class="right-main-chart">
        <el-card class="right1" shadow="hover">
          <!-- <div class="inValue">巡检班组通告情况</div> -->
          <div class="inValue">
            <b>
              <h4>今日员工到岗情况</h4>
            </b>
          </div>
          <div class="chart-right1">
            <dv-percent-pond
              :config="config2"
              style="width: 290px; height: 50px"
            />
          </div>
        </el-card>
        <el-card class="right2" shadow="hover">
          <b>
            <h4>巡检信息实时上报</h4>
          </b>
          <div class="bc-chart-pont">
            <dv-scroll-board
              :config="config6"
              style="width: 300px; height: 350px"
            />
          </div>
        </el-card>
        <el-card class="right3" shadow="hover">
          <div class="bc-chart-item">
            <dv-active-ring-chart
              :config="config1"
              style="width: 240px; height: 148px; padding-top: 0px"
            />
            <!-- <Label-Tag :config="labelConfig" /> -->
          </div>
        </el-card>
        <el-card class="right4" shadow="hover">
          <div class="inValue">今日巡检完成情况</div>
          <dv-capsule-chart
            :config="config3"
            style="width: 300px; height: 200px"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TencentMapHome from "@/components/TencentMapHome";
import * as echarts from "echarts";
export default {
  name: "page_home",
  components: {
    TencentMapHome,
  },
  data() {
    return {
      //餅狀圖
      config1: {
        data: [
          {
            name: "正常巡检设备",
            value: 957,
          },
          {
            name: "异常巡检设备",
            value: 43,
          },
          {
            name: "正常危险源设备",
            value: 700,
          },
          {
            name: "异常危险源设备",
            value: 87,
          },
        ],
        color: ["#67e0e3", "#ffdb5c", "#9fe6b8", "#37a2da"],
        radius: "65%",
        activeRadius: "70%",
        digitalFlopStyle: {
          fill: "#000",
        },
        lineWidth: 20,
      },
      //进度池
      config2: {
        value: 98,
        localGradient: true,
      },
      config3: {
        data: [
          {
            name: "巡检1班",
            value: 62,
          },
          {
            name: "巡检2班",
            value: 47,
          },
          {
            name: "巡检3班",
            value: 88,
          },
          {
            name: "巡检4班",
            value: 56,
          },
          {
            name: "巡检5班",
            value: 73,
          },
          {
            name: "巡检6班",
            value: 99,
          },
          {
            name: "巡检7班",
            value: 86,
          },
        ],
        colors: [
          "#fb7293",
          "#32c5e9",
          "#96bfff",
          "#70f3ff",
          "#ff461f",
          "#bce672",
          "#eaff56",
        ],
        unit: "次",
        showValue: true,
      },
      config4: {
        data: [
          {
            name: "东1区",
            value: 50,
          },
          {
            name: "南2区",
            value: 60,
          },
          {
            name: "北3区",
            value: 90,
          },
        ],

        colors: ["#fb7293", "#32c5e9", "#96bfff"],
      },
      config5: {
        header: ["区域", "设备号", "时间"],
        data: [
          ["东一区", "JS123", "08:23"],
          ["西二区", "MK098", "09:23"],
          ["东二区", "SG765", "10:02"],
          ["南一区", "ML987", "23:02"],
          ["北二区", "JH544", "10:29"],
          ["西一区", "GH742", "21:19"],
          ["东二区", "ZD234", "22:53"],
          ["西一区", "OL098", "17:48"],
          ["南二区", "EC234", "16:09"],
          ["北二区", "IH789", "19:52"],
        ],

        // columnWidth: [50],
        align: ["center"],
        index: true,
        hoverPause: true,
        // headerHeight: 60,
        indexHeader: "",
        headerBGC: "#000",
        oddRowBGC: "#ff2d51",
        evenRowBGC: "#ffb61e",
      },
      config6: {
        header: ["工号", "设备号", "时间"],
        data: [
          ["09020223", "KM122", "12:02"],
          ["09010123", "KL152", "13:07"],
          ["09030328", "ML284", "23:03"],
          ["57020123", "SD278", "01:09"],
          ["57020223", "ML987", "04:07"],
          ["57030343", "CD873", "07:38"],
          ["57030412", "AB245", "09:56"],
          ["58020112", "QP098", "11:39"],
          ["58030232", "SD826", "13:52"],
          ["58020352", "WW234", "22:10"],
        ],

        columnWidth: [40, 100, 80, 80],
        align: ["center"],
        index: true,
        hoverPause: true,
        indexHeader: "",
        headerBGC: "#000",
        oddRowBGC: "#0aa344",
        evenRowBGC: "#c9dd22",
      },
      config7: {
        header: ["巡检班组", "负责路线", "总人数", "今日人数"],
        data: [
          ["巡检1班", "巡检路线3", "10", "8"],
          ["巡检2班", "巡检路线9", "12", "10"],
          ["巡检3班", "巡检路线4", "14", "12"],
          ["巡检4班", "巡检路线6", "11", "8"],
          ["巡检5班", "巡检路线5", "12", "10"],
          ["巡检6班", "巡检路线2", "12", "11"],
          ["巡检7班", "巡检路线1", "10", "10"],
          ["巡检8班", "巡检路线7", "11", "9"],
          ["巡检9班", "巡检路线8", "13", "10"],
        ],

        // columnWidth: [50],
        align: ["center"],
        index: true,
        hoverPause: true,
        // headerHeight: 60,
        indexHeader: "",
        headerBGC: "#869ad7",
        oddRowBGC: "#96bfff",
        evenRowBGC: "#32c5e9",
      },
      // index: 1,
      // // mybgi:'../src/assets/'+this.index+'.png'
      // imglist: [
      //     '../src/assets/1.png',
      //     '../src/assets/2.png',
      //     '../src/assets/3.png'
      // ]
      // flag:true
      // urls: [
      //     { url: require('../src/assets/1.png') },
      //     { url: require('../src/assets/2.png') },
      //     { url: require('../src/assets/3.png') },
      //     { url: require('../src/assets/4.png') },
      //     { url: require('../src/assets/5.png') },
      // ],

      // imglist: [
      //     'background-image:url(' + '../src/assets/1.png' + ')',
      //     'background-image:url(' + '../src/assets/2.png' + ')',
      //     'background-image:url(' + '../src/assets/3.png' + ')',
      //     'background-image:url(' + '../src/assets/4.png' + ')'
      // ]
    };
  },
  methods: {
    change: function () {
      //console.log("yes");
      this.$refs.tencentMap.getAllPos();
        // this.$http
        //   .get("/employee/findAllp", {
        //     headers: {
        //       token: localStorage.getItem("token"),
        //     },
        //   })
        //   .then((res) => {
        //     let positions = [];
        //     for (let i = 0; i < res.data.length; i++) {
        //       positions.push({
        //         id: res.data[i].inspectEmployeeId,
        //         longitude: res.data[i].longitude,
        //         latitude: res.data[i].latitude,
        //       });
        //     }
        //     console.log(positions)
        //     this.$refs.tencentMap.changePosition(positions);
        //   });
    },
    // changebgi() {
    //     setInterval(this.flag=!this.flag, 5000)
    // }
  },
  mounted() {
    setInterval(this.change, 5000);
    // this.change()
    let myecharts = echarts.init(this.$refs.chart2);
    myecharts.setOption({
      title: {
        text: "预警次数",
      },
      xAxis: {
        name: "日期",
        data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
      },
      yAxis: {
        // name: '预警次数',
        // data: '预警次数'
      },
      series: [
        {
          data: [1200, 2230, 1900, 2100, 3500, 4200, 3985],
          type: "line",
        },
      ],
    });

    let myecharts1 = echarts.init(this.$refs.chart1);
    myecharts1.setOption({
      tooltip: {
        trigger: "axis",
      },
      legend: {
        data: ["班组1", "班组2", "班组3", "班组4", "班组5"],
      },
      grid: {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true,
      },
      toolbox: {
        feature: {
          saveAsImage: {},
        },
      },
      xAxis: {
        type: "category",
        boundaryGap: false,
        data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          name: "班组1",
          type: "line",
          stack: "Total",
          data: [120, 132, 101, 134, 90, 230, 210],
        },
        {
          name: "班组2",
          type: "line",
          stack: "Total",
          data: [220, 182, 191, 234, 290, 330, 310],
        },
        {
          name: "班组3",
          type: "line",
          stack: "Total",
          data: [150, 232, 201, 154, 190, 330, 410],
        },
        {
          name: "班组4",
          type: "line",
          stack: "Total",
          data: [320, 332, 301, 334, 390, 330, 320],
        },
        {
          name: "班组5",
          type: "line",
          stack: "Total",
          data: [820, 932, 901, 934, 1290, 1330, 1320],
        },
      ],
    });

    let myecharts2 = echarts.init(this.$refs.chart3);
    myecharts2.setOption({
      // title: {
      //     text: '各区设备数量'
      // },
      // series: [
      //     {
      //         type: 'pie',
      //         data: [
      //             { name: '东一区', value: 233 },
      //             { name: '东二区', value: 168 },
      //             { name: '北一区', value: 38 },
      //             { name: '北二区', value: 122 },
      //             { name: '南一区', value: 65 },
      //             { name: '南二区', value: 99 },
      //             { name: '西一区', value: 120 },
      //             { name: '西二区', value: 52 },
      //         ],
      //         insideLabel: {
      //             show: true
      //         }
      //     }
      // ]
      color: [
        "#ffdb5c",
        "#37a2da",
        "#32c5e9",
        "#67e0e3",
        "#9fe6b8",
        "#e062ae",
        "#e690d1",
        "#96bfff",
        "#ea7ccc",
      ],
      title: {
        text: "各区设备数量",
        left: "center",
      },
      tooltip: {
        trigger: "item",
      },
      legend: {
        orient: "vertical",
        left: "left",
      },
      series: [
        {
          name: "Access From",
          type: "pie",
          radius: "50%",
          data: [
            { name: "东一区", value: 233 },
            { name: "东二区", value: 168 },
            { name: "北一区", value: 38 },
            { name: "北二区", value: 122 },
            { name: "南一区", value: 65 },
            { name: "南二区", value: 99 },
            { name: "西一区", value: 120 },
            { name: "西二区", value: 52 },
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
        },
      ],
    });
  },
};
</script>
<style lang="less">
* {
  margin: 0px;
  padding: 0px;
}

.container {
  background-image: url(../assets/img/home-background.jpg);
  background-repeat: repeat;

  .el-row-all {
    background-color: rgba(255, 255, 255, 0);

    .left-main-chart {
      height: 100%;
      background-color: rgba(255, 255, 255, 0);

      .left1 {
        height: 20%;
        background-color: rgba(225, 225, 225, 0.5);

        .el-card__body {
          padding: 0%;

          .back-video2 {
            width: 100%;
            height: 100%;
            // position: fixed;
            z-index: -100;
            object-fit: cover;
            // filter: hue-rotate(40deg);
            filter: brightness(100%);
            filter: hue-rotate(30deg);
          }

          .value2 {
            position: absolute;
            top: 0;
            left: 70px;
            z-index: 2;
            color: #fff;
            // color: #869ad7;
            font-size: 20px;

            .headValue {
              font-size: 28px;
              font-weight: 1000;
              // font-family:'Times New Roman', Times, serif
            }

            .inValue {
              font-size: 20px;
              font-weight: 1000;
            }
          }
        }

        // background-image: url(../src/assets/logo_.png);
        // background-size: cover;
        // background-repeat: no-repeat;
        // background:center;
      }

      .left2 {
        height: 36%;
        background-color: rgba(255, 255, 255, 0.5);

        // background-image: url(../src/assets/bei10.jpg);
        // background-size: cover;
      }

      .left3 {
        padding-top: 17px;
        height: 40%;
        background-color: rgba(255, 255, 255, 0.5);

        .inValue {
          font-size: 25px;
          font-weight: 1000;
          color: black;
        }

        .dv-capsule-chart .label-column {
          color: #869ad7;
          // color: #000;
          font-size: 20px !important;
          font-weight: 1000;
        }
        .el-card__body {
          padding: 0;
        }
      }

      // .left4 {
      //     height: 30%;
      //     background-color: rgba(255, 255, 255, 0.5);

      //     .inValue {
      //         font-size: 25px;
      //         font-weight: 1000;
      //         color: #869ad7;
      //     }

      //     .dv-scroll-board {
      //         .header {
      //             .header-item {
      //                 font-size: 17px;
      //             }
      //         }
      //     }
      // }

      // .left5 {
      //     height: 20%;
      //     background-color: rgba(255, 255, 255, 0.5);

      //     .inValue {
      //         font-size: 25px;
      //         font-weight: 1000;
      //         color: #869ad7;
      //     }

      //     .dv-scroll-board {
      //         .header {
      //             .header-item {
      //                 font-size: 17px;
      //             }
      //         }
      //     }
      // }
    }
  }

  //el-row-all
}

.mid-main-chart {
  height: 100%;
  background-color: rgba(255, 255, 255, 0);

  .mid1 {
    height: 70%;
    background-color: rgba(255, 255, 255, 0);
    padding: 0px;

    .mid11 {
      height: 100%;
      background-color: rgba(255, 255, 255, 0);

      .mid11-card {
        height: 100%;
        background-color: rgba(255, 255, 255, 0.5);
        background-size: cover;
      }
    }
  }

  .mid2 {
    height: 30%;
    background-color: rgba(255, 255, 255, 0);

    .mid21 {
      height: 100%;
      width: 50%;
      background-color: rgba(255, 255, 255, 0);

      .mid21-card {
        height: 100%;
        // background-color: #000;
        background-color: rgba(255, 255, 255, 0);

        .el-card__body {
          background-color: rgba(255, 255, 255, 0.5);
        }
      }
    }

    .mid22 {
      height: 100%;
      width: 50%;
      background-color: rgba(255, 255, 255, 0);

      .mid22-card {
        // padding-left: 20px;
        height: 100%;
        background-color: rgba(255, 255, 255, 0);

        .el-card__body {
          background-color: rgba(255, 255, 255, 0.5);
        }
      }
    }
  }
}

.right-main-chart {
  height: 100%;
  background-color: rgba(255, 255, 255, 0);

  .right1 {
    height: 10%;
    background-color: rgba(255, 255, 255, 0.5);

    .el-card__body {
      padding: 0;
    }
    .inValue {
      font-weight: 1000;
    }

    .dv-scroll-board {
      .header {
        .header-item {
          font-size: 17px;
        }
      }
    }
  }

  .right2 {
    height: 40%;
    background-color: rgba(255, 255, 255, 0.5);
    padding-top: 10px;
    .el-card__body {
      padding: 0;
    }

    // }
  }

  .right3 {
    height: 18%;
    border: 0px;
    background-color: rgba(255, 255, 255, 0.5);

    // .bc-chart-pont{
    //     align-self: start;
    // }
    .el-card__body {
      padding: 0px;

      .bc-chart-item {
        padding-top: 0;

        .active-ring-name {
          color: #000;
        }

        .active-ring-name {
          font-size: 16px !important;
          font-weight: 1000 !important;
        }
      }
    }
  }

  .right4 {
    height: 30%;
    background-color: rgba(255, 255, 255, 0.5);
    .el-card__body {
      padding: 0;
    }
    .label-column {
      color: #000;
      font-size: 20px;
    }

    .capsule-container {
      color: #000;
      .unit-label {
        color: #000;
        font-size: 20px;
      }
    }

    .inValue {
      font-size: 20px;
      font-weight: bolder;
      color: #464646;
    }
  }

  // .right5 {
  //     height: 20%;
  //     background-color: rgba(255, 255, 255, 0.5);

  //     .inValue {
  //         font-size: 25px;
  //         font-weight: 1000;
  //         // color: #869ad7;
  //     }

  //     .dv-capsule-chart .label-column {
  //         color: #000;
  //         font-size: 20px !important;
  //     }
  // }
}

html,
body,
#app,
.el-row-all,
.container {
  padding: 0px;
  margin: 0px;
  height: 100%;
}

video {
}

#chart22 {
  height: 100%;
  width: 100%;
}

.left-main-chart .el-col .el-col-6 {
  padding-right: 0px;
}
</style>
