<template>
  <el-card class="card-carousel">
  <el-carousel  class="push"
                :interval="5000"
                arrow="always" height="800px">

    <el-carousel-item v-for="item in articleList" :key="item.id">
      <a :href="'jotter/article?id='+item.id" target="_self">
        <img :src="item.articleCover" class="img"
             :onerror="defaultImg" alt="" >

      </a>
      <h3>{{item.articleTitle}}</h3>
    </el-carousel-item>
  </el-carousel>
  </el-card>
</template>

<style>
  .el-carousel__item h3 {
    color: black;
    font-size: 15px;
    /*line-height: 300px;*/
    bottom: 45px;
    /*43*/
    float: left;
    position: relative;
  }

  .card-carousel {
    /*margin: 0 auto;*/
    margin-left: 15px;
    /*20*/
    width: 948px;
    /*680*/
    height: 486px;
  }

  /*.push {*/
  /*  !*margin-left: -15px;*!*/
  /*}*/

  .carousel_img {
    /*height: 100%;*/
    height: inherit;
    width: 100%;
    text-align: center;
  }
</style>

<script>
function sortData(a, b) {
  return new Date(b.articleDate).getTime() - new Date(a.articleDate).getTime()
}
  export default {
    name: 'Carousel',
    data: function () {
      return {
        pageSize: 10,
        articles:[],
        bannerHeight:" ",
        defaultImg: 'this.src="' + require('F:\\张阳的文件夹1\\毕设\\School-News\\news-vue\\src\\components\\home\\公告.png') + '"' ,
        // items: [
        //   {
        //   id: 1,
        //   title: 'How2J.cn - Java 全栈学习网站',
        //   img: '../../../static/img/carousel/how2j.png',
        //   link: 'http://how2j.cn?p=50613'},
        // {
        //   id: 2,
        //   title: 'Vue.js - 渐进式 JavaScript 框架',
        //   img: '../../../static/img/carousel/vue.png',
        //   link: 'https://cn.vuejs.org/'
        // },
        // {
        //   id: 3,
        //   title: 'element-ui - 网站快速成型工具',
        //   img: '../../../static/img/carousel/element.png',
        //   link: 'http://element-cn.eleme.io/#/zh-CN'
        // }]
      }
    },
    computed: {
      articleList(){
        return this.articles.length > 6 ? this.articles.slice(0, 6) : this.articles
      }
    },
    methods: {
      imgload() {
        this.$nextTick(() => {
          this.bannerHeight = this.$refs.bannerHeight[0].height
        })
      },
      loadArticles () {
        var _this = this
        this.$axios.get('/article').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result
            this.articles=this.articles.sort(sortData)
            // console.log("data??:",this.articles)
            // _this.total = resp.data.result.totalElements
          }
        })
      },
    },
    mounted () {
      this.imgload()
      this.loadArticles()
      window.addEventListener(
        'resize',
        () => {
          this.bannerHeight = this.$refs.bannerHeight[0].height
          this.imgload()
        },
        false
      )
    }

  }
</script>
