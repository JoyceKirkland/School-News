<template>
  <div class="articles-area">
    <el-card style="text-align: left;width: 990px;margin: 35px auto 0 auto">
      <div>
        <span style="font-size: 20px"><strong>{{article.articleTitle}}</strong></span>
        <el-divider content-position="left">{{article.articleDate}}</el-divider>
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
        article: []
      }
    },
    mounted () {
      this.loadArticle()
      // this.updateView()

    },
    methods: {
      loadArticle () {

        var _this = this
        this.$axios.get('/allarticle/'+this.$route.query.id).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.article = resp.data.result
          }
        })
        console.log("?????")

      },
      // updateView(){
      //   console.log("article:"+this.article.id)
      //   this.$axios
      //     .post('/admin/content/auditarticle/'+this.article.id+'/'+this.$route.query.id)
      //     .then(resp => {
      //       console.log("resp.data.code:"+resp.data.code)
      //
      //     })
      // }
    }
  }
</script>

<style scoped>
  @import "../../styles/markdown.css";
</style>
