<template>
  <div class="articles-area">
    <el-card style="text-align: left;width: 990px;margin: 35px auto 0 auto">
      <div>
        <span style="font-size: 20px"><strong>当前新闻审核状态：{{article.audit_status.status}}</strong></span>
      </div>
    </el-card>
    <el-card style="text-align: left;width: 990px;margin: 35px auto 0 auto">
      <div>

        <span style="font-size: 20px"><strong>{{article.articleTitle}}</strong></span>
        <el-divider content-position="left">{{article.articleDate}}</el-divider>
        <div class="markdown-body">
          <div v-html="article.articleContentHtml"></div>
        </div>
      </div>
    </el-card>
<!--    <div style="margin-left:-800px;margin-top:40px;margin-bottom:30px">-->
<!--      <el-button style="left: 10px" type="primary" v-if="article.audit_status.id == 1 || article.audit_status.id == 6"-->
<!--                 @click="saveAuditArticles(2,'初审通过')">初审通过</el-button>-->
<!--      <el-button style="left: 100px;" type="danger" v-if="article.audit_status.id == 1 || article.audit_status.id == 6"-->
<!--                 @click="saveAuditArticles(6,'不通过')">不通过</el-button>-->
<!--    </div>-->

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
    },
    methods: {
      loadArticle () {
        var _this = this
        this.$axios.get('/article/' + this.$route.query.id).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.article = resp.data.result
            // console.log("status:"+_this.article.audit_status.id)
          }
        })
      },
      saveAuditArticles (aid,status_name) {
        // value 是 md，render 是 html
        var message='';
        if(status_name=='不通过')
        {
          message='确认不通过并打回该文章?';
        }else {
          message='确认初审通过?';
        }
        this.$confirm(message, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

            this.$axios
              .post('/admin/content/auditarticle/'+aid+'/'+this.article.id)
              .then(resp => {
              console.log("resp.data.code:"+resp.data.code)
              if (resp && resp.data.code === 200) {

                this.$message({
                  type: 'info',
                  message: '操作成功'
                })
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
      }
    }
  }
</script>

<style scoped>
  @import "../../styles/markdown.css";

</style>
