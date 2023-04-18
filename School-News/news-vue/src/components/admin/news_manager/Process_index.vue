<template>
  <div>
    <NavMenu></NavMenu>
    <side-menu id="side-menu"></side-menu>
    <span style="font-size: 22px;font-weight: bold;position:absolute;top: 100px">新闻业务流程</span>

    <router-view/>
    <div class="home">
      <div id="header-div">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <!--<el-button style="float: right; padding: 3px 0" type="text">修改</el-button>-->
          </div>
          <!--    <div class="block">-->
          <el-timeline>
            <div style="margin-left:1%;margin-right:1%">
              <el-row>
                <el-col >
                  <div style="margin-top:15px">
                    <img :src="'F:\\张阳的文件夹1\\毕设\\School-News\\news-vue\\src\\components\\home\\defualt.jpg'" class="img"
                    >
                  </div>
                </el-col>
              </el-row>

            </div>
          </el-timeline>
          <!--    </div>-->
        </el-card>
        <!--<div style="height: 20px"></div>-->
        <!--          <carousel></carousel>-->
        <!--          <quick-nav style="float: right;margin-top: -450px;margin-right: 480px"></quick-nav>-->
      </div>
      <!--        <update-card id="update-card"></update-card>-->
      <slogan id="slogan"></slogan>
      <about id="about"></about>
    </div>
  </div>
</template>

<script>
import Cookie from 'js-cookie'
export default {
  computed:{
    ShowUserName(){
      return Cookie.get('username')
    }
  }
}
</script>

<style scoped>
.home {
  width: 990px;
  margin-left: auto;
  margin-right: auto;
  margin-top: -20px;
}

#header-div {
  padding-bottom: 20px;
  padding-left: 5px;
  background-color: white;
}

#side-menu {
  position: fixed;
  margin-left: 50%;
  left: -680px;
  top: 100px;
}
</style>
