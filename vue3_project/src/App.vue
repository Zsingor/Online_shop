<template>
  <div id="background">
    <div id="main">
      <el-container>
        <el-header>
          <PageHeader/>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
        <el-footer style="height: 120px">
          <PageFooter/>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>

<script setup>
import PageHeader from "./components/PageHeader.vue";
import PageFooter from "./components/PageFooter.vue";
</script>

<style scoped>
#background{
  display: flex;
  justify-content: center;
  text-align: center;
  margin: auto;
  background-color: #f5e6e4;
}

#main{
  width:100%
}

.el-header {
  background-color: #333333;
  color: #ff5000;
  text-align: center;
  line-height: 60px;
  padding-left: 10%;
  padding-right: 10%;
}

.el-footer {
  background-color: #333333;
  color: #ff5000;
  text-align: center;
  padding-left: 0;
  padding-right: 0;
}

.el-main {
  background-color: white;
  color: #333;
  text-align: center;
  padding: 0;
}
</style>
