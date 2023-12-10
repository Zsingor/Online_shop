<template>
  <div class="background">
    <el-row>
      <el-col class="col-left" :span="12">
        <div class="carousel">
          <el-carousel trigger="click" height="500px">
            <el-carousel-item v-for="(item,index) in goods.carouselImg" :key="index">
              <el-image
                  style="width: 100%; height: 100%"
                  :src="item.img"
                  :fit=cover></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col class="col-right" :span="12">
        <h1 class="goodsname">{{ good.name }}</h1>
        <h3 class="goodsname">{{ good.description }}</h3>
        <p class="goodsprice">￥{{ good.price }}</p>
<!--        <div>-->
<!--          <div style="margin-top: 20px" v-for="(skuKey, index) in skuKeys" :key="index">-->
<!--            <p class="goodskey">{{ skuKey }} :</p>-->
<!--            <el-radio-group v-model="skuKeys[index]" size="medium">-->
<!--              <el-radio-button v-for="key in skuObj[skuKey]" :label="key" :key="key">{{ key }}</el-radio-button>-->
<!--            </el-radio-group>-->
<!--          </div>-->
<!--        </div>-->
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
            :src="goods.detailImg"
            :fit=cover></el-image>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utility/request";

export default {
  data() {
    return {
      good:{},
      goods: {
        id: 1001,
        name: "LightAir负氧离子发生器空气净化器除醛霾二手烟杀菌",
        description: "",
        detailImg:require("@/assets/detail1.jpg"),
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
            img: require("@/assets/product2.jpg")
          }
        ]
      },
      skuKeys: []
    }
  },
  methods:{
    //实现添加购物车功能
    addcart(){
      var product={
        goodsid:0,
        img:"",
        name:"",
        price: "",
        number:1
      };
      product["goodsid"]=this.good.id
      product["img"]=this.goods.carouselImg[0].img;
      product["name"]=this.good.name;
      product["price"]=this.good.price;
      console.log(product)
      var goodlist=JSON.parse(localStorage.getItem("goods"))
      if(goodlist)
      {
        // 使用 findIndex 方法查找相同 goodsid 的元素
        var index = goodlist.findIndex(item => item.goodsid === product.goodsid);

        if (index !== -1)
        {
          this.$message({
            showClose: true,
            message: "商品已在购物车中，无需添加",
            type: "success"
          });
          return
        }
        goodlist.push(product)
      }
      else
      {
        goodlist=[];
        goodlist.push(product)
        console.log(goodlist)
      }
      localStorage.setItem("goods",JSON.stringify(goodlist))
      this.$message({
        showClose: true,
        message: '添加购物车成功',
        type: 'success'
      })
    },
    buynow()
    {
      var product={
        goodsid:0,
        img:"",
        name:"",
        price: "",
        number:1
      };
      product["goodsid"]=this.good.id
      product["img"]=this.goods.carouselImg[0].img;
      product["name"]=this.good.name;
      product["price"]=this.good.price;
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
        console.log(goodlist)
      }
      localStorage.setItem("goods",JSON.stringify(goodlist))
      this.$router.push("/shopcart")
    },
    handleSku(){
      this.goods.skus.forEach(item => {
        const obj = JSON.parse(item.sku);
        for (let key in obj)
        {
          if (this.skuKeys.indexOf(key)  === -1)
          {
            this.skuKeys.push(key);
          }
        }
      });
      console.log(this.skuKeys)
    }
  },
  computed: {
    skuObj(){
      let skuObj = {};
      this.skuKeys.forEach(item => {
        skuObj[item] = [];
      })
      this.goods.skus.forEach(item => {
        const obj = JSON.parse(item.sku);  // 获取sku并转为对象
        this.skuKeys.forEach(item => {
          if (skuObj[item].indexOf(obj[item]) === -1){
            skuObj[item].push(obj[item]);
          }
        })
      });
      return skuObj;
    },
  },
  mounted() {
    //this.handleSku()
    request.post("/goodsselect",{
      "id":this.$route.query.index
    }).then(result=>{
      if(result.code===1)
      {
        this.good=result.data
        this.goods.carouselImg[0]["img"]=this.$baseUrl+`/download/${this.good.mainimg}`
      }
      else {
        this.$message.error("用户未登录")
        this.$router.push("/login")
        localStorage.removeItem("username")
        localStorage.removeItem("usertoken")
        localStorage.removeItem("goods")
      }
    })
        .catch(error=>{
          console.log(error)
        })
  }
}
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