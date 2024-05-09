<template>
  <div class="background">
    <p class="toptext">为您推荐</p>
    <div v-if="loading1" class="product-container">
      <!-- Placeholder content -->
      <div class="tupian" v-for="i in 5" :key="i">
        <el-card shadow="hover" v-loading="true" element-loading-text="正在加载...">
        </el-card>
      </div>
    </div>
    <div v-else class="product-container">
      <div class="tupian" v-for="(item,index) in recommendlist.slice(0, 5)" :key="index">
        <router-link :to="{path:item.link,query:{index:item.id}}">
          <el-card shadow="hover">
            <div class="imgblock">
              <el-image
                  style="width: 100%; height: 100%"
                  :src="$baseUrl+`/download/${item.mainimg}`"
                  :fit='cover'></el-image>
            </div>
            <div>
              <p class="itemname">{{item.name}}</p>
              <p class="itemprice">￥{{item.price}}</p>
            </div>
          </el-card>
        </router-link>
      </div>
    </div>
  </div>

  <div class="background">
    <p class="toptext1">热销商品</p>
    <div class="line">
      <router-link to="/product" class="fontlink">
        <el-link :underline="false">查看全部<el-icon><ArrowRight /></el-icon></el-link>
      </router-link>
    </div>
    <div v-if="loading2" class="product-container">
      <!-- Placeholder content -->
      <div class="tupian" v-for="i in 10" :key="i">
        <el-card shadow="hover" v-loading="true" element-loading-text="正在加载...">
        </el-card>
      </div>
    </div>
    <div v-else class="product-container">
      <div class="tupian" v-for="(item,index) in goodlist.slice(0, 10)" :key="index">
        <router-link :to="{path:item.link,query:{index:item.id}}">
          <el-card shadow="hover">
            <div class="imgblock">
              <el-image
                  style="width: 100%; height: 100%"
                  :src="$baseUrl+`/download/${item.mainimg}`"
                  :fit='cover'></el-image>
            </div>
            <div>
              <p class="itemname">{{item.name}}</p>
              <p class="itemprice">￥{{item.price}}</p>
            </div>
          </el-card>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref,onMounted} from 'vue';
import request from "@/utility/request";

const goodlist=ref([])
const recommendlist=ref([])
const loading1=ref(true)
const loading2=ref(true)

onMounted(()=>{

  request.post("/showrecommend",
      {"username": JSON.parse(localStorage.getItem("username"))}
  ).then(result=>{
    result.data.forEach(item=>{
      item.link="/detail"
      recommendlist.value.push(item)
    })
    loading1.value=false
  })

  request.post("/goodsshow").then(result=>{
    result.data.forEach(item=>{
      item.link="/detail"
      goodlist.value.push(item)
    })
    loading2.value=false
  })
      .catch(error=>{
        console.log(error)
      })
})
</script>

<style scoped>
.toptext{
  user-select: none;
  font-size: 30px;
  margin-bottom: 50px;
  margin-top: 60px;
}

.toptext1{
  user-select: none;
  font-size: 30px;
  margin-bottom: 10px;
  margin-top: 60px;
}

.line {
  display: flex;
  align-items: center;
  height: 30px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.fontlink{
  margin-left: auto;
  margin-right: 2%;
}

.product-container{
  display: flex;
  flex-wrap: wrap; /* 允许子元素换行 */
}

a{
  text-decoration: none;
}

.tupian{
  width: 16%;
  margin-left: 2%;
  margin-right: 2%;
  margin-bottom: 30px;
}

.el-card{
  height: 320px;
}

.el-card ::v-deep .el-card__body {
  padding: 0;
}

.imgblock{
  height: 250px;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持宽高比并填充容器 */
}

.itemname{
  margin-top: 10px;
  margin-bottom: 5px;
}

.itemprice{
  margin-top: 5px;
  color: #ff5000;
}
</style>