<template>
  <el-menu :default-active="$route.path"
           class="el-menu-demo"
           mode="horizontal"
           :ellipsis="false"
           background-color="#333333"
           text-color="#ffffff"
           active-text-color="#fe0038"
           :router="true"
  >
    logo
    <el-sub-menu v-if="hasUsername()" index="1"  style="margin-left: auto">
      <template #title><el-icon><User /></el-icon>{{ managername }}</template>
      <el-menu-item  @click="deleteaccount">注销账号</el-menu-item>
      <el-menu-item index="/login" @click="signout">退出登录</el-menu-item>
    </el-sub-menu>
    <!-- 当 managername 不存在时显示 login -->
    <el-menu-item v-else index="/login" style="margin-left: auto">
      <el-icon><User /></el-icon>
      登录
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import {ref} from 'vue'
import request from "@/utility/request";
import {ElMessage,ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router'

const router = useRouter()
var managername=ref(JSON.parse(localStorage.getItem("username")))

const hasUsername = () => {
  managername.value=JSON.parse(localStorage.getItem("username"))
  return !!JSON.parse(localStorage.getItem("username"));
}

const signout=()=>{
  localStorage.removeItem("username")
  localStorage.removeItem("usertoken")
  managername.value=JSON.parse(localStorage.getItem("username"))
}

const deleteaccount=()=>{
  // 弹出消息提示
  ElMessageBox.confirm(
      '此操作将永久删除该账号, 是否继续?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    request.post("/managerdelete", {
      "managername": JSON.parse(localStorage.getItem("username"))
    }).then(result => {
      console.log(result)
      if (result.code === 1) {
        ElMessage.success("注销成功")
        localStorage.removeItem("username")
        localStorage.removeItem("usertoken")
        managername.value=JSON.parse(localStorage.getItem("username"))
        router.push("/login")
      } else {
        ElMessage.error(result.msg)
      }
    })
        .catch(error => {
          ElMessage.error("注销失败")
          console.log(error)
        })
  }).catch(() => {

  });
}
</script>

<style scoped>
/* 菜单栏的样式 */
.el-menu-demo {
  display: flex;
  width: 100%;
}
</style>