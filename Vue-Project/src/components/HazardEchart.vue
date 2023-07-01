<template>
  <div id="myChart" :style="{ width: '280px', height: '280px' }"></div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";
import data from '@/assets/data';
export default {
  name: "HazardEchart",

  data() {
    return {
      device_id: "null", //设备id
      interval: 5, //查询设备最近时间的范围，单位秒
      isWork: false,
      data_maxlen: 20, // 表格中显示数组的最大长度
      data: [],
      echart: null,
      curDate: new Date(),
      curVal: 0,
    };
  },

  mounted() {
    this.drawLine();
    this.requestData();

    setInterval(()=>{
      this.requestData()
    }, 5000) //查询数据库的时间间隔
  },

  methods: {
      rebulidChart(data){
        this.data = []
        this.echart.setOption({
          series: [
            {
              data: [],
            },
          ],
        })
        this.device_id = data.number // 根据设备编号找id
        console.log(data)
        this.requestData()
      },
      getThisData(){
          return this.data
      },
      shiftThisData(){
          this.data.shift()
      },
      pushThisData(now, val){
        this.data.push(this.getData(now, val))
      },
      fix(num, length) {
        return ("" + num).length < length
          ? (new Array(length + 1).join("0") + num).slice(-length)
          : "" + num;
      },
      getCurDate(){
        return this.curDate
      },
      getCurVal(){
        return this.curVal
      },
      getDataMaxLen(){
        return this.data_maxlen
      },

      getData(now, val) {
        var fix = this.fix
        //设置时间和数值
        // let now = new Date();
        // value = value + Math.random() * 21 - 10;
        return {
          name: now.toString(),
          value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join("/") +
              " " +
              [
                fix(now.getHours(), 2),
                fix(now.getMinutes(), 2),
                fix(now.getSeconds(), 2),
              ].join(":"),
            val.toFixed(4),
          ],
        };
      },

      requestData() {
        let device_id = this.device_id
        let interval = this.interval
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
            'from(bucket:"temp-bucket")' +
            "|> range(start: -" +
            interval +
            "s)" +
            '|> filter(fn: (r) => r._measurement == "temp" and r.device_id == ' +
            '"' +
            device_id +
            '"' +
            ")",
        })
          .then((rsp) => {
            let val = rsp.data.split(",")[16];
            console.log(val);
            if (val == null) {
              //未找到数据，说明设备工作异常
              this.isWork = false
              this.$emit("working", this.isWork);
              return;
            }
            //设备正常工作
            let tmp  = parseFloat(val)
            if(isNaN(tmp)){
              this.isWork = false;
              this.$emit("working", this.isWork);
              return
            }
            this.curVal = tmp
            this.isWork = true
            this.$emit("working", this.isWork);
          })
          .catch((err) => {
            //未找到数据，说明设备工作异常
            this.isWork = false
            this.$emit("working", this.isWork);
          });
      },

    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("myChart"));
      this.echart = myChart

      //let a = requestData("RoomAdmin", 10);

      var getData = this.getData
      var fix = this.fix
      var getThisData = this.getThisData
      var shiftThisData = this.shiftThisData
      var pushThisData = this.pushThisData
      var getDataMaxLen = this.getDataMaxLen
      //初始化

      let option = {
        title: {
          text: "设备实时数据",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return (
              "时间:" +
              fix(date.getHours(), 2) +
              ":" +
              fix(date.getMinutes(), 2) +
              ":" +
              fix(date.getSeconds(), 2) +
              "  数值:" +
              params.value[1]
            );
          },
          axisPointer: {
            animation: false,
          },
        },
        xAxis: {
          type: "time",
          splitLine: {
            show: false,
          },
        },
        yAxis: {
          type: "value",
          boundaryGap: [0, "100%"],
          splitLine: {
            show: false,
          },
        },
        series: [
          {
            name: "Hazard Sensor Data",
            type: "line",
            showSymbol: false,
            data: data,
          },
        ],
      };
      setInterval(() => {

        let data = getThisData()
        if(data.length > getDataMaxLen()) shiftThisData();
        if(this.isWork) pushThisData(new Date(), this.curVal)

        myChart.setOption({
          series: [
            {
              data: data,
            },
          ],
        });
      }, 2500); //刷新图表的数据间隔

      // 绘制图表
      myChart.setOption(option);
    },
  },
};
</script>
