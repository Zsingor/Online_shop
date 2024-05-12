<template>
  <div class="background">
    <el-row>
      <el-col class="col-left" :span="12">
        <div class="carousel">
          <el-carousel trigger="click" height="500px">
            <el-carousel-item v-for="(item,index) in goods.carouselImg" :key="index">
              <el-image
                  style="width: 100%; height: 100%"
                  :src="$baseUrl+`/download/${item.img}`"
                  :fit=cover></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col class="col-right" :span="12">
        <h1 class="goodsname">{{ good.name }}</h1>
        <h3 class="goodsname">{{ good.description }}</h3>
        <p class="goodsprice">￥{{ good.price }}</p>
        <el-button-group>
          <el-button class="buttonleft" type="primary" @click="addcart">加入购物车</el-button>
          <el-button class="buttonright" type="primary" @click="buynow">立即购买</el-button>
        </el-button-group>
      </el-col>
    </el-row>
    <div>
      <p class="detailtitle">宝贝详情</p>
      <div class="deetailimg">
        <el-image
            style="width: 100%; height: 100%"
            :src=goods.detailImg
            :fit=cover></el-image>
      </div>
    </div>
  </div>
</template>

<script setup>
import request from "@/utility/request";
import {ref,onMounted,inject} from 'vue';
import {ElMessage} from "element-plus";
import { useRouter,onBeforeRouteLeave } from 'vue-router'

const router = useRouter()
const baseUrl = inject('baseUrl');
var good=ref({})
var goods=ref({
  id: 1001,
  name: "LightAir负氧离子发生器空气净化器除醛霾二手烟杀菌",
  description: "",
  detailImg:new URL("@/assets/detail1.jpg", import.meta.url),
  price: "200",
  skus: [
    {
      id: "1001",
      sku: '{"口味": "原味", "包装": "手袋单人份"}'
    },
    {
      id: "1002",
      sku: '{"口味": "奶油", "包装": "离合双人份"}'
    },
    {
      id: "1002",
      sku: '{"口味": "炭烧", "包装": "全家福礼包"}'
    }
  ],
  carouselImg: [
    {
      img: ""
    },
    {
      img: "product2.jpg"
    }
  ]
})

var entrytime=ref("")
var leavetime=ref("")

const getImageUrl = (name) => {
  return new URL(name, import.meta.url).href
}

const addcart=()=>{
  var product={
    goodsid:0,
    img:"",
    name:"",
    price: "",
    number:1
  };
  product["goodsid"]=good.value.id
  product["img"]=goods.value.carouselImg[0].img;
  product["name"]=good.value.name;
  product["price"]=good.value.price;
  var goodlist=JSON.parse(localStorage.getItem("goods"))
  if(goodlist)
  {
    // 使用 findIndex 方法查找相同 goodsid 的元素
    var index = goodlist.findIndex(item => item.goodsid === product.goodsid);

    if (index !== -1)
    {
      ElMessage({
        message: '商品已在购物车中，无需添加',
        type: 'success',
      })
      return
    }
    goodlist.push(product)
  }
  else
  {
    goodlist=[];
    goodlist.push(product)
  }
  localStorage.setItem("goods",JSON.stringify(goodlist))
  ElMessage({
    message: '添加购物车成功',
    type: 'success',
  })
}

const buynow=()=>{
  var product={
    goodsid:0,
    img:"",
    name:"",
    price: "",
    number:1
  };
  product["goodsid"]=good.value.id
  product["img"]=goods.value.carouselImg[0].img;
  product["name"]=good.value.name;
  product["price"]=good.value.price;
  var goodlist=JSON.parse(localStorage.getItem("goods"))
  if(goodlist)
  {
    // 使用 findIndex 方法查找相同 goodsid 的元素
    var index = goodlist.findIndex(item => item.goodsid === product.goodsid);

    if(index===-1)
    {
      goodlist.push(product)
    }
  }
  else
  {
    goodlist=[];
    goodlist.push(product)
  }
  localStorage.setItem("goods",JSON.stringify(goodlist))
  router.push("/shopcart")
}

onMounted(()=>{
  request.post("/goodsselect",{
    "id":router.currentRoute.value.query.index
  }).then(result=>{
    if(result.code===1)
    {
      good.value=result.data
      goods.value.carouselImg[0]["img"]=good.value.mainimg
    }
    else {
      ElMessage.error("用户未登录")
      router.push("/login")
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
      localStorage.removeItem("goods")
    }
  })
      .catch(error=>{
        console.log(error)
      })
  // 进入页面的时间戳
  entrytime.value = Date.now()
})

onBeforeRouteLeave((to, from, next) => {
  // 离开页面的时间戳
  leavetime.value = Date.now()-entrytime.value
  leavetime.value=parseInt(leavetime.value/1000)
  request.post("/addbrowse", {
    "username":JSON.parse(localStorage.getItem("username")),
    "goodsid":good.value.id,
    "time":leavetime.value})
  next();
})
</script>

<style scoped>
.background {
  padding-right: 10%;
  padding-left: 10%;
}

.el-row {
  margin-top: 40px;
  margin-bottom: 40px;
}

.col-left {
  display: flex;
  padding-right: 20px;
  justify-content: center;
}

.carousel {
  width: 380px;
  height: 500px;
}

.col-right {
  padding-right: 30px;
}

.goodsname {
  text-align: left;
}

.goodsprice {
  color: #ff5000;
  font-size: 20px;
  text-align: left;
}

.goodskey{
  width: 15%;
  text-align: left;
}

.el-checkbox-group{
  width: 80%;
  text-align: left;
}

.buttonleft{
  background-color: #ffad01;
  border: 0;
  width: 130px;
}

.buttonright{
  background-color: #ff6000;
  border: 0;
  width: 130px;
}

.detailtitle{
  font-size: 20px;
  margin-bottom: 50px;
  margin-top: 50px;
}

.deetailimg{
  padding-left: 10%;
  padding-right: 10%;
  display: flex;
  justify-content: center;
  text-align: center;
}
</style>