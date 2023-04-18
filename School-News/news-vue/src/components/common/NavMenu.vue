<template>
  <div>
    <el-menu
      :default-active="currentPath"
      router
      mode="horizontal"
      background-color="white"
      text-color="#222"
      active-text-color="red"
      style="min-width: 1300px">
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
      </el-menu-item>
<!--      White Jotter - Your Mind Palace-->
      <span style="margin-right:50px;position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">新闻网</span>

      <el-input
        placeholder="快速搜索..."
        prefix-icon="el-icon-search"
        size="medium"
        style="width: 300px;position:absolute;margin-top: 12px;right: 18%"
        v-model="keywords">
      </el-input>
      <el-button style="margin-top: 12px;margin-right:-20px"  size="small" type="primary" icon="el-icon-search"
                 @click="searchBooksByKeyWord()">搜索</el-button>

    </el-menu>

  </div>
</template>

<script>
  export default {
    name: 'NavMenu',
    data () {
      return {
        navList: [
          {name: '/index', navItem: '首页'},
          // {name: '/jotter', navItem: '笔记本'},
          // {name: '/library', navItem: '图书馆'},
          {name: '/login', navItem: '个人中心'}
        ],
        keywords: ''
      }
    },
    computed: {
      hoverBackground () {
        return '#ffd04b'
      },
      searchBooksByKeyWord() {
        var _this = this
        if (this.keywords=='')
        {
          this.$message('搜索内容不能为空')
        }else
        {
          let articlesUrl=this.$router.resolve({
            path:'../news/query',
            query:{
              keywords:this.keywords
            }
          })
          window.open(articlesUrl.href, '_blank')
        }
        // this.$axios
        //   .post('/findvalueLike', {
        //     keywords: this.keywords
        //   }).then(resp => {
        //   if (resp && resp.status === 200) {
        //     _this.books = resp.data
        //   }
        // })
      },
      currentPath () {
        var x = this.$route.path.indexOf('/', 1)
        if (x !== -1) {
          return this.$route.path.substring(0, x)
        } else {
          return this.$route.path
        }
      }
    }
  }
</script>

<style scoped>
  a{
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }

</style>
