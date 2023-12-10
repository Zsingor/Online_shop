<template>
  <div class="cart">
    <el-table
        :data="tableData"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
        :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
    >
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column type="index" label="序号" width="80" align="center"></el-table-column>
      <el-table-column prop="img" label="图片" align="center">
        <template slot-scope="scope">
          <img :src="scope.row.img" height="200"/>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="类目" width="180" align="center"></el-table-column>
      <el-table-column prop="price" label="价格" align="center"></el-table-column>
      <el-table-column label="数量" align="center">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.number" :min="1" :max="99" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="total_warpper">
      <h3>共计：{{ totalPrice }}</h3>
      <h3>总数：{{ totalNumber }}</h3>
    </div>
    <div class="group_btn">
      <el-button @click="returnback">返回上级</el-button>
      <el-button type="success" @click="emitorder">提交订单</el-button>
    </div>
    <!-- 添加弹窗的内容 -->
    <el-dialog title="您确定要结算吗" :visible.sync="emitIsDialog" class="emitDialog" width="30%">
      <h3>总价为：{{ totalPrice }}</h3>
      <div slot="footer" class="dialog-footer">
        <el-button @click="emitIsDialog = false">取 消</el-button>
        <el-button type="primary" @click="emitSureBtn">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utility/request";

export default {
  data() {
    return {
      //结算弹窗
      emitIsDialog: false,
      //存放已选择的数据
      multipleSelection: [],
      //表格中的数据
      tableData: []
    };
  },
  methods: {
    // 点击发生的变化
    handleSelectionChange(val) {
      this.multipleSelection = val; //给定义的数组赋值
      console.log(this.multipleSelection)
    },
    //删除购物车中的商品
    handleDelete(index) {
      this.tableData.splice(index, 1); //删除表格的数据
      localStorage.removeItem("goods")
      localStorage.setItem("goods", JSON.stringify(this.tableData))
    },
    //返回上级页面
    returnback() {
      this.$router.go(-1);
    },
    emitorder(){
      if(this.multipleSelection.length!==0)
      {
        this.emitIsDialog=true
      }
      else
      {
        this.$message({
          showClose: true,
          message: '未选择商品',
          type: 'error'
        })
      }
    },
    emitSureBtn(){
      var goodList=[]
      this.multipleSelection.forEach(item=>{
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
        this.tableData = this.tableData.filter(item => {
          // 返回在 sub 中不存在的项
          return !this.multipleSelection.some(subItem => {
            return subItem.goodsid === item.goodsid;
          });
        });
        this.emitIsDialog=false
        this.multipleSelection.splice(0)
        localStorage.removeItem("goods")
        if(this.tableData.length!==0)
        {
          localStorage.setItem("goods", JSON.stringify(this.tableData))
        }
        this.$message.success("购买成功")
        console.log(result)
      })
          .catch(error=>{
            this.$message.error("购买失败")
            console.log(error)
          })
      request.post("/sendemail",JSON.stringify(goodList)).then(result=>
      {
        console.log(result)
      })
          .catch(error=>{
            this.$message.error("邮件发送失败，请前往个人订单查看购买信息")
            console.log(error)
          })
    }
  },
  // 通过computed计算属性及时改变
  computed: {
    // 总价
    totalPrice() {
      var price_total = 0;
      for (var i = 0; i < this.multipleSelection.length; i++) {
        price_total +=
            this.multipleSelection[i].price * this.multipleSelection[i].number;
      }
      return price_total;
    },
    // 总数
    totalNumber() {
      var number_total = 0;
      for (var i = 0; i < this.multipleSelection.length; i++) {
        number_total += this.multipleSelection[i].number;
      }
      return number_total;
    },
  },
  mounted() {
    request.post("/checklogin").then(result=>{
      if(result.code===1)
      {
        console.log(result)
      }
      else {
        this.$message.error("用户未登录")
        this.$router.push("/login")
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
    product.forEach(item => {
      this.tableData.push(item);
    });
  }
};
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