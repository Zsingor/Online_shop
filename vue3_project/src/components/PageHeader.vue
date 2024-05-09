<template>
  <el-menu
      :default-active="$route.path"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
      background-color="#333333"
      text-color="#ffffff"
      active-text-color="#fe0038"
      :router="true"
  >
    <div class="flex-grow"/>
    <el-menu-item index="/" style="margin-left: auto">
      <el-icon><House /></el-icon>
      首页
    </el-menu-item>
    <el-menu-item index="/shopcart">
      <el-icon><ShoppingCart /></el-icon>
      购物车
    </el-menu-item>
    <el-sub-menu v-if="hasUsername()" index="3">
      <template #title>
        <el-icon><User /></el-icon>{{ username }}
      </template>
      <el-menu-item index="/person">个人中心</el-menu-item>
      <el-menu-item index="/order">订单信息</el-menu-item>
      <el-menu-item index="/login" @click="signout">退出登录</el-menu-item>
    </el-sub-menu>
    <!-- 当 username 不存在时显示 login -->
    <el-menu-item v-else index="/login">
      <el-icon><User /></el-icon>
      登录
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import {ref} from 'vue'

const username = ref(JSON.parse(localStorage.getItem("username")));

const hasUsername = () => {
  username.value=JSON.parse(localStorage.getItem("username"))
  return !!JSON.parse(localStorage.getItem("username"));
}

const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
}
const signout = () => {
  localStorage.removeItem("username")
  localStorage.removeItem("usertoken")
  localStorage.removeItem("goods")
  username.value = JSON.parse(localStorage.getItem("username"))
}
</script>

<style scoped>
/* 菜单栏的样式 */
.el-menu-demo {
  display: flex;
  width: 100%;
}

.flex-grow {
  flex-grow: 1;
}
</style>