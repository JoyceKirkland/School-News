<template>
  <div>
    <NavMenu></NavMenu>
    <side-menu id="side-menu"></side-menu>

    <router-view/>
      <div class="home">
        <div id="header-div">
          <!--<div style="height: 20px"></div>-->
<!--          <carousel></carousel>-->
<!--          <quick-nav style="float: right;margin-top: -450px;margin-right: 480px"></quick-nav>-->
        </div>
        <query_news_card id="query_news_card"></query_news_card>
<!--        <news-card id="news-card"></news-card>-->
<!--        <update-card id="update-card"></update-card>-->
        <slogan id="slogan"></slogan>

        <about id="about"></about>
      </div>
  </div>
</template>

<script>

  // import Carousel from './Carousel'
  import QuickNav from '../home/QuickNav'
  import query_news_card from '../home/query_news_card'
  // import About from '@/components/common/About'
  import Slogan from '../home/Slogan'
  import SideMenu from '../home/SideMenu'
  import NavMenu from "../common/NavMenu";
  export default {
    name: 'AppIndex',
    components: { QuickNav, query_news_card, SideMenu,NavMenu,Slogan},
    // data(){
    //   return{
    //     articles: [],
    //   }
    // }
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
