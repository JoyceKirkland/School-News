<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>文章操作明细</el-breadcrumb-item>
<!--        <el-breadcrumb-item>文章管理</el-breadcrumb-item>-->
      </el-breadcrumb>
    </el-row>

<!--    <el-form :inline="true" :model="form">-->
<!--      <el-form-item label="新闻标题：">-->
<!--        <el-input-->
<!--          v-model="form.articleTitle"-->
<!--          placeholder="请输入"-->
<!--          style="height: 32px"-->
<!--        ></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button-group>-->
<!--          <el-button @click="reset1">重置</el-button>-->
<!--          <el-button type="primary" @click="search1">查询</el-button>-->
<!--        </el-button-group>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

<!--    <el-form :underline="false" :model="form_date">-->
<!--      <el-form-item label="时间筛选：">-->
<!--        <el-date-picker-->
<!--          v-model="form_date.startTime"-->
<!--          type="datetime"-->
<!--          placeholder="选择开始日期"-->
<!--          value-format="yyyy-MM-dd HH:mm:ss"-->
<!--        >-->
<!--        </el-date-picker> &#45;&#45;-->
<!--        <el-date-picker-->
<!--          v-model="form_date.endTime"-->
<!--          type="datetime"-->
<!--          placeholder="选择结束日期"-->
<!--          value-format="yyyy-MM-dd HH:mm:ss"-->
<!--        >-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" size ="small" icon="search" @click='page_one()'>筛选</el-button>-->
<!--        <el-button @click="reset2">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->


<!--    <edit-articles-form @onSubmit="loadCategorys()" ref="edit"></edit-articles-form>-->
<!--  :data="articles"  -->
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="auditInfos"
        stripe
        style="width: 100%"
        :max-height="tableHeight"
      >
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
<!--        <el-table-column type="expand">-->
<!--          <template slot-scope="props">-->
<!--            <el-form label-position="left" inline>-->
<!--              <el-form-item>-->
<!--                <span>{{ props.row.articleAbstract }}</span>-->
<!--              </el-form-item>-->
<!--            </el-form>-->
<!--          </template>-->
<!--        </el-table-column>-->
        <el-table-column
          prop="nid"
          label="新闻标题"
          width="360">
