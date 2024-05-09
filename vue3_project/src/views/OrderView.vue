<template>
  <div class="background">
    <div class="maintable">
      <el-table
          :data="updateDataShow()"
          border
          style="width: 100%"
          :row-style="{height: '100px'}"
          :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
      >
        <el-table-column prop="username" label="购买用户" width="180" align="center"></el-table-column>
        <el-table-column prop="goodsname" label="商品名称" width="180" align="center"></el-table-column>
        <el-table-column prop="number" label="购买数量" width="120" align="center"></el-table-column>
        <el-table-column prop="buytime" label="购买时间" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template #default="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <el-pagination
        @current-change="handleCurrentChange"
        :pager-count="currentPage"
        :page-size="pagesize"
        layout="prev, pager, next"
        :total="tabletotal">
    </el-pagination>
  </div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {ElMessage,ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";

const router = useRouter()
//当前页
var currentPage=ref(1)
//显示的订单数
var pagesize=ref(10)
//总订单数
var tabletotal=ref(0)
//搜索框的内容
var search=ref("")
//表格中的数据
var tableData=ref([])
//存储原始的时间戳
var tableTime=ref([])

//删除订单的信息
const handleDelete=(index)=>{
  index=index+(currentPage.value - 1) * pagesize.value
  // 弹出消息提示
  ElMessageBox.confirm(
      '此操作将永久删除该订单信息, 是否继续?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    //删除商品
    request.post("/deleteorder", {
      "username": tableData.value[index]["username"],
      "goodsid": tableData.value[index]["goodsid"],
      "buytime": tableTime.value[index],
    }).then(result => {
      if (result.code === 1) {
        ElMessage.success("删除成功")
        tabletotal.value-=1
        tableData.value.splice(index, 1); //删除表格的数据
      } else {
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

onMounted(()=>{
  request.post("/selectuserorder",{
    "username":JSON.parse(localStorage.getItem("username"))
  }).then(result => {
    if(result.code===1)
    {
      result.data.forEach(item => {
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
      tabletotal.value=tableData.value.length
    }
    else {
      ElMessage.error("用户未登录")
      router.push("/login")
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
      localStorage.removeItem("goods")
    }
  })
      .catch(error => {
        console.log(error)
      })
})
</script>

<style scoped>

.maintable {
  margin-top: 30px;
  padding-right: 10%;
  padding-left: 10%;
}

.el-pagination{
  justify-content: center;
  margin-bottom: 30px;
  margin-top: 30px;
}
</style>