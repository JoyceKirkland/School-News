<template>
  <div>
    <nav-menu class="nav-menu"></nav-menu>
    <side-menu id="side-menu"></side-menu>

    <router-view/>
      <div class="home">
        <div id="header-div">
          <!--<div style="height: 20px"></div>-->
<!--          <carousel></carousel>-->
<!--          <quick-nav style="float: right;margin-top: -450px;margin-right: 480px"></quick-nav>-->
        </div>
        <news-card id="news-card"></news-card>
<!--        <update-card id="update-card"></update-card>-->
        <slogan id="slogan"></slogan>

        <about id="about"></about>
      </div>
  </div>
</template>

<script>

  // import Carousel from './Carousel'
  import QuickNav from '../home/QuickNav'
  import NewsCard from '../home/news_card'
  // import About from '@/components/common/About'
  import Slogan from '../home/Slogan'
  import SideMenu from '../home/SideMenu'
  import NavMenu from "../common/NavMenu";
  export default {
    name: 'AppIndex',
    components: { QuickNav, NewsCard, SideMenu,NavMenu,Slogan},
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
