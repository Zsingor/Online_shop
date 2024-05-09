<template>
  <div class="cart">
    <p class="toptext">商品销售信息</p>
    <div class="searchinputdiv">
      <el-input class="searchinput" v-model="search" placeholder="请输入关键字搜索" size="large">
        <template #prepend>
          <el-select v-model="searchflag" style="width: 110px" placeholder="请选择" size="large">
            <el-option label="商品编号" value="0"></el-option>
            <el-option label="商品类别" value="1"></el-option>
          </el-select>
        </template>
      </el-input>
      <el-button class="searchbtn" type="primary" @click="select">查询</el-button>
      <el-button class="searchbtn" type="danger" @click="cancel">取消</el-button>
    </div>
    <el-table
        ref="tableRef"
        :data="updateDataShow()"
        border
        style="width: 100%"
        :row-style="{height: '260px'}"
        :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
    >
      <el-table-column prop="id" label="商品编号" width="100" align="center"></el-table-column>
      <el-table-column prop="mainimg" label="图片" align="center">
        <template #default="scope">
          <el-image
              style="width: 150px; height: 200px"
              :src="$baseUrl+`/download/${scope.row.mainimg}`"
              :fit="cover"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="180" align="center"></el-table-column>
      <el-table-column prop="description" label="描述" width="180" align="center"></el-table-column>
      <el-table-column prop="type" label="类别" width="180" align="center"></el-table-column>
      <el-table-column prop="number" sortable label="销量" align="center"></el-table-column>
      <el-table-column prop="price" sortable label="总销售额" align="center"></el-table-column>
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
import {ElMessage,ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";

const echarts = inject('echarts');
const router = useRouter()
const tableRef=ref(null)

var managertoken=ref(JSON.parse(localStorage.getItem("managertoken") || '{}'))
//当前页
var currentPage=ref(1)
//显示的订单数
var pagesize=ref(10)
//总商品数
var tabletotal=ref(0)
//搜索的类别
var searchflag=ref("0");
//搜索框的内容
var search=ref("")
//表格中的数据
var tableData=ref([])
//显示的数据
var tableShow=ref([])
//类别的信息
var typename=ref([])
//类别的销售量
var typenum=ref([])
//类别的销售额
var typeprice=ref([])

// 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
const handleCurrentChange=(val)=>{
  currentPage.value = val
}

const gettablemsg=()=>{
  request.post("/goodssalesearch",
      {
        "id":JSON.parse(localStorage.getItem("username"))
      }).then(result => {
    if (result.code === 1) {
      result.data.forEach(item => {
        tableData.value.push(item)
      })
      tableShow.value = tableData.value.slice()
      tabletotal.value = tableShow.value.length
    }
  })
      .catch(error => {
        console.log(error)
      })
}

const gettype=()=>{
  request.post("/goodssaletype",
      {
        "id":JSON.parse(localStorage.getItem("username"))
      }).then(result => {
    if (result.code === 1) {
      result.data.forEach(item => {
        typename.value.push(item.type)
        typenum.value.push(item.number)
        typeprice.value.push(item.price)
      })
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
      text: '商品种类销售信息'
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
      data: typename.value
    },
    yAxis: {
      type: 'value',
      boundaryGap: [0, 0.01]
    },
    series: [
      {
        name: '销售量',
        type: 'bar',
        data: typenum.value
      },
      {
        name: '销售额',
        type: 'bar',
        data: typeprice.value
      }
    ]
  }
  console.log("你好")
  myChart.setOption(option);
}

const select=()=>{
  tableRef.value.clearFilter();
  if(searchflag.value==="0")
  {
    tableShow.value=tableData.value.filter(data => {
      return (!search.value || data.id.toString().toLowerCase().includes(search.value.toLowerCase()))
    })
  }
  else
  {
    tableShow.value=tableData.value.filter(data => {
      return (!search.value || data.type.toString().toLowerCase().includes(search.value.toLowerCase()))
    })
  }
  tabletotal.value=tableShow.value.length
}

const cancel=()=>{
  search.value=""
  tableShow.value = tableData.value.slice()
  tabletotal.value = tableShow.value.length
}

const updateDataShow=()=>{
  // 分页开始的项目索引
  let startPage = (currentPage.value - 1) * pagesize.value
  // 分页结束的项目索引
  let endPage = startPage + pagesize.value
  return tableShow.value.slice(startPage, endPage)
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
  gettype()
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
  margin-bottom: 30px;
}

.searchinput{
  width: 30%;
}

.searchbtn{
  margin-left: 10px;
}

.group_btn {
  margin-top: 20px;
}

.el-pagination {
  justify-content: center;
  margin-top: 30px;
}

.chartdiv{
  margin-top: 50px;
}
</style>