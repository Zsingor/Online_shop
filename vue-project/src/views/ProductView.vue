<template>
  <div class="background">
    <div class="topfont">
        <p style="font-size: 30px">商品</p>
    </div>
    <el-row>
      <el-col class="custom-col" v-for="(item, index) in updateDataShow" :key="index" :span="6">
        <router-link :to="{path:item.link,query:{index:item.id}}">
          <el-card shadow="always">
            <div class="imgblock">
              <el-image
                  style="width: 100%; height: 100%"
                  :src="$baseUrl+`/download/${item.mainimg}`"
                  :fit="cover"></el-image>
            </div>
            <div class="textblock">
              <p class="itemname">{{item.name}}</p>
              <p class="itemprice">{{item.price}}</p>
            </div>
          </el-card>
        </router-link>
      </el-col>
    </el-row>

    <!-- 分页 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pagesize"
        layout="prev, pager, next, jumper"
        :total="tabletotal">
    </el-pagination>
  </div>
</template>

<script>
import request from "@/utility/request";

export default {
  data() {
    return {
      currentPage: 1,
      pagesize: 8,
      //总商品数
      tabletotal:0,
      goodlist:[],
    };
  },
  computed:{
    updateDataShow() {
      // 分页开始的项目索引
      let startPage = (this.currentPage - 1) * this.pagesize
      // 分页结束的项目索引
      let endPage = startPage + this.pagesize
      return  this.goodlist.slice(startPage, endPage)
    }
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
    }
  },
  mounted() {
    request.post("/goodsshow").then(result=>{
      result.data.forEach(item=>{
        //item.mainimg=require("@/assets/product1.jpg")
        item.link="/detail"
        this.goodlist.push(item)
      })
      this.tabletotal=this.goodlist.length
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

.topfont{
  margin-top: 40px;
}

.el-row {
  margin-bottom: 20px;
  height: 900px;
  &:last-child {
    margin-bottom: 0;
  }
}

.custom-col {
  /* 调整每个图片显示区域的样式 */
    width: 21%;
    margin-left: 2%;
    margin-right: 2%;
    height: 400px;
    margin-top: 40px;
}

.el-pagination{
  margin-bottom: 20px;
}

a{
  text-decoration: none;
}

.el-card{
  height: 400px;
}

.el-card ::v-deep .el-card__body {
  padding: 0;
}

.imgblock{
  height: 320px;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持宽高比并填充容器 */
}

.textblock{
  margin-top: 0;
}

.itemname{
  margin-top: 15px;
  margin-bottom: 5px;
}

.itemprice{
  margin-top: 5px;
  margin-bottom: 0;
  color: #ff5000;
}
</style>