<template>
  <div>
    <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="1" @click="show1">
        <el-icon><Present /></el-icon>
        <span slot="title">商品信息</span>
      </el-menu-item>
      <el-menu-item index="2" @click="show2">
        <el-icon><Menu /></el-icon>
        <span slot="title">订单信息</span>
      </el-menu-item>
      <el-sub-menu index="3">
        <template #title>
          <el-icon><TrendCharts /></el-icon>
          <span>销售报表</span>
        </template>
        <el-menu-item index="3-1" @click="show3">
          <span slot="title">商品销售</span>
        </el-menu-item>
        <el-menu-item index="3-2" @click="show4">
          <span slot="title">每日销售</span>
        </el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="4">
        <template #title>
          <el-icon><User /></el-icon>
          <span>销售人员</span>
        </template>
        <el-menu-item index="4-1" @click="show5">信息管理</el-menu-item>
        <el-menu-item index="4-2" @click="show6">销售业绩</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="5">
        <template #title>
          <el-icon><Document /></el-icon>
          <span>日志管理</span>
        </template>
        <el-menu-item index="5-1" @click="show7">操作日志</el-menu-item>
        <el-menu-item index="5-2" @click="show8">浏览日志</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
import GoodsList from '@/components/GoodsList.vue'
import OrderList from "@/components/OrderList.vue";
import GoodsSaleList from "@/components/GoodsSaleList.vue";
import SaleList from "@/components/SaleList.vue";
import SalesMessageList from "@/components/SalesMessageList.vue";
import SalePerformance from "@/components/SalePerformance.vue";
import OperateLog from "@/components/OperateLog.vue";
import BrowseLog from "@/components/BrowseLog.vue";

const emitEvents = defineEmits(['son-click'])

const show1=()=>{
  emitEvents('son-click',GoodsList)
}
const show2=()=>{
  emitEvents('son-click',OrderList)
}

const show3=()=>{
  emitEvents('son-click',GoodsSaleList)
}

const show4=()=>{
  emitEvents('son-click',SaleList)
}
const show5=()=>{
  emitEvents('son-click',SalesMessageList)
}
const show6=()=>{
  emitEvents('son-click',SalePerformance)
}

const show7=()=>{
  emitEvents('son-click',OperateLog)
}

const show8=()=>{
  emitEvents('son-click',BrowseLog)
}
</script>

<style scoped>
.el-menu{
  border: 0;
}
</style>