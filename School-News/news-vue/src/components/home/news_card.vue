<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span style="font-weight: bold;font-size: 20px">{{ title || '综合新闻' }}</span>
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

<!--                  <div>-->
<!--                    <span>{{item.articleTitle}}</span><br>-->
<!--                    <span>{{item.articleAbstract}}</span><br>-->
<!--                    <div class="bottom clearfix">-->
<!--                      <time class="time"><strong>发布时间:</strong>{{item.articleDate}}</time>-->
<!--                      <el-button type="text" class="button" @click="add(item)">查看</el-button>-->
<!--                    </div>-->

<!--                  </div>-->
                </el-card>
              </div>
            </el-col>

          </el-row>
          <div style="margin: 20px 0 50px 0">
            <el-pagination
              background
              style="float:right;"
              layout="total, prev, pager, next, jumper"
              @current-change="imtScreen"
              :page-size="pageSize"
              :total="total">
            </el-pagination>
          </div>

        </div>
      </el-timeline>
<!--    </div>-->
  </el-card>
</template>

<script>
function sortData(a, b) {
  return new Date(b.articleDate).getTime() - new Date(a.articleDate).getTime()
}
  export default {
    name: 'NewsCard',
    data(){
      return{
        title: '',
        articles: [],
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
      this.title = this.$route.query.name
      // this.loadArticles()
      // this.page_one()
    },
    created(){
      this.page_one();
    },
    methods:{
      page_one(){

        const _this = this;
        const cid = this.$route.query.id
        const page_one=1
        const stime = '';
        const etime = '';
        console.log("????????????????????????????????")
        this.$axios.get(`/article/${cid}/${this.pageSize}/${page_one}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            this.articles = resp.data.result.articlesList
            _this.total = resp.data.result.TotalNum
            this.articles=this.articles.sort(sortData)
            console.log("this.articles",this.articles)
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
          }
        })
      },
      // async imtScreen(){
      imtScreen(currentPage){
        //判断是否输入时间区间
        const stime = '';
        const etime = '';
        const cid = this.$route.query.id
        // const pageSize = 10
        // const page = 1
        console.log("currentPage",currentPage)
        this.$axios.get(`/article/${cid}/${this.pageSize}/${currentPage}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            this.articles = resp.data.result.articlesList
            this.articles=this.articles.sort(sortData)
            // _this.total = resp.data.result.totalElements
          }
        })
      },
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

        const cid = this.$route.query.id
        console.log('cid = ', cid)

        this.$axios.get('/categories/' + cid + '/article').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result
            this.articles=this.articles.sort(sortData)

            // _this.total = resp.data.result.totalElements
          }
        })
      },
      handleCurrentChange (page) {
        var _this = this
        this.$axios.get('/article/' + this.pageSize + '/' + page).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result.content
            _this.total = resp.data.result.totalElements
          }
        })
      },
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
