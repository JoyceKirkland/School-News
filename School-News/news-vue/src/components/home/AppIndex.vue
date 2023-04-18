<template>
  <div style="margin-top: 50px">
    <side-menu id="side-menu" @indexSelect="listByCategory" ref="sideMenu"></side-menu>
      <div class="home">
        <div id="header-div">
          <div style="height: 20px"></div>
          <carousel></carousel>
          <quick-nav style="float: right;margin-top: -450px;margin-right: 480px"></quick-nav>
        </div>
        <el-main>
        <update-card id="update-card" class="books-area" ref="booksArea"></update-card>
        </el-main>
        <slogan id="slogan"></slogan>
        <about id="about"></about>
      </div>
  </div>
</template>

<script>
  import Carousel from './Carousel'
  import QuickNav from './QuickNav'
  import Slogan from './Slogan'
  // import About from '@/components/common/About'
  import UpdateCard from './UpdateCard'
  import SideMenu from './SideMenu'
  export default {
    name: 'AppIndex',
    components: {Carousel, QuickNav, Slogan, UpdateCard, SideMenu},
    methods: {
      listByCategory () {
        var _this = this
        var cid = this.$refs.sideMenu.cid
        var url = 'categories/' + cid + '/article'
        this.$axios.get(url).then(resp => {
          if (resp && resp.data.code === 200) {
            _this.$refs.booksArea.article = resp.data.result
            _this.$refs.booksArea.currentPage = 1
          }
        })
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
