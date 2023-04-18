<template>
  <el-menu
    :default-active="currentPath"
    class="el-menu-admin"
    router
    mode="vertical"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    :collapse="isCollapse">
    <div style="height: 80px;"></div>
      <!--index 没有用但是必需字段-->
    <template v-for="(item,i) in adminMenus">
      <el-submenu
          v-if="item.children.length > 0"
          :key="i"
          :index="(i).toString()" style="text-align: left">
        <span slot="title" style="font-size: 17px;">
          <i :class="item.iconCls"></i>
          {{item.nameZh}}
<!--      正常有子菜单的父菜单-->
        </span>
        <el-menu-item v-for="child in item.children" :key="child.path" :index="child.path"
                      style="text-align: left">
          <i :class="child.iconCls" style="text-align: left"></i>
          {{ child.nameZh}}

        </el-menu-item>
      </el-submenu>
<!--      原本有父菜单的子菜单，全实现成父菜单形式-->
      <el-menu-item v-else :key="item.path" :index="item.path" style="text-align: left">
        <i :class="item.iconCls"></i>
        {{ item.nameZh }}
      </el-menu-item>
    </template>

  </el-menu>
</template>

<script>
    export default {
      name: 'AdminMenu',
      data () {
        return {
          isCollapse: false
        }
      },
      computed: {
        adminMenus () {
          console.log('所有菜单 = ', this.$store.state.adminMenus)
          return this.$store.state.adminMenus
        },
        currentPath () {
          return this.$route.path
        }
      }
    }
</script>

<style scoped>
  .el-menu-admin {
    border-radius: 5px;
    height: 100%;
  }
</style>
