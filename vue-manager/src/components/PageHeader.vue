<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
           background-color="#333333"
           text-color="#ffffff"
           active-text-color="#fe0038"
           router
  >
    logo
<!--    <el-submenu v-if="managername" index="1" style="margin-left: auto">
      <template slot="title"><i class="el-icon-user"></i>{{ managername }}</template>
      <el-menu-item index="/login" @click="signout">退出登录</el-menu-item>
    </el-submenu>-->
    <!-- 当 username 存在时显示 submenu -->
    <el-submenu v-if="managername" index="1"  style="margin-left: auto">
      <template slot="title"><i class="el-icon-user"></i>{{ managername }}</template>
      <el-menu-item  @click="deleteaccount">注销账号</el-menu-item>
      <el-menu-item index="/login" @click="signout">退出登录</el-menu-item>
    </el-submenu>
    <!-- 当 managername 不存在时显示 login -->
    <el-menu-item v-else index="/login" style="margin-left: auto">
      <i class="el-icon-user"></i>
      登录
    </el-menu-item>
  </el-menu>
</template>

<script>
import request from "@/utility/request";

export default {
  name: "MainView",
  data() {
    return {
      managername: JSON.parse(localStorage.getItem("managername")),
      activeIndex: '/',
    };
  },
  methods: {
    signout() {
      localStorage.removeItem("managername")
      localStorage.removeItem("managertoken")
      this.managername=JSON.parse(localStorage.getItem("managername"))
    },
    deleteaccount(){
      // 弹出消息提示
      this.$confirm('此操作将永久删除该账号, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.post("/managerdelete", {
          "managername": JSON.parse(localStorage.getItem("managername"))
        }).then(result => {
          console.log(result)
          if (result.code === 1) {
            this.$message.success("注销成功")
            localStorage.removeItem("managername")
            localStorage.removeItem("managertoken")
            this.managername=JSON.parse(localStorage.getItem("managername"))
            this.$router.push("/login")
          } else {
            this.$message.error(result.msg)
          }
        })
            .catch(error => {
              this.$message.error("注销失败")
              console.log(error)
            })
      }).catch(() => {

      });

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