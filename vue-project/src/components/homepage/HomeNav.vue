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
          @open="handleOpen"
          @close="handleClose"
          router
      >
        <el-menu-item index="/product">
          <i class="el-icon-s-help"></i>
          <span slot="title">商品一</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <i class="el-icon-menu"></i>
          <span slot="title">商品二</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <i class="el-icon-document"></i>
          <span slot="title">商品三</span>
        </el-menu-item>
        <el-menu-item index="/product">
          <i class="el-icon-setting"></i>
          <span slot="title">商品四</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 主页面 -->
    <el-main>
      <el-carousel trigger="click" height="400px">
        <el-carousel-item v-for="item in carouselImg" :key="item.src">
          <img :src="item.img" class="bannerimg"/>
        </el-carousel-item>
      </el-carousel>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      carouselImg: [
        {
          link: "/detail?index=1001",
          img: require("@/assets/banner1.jpg")
        },
        {
          link: "/detail?index=1002",
          img: require("@/assets/banner2.jpg")
        }
      ]
    }
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
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