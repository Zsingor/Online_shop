<template>
  <div id="main">
    <home-nav></home-nav>
    <home-products></home-products>
  </div>
</template>

<script>
//import request from "@/utility/request";
import HomeNav from "@/components/homepage/HomeNav.vue";
import HomeProducts from "@/components/homepage/HomeProducts.vue";

export default {
  name: "MainView",
  components: {HomeProducts, HomeNav},
  beforeRouteEnter(to, from, next)
  {
    next();
    // 判断是否是从 /login 到 / 的路由切换
    if (from.path === '/login' && to.path === '/') {
      // 执行你想要的逻辑，例如刷新页面
      window.location.reload();
    }
  },
  data(){
    return {

    };
  },
  mounted() {
  },
  methods:{

  }
}
</script>

<style scoped>
#main{
  padding-top: 20px;
  padding-bottom: 20px;
  padding-right: 10%;
  padding-left: 10%;
  box-sizing: border-box;
}

</style>