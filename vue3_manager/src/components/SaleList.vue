<template>
  <div class="background">
    <p class="toptext">每日销售</p>
    <div class="tablediv">
      <el-table
          :data="updateDataShow()"
          border
          style="width: 100%"
          :row-style="{height: '100px'}"
          :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
      >
        <el-table-column prop="saledate" label="日期"  align="center"></el-table-column>
        <el-table-column prop="buynum" label="购买次数"  align="center"></el-table-column>
        <el-table-column prop="goodsnum" label="购买商品总数"  align="center"></el-table-column>
        <el-table-column prop="totalprice" label="购买总价" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template #default="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagination">
      <!-- 分页 -->
      <el-pagination
          @current-change="handleCurrentChange"
          :page-size="pagesize"
          :pager-count="currentPage"
          layout="prev, pager, next"
          :total="tabletotal"
      />
    </div>

    <div class="chartdiv">
      <!-- 用于echart引用这个元素 -->
      <div ref="chart" id="myChart" style="width: 100%; height: 400px;"></div>
    </div>

  </div>
</template>

<script setup>
import {ref,onMounted,inject} from 'vue'
import {ElMessage,ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";

const echarts = inject('echarts');
const router=useRouter()
//当前页
var currentPage=ref(1)
//显示的订单数
var pagesize=ref(10)
//总商品数
var tabletotal=ref(0)
//搜索框的内容
var search=ref("")
//表格中的数据
var tableData=ref([])
//存储原始的时间戳
var tableTime=ref([])
var chartInstance=ref(null)
//图表的日期
var chartdate=ref([])
//图表的数量
var chartprice=ref([])

// 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
const handleCurrentChange=(val)=>{
  currentPage.value = val
}

const updateDataShow=()=>{
  // 分页开始的项目索引
  let startPage = (currentPage.value - 1) * pagesize.value
  // 分页结束的项目索引
  let endPage = startPage + pagesize.value
  return  tableData.value.slice(startPage, endPage)
}

//删除订单的信息
const handleDelete=(index)=> {
  index=index+(currentPage.value - 1) * pagesize.value
  // 弹出消息提示
  ElMessageBox.confirm('此操作将永久删除该订单, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    //删除商品
    request.post("/deletesale", {
      "saledate": tableTime.value[index],
    }).then(result => {
      if(result.code===1)
      {
        ElMessage.success("删除成功")
        tabletotal.value-=1
        tableData.value.splice(index, 1); //删除表格的数据
      }
      else {
        ElMessage.error(result.msg)
      }
    })
        .catch(error => {
          ElMessage.error("删除失败")
          console.log(error)
        })
  }).catch(() => {

  });
}

const echartsInit=()=>{
  const dom = document.getElementById('myChart');
  // 基于准备好的dom，初始化echarts实例
  const myChart = echarts.init(dom); // 初始化echarts实例
  const option={
    xAxis: {
      type: 'category',
      data: chartdate.value.slice(0,10).reverse()
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: chartprice.value.slice(0,10).reverse(),
        type: 'line'
      }
    ]
  }
  myChart.setOption(option)
}

onMounted(()=>{
  request.post("/showsale").then(result=>{
    if(result.code===1)
    {
      result.data.forEach(item=>{
        tableTime.value.push(item.saledate)
        // 原始的 UTC 时间戳
        const utcTimestamp = item.saledate;
        // 创建 Date 对象并将 UTC 时间戳传入
        const utcDate = new Date(utcTimestamp);
        // 获取本地时间
        const localDate = new Date(utcDate.getTime());
        // 获取本地时间的字符串表示
        item.saledate = localDate.toLocaleDateString();
        tableData.value.push(item)
        chartdate.value.push(item.saledate)
        chartprice.value.push(item.totalprice)
      })
      tabletotal.value=tableData.value.length
      echartsInit()
    }
    else {
      ElMessage.error("用户未登录")
      router.push("/login")
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
    }
  })
      .catch(error=>{
        console.log(error)
      })
})
</script>

<style scoped>
.toptext{
  user-select: none;
  font-size: 20px;
  margin-bottom: 40px;
  margin-top: 10px;
}

.pagination{
  text-align: center;
  justify-content: center;
  width: 100%;
}

.el-pagination{
  justify-content: center;
  margin-top: 20px;
}
</style>