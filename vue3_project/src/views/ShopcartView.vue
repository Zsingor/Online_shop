<template>
  <div class="cart">
    <el-table
        :data="tableData"
        empty-text="购物车为空"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
        :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
    >
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column type="index" label="序号" width="80" align="center"></el-table-column>
      <el-table-column prop="img" label="图片" align="center">
        <template #default="scope">
          <img :src="$baseUrl+`/download/${scope.row.img}`" height="200"/>
        </template>
      </el-table-column>
      <el-table-column property="name" label="类目" width="180" align="center"></el-table-column>
      <el-table-column property="price" label="价格" align="center"></el-table-column>
      <el-table-column label="数量" align="center">
        <template #default="scope">
          <el-input-number v-model="scope.row.number" :min="1" :max="99" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button size="small" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="total_warpper">
      <h3>共计：{{ totalPrice() }}</h3>
      <h3>总数：{{ totalNumber() }}</h3>
    </div>
    <div class="group_btn">
      <el-button @click="returnback">返回上级</el-button>
      <el-button type="success" @click="emitorder">提交订单</el-button>
    </div>
    <!-- 添加弹窗的内容 -->
    <el-dialog title="您确定要结算吗" v-model="emitIsDialog" class="emitDialog" width="30%">
      <h3>总价为：{{ totalPrice() }}</h3>
      <div slot="footer" class="dialog-footer">
        <el-button @click="emitIsDialog = false">取 消</el-button>
        <el-button type="primary" @click="emitSureBtn">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {ElMessage} from "element-plus";
import { useRouter } from 'vue-router'
import request from "@/utility/request";

const router = useRouter()
//结算弹窗
var emitIsDialog=ref(false)
//存放已选择的数据
var multipleSelection=ref([])
//表格中的数据
var tableData=ref([])

// 点击发生的变化
const handleSelectionChange=(val)=> {
  multipleSelection.value = val; //给定义的数组赋值
}

//删除购物车中的商品
const handleDelete=(index)=> {
  tableData.value.splice(index, 1); //删除表格的数据
  localStorage.removeItem("goods")
  localStorage.setItem("goods", JSON.stringify(tableData.value))
}

//返回上级页面
const returnback=()=>{
  router.go(-1);
}

//结算
const emitorder=()=>{
  if(multipleSelection.value.length!==0)
  {
    emitIsDialog.value=true
  }
  else
  {
    ElMessage.error("未选择商品")
  }
}

const emitSureBtn=()=>{
  var goodList=[]
  console.log(22222,multipleSelection.value)
  multipleSelection.value.forEach(item=>{
    var goodsmessage = {
      username: JSON.parse(localStorage.getItem("username")),
      goodsname: item.name,
      goodsid: item.goodsid,
      number: item.number
    };
    goodList.push(goodsmessage)
  })
  request.post("/billing",JSON.stringify(goodList)).then(result=>
  {
    // 使用 filter 方法来删除 data 中与 sub 中相同的部分
    tableData.value = tableData.value.filter(item => {
      // 返回在 sub 中不存在的项
      return !multipleSelection.value.some(subItem => {
        return subItem.goodsid === item.goodsid;
      });
    });
    emitIsDialog.value=false
    multipleSelection.value.splice(0)
    localStorage.removeItem("goods")
    if(tableData.value.length!==0)
    {
      localStorage.setItem("goods", JSON.stringify(tableData.value))
    }
    ElMessage({
      message: '购买成功',
      type: 'success',
    })
    console.log(result)
  })
      .catch(error=>{
        ElMessage.error("购买失败")
        console.log(error)
      })
  request.post("/sendemail",JSON.stringify(goodList)).then(result=>
  {
    console.log(result)
  })
      .catch(error=>{
        ElMessage.error("邮件发送失败，请前往个人订单查看购买信息")
        console.log(error)
      })
}

// 总价
const totalPrice=()=>{
  var price_total = 0;
  for (var i = 0; i < multipleSelection.value.length; i++) {
    price_total +=
        multipleSelection.value[i].price * multipleSelection.value[i].number;
  }
  return price_total;
}

// 总数
const totalNumber=()=>{
  var number_total = 0;
  for (var i = 0; i < multipleSelection.value.length; i++) {
    number_total += multipleSelection.value[i].number;
  }
  return number_total;
}

onMounted(()=>{
  request.post("/checklogin").then(result=>{
    if(result.code===1)
    {
      console.log(result)
    }
    else {
      ElMessage.error("用户未登录")
      router.push("/login")
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
      localStorage.removeItem("goods")
    }
  })
      .catch(error=>{
        console.log(error)
      })
  // 初始化购物车表单数据
  var product = JSON.parse(localStorage.getItem("goods"));
  console.log(11111,product)
  if(product!==null)
  {
    product.forEach(item => {
      tableData.value.push(item);
    });
  }
})

</script>

<style scoped>
.cart {
  margin: 50px auto;
  width: 80%;
}

.group_btn {
  margin-bottom: 30px;
}

.emitDialog .el-input {
  width: 90%;
}

.total_warpper {
  text-align: right;
  margin-top: 30px;
}

h3 {
  margin-top: 10px;
}

.dialog-footer{
  display: flex;
  text-align: center;
  justify-content: center;
}
</style>