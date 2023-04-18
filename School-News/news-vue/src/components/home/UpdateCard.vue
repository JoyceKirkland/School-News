<template>
<div>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span style="font-weight: bold;font-size: 20px">置顶新闻</span>
      <!--<el-button style="float: right; padding: 3px 0" type="text">修改</el-button>-->
    </div>
    <!--    <div class="block">-->
    <el-timeline>
      <div style="margin-left:1%;margin-right:1%">
        <el-row>
          <el-col v-for="(item) in top_articles" :key="item.id" >
            <div style="margin-top:15px">
              <el-card :body-style="{ padding: '0px'}">
                <!--                  <img src="item.articleCover" class="image">-->
                <!--                  {backgroundImage:url(${articleCover})-->
                <!--                  style="{position: relative;-->
                <!--                  z-index: 1}"-->
                <div class="card-warp">
                  <!--                    -->
                  <img :src="item.articleCover" class="img"
                       :onerror="defaultImg">
                  <div class="txt">
                    <h1>
                      <a :href="'jotter/article?id='+item.id" target="_self" class="title"> {{item.articleTitle}} </a>
                    </h1>
                    <p>
                      <a href="" target="_blank" :title="item.articleTitle" class="detail-txt">
                        {{item.articleAbstract}}
                      </a>
                    </p>
                    <time class="time">
                      <strong>发布时间:</strong>{{item.articleDate}}
                    </time>
                  </div>
                </div>
              </el-card>
            </div>
          </el-col>

        </el-row>
      </div>
    </el-timeline>
    <!--    </div>-->
  </el-card>


  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span style="font-weight: bold;font-size: 20px">最近新闻</span>
      <!--<el-button style="float: right; padding: 3px 0" type="text">修改</el-button>-->
    </div>
    <!--    <div class="block">-->
    <el-timeline>
      <div style="margin-left:1%;margin-right:1%">
        <el-row>
          <el-col v-for="(item) in articles" :key="item.id" >
            <div style="margin-top:15px">
              <el-card :body-style="{ padding: '0px'}">
                <!--                  <img src="item.articleCover" class="image">-->
                <!--                  {backgroundImage:url(${articleCover})-->
                <!--                  style="{position: relative;-->
                <!--                  z-index: 1}"-->
                <div class="card-warp">
                  <!--                    -->
                  <img :src="item.articleCover" class="img"
                       :onerror="defaultImg">
                  <div class="txt">
                    <h1>
                      <a :href="'jotter/article?id='+item.id" target="_self" class="title"> {{item.articleTitle}} </a>
                    </h1>
                    <p>
                      <a href="" target="_blank" :title="item.articleTitle" class="detail-txt">
                        {{item.articleAbstract}}
                      </a>
                    </p>
                    <time class="time">
                      <strong>发布时间:</strong>{{item.articleDate}}
                    </time>
                  </div>
                </div>
              </el-card>
            </div>
          </el-col>

        </el-row>
      </div>
    </el-timeline>

  </el-card>
</div>
</template>

<script>
function sortData(a, b) {
  return new Date(b.articleDate).getTime() - new Date(a.articleDate).getTime()
}

export default {
  name: 'UpdateCard',
  data(){
    return{
      title: '',
      a:'',
      b:'',
      articles: [],
      top_articles:[],
      pageSize: 6,
      total: 0,
      defaultImg: 'this.src="' + require('F:\\张阳的文件夹1\\毕设\\School-News\\news-vue\\src\\components\\home\\defualt.jpg') + '"' //默认图地址
    }
  },
  // computed: {
  //   defaultImg () {
  //     return 'this.src="' + require('defualt.jpg') + '"';
  //   }
  // },

  mounted () {
    // this.title = this.$route.query.name
    this.loadArticles(),
    this.loadTopArticles()
  },
  methods:{
    viewArticle (id) {
      let articleUrl = this.$router.resolve(
        {
          path: '../../jotter/article',
          query: {
            id: id
          }
        }
      )
      window.open(articleUrl.href, '_blank')
    },
    loadArticles () {
      var _this = this
      this.$axios.get('/article' ).then(resp => {
        if (resp && resp.data.code === 200) {
          _this.articles = resp.data.result
          this.articles=this.articles.sort(sortData)
          // _this.total = resp.data.result.totalElements
        }
      })
    },
    loadTopArticles () {
      var _this = this
      this.$axios.get('/Toparticle' ).then(resp => {
        if (resp && resp.data.code === 200) {
          _this.top_articles = resp.data.result
          this.top_articles=this.top_articles.sort(sortData)
          // _this.total = resp.data.result.totalElements
        }
      })
    },
    // handleCurrentChange (page) {
    //   var _this = this
    //   this.$axios.get('/article/' + this.pageSize + '/' + page).then(resp => {
    //     if (resp && resp.data.code === 200) {
    //       _this.articles = resp.data.result.content
    //       _this.total = resp.data.result.totalElements
    //     }
    //   })
    // },
  },

}
</script>

<style scoped >
.card-warp {
  display: flex;
  height: 168px;
  gap: 20px;
}

.img {
  width: 257px;
  height: 100%;
}

.txt {
  height: 100%;
  flex: 1;
}

.detail-txt {
  word-break: break-all;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow-y: hidden;
}

.title {
  width: 500px;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.box-card {
  margin-top: 5px;
  width: 988px;
  overflow-y: auto;
  text-align: left;
}
.box-card .el-card{
  min-width: 100%;
  height: 100%;
  margin-right: 20px;
  border: 0px;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
}
/*.box{*/
/*  height:100%;*/
/*  overflow:auto;*/
/*}*/

</style>
