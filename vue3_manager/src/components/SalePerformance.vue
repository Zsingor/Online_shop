<template>
  <div class="cart">
    <p class="toptext">销售业绩</p>
    <div class="searchinputdiv">
      <el-input class="searchinput" :prefix-icon="Search" v-model="search" placeholder="请输入销售员ID">
      </el-input>
      <el-button class="searchbtn" type="primary" @click="select">查询</el-button>
      <el-button class="searchbtn" type="danger" @click="cancel">取消</el-button>
    </div>
    <el-table
        ref="tableRef"
        :data="updateDataShow()"
        border
        style="width: 100%"
        :row-style="{height: '100px'}"
        :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
    >
      <el-table-column prop="type" label="销售员ID"  align="center"></el-table-column>
      <el-table-column prop="number" sortable label="销售量"  align="center"></el-table-column>
      <el-table-column prop="price" sortable label="销售额"  align="center"></el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        @current-change="handleCurrentChange"
        :page-size="pagesize"
        :pager-count="currentPage"
        layout="prev, pager, next"
        :total="tabletotal"
    />

    <div class="chartdiv">
      <!-- 用于echart引用这个元素 -->
      <div ref="chart" id="myChart" style="width: 100%; height: 400px;"></div>
    </div>
  </div>
</template>

<script setup>
import {ref,onMounted,inject} from 'vue'
import {ElMessage} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";
import {Search} from "@element-plus/icons-vue";

const echarts = inject('echarts');
const router = useRouter()
const tableRef=ref(null)

var managertoken=ref(JSON.parse(localStorage.getItem("usertoken") || '{}'))
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
//显示的数据
var tableShow=ref([])
//销售员ID
var typename=ref([])
//销售员的销售量
var typenum=ref([])
//销售员的销售额
var typeprice=ref([])

const updateDataShow=()=>{
  // 分页开始的项目索引
  let startPage = (currentPage.value - 1) * pagesize.value
  // 分页结束的项目索引
  let endPage = startPage + pagesize.value
  return tableShow.value.slice(startPage, endPage)
}

//查询事件
const select=()=>{
  tableRef.value.clearFilter();
  tableShow.value=tableData.value.filter(data => {
    return (!search.value || data.type.toString().toLowerCase().includes(search.value.toLowerCase()))
  })
  tabletotal.value=tableShow.value.length
}

const cancel=()=>{
  search.value=""
  tableShow.value = tableData.value.slice()
  tabletotal.value = tableShow.value.length
}

const gettablemsg=()=>{
  request.post("/salesorder").then(result => {
    if (result.code === 1) {
      result.data.forEach(item => {
        tableData.value.push(item)
        typename.value.push(item.type)
        typenum.value.push(item.number)
        typeprice.value.push(item.price)
      })
      tableShow.value = tableData.value.slice()
      tabletotal.value = tableShow.value.length
      echartsInit()
    }
  })
      .catch(error => {
        console.log(error)
      })
}

const echartsInit=()=>{
  const dom = document.getElementById('myChart');
  // 基于准备好的dom，初始化echarts实例
  const myChart = echarts.init(dom); // 初始化echarts实例
  console.log(typename)
  console.log(typenum)
  console.log(typeprice)
  var option={
    title: {
      text: '销售员销售情况'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {},
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: typename.value.slice(0,10)
    },
    yAxis: {
      type: 'value',
      boundaryGap: [0, 0.01]
    },
    series: [
      {
        name: '销售量',
        type: 'bar',
        data: typenum.value.slice(0,10)
      },
      {
        name: '销售额',
        type: 'bar',
        data: typeprice.value.slice(0,10)
      }
    ]
  }
  console.log("你好")
  myChart.setOption(option);
}

onMounted(()=>{
  request.post("/checklogin").then(result => {
    if (result.code === 1) {
      console.log(result)
    } else {
      ElMessage.error("用户未登录")
      router.push("/login")
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
    }
  })
      .catch(error => {
        console.log(error)
      })
  gettablemsg()
})
</script>

<style scoped>
.toptext{
  user-select: none;
  font-size: 20px;
  margin-bottom: 30px;
  margin-top: 10px;
}

.searchinputdiv{
  margin-bottom: 20px;
}

.searchinput{
  width: 50%;
}

.searchbtn{
  margin-left: 10px;
}

.group_btn {
  margin-top: 50px;
}

.el-pagination {
  justify-content: center;
  margin-top: 30px;
}

.chartdiv{
  margin-top: 50px;
}
</style>