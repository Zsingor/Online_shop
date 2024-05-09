<template>
  <div class="background">
    <div class="topfont">
      <p style="font-size: 30px">商品</p>
    </div>

    <div v-if="loading">
      <el-row class="rowdiv">
        <el-col class="custom-col" v-for="i in 12" :key="i" :span="5">
          <el-card shadow="hover" v-loading="true" element-loading-text="正在加载...">
          </el-card>
        </el-col>
      </el-row>
      <div style="margin-bottom:80px"/>
    </div>

    <div v-else>
      <el-row class="rowdiv">
        <el-col class="custom-col" v-for="(item, index) in updateDataShow()" :key="index" :span="5">
          <router-link :to="{path:item.link,query:{index:item.id}}">
            <el-card shadow="hover">
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
          @current-change="handleCurrentChange"
          :page-size="pagesize"
          :pager-count="currentPage"
          layout="prev, pager, next"
          :total="tabletotal"
      />
    </div>

  </div>
</template>

<script setup>
import request from "@/utility/request";
import {ref,onMounted} from 'vue';

var loading=ref(true)
var currentPage=ref(1)
var pagesize=ref(12)
var tabletotal=ref(0)
var goodlist=ref([])

var updateDataShow=()=>{
  // 分页开始的项目索引
  let startPage = (currentPage.value - 1) * pagesize.value
  // 分页结束的项目索引
  let endPage = startPage + pagesize.value
  return  goodlist.value.slice(startPage, endPage)
}

// 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
var handleCurrentChange=(val)=>{
  currentPage.value = val
}

onMounted(()=>{
  request.post("/goodsshow").then(result=>{
    result.data.forEach(item=>{
      item.link="/detail"
      goodlist.value.push(item)
    })
    loading.value=false
    tabletotal.value=goodlist.value.length
  })
      .catch(error=>{
        console.log(error)
      })
})

</script>

<style scoped>
.background {
  padding-right: 10%;
  padding-left: 10%;
}

.topfont{
  margin-top: 40px;
}

.rowdiv {
  margin-bottom: 40px;
  min-height: 900px;
  &:last-child {
    margin-bottom: 0;
  }
}

.custom-col {
  /* 调整每个图片显示区域的样式 */
  //width: 15%;
  margin-left: 2%;
  margin-right: 2%;
  height: 400px;
  margin-top: 40px;
}

.el-pagination{
  margin-bottom: 30px;
  justify-content: center;
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