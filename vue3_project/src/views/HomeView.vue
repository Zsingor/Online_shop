<template>
  <div id="main">
    <HomeNav/>
    <HomeProducts/>
  </div>
</template>

<script setup>
import HomeNav from "../components/HomeNav.vue";
import HomeProducts from "../components/HomeProducts.vue";

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