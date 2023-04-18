<template>
  <div style="text-align: left">
<!--    <el-button class="add-button" type="success" @click="dialogFormVisible = true">添加角色</el-button>-->
    <el-dialog
      title="修改密码"
      :visible.sync="dialogFormVisible"
      @close="clear"
      width="25%">

<!--        <el-form-item prop="name">-->
<!--          <el-input type="text" v-model="pwdForm.name"-->
<!--                    auto-complete="off" placeholder="用户名"></el-input>-->
      <label>用户名：{{username}}</label>
<!--        </el-form-item>-->
        <el-form :model="pwdForm" :rules="rules" label-position="left"
                 label-width="0px" v-loading="loading">
        <el-form-item prop="nameZh">
          <el-input type="text" v-model="pwdForm.old_pwd"
                    auto-complete="off" placeholder="旧密码"></el-input>
        </el-form-item>
        <el-form-item prop="nameZh">
          <el-input type="text" v-model="pwdForm.new_pwd"
                    auto-complete="off" placeholder="新密码"></el-input>
        </el-form-item>
          <el-form-item prop="nameZh">
            <el-input type="text" v-model="pwdForm.check_new_pwd"
                      auto-complete="off" placeholder="确认新密码"></el-input>
          </el-form-item>
        <el-form-item style="width: 100%">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" style="width: 40%;background: #505458;border: none" @click="onSubmit1">确定</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'PasswordCreate',
    data () {
      return {
        dialogFormVisible: false,

        checked: true,
        username: '',
        pwdForm: {
          old_pwd: '',
          new_pwd: '',
          check_new_pwd:''
        },
        loading: false
      }
    },
    methods: {
      clear () {
        this.pwdForm = {
          old_pwd: '',
          new_pwd: '',
          check_new_pwd:''
        }
      },
      onSubmit1(){
        console.log("???????????????????")
        this.$axios.post('/admin/checkUserAndWord', {
          username: this.username,
          password: this.pwdForm.old_pwd
          // roles: roles
        }).then(resp => {
          if (resp && resp.data.code !=200) {
            // this.$alert('用户信息修改成功')
            // this.dialogFormVisible = false
            this.$message.error('旧密码输入错误')
            console.log("password",this.pwdForm.old_pwd)
            console.log("resp.data.code",resp.data.code)
          }
          else {
            this.$message.success('旧密码正确')
            this.$axios.put('/user/password' ,{
              username: this.username,
              password: this.pwdForm.new_pwd
            }).then(resp => {
              if (resp && resp.data.code === 200) {
                // this.$alert('用户信息修改成功')
                // this.dialogFormVisible = false
                this.$message.success('密码重置成功')
              } else {
                this.$message.error('密码重置失败')
              }
            })

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
