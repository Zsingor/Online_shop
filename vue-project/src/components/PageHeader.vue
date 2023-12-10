<template>
  <el-menu :default-active="this.$route.path" class="el-menu-demo" mode="horizontal"
           background-color="#333333"
           text-color="#ffffff"
           active-text-color="#fe0038"
           router
  >
    logo
    <el-menu-item index="/" style="margin-left: auto">
      <i class="el-icon-house"></i>
      首页
    </el-menu-item>
    <el-menu-item index="/shopcart">
      <i class="el-icon-shopping-cart-2"></i>
      购物车
    </el-menu-item>
    <!-- 当 username 存在时显示 submenu -->
    <el-submenu v-if="username" index="3">
      <template slot="title"><i class="el-icon-user"></i>{{ username }}</template>
      <el-menu-item index="/person">个人中心</el-menu-item>
      <el-menu-item index="/order">订单信息</el-menu-item>
      <el-menu-item index="/login" @click="signout">退出登录</el-menu-item>
    </el-submenu>
    <!-- 当 username 不存在时显示 login -->
    <el-menu-item v-else index="/login">
      <i class="el-icon-user"></i>
      登录
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "MainView",
  data() {
    return {
      username: JSON.parse(localStorage.getItem("username")),
      activeIndex: '/',
    };
  },
  methods: {
    signout() {
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
      localStorage.removeItem("goods")
      this.username=JSON.parse(localStorage.getItem("username"))
    }
  }
}
</script>

<style scoped>
/* 菜单栏的样式 */
.el-menu-demo {
  display: flex;
  width: 100%;
}

</style>