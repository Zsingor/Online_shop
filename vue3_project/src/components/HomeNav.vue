<template>
  <el-container
      style="height: 400px;
      margin: 0;
      border: 0;
      padding: 0;">     <!-- style： 隐藏滚动条  去掉白边-->
    <!-- 侧边栏 -->
    <el-aside style="width: 200px;">
      <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          :router="true"
      >
        <el-menu-item index="/product">
          <el-icon><Goods /></el-icon>
          <span slot="title">商品一</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <el-icon><Handbag /></el-icon>
          <span slot="title">商品二</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <el-icon><TakeawayBox /></el-icon>
          <span slot="title">商品三</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <el-icon><Wallet /></el-icon>
          <span slot="title">商品四</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 主页面 -->
    <el-main>
      <el-carousel trigger="click" height="400px">
        <el-carousel-item v-for="item in carouselImg" :key="item.src">
          <img :src=getImageUrl(item.img) class="bannerimg" alt="image"/>
        </el-carousel-item>
      </el-carousel>
    </el-main>
  </el-container>
</template>

<script setup>
import {ref} from 'vue';

const carouselImg=ref([
  {
    link: "/detail?index=1001",
    img: "../assets/banner1.jpg"
  },
  {
    link: "/detail?index=1002",
    img: "../assets/banner2.jpg"
  }
])

const getImageUrl = (name) => {
  return new URL(name, import.meta.url).href
}
</script>

<style scoped>
/* 走马灯指示器的样式 */
/deep/ .el-carousel__indicators {
  bottom: 0;
}

/* 走马灯图片的样式 */
.bannerimg {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持宽高比并填充容器 */
}

.el-main {
  padding-top: 0;
  padding-bottom: 0;
  padding-right: 0;
}
</style>