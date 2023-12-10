<template>
  <div class="background">
    <div class="line">
      <p>商品</p>
      <router-link to="/product" class="fontlink">
        <el-link :underline="false">查看全部 <i class="el-icon-right"></i></el-link>
      </router-link>
    </div>
    <div class="product-container">
      <div class="tupian" v-for="(item,index) in goodlist.slice(0, 10)" :key="index">
        <router-link :to="{path:item.link,query:{index:item.id}}">
          <el-card shadow="always">
            <div class="imgblock">
              <el-image
                  style="width: 100%; height: 100%"
                  :src="$baseUrl+`/download/${item.mainimg}`"
                  :fit="cover"></el-image>
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

<script>
import request from "@/utility/request";

export default {
  data() {
    return {
      goodlist:[],
    }
  },
  mounted() {
    request.post("/goodsshow").then(result=>{
      result.data.forEach(item=>{
        //item.mainimg=require("@/assets/product1.jpg")
        item.link="/detail"
        this.goodlist.push(item)
      })
    })
        .catch(error=>{
          console.log(error)
        })
  }
}
</script>

<style scoped>
.background{

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