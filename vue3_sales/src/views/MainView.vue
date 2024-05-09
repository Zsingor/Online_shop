<template>
  <div class="backround">
    <el-container>
      <el-header>
        <PageHeader/>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <PageAside @son-click="changepage"/>
        </el-aside>
        <el-container>
          <el-main>
            <div>
              <component :is="comName"></component>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import GoodsList from '@/components/GoodsList.vue'
import {shallowRef} from 'vue'
import PageHeader from "@/components/PageHeader.vue";
import PageAside from "@/components/PageAside.vue";

var comName=shallowRef(GoodsList)

const changepage=(page)=>{
  comName.value=page
}
</script>

<style scoped>
.el-header{
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
  padding: 0;
}

.el-footer{
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 0;
  padding: 0;
  margin: 0;
}

.el-aside {
  background-color: #545c64;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  min-height: 700px;
}
</style>