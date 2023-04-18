<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>新闻管理</el-breadcrumb-item>
        <el-breadcrumb-item>文章管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-link href="/admin/content/add_editor" :underline="false" target="_blank" class="add-link">
      <el-button type="success">写文章</el-button>
    </el-link>

    <el-form :inline="true" :model="form">
      <el-form-item label="新闻标题：">
        <el-input
          v-model="form.articleTitle"
          placeholder="请输入"
          style="height: 32px"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button-group>
          <el-button @click="reset1">重置</el-button>
          <el-button type="primary" @click="search1">查询</el-button>
        </el-button-group>
      </el-form-item>
    </el-form>

    <el-form :underline="false" :model="form_date">
    <el-form-item label="时间筛选：">
      <el-date-picker
        v-model="form_date.startTime"
        type="datetime"
        placeholder="选择开始日期"
        value-format="yyyy-MM-dd HH:mm:ss"
      >
      </el-date-picker> --
      <el-date-picker
        v-model="form_date.endTime"
        type="datetime"
        placeholder="选择结束日期"
        value-format="yyyy-MM-dd HH:mm:ss"
      >
      </el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size ="small" icon="search" @click='page_one()'>筛选</el-button>
      <el-button @click="reset2">重置</el-button>
    </el-form-item>
    </el-form>
<!--    <edit-articles-form @onSubmit="loadCategorys()" ref="edit"></edit-articles-form>-->
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="articles"
        stripe
        style="width: 100%"
        :max-height="tableHeight">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline>
              <el-form-item>
                <span>{{ props.row.articleAbstract }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="articleTitle"
          label="题目（展开查看摘要）"
          fit>
        </el-table-column>
        <el-table-column
          prop="articleDate"
          label="发布日期"
          width="100">
        </el-table-column>
        <el-table-column
          prop="articleAuthor"
          label="作者"
          width="75">
        </el-table-column>

        <el-table-column
          prop="audit_status.status"
          label="审核状态"
          :filters="AuditList"
          :filter-method="filterTag_adudit"
          width="88">
        </el-table-column>
        <el-table-column
          prop="articleRate"
          label="新闻等级"
          :filters="[{text:'1',value:'1'},{text:'2',value:'2'},{text:'3',value:'3'},{text:'4',value:'4'}]"
          :filter-method="filterTag_rate"
          width="88">
        </el-table-column>
        <el-table-column
          prop="category.name"
          label="所属栏目"
          :filters="CategroyList"
          :filter-method="filterTag_category"
          width="88">
        </el-table-column>
        <el-table-column
          label="置顶"
          width="85"
          :filters="[{text:'已置顶',value:true},{text:'未置顶',value:false}]"
          :filter-method="filterTag_top"
        >
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.articleisTop"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="(value) => commitStatusChange(value, scope.row.id)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="170">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="viewArticle(scope.row.id)"
              type="text"
              size="small">
              查看
            </el-button>
            <el-button
              @click.native.prevent="editArticle(scope.row)"
              type="text"
              size="small">
              编辑
            </el-button>
            <el-button
              @click.native.prevent="deleteArticle(scope.row.id)"
              type="text"
              size="small">
              移除
            </el-button>

            <el-button
              v-if="scope.row.audit_status.id ==4 "
              @click.native.prevent="publish(scope.row.audit_status.id,scope.row.id)"
              type="text"
              size="small">
              发布
            </el-button>
            <el-button
              v-if="scope.row.audit_status.id ==5 "
              @click.native.prevent="recall(scope.row.audit_status.id,scope.row.id)"
              type="text"
              size="small">
              撤销
            </el-button>

          </template>
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
        articles: [],
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
      // this.imtScreen()
      // this.page_one()
      this.getStatusList()
      this.getCategroyList()
    },
    computed: {
      tableHeight () {
        return window.innerHeight - 320
      }
    },
    created(){
      this.page_one();
    },
    methods: {
      page_one(){
        const _this = this;
        const page_one=1
        const stime = this.form_date.startTime;
        const etime = this.form_date.endTime;
        this.$axios.get(`/article/${this.pageSize}/${page_one}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            this.articles = resp.data.result.articlesList
            _this.total = resp.data.result.TotalNum
            console.log("this.articles",this.articles)
            // this.allTableDate  = resp.data.result.content
            // this.filterTableData=resp.data.result.content
          }
        })
      },
      // async imtScreen(){
      imtScreen(currentPage){
        //判断是否输入时间区间
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
        this.$axios.get(`/article/${this.pageSize}/${currentPage}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            this.articles = resp.data.result.articlesList
            // _this.total = resp.data.result.totalElements
          }
        })
      },

      // 前端实现查询功能
      search1() {
        const _this = this;
        // const cid = this.$route.query.id
        const page_one=1
        const stime = '';
        const etime = '';
        const keywords = this.form.articleTitle
        this.$axios.get(`/findvalueLike/${keywords}/${this.pageSize}/${page_one}?startTime=${stime}&endTime=${etime}`).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result.articlesList
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
        // this.articles_temp=this.articles
        // this.articles_temp.forEach(function (item) {
        //   if (item.articleTitle.indexOf(key) > -1) {
        //     vm.searchData1.push(item);
        //   }
        // });
        // // this.resetData1 = this.tableData1;
        // this.articles = this.searchData1;
        // console.log("查询后", this.articles);
      },
      // 重置数据
      reset1() {
        this. page_one();
        this.form = {
          articleTitle: '',
        };
      },
      reset2() {
        this.form_date = {
          startTime:'',
          endTime:''
        };
        this. page_one();
      },
      loadArticles () {
        var _this = this
        // return this.$axios.get('/article/' + this.pageSize + '/1').then(resp => {
        this.$axios.get('/article/' + this.pageSize + '/1').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result.content
            _this.total = resp.data.result.totalElements
            this.allTableDate  = resp.data.result.content
            this.filterTableData=resp.data.result.content
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
      // handleCurrentChange (page) {
      //   var _this = this
      //   this.$axios.get('/article/' + this.pageSize + '/' + page).then(resp => {
      //     if (resp && resp.data.code === 200) {
      //       _this.articles = resp.data.result.content
      //       _this.total = resp.data.result.totalElements
      //     }
      //   })
      // },
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
            message: '已取消'
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
        var username =Cookie.get('username')
        this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios
              .delete('/admin/content/deleteArticle/' + id+'/'+username).then(resp => {
              if (resp && resp.data.code === 200) {
                this.page_one()
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
