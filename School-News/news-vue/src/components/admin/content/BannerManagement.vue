<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>新闻管理</el-breadcrumb-item>
        <el-breadcrumb-item>新闻栏目管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <edit-category-form @onSubmit="loadCategorys()" ref="edit"></edit-category-form>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="categorys"
        stripe
        :max-height="tableHeight"
        style="width: 100%">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>

        <el-table-column
          prop="id"
          label="栏目编号"
          fit>
        </el-table-column>
        <el-table-column
          prop="name"
          label="栏目名称"
          fit>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-dialog title="提示" :visible.sync="dialogVisible" >

              <EditCategoryForm/>

            </el-dialog>
            <el-button
              @click.native.prevent="editCategory(scope.row)"
              type="text"
              size="small">
              编辑
            </el-button>
            <el-button
              @click.native.prevent="deleteCategory(scope.row.id)"
              type="text"
              size="small">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 20px 0 20px 0;float: left">
        <el-button>取消选择</el-button>
        <el-button>批量删除</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
    import EditCategoryForm from './EditCategoryForm'
    // import EditForm from './EditForm'

    export default {
      name: 'BannerManagement',
      components: {EditCategoryForm},
      // components: {EditForm},
      data () {
        return {
          categorys: []
        }
      },
      mounted () {
        this.loadCategorys()
      },
      computed: {
        tableHeight () {
          return window.innerHeight - 320
        }
      },
      methods: {
        loadCategorys () {
          var _this = this
          this.$axios.get('/categorys').then(resp => {
            if (resp && resp.data.code === 200) {
              _this.categorys = resp.data.result
            }
          })
        },
        editCategory (item) {
          // console.log("wtf")
          this.$refs.edit.dialogFormVisible = true
          this.$refs.edit.form = {
            id: item.id,
            name: item.name
          }
        },
        deleteCategory (id) {
          this.$confirm('此操作将永久删除该栏目并同步删除该栏目下的所有文章, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              this.$axios
                .post('/admin/content/categorys/delete', {id: id}).then(resp => {
                if (resp && resp.data.code === 200) {
                  this.loadCategorys()
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

</style>
