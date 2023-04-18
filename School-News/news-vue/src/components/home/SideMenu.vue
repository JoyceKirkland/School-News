
<template>
  <div>
<!--    {{category.id}}-->
    <el-menu
      default-active="$route.path"
      style="width: 170px"
      router
    >
      <el-menu-item
        v-for="item in category"
        :key="item.id"
        @click="handleMenuClick(item)"
      >
        <i class="el-icon-menu"></i>
        <span slot="title">{{ item.name }}</span>
      </el-menu-item>

    </el-menu>

  </div>
</template>

<script>
  export default {
    name: 'SideMenu',
    data () {
      return {
        cid: '',
        category:[]
      }
    },
    // computed: {
    //   category(){
    //     console.log('所有菜单 = ', this.category)
    //     return this.category
    //   }
    // },
    mounted () {
      this.loadCategorys()
    },
    methods: {
      handleMenuClick(item){
        this.listByCategory(item.id, item.name)
      },
      // handleSelect (key, keyPath) {
      //   this.cid = key
      //   this.$emit('indexSelect')
      // },
      loadCategorys () {
        var _this = this
        this.$axios.get('/categorys').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.category = resp.data.result
          }
        })
      },
      listByCategory (cid, cname) {
        // var _this = this
        // // var cid = this.$refs.categories.cid
        // var url = 'categories/' + cid + '/article'
        // this.$axios.get(url).then(resp => {
        //   if (resp && resp.data.code === 200) {
        //     // _this.$refs.booksArea.article = resp.data.result
        //     // _this.$refs.booksArea.currentPage = 1
        //   }
        // })
        let articlesUrl = this.$router.resolve(
          {
            path: '../news',
            query: {
              id: cid,
              name: cname
            }
          }
        )
        window.open(articlesUrl.href, '_blank')
      }
    }
  }
</script>

<style>
  a {
    text-decoration: none;
    color: inherit;
  }
</style>
