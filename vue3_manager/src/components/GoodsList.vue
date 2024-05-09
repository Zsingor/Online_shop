<template>
  <div class="cart">
    <p class="toptext">商品信息</p>
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
      <el-table-column prop="price" label="价格" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button size="small" type="primary" @click="handlechange(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="group_btn">
      <el-button type="success" @click="addgoods">添加商品</el-button>
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
      <el-form :label-position="'left'" :model="addData" label-width="auto" :rules="rules" ref="addRef">
        <el-form-item label="商品编号" prop="id">
          <el-input v-model="addData.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="addData.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="addData.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="addData.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="addData.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="销售员ID" prop="salesid">
          <el-input v-model="addData.salesid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <div>
            <el-upload
                :action="$baseUrl+'/upload'"
                :headers="{token:managertoken}"
                :on-success="handleAddSuccess"
                :before-upload="beforeUpload"
                list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>
            </el-upload>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addIsDialog = false">取 消</el-button>
        <el-button type="primary" @click="addSureBtn">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加编辑的内容 -->
    <el-dialog title="修改信息" v-model="editIsDialog" class="editDialog">
      <el-form :model="editTemp" :rules="rules" ref="editRef">
        <el-form-item label="名称" prop="name" :label-width="formLabelWidth">
          <el-input v-model="editTemp.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description" :label-width="formLabelWidth">
          <el-input v-model="editTemp.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price" :label-width="formLabelWidth">
          <el-input v-model="editTemp.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" :label-width="formLabelWidth">
          <div>
            <el-upload
                :action="$baseUrl+'/upload'"
                :headers="{token:managertoken}"
                :on-success="handleEditSuccess"
                :before-upload="beforeUpload"
                list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>
            </el-upload>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editCancleBtn">取 消</el-button>
        <el-button type="primary" @click="editSureBtn">确 定</el-button>
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
//搜索的类别
var searchflag=ref("0");
//搜索框的内容
var search=ref("")
//编辑弹窗
var editIsDialog=ref(false)
//添加弹窗
var addIsDialog=ref(false)
//存放要修改的数据
var editTemp=ref({})
//存放要添加的数据
var addData=ref({
  id: "",
  name: "",
  description: "",
  price: "",
  mainimg: "",
  type:"",
  salesid:""
})
//表格中的数据
var tableData=ref([])
//显示的数据
var tableShow=ref([])
//商品id校验
var checkId = (rule, value, callback) => {
  const regId = /^[0-9]*$/;
  if (!regId.test(value)) {
    callback(new Error('请输入正确的ID格式'));
  } else {
    callback();
  }
};
//价格校验
var checkPrice = (rule, value, callback) => {
  const regId = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
  if (!regId.test(value)) {
    callback(new Error('请输入正确的价格'));
  } else {
    callback();
  }
};
//类型校验
var checkType = (rule, value, callback) => {
  const regId = /[\u4e00-\u9fa5]/;
  if (!regId.test(value)) {
    callback(new Error('请输入正确的类型'));
  } else {
    callback();
  }
};
//销售员id校验
var checkSalesid = (rule, value, callback) => {
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
var rules=ref({
  id: [
    {required: true, message: "请输入商品ID", trigger: "blur"},
    {validator: checkId, trigger: 'blur'}
  ],
  name: [
    {required: true, message: "请输入商品名称", trigger: "blur"}
  ],
  description: [
    {required: true, message: "请输入商品描述", trigger: "blur"}
  ],
  price: [
    {required: true, message: "请输入商品价格", trigger: "blur"},
    {validator: checkPrice, trigger: 'blur'}
  ],
  type: [
    {required: true, message: "请输入商品类型", trigger: "blur"},
    {validator: checkType, trigger: 'blur'}
  ],
  salesid: [
    {required: true, message: "请输入销售员ID", trigger: "blur"},
    {validator: checkSalesid, trigger: 'blur'}
  ]
})

//删除商品列表中的商品
const handleDelete=(row)=>{
  // 弹出消息提示
  ElMessageBox.confirm('此操作将永久删除该商品, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    //删除商品的图片文件
    request.post(`/deletefile/${row.mainimg}`).then(result => {
      console.log(result)
    })
        .catch(error => {
          console.log(error)
        })
    //删除商品
    request.post("/goodsdelete", {
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

// 点击弹出添加弹窗
const handlechange=(row)=>{
  editIsDialog.value = true;
  editTemp.value = { ...row }
}

// 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
const handleCurrentChange=(val)=>{
  currentPage.value = val
}

const editCancleBtn=()=>{
  editIsDialog.value = false
}

//编辑商品
const editSureBtn=()=>{
  editRef.value.validate((valid) => {
    // 验证通过
    if (valid) {
      editIsDialog.value = false
      const index = tableData.value.findIndex(item => item.id === editTemp.value["id"]);
      const index2 = tableShow.value.findIndex(item => item.id === editTemp.value["id"]);
      request.post("/goodschange", {
        "id": editTemp.value["id"],
        "name": editTemp.value["name"],
        "description": editTemp.value["description"],
        "price": editTemp.value["price"],
        "mainimg": editTemp.value["mainimg"]
      }).then(result => {
        if (result.code === 1) {
          tableData.value[index]=editTemp.value;
          tableShow.value[index2]=editTemp.value;
          ElMessage.success("修改成功")
          editTemp.value = {}
        } else {
          ElMessage.error("修改失败")
        }
      })
          .catch(error => {
            ElMessage.error("修改失败")
            console.log(error)
          })
    }
  });
}

const addgoods=()=>{
  addIsDialog.value = true
}

//添加商品
const addSureBtn=()=>{
  addRef.value.validate((valid) => {
    // 验证通过
    if (valid) {
      addIsDialog.value = false;
      request.post("/goodsadd", {
        "id": addData.value["id"],
        "name": addData.value["name"],
        "description": addData.value["description"],
        "price": addData.value["price"],
        "mainimg": addData.value["mainimg"],
        "type":addData.value["type"],
        "salesid":addData.value["salesid"]
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
            id: 0,
            name: "",
            description: "",
            price: 0,
            mainimg: "",
            type:"",
            salesid:0
          }
        } else {
          ElMessage.error(result.msg)
        }
      })
          .catch(error => {
            ElMessage.error("添加失败")
            console.log(error)
            //初始化添加表单
            addData.value = {
              id: 0,
              name: "",
              description: "",
              price: 0,
              mainimg: "",
              type:"",
              salesid:0
            }
          })
    }
  });
}

const handleAddSuccess=(res)=>{
  addData.value.mainimg = res.data;
}

const beforeUpload=(file)=>{
  const isJPG = file.type === 'image/jpeg';
  const isPNG = file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG && !isPNG) {
    ElMessage.error('上传图片只能是 JPG或PNG 格式!');
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 10MB!');
  }
  return (isJPG || isPNG) && isLt2M;
}

const handleEditSuccess=(res)=>{
  editTemp.value.mainimg = res.data;
}

const gettablemsg=()=>{
  request.post("/goodsshow").then(result => {
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
</style>
