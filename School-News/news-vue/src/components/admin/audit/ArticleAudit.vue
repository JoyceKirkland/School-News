<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>新闻管理</el-breadcrumb-item>
        <el-breadcrumb-item>文章审核</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
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
<!--    <el-link href="/admin/content/editor" :underline="false" target="_blank" class="add-link">-->
<!--      <el-button type="success">写文章</el-button>-->
<!--    </el-link>-->
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
          width="120">
        </el-table-column>
        <el-table-column
          prop="articleAuthor"
          label="作者"
          width="80">
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
          fixed="right"
          label="操作"
          width="180">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="viewArticle(scope.row.id)"
              type="text"
              size="small">
              审核
            </el-button>
<!--            <el-button-->
<!--              @click.native.prevent="editArticle(scope.row)"-->
<!--              type="text"-->
<!--              size="small">-->
<!--              编辑-->
<!--            </el-button>-->
<!--            <el-button-->
<!--              @click.native.prevent="deleteArticle(scope.row.id)"-->
<!--              type="text"-->
<!--              size="small">-->
<!--              移除-->
<!--            </el-button>-->
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
<!--
    public void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            // 需要再删掉不在角色菜单中的子菜单
            children.removeIf(child -> !menus.contains(child));
            m.setChildren(children);
        });
-->
<script>
  import EditArticlesForm from "../content/EditArticlesForm";
  export default {
    name: 'Editor',
    components:{EditArticlesForm},
    data () {
      return {
        articles: [],
        pageSize: 10,
        total: 0,
        form:{
          articleTitle:''
        },
        form_date:{
          startTime:'',
          endTime:''
        },
        articles_temp:[],
        AuditList:[],
        CategroyList:[],
      }
    },
    mounted () {
      // this.loadArticles()
      this.getStatusList()

    },
    computed: {
      tableHeight () {
        return window.innerHeight - 320
      }
    },
    created(){
      this.page_one();

      // const _this = this;
      // const page_one=0
      // this.$axios.get(`/article/${this.pageSize}/${page_one}?startTime=${stime}&endTime=${etime}`).then(resp => {
      //   if (resp && resp.data.code === 200) {
      //     this.articles = resp.data.result.articlesList
      //     _this.total = resp.data.result.TotalNum
      //     // this.allTableDate  = resp.data.result.content
      //     // this.filterTableData=resp.data.result.content
      //   }
      // })
    },
    methods: {
      page_one(){
        console.log("????????????????????????????????")
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
      filterTag_rate(value, row) {
        return row.articleRate === value;
      },
      filterTag_adudit(value, row) {
        return row.audit_status.status === value;
      },
      loadArticles () {
        var _this = this
        this.$axios.get('/article/' + this.pageSize + '/1').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.articles = resp.data.result.content
            _this.total = resp.data.result.totalElements
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
      viewArticle (id) {
        let articleUrl = this.$router.resolve(
          {
            path: '../../jotter/Audit_ArticleDetails',
            query: {
              id: id
            }
          }
        )
        window.open(articleUrl.href, '_blank')
      },
      editArticle (article) {
        this.$router.push(
          {
            // name: 'Editor_Auit',
            name:'Editor',
            params: {
              article: article
            }
          }
        )
      }
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
      // deleteArticle (id) {
      //   this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(() => {
      //       this.$axios
      //         .delete('/admin/content/article/' + id).then(resp => {
      //         if (resp && resp.data.code === 200) {
      //           this.loadArticles()
      //         }
      //       })
      //     }
      //   ).catch(() => {
      //     this.$message({
      //       type: 'info',
      //       message: '已取消删除'
      //     })
      //   })
      // }
    }
  }
</script>

<style scoped>
  .add-link {
    margin: 18px 0 15px 10px;
    float: left;
  }
</style>
