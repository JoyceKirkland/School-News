<template>
  <div style="text-align: left">
<!--    <el-button class="add-button" type="success" @click="dialogFormVisible = true">添加图书</el-button>-->
    <el-dialog
      title="修改文章信息"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="标题" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.articleTitle" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.articleAuthor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="发布日期" :label-width="formLabelWidth" prop="date">
          <el-input v-model="form.articleDate" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新闻等级" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.articleRate" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否审核" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.articleisCheck" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="新闻栏目" :label-width="formLabelWidth" prop="cid">-->
        <!--        <el-select v-model="form.category.id" placeholder="请选择分类">-->
        <!--          <el-option label="综合新闻" value="1"></el-option>-->
        <!--          <el-option label="招生就业" value="2"></el-option>-->
        <!--          <el-option label="党政建设" value="3"></el-option>-->
        <!--          <el-option label="教学科研" value="4"></el-option>-->
        <!--          <el-option label="后勤服务" value="5"></el-option>-->
        <!--          <el-option label="test" value="6"></el-option>-->
        <!--        </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item prop="id" style="height: 0">
          <el-input type="hidden" v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  // import ImgUpload from './ImgUpload'
  export default {
    name: 'EditForm',
    // components: {ImgUpload},
    data () {
      return {
        dialogFormVisible: false,
        form: {
          id: '',
          articleTitle: '',
          articleAuthor: '',
          articleDate: '',
          articleRate: '',
          articleisCheck: ''
        },
        formLabelWidth: '120px'
      }
    },
    methods: {
      clear () {
        // this.$refs.imgUpload.clear()
        this.form = {
          id: '',
          articleTitle: '',
          articleAuthor: '',
          articleDate: '',
          articleRate: '',
          articleisCheck: ''
        }
      },
      onSubmit () {
        this.$axios
          .post('/admin/content/article', {
            id: this.form.id,
            articleTitle: this.form.articleTitle,
            articleAuthor: this.form.articleAuthor,
            articleDate: this.form.articleDate,
            articleRate: this.form.articleRate,
            articleisCheck: this.form.articleisCheck
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              this.dialogFormVisible = false
              this.$emit('onSubmit')
            }
        })
      },
      // uploadImg () {
      //   this.form.cover = this.$refs.imgUpload.url
      // }
    }
  }
</script>

<style scoped>
  .add-button {
    margin: 18px 0 0 10px;
  }
</style>