<!--          getNewsTitle(auditInfos.nid)-->
          <template slot-scope="{ row }">
            <span>{{ row.nname || '' }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="operate_audit"
          label="操作"
          width="120">
        </el-table-column>
        <el-table-column
          prop="audit_mind"
          label="审核意见"
          width="160">
        </el-table-column>
<!--:filters="AuditList"
          :filter-method="filterTag_adudit"-->
        <el-table-column
          prop="uid"
          label="操作人"
          width="100">
          <template slot-scope="{ row }">
            <span>{{ row.uname || '' }}</span>
          </template>
        </el-table-column>
<!--      :filters="[{text:'1',value:'1'},{text:'2',value:'2'},{text:'3',value:'3'},{text:'4',value:'4'}]"
          :filter-method="filterTag_rate"  -->
        <el-table-column
          prop="operate_date"
          label="操作时间"
          width="150"
          >
        </el-table-column>

      </el-table>
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
    </el-card>
  </div>
</template>

<script>
  // import EditArticlesForm from "./EditArticlesForm";
  import Cookie from 'js-cookie'
  export default {
    name: 'ArticleManagement',
    // components:{EditArticlesForm},
    data () {
      return {
        auditInfos: [],
        articles: [],
        articles_id:[],
        articles_title:[],
        articles_titles:[],
        usernameZH:[],
        articles_temp:[],
        pageSize: 10,
        total: 0,
        // audit_status:[],
        AuditList:[],
        CategroyList:[],
        form:{
          articleTitle:''
        },
        form_date:{
          startTime:'',
          endTime:''
        },
        allTableDate: [],	//存储从后端获取到的全部数据
        filterTableData: [],	//经过时间筛选后得到的数据
      }
    },
    // created() {
    //   this.getStatusList();
    // },
    mounted () {
      // this.loadOperates()
      // this.getArticle_title()
      this.getStatusList()
      // this.getCategroyList()
    },
    computed: {
      tableHeight () {
        return window.innerHeight - 320
      }
    },
    created(){
      // this.getTableDate();
      this.page_one()
    },
    methods: {
      // async imtScreen(){
      page_one(){
        // console.log("????????????????????????????????")
        const _this = this;
        const page_one=1
        const stime = this.form_date.startTime;
        const etime = this.form_date.endTime;
        this.$axios.get(`/Operate/${this.pageSize}/${page_one}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.auditInfos = resp.data.result.OperateList
            // _this.articles_id = resp.data.result.OperateList.nid
            _this.total = resp.data.result.TotalNum
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
            console.log("auditInfos:",_this.auditInfos)
            _this.auditInfos.forEach((item, i) => {
              this.xbFormatter(item.nid, i)
            })
            _this.auditInfos.forEach((item, i) => {
              this.xbFormatter2(item.uid, i)
            })
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
          }
        })
      },
      // async imtScreen(){
      imtScreen(currentPage){
        //判断是否输入时间区间
        var _this=this
        const stime = this.form_date.startTime;
        const etime = this.form_date.endTime;
        // if(!this.form_date.startTime || !this.form_date.endTime){
        //   this.$message({
        //     type: 'warning',
        //     message: "请选择时间区间！"
        //   })
        // }
        // const pageSize = 10
        // const page = 1
        console.log("currentPage",currentPage)
        this.$axios.get(`/Operate/${this.pageSize}/${currentPage}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.auditInfos = resp.data.result.OperateList
            // _this.articles_id = resp.data.result.articlesList.nid

            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
            console.log("time:",_this.auditInfos)
            _this.auditInfos.forEach((item, i) => {
              this.xbFormatter(item.nid, i)
            })
            _this.auditInfos.forEach((item, i) => {
              this.xbFormatter2(item.uid, i)
            })
          }
        })
      },
      // getTableDate(){
      //   // var auditfindall=[];
      //   this.$axios.get('/admin/DateList').then(resp => {
      //     // console.log(resp.data.result.list)
      //     if (resp && resp.data.code === 200) {
      //       this.allTableDate  = resp.data.result
      //       this.filterTableData=resp.data.result
      //
      //     }
      //   }).catch(function (error) {
      //     console.log(error)
      //   })
      // },
      // 前端实现查询功能
      search1() {
        const _this = this;
        // const cid = this.$route.query.id
        const page_one=1
        const stime = '';
        const etime = '';
        const keywords = this.form.articleTitle
        this.$axios.get(`/findvalueLikeOperate/${keywords}/${this.pageSize}/${page_one}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.auditInfos = resp.data.result.OperateList
            _this.total = resp.data.result.TotalNum
            console.log("this.articles",this.articles)
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
          }
        })
        // this.flag1 = true;
        // const key = this.form.articleTitle; // 查询表单中的输入
        // const vm = this;
        // this.searchData1 = []; //清空查询数组-防止多次查询记录一直被push在最后
        // console.log("查询前", this.articles_temp);
        // this.articles_temp=this.articles_titles
        // this.articles_temp.forEach(function (item) {
        //   if (item.indexOf(key) > -1) {
        //     vm.searchData1.push(item);
        //   }
        // });
        // // this.resetData1 = this.tableData1;
        // this.auditInfos = this.searchData1;
        // console.log("查询后", this.auditInfos);
      },
      // 重置数据
      reset1() {
        this. page_one();
        this.form = {
          articleTitle: '',
        };
      },
      reset2() {
        // this.loadOperates();
        this.form_date = {
          startTime:'',
          endTime:''
        };
        this. page_one();
      },
      xbFormatter(nid, index){
        var article_title;
        this.$axios.get('/article/' + nid ).then(resp => {
          if (resp && resp.data.code === 200) {
            // this.auditInfos = resp.data.result.content
            article_title= resp.data.result.articleTitle


            // if(resp.data.result==''){
              // this.$set(this.auditInfos[index], 'nname', '文章不存在')
            // }else{
              this.$set(this.auditInfos[index], 'nname', article_title)
            // }
          }
        })
        console.log("articles_titles:",this.articles_titles)
      },
      xbFormatter2(uid, index){
        var usernameZH;
        this.$axios.get('/admin/findbyUserId/' + uid ).then(resp => {
          if (resp && resp.data.code === 200) {
            // this.auditInfos = resp.data.result.content
            usernameZH= resp.data.result.name
            this.$set(this.auditInfos[index], 'uname', usernameZH)
          }
        })
      },
      loadOperates () {
        var _this = this
        // return this.$axios.get('/article/' + this.pageSize + '/1').then(resp => {
        this.$axios.get('/admin/auditInfo/' + this.pageSize + '/1').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.auditInfos = resp.data.result.content
            _this.articles_id = resp.data.result.content.nid
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
            console.log("time:",_this.auditInfos)
            _this.auditInfos.forEach((item, i) => {
              this.xbFormatter(item.nid, i)
            })
            _this.auditInfos.forEach((item, i) => {
              this.xbFormatter2(item.uid, i)
            })
          }
        })
      },
      getArticle_title () {
        var _this = this
        // return this.$axios.get('/article/' + this.pageSize + '/1').then(resp => {
        this.$axios.get('/admin/auditInfo/' + this.pageSize + '/1').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.auditInfos = resp.data.result.content
            _this.articles_id = resp.data.result.content.nid
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
          }
        })
      },
      // 表格过滤
      filterTag_adudit(value, row) {
        return row.audit_status.status === value;
      },
      filterTag_top(value, row) {
        return row.articleisTop === value;
      },
      filterTag_category(value, row) {
        return row.category.name === value;
      },
      filterTag_rate(value, row) {
        return row.articleRate === value;
      },
      commitStatusChange (value, id) {
          this.$confirm('是否更改文章置顶状态？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$axios.post('/admin/article_Top/'+value+'/'+id)
              .then(resp => {
              if (resp && resp.data.code === 200) {
                if (value) {
                  this.$message('已置顶')
                } else {
                  this.$message('已取消置顶')
                }
              }
            })
          }).catch(() => {
            article.articleisTop = !article.articleisTop
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })

      },
      getCategroyList(){
        // var auditfindall=[];
        this.$axios.get('/admin/CategroyList').then(resp => {
          // console.log(resp.data.result.list)
          if (resp && resp.data.code === 200) {
            this.CategroyList = resp.data.result.map(item => {
              return {
                text: item,
                value: item
              }
            })
            // console.log('getChoiceList',resp.data.result)
          }
        }).catch(function (error) {
          console.log(error)
        })
      },
      getStatusList(){
        var auditfindall=[];
        this.$axios.get('/admin/AuditList').then(resp => {
          // console.log(resp.data.result.list)
          if (resp && resp.data.code === 200) {
            this.AuditList = resp.data.result.map(item => {
              return {
                text: item,
                value: item
              }
            })
            // console.log('getChoiceList',resp.data.result)
          }
        }).catch(function (error) {
          console.log(error)
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
      publish (aid,id) {
          // value 是 md，render 是 html
          var message='确认发布该新闻?';
          var username =Cookie.get('username')
          this.$confirm(message, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            aid=5
              this.$axios
                .post('/admin/content/publish_or_Revoke/'+aid+'/'+id
                +'/'+username)
                .then(resp => {
                  if (resp && resp.data.code === 200) {
                    this.$message({
                      type: 'info',
                      message: '发布成功'
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
      },
      recall (aid,id) {
        // value 是 md，render 是 html
        var message='确认撤销发布该新闻?';
        this.$confirm(message, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            aid=9
            var username =Cookie.get('username')
          this.$axios
            .post('/admin/content/publish_or_Revoke/'+aid+'/'+id
              +'/'+username)
            .then(resp => {
                if (resp && resp.data.code === 200) {
                  this.$message({
                    type: 'info',
                    message: '撤销成功'
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
      },
      editArticle (article) {
        this.$router.push(
          {
            name: 'Editor',
            params: {
              article: article
            }
          }
        )
      },
      // editArticle_others (item) {
      //   // console.log("wtf")
      //   this.$refs.edit.dialogFormVisible = true
      //   this.$refs.edit.form = {
      //     id: item.id,
      //     articleTitle: item.articleTitle,
      //     articleAuthor: item.articleAuthor,
      //     articleDate: item.articleDate,
      //     articleRate: item.articleRate,
      //     articleisCheck: item.articleisCheck
      //   }
      // },
      deleteArticle (id) {
        this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios
              .delete('/admin/content/article/' + id).then(resp => {
              if (resp && resp.data.code === 200) {
                this.loadArticles()
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
    }
  }
</script>

<style scoped>
  .add-link {
    margin: 18px 0 15px 10px;
    float: left;
  }
</style>
