<template>
  <div>
<!--    <el-dialog-->
    <span style="font-weight: bold;font-size: 20px">修改个人信息</span>
<!--      :visible.sync="dialogFormVisible"-->
<password-create @onSummit="getUserbyUsername()" ref="editpwd"></password-create>
      <el-form v-model="users" style="text-align: left" ref="dataForm">
        <el-form-item label="用户名" label-width="120px" prop="username">
          <label>{{users.username}}</label>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="120px" prop="name">
          <el-input v-model="users.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" prop="phone">
          <el-input v-model="users.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="120px" prop="email">
          <el-input v-model="users.email" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="密码" label-width="120px" prop="password">-->
<!--          <el-input v-model="users.password" autocomplete="off"></el-input>-->
          <el-button type="warning" @click="editPassword()">重置密码</el-button>
<!--        </el-form-item>-->
<!--        <el-form-item label="角色分配" label-width="120px" prop="roles">-->
<!--          <el-checkbox-group v-model="selectedRolesIds">-->
<!--              <el-checkbox v-for="(role,i) in roles" :key="i" :label="role.id">{{role.nameZh}}</el-checkbox>-->
<!--          </el-checkbox-group>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit(selectedUser)">确 定</el-button>
      </div>

  </div>
</template>

<script>
  // import BulkRegistration from './BulkRegistration'
  import PasswordCreate from './PasswordCreate'
  import Cookie from 'js-cookie'
    export default {
      // name: 'UsProfile',
      components: {PasswordCreate},
      data () {
          return {
            dialogFormVisible: false,
            users: [],
            roles: [],
            // dialogFormVisible: false,
            selectedUser: [],
            // selectedRolesIds: []
          }
      },
      mounted () {
        // this.listUsers()
        // this.listRoles()
        this.getUserbyUsername()
      },
      computed: {
        tableHeight () {
          return window.innerHeight - 320
        },
        ShowUserName(){
          return Cookie.get('username')
        }
      },
      methods: {
        getUserbyUsername(){
          var _this = this
          var username =Cookie.get('username')
          this.$axios.get('/admin/'+username).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.users = resp.data.result
            }
          })
        },
        editPassword(){
          this.$refs.editpwd.dialogFormVisible = true
          this.$refs.editpwd.username=this.users.username
        },
        // commitStatusChange (value, user) {
        //   if (user.username !== 'admin') {
        //     this.$axios.put('/admin/user/status', {
        //       enabled: value,
        //       username: user.username
        //     }).then(resp => {
        //       if (resp && resp.data.code === 200) {
        //         if (value) {
        //           this.$message('用户 [' + user.username + '] 已启用')
        //         } else {
        //           this.$message('用户 [' + user.username + '] 已禁用')
        //         }
        //       }
        //     })
        //   } else {
        //     user.enabled = true
        //     this.$alert('不能禁用管理员账户')
        //   }
        // },
        onSubmit () {
          var _this = this
          // 根据视图绑定的角色 id 向后端传送角色信息
          // let roles = []
          // for (let i = 0; i < _this.selectedRolesIds.length; i++) {
          //   for (let j = 0; j < _this.roles.length; j++) {
          //     if (_this.selectedRolesIds[i] === _this.roles[j].id) {
          //       roles.push(_this.roles[j])
          //     }
          //   }
          // }
          let user = this.users
          this.$axios.put('/admin/all_user', {
            username: user.username,
            name: user.name,
            phone: user.phone,
            email: user.email,
            // password:user.password,
            enable:user.enable,

            // roles: roles
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              this.$alert('用户信息修改成功')
              this.dialogFormVisible = false
              // 修改角色后重新请求用户信息，实现视图更新
              this.listUsers()
            } else {
              this.$alert(resp.data.message)
            }
          })
        },
        editUser (user) {
          // this.dialogFormVisible = true
          this.selectedUser = user
          let roleIds = []
          for (let i = 0; i < user.roles.length; i++) {
            roleIds.push(user.roles[i].id)
          }
          this.selectedRolesIds = roleIds
        },

        resetPassword (username) {
          this.$axios.put('/admin/user/password', {
            username: username
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              this.$alert('密码已重置为 123')
          }
          })
        }
      }
    }
</script>

<style scoped>

</style>
