<template>
  <div>
    <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="1" @click="show1">
        <i class="el-icon-box"></i>
        <span slot="title">商品信息</span>
      </el-menu-item>
      <el-menu-item index="2" @click="show2">
        <i class="el-icon-menu"></i>
        <span slot="title">订单信息</span>
      </el-menu-item>
      <el-menu-item index="3" @click="show3">
        <i class="el-icon-pie-chart"></i>
        <span slot="title">销售报表</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import GoodsList from '@/components/GoodsList.vue'
import OrderList from "@/components/OrderList.vue";
import SaleList from "@/components/SaleList.vue";
const emitEvents = defineEmits(['son-click'])

const show1=()=>{
  emitEvents('son-click',GoodsList)
}
const show2=()=>{
  emitEvents('son-click',OrderList)
}
const show3=()=>{
  emitEvents('son-click',SaleList)
}
</script>

<style scoped>
.el-menu{
  border: 0;
}
</style>