<template>
  <div style="text-align: left">
    <el-button class="add-button" type="success" @click="dialogFormVisible = true">添加新闻栏目</el-button>
    <el-dialog
      title="添加/修改栏目"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="栏目名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
<!--&lt;!&ndash;        <el-form-item label="简介" :label-width="formLabelWidth" prop="abs">&ndash;&gt;-->
<!--&lt;!&ndash;          <el-input type="textarea" v-model="form.abs" autocomplete="off"></el-input>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-form-item>&ndash;&gt;-->
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
    name: 'EditCategory',
    // components: {ImgUpload},
    data () {
      return {
        dialogFormVisible: false,
        form: {
          id: '',
          name: ''
        },
        formLabelWidth: '120px'
      }
    },
    methods: {
      clear () {
        // this.$refs.imgUpload.clear()
        this.form = {
          id: '',
          name: ''
        }
      },
      onSubmit () {
        this.$axios
          .post('/admin/content/categorys', {
            id: this.form.id,
            name: this.form.name
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              this.dialogFormVisible = false
              this.$emit('onSubmit')
            }
        })
      }
    }
  }
</script>

<style scoped>
  .add-button {
    margin: 18px 0 0 10px;
  }
</style>
