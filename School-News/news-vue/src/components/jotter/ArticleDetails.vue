<template>
  <div class="articles-area">
    <el-card style="text-align: left;width: 990px;margin: 35px auto 0 auto">
      <div>
        <span style="font-size: 20px"><strong>{{article.articleTitle}}</strong></span>
        <el-divider content-position="left">{{article.articleDate}}</el-divider>
        <div v-if="article.articleVideo !='' ">
          <video :src="article.articleVideo"
                 class="avatar" controls="controls">
            您的浏览器不支持视频播放</video>
        </div>

        <div class="markdown-body">
          <div v-html="article.articleContentHtml"></div>
        </div>
      </div>
    </el-card>
<!--    {{article.articleViews}}-->
  </div>

</template>

<script>
  export default {
    name: 'ArticleDetails',
    data () {
      return {
        // videoFlag:
        article: []
      }
    },
    mounted () {
      this.loadArticle()

    },
    methods: {
      loadArticle () {

        var _this = this
        this.$axios.get('/article/' + this.$route.query.id).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.article = resp.data.result
            this.updateView()
          }
        }
        )
        console.log("?????")

      },
      updateView(){
        console.log("article:"+this.article.id)
        var view=this.article.articleViews+1
        this.$axios
          .post('/article/content/countarticle/'+view+'/'+this.$route.query.id)
          .then(resp => {
            console.log("resp.data.code:"+resp.data.code)

          })
      }
    }
  }
</script>

<style scoped>
  @import "../../styles/markdown.css";
</style>
