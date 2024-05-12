<template>
  <div>
    <p class="toptext">订单信息</p>
    <div class="searchinputdiv">
      <el-input class="searchinput" v-model="search" placeholder="请输入关键字搜索" size="large">
        <template #prepend>
          <el-select v-model="searchflag" style="width: 110px" placeholder="请选择" size="large">
            <el-option label="用户名" value="0"></el-option>
            <el-option label="商品编号" value="1"></el-option>
          </el-select>
        </template>
      </el-input>
      <el-button class="searchbtn" type="primary" @click="select">查询</el-button>
      <el-button class="searchbtn" type="danger" @click="cancel">取消</el-button>
    </div>
    <div class="tablediv">
      <el-table
          ref="tableRef"
          :data="updateDataShow()"
          border
          style="width: 100%"
          :row-style="{height: '100px'}"
          :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
      >
        <el-table-column prop="username" label="购买用户" width="180" align="center"></el-table-column>
        <el-table-column prop="goodsid" label="商品编号" width="180" align="center"></el-table-column>
        <el-table-column prop="number" label="购买数量" width="120" align="center"></el-table-column>
        <el-table-column prop="buytime" label="购买时间" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template #default="scope">
            <el-button size="small" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <el-pagination
        @current-change="handleCurrentChange"
        :page-size="pagesize"
        :pager-count="currentPage"
        layout="prev, pager, next"
        :total="tabletotal"
    />
  </div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {ElMessage,ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";
import {Search} from "@element-plus/icons-vue";

const router = useRouter()
const tableRef=ref(null)

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
//存储原始的时间戳
var tableTime=ref([])
//搜索的类别
var searchflag=ref("0");

// 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
const handleCurrentChange=(val)=>{
  currentPage.value = val
}

//删除订单的信息
const handleDelete=(index)=> {
  index=index+(currentPage.value - 1) * pagesize.value
  const name=tableData.value[index]["username"]
  const id=tableData.value[index]["goodsid"]
  // 弹出消息提示
  ElMessageBox.confirm('此操作将永久删除该订单, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    //删除商品
    request.post("/deleteorder", {
      "username": tableData.value[index]["username"],
      "goodsid": tableData.value[index]["goodsid"],
      "buytime": tableTime.value[index],
    }).then(result => {
      if(result.code===1)
      {
        ElMessage.success("删除成功")
        tableData.value = tableData.value.filter(data => !(data.username === name && data.goodsid===id));
        tableShow.value = tableShow.value.filter(data => !(data.username === name && data.goodsid===id));
        tabletotal.value-=1
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

const selectsalesorder=()=>{
  request.post("/selectsalesorder",{
    "id":JSON.parse(localStorage.getItem("username"))
  }).then(result=>{
    if(result.code===1)
    {
      result.data.forEach(item=>{
        tableTime.value.push(item.buytime)
        // 原始的 UTC 时间戳
        const utcTimestamp = item.buytime;
        // 创建 Date 对象并将 UTC 时间戳传入
        const utcDate = new Date(utcTimestamp);
        // 获取本地时间
        const localDate = new Date(utcDate.getTime());
        // 获取本地时间的字符串表示
        item.buytime = localDate.toLocaleString();
        tableData.value.push(item)
      })
      tableShow.value = tableData.value.slice()
      tabletotal.value = tableShow.value.length
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
}

const select=()=>{
  tableRef.value.clearFilter();
  if(searchflag.value==="0")
  {
    tableShow.value=tableData.value.filter(data => {
      return (!search.value || data.username.toString().toLowerCase().includes(search.value.toLowerCase()))
    })
  }
  else
  {
    tableShow.value=tableData.value.filter(data => {
      return (!search.value || data.goodsid.toString().toLowerCase().includes(search.value.toLowerCase()))
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
  return  tableShow.value.slice(startPage, endPage)
}

onMounted(()=>{
  selectsalesorder()
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


.el-pagination{
  justify-content: center;
  margin-top: 20px;
}
</style>