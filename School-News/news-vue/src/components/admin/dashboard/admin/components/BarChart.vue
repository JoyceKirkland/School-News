<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
import resize from './mixins/resize'
require('echarts/theme/macarons') // echarts theme

const animationDuration = 6000

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data () {
    return {
      MapList:[],
      views:[],
      chart: null,
      max_category:[],
      categorys: [],
      categorynames:[]
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.initChart()
    })
    // this.loadCategorys()
  },
  beforeDestroy () {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  created() {
    this.loadCategorys();
    this.getBarList();
  },
  methods: {
    loadCategorys () {
      var _this = this
      this.$axios.get('/categorys').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.categorys = resp.data.result
          this.categorynames=resp.data.result.name
        }
      })
    },
    getMaxCategory(){
      this.$axios.get('/getallCategorys').then(resp => {
        if (resp && resp.data.code === 200) {
          this.max_category = resp.data.result.content
          // _this.total = resp.data.result.totalElements
          // this.allTableDate  = resp.data.result.content
          // this.filterTableData=resp.data.result.content
        }
      })
    },
    getBarList() {
      this.loading = true;
      this.$axios.get('/admin/getallCategorys').then(resp => {
        let list = resp.data.result;
        let names = list.map(item => item.name)
        let values = list.map(item => item.allviews)

        //动态插入数据时，数据在这里设置更新
        this.chart.setOption({
          xAxis: {
            //从getallCategorys中查出来的栏目名称
            data: names
          },
          series: [{
            name: '浏览量',
            data: values,
          }]
        });
      });
    },
    initChart () {
      this.chart = echarts.init(this.$el, 'macarons')
      // console.log("this.categorynames",this.categorynames)

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: [],
          axisTick: {
            alignWithLabel: true
          },
          axisLabel: {
            interval: 0,
            // rotate: 45
          }
        },
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: 'pageA',
          type: 'bar',
          // stack: 'vistors',
          barWidth: '40%',
          data: [],
          animationDuration
        }]
      })
    }
  }
}
</script>
