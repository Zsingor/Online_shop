<template>
  <div class="cart">
    <p class="toptext">信息管理</p>
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
      <el-table-column prop="id" label="销售员ID"  align="center"></el-table-column>
      <el-table-column prop="password" label="密码"  align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="300">
        <template #default="scope">
          <el-button size="default" type="primary" @click="handlechange(scope.row)">重置密码</el-button>
          <el-button size="default" type="danger" @click="handleDelete(scope.row)">删除账号</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="group_btn">
      <el-button type="success" @click="addsales">添加信息</el-button>
    </div>

    <!-- 分页 -->
    <el-pagination
        @current-change="handleCurrentChange"
        :page-size="pagesize"
        :pager-count="currentPage"
        layout="prev, pager, next"
        :total="tabletotal"
    />

    <!-- 添加新增的内容 -->
    <el-dialog title="添加信息" v-model="addIsDialog" class="addDialog">
      <el-form
          :model="addData"
          label-width="auto"
          :rules="rules"
          ref="addRef"
          :label-position="'left'">
        <el-form-item label="销售员ID :" prop="id">
          <el-input v-model="addData.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码 :" prop="password">
          <el-input v-model="addData.password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCancleBtn">取 消</el-button>
        <el-button type="primary" @click="addSureBtn">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {ElMessage,ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";
import {Search} from "@element-plus/icons-vue";

const router = useRouter()
const editRef=ref(null)
const addRef=ref(null)
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
//编辑弹窗
var editIsDialog=ref(false)
//添加弹窗
var addIsDialog=ref(false)
//存放要修改的数据
var editData=ref({})
//存放要修改的数据副本
var editTemp=ref({})
//存放要添加的数据
var addData=ref({
  id: "",
  password: ""
})
//表格中的数据
var tableData=ref([])
//显示的数据
var tableShow=ref([])

//销售员id校验
var checkId = (rule, value, callback) => {
  const regId = /^[0-9]*$/;
  if (!regId.test(value)) {
    callback(new Error('请输入正确的ID格式'));
  }
  else if (value.length<6 || value.length>10) {
    callback(new Error('ID长度为6-10位'));
  }
  else {
    callback();
  }
};
//销售员密码校验
var checkPassword = (rule, value, callback) => {
  const regPwd=/^[A-Za-z0-9`~!@#$%^&*()_+<>?:"{},.\/;'[\]]+$/
  if(!regPwd.test(value))
  {
    callback(new Error('密码仅能由数字/字母/英文特殊字符组成'));
  }
  else if (value.length<6 || value.length>15) {
    callback(new Error('密码长度为6-15位'));
  } else {
    callback();
  }
};
var rules=ref({
  id: [
    {required: true, message: "请输入销售员ID", trigger: "blur"},
    {validator: checkId, trigger: 'blur'}
  ],
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {validator: checkPassword, trigger: 'blur'}
  ]
})

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
    return (!search.value || data.id.toString().toLowerCase().includes(search.value.toLowerCase()))
  })
  tabletotal.value=tableShow.value.length
}

const cancel=()=>{
  search.value=""
  tableShow.value = tableData.value.slice()
  tabletotal.value = tableShow.value.length
}

const addsales=()=>{
  addIsDialog.value = true
}

const addCancleBtn=()=>{
  addIsDialog.value = false
  //初始化添加表单
  addData.value = {
    id: "",
    password: ""
  }
}

//添加销售人员账号
const addSureBtn=()=>{
  addRef.value.validate((valid) => {
    // 验证通过
    if (valid) {
      addIsDialog.value = false;
      request.post("/salesregister", {
        "id": addData.value["id"],
        "password": addData.value["password"],
      }).then(result => {
        console.log(result)
        if (result.code === 1) {
          tableData.value.push(addData.value);
          if(!search.value)
          {
            tableShow.value.push(addData.value);
          }
          tabletotal.value += 1
          ElMessage.success("添加成功")
          //初始化添加表单
          addData.value = {
            id: "",
            password: ""
          }
        } else {
          ElMessage.error("商品编号已存在")
        }
      })
          .catch(error => {
            ElMessage.error("添加失败")
            console.log(error)
            //初始化添加表单
            addData.value = {
              id: "",
              password: ""
            }
          })
    }
  });
}

// 重置销售人员的密码
const handlechange=(row)=>{
  const index = tableData.value.findIndex(item => item.id === row["id"]);
  const index2 = tableShow.value.findIndex(item => item.id === row["id"]);
  // 弹出消息提示
  ElMessageBox.confirm('此操作将会重置该销售员的密码, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    //重置密码
    request.post("/saleschange", {
      "id": row["id"]
    }).then(result => {
      if (result.code === 1) {
        ElMessage.success("重置成功")
        tableData.value[index]["password"]="12345678";
        tableData.value[index2]["password"]="12345678";
      } else {
        ElMessage.error("重置失败")
      }
    })
        .catch(error => {
          ElMessage.error("重置失败")
          console.log(error)
        })
  }).catch(() => {

  });
}

//删除销售人员的信息
const handleDelete=(row)=>{
  // 弹出消息提示
  ElMessageBox.confirm('此操作将永久删除该销售员信息, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    request.post("/salesdelete", {
      "id": row["id"]
    }).then(result => {
      if (result.code === 1) {
        ElMessage.success("删除成功")
        tableData.value = tableData.value.filter(data => data.id !== row["id"]);
        tableShow.value = tableShow.value.filter(data => data.id !== row["id"]);
        tabletotal.value -= 1
      } else {
        ElMessage.error("删除失败")
      }
    })
        .catch(error => {
          ElMessage.error("删除失败")
          console.log(error)
        })
  }).catch(() => {

  });
}

const gettablemsg=()=>{
  request.post("/salessshow").then(result => {
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
</style>