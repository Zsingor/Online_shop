<template>
  <div>
    <div class="searchinputdiv">
      <el-input class="searchinput" prefix-icon="el-icon-search" v-model="search" placeholder="请输入查询用户"></el-input>
      <el-button class="searchbtn" type="primary" @click="select">查询</el-button>
      <el-button class="searchbtn" type="danger" @click="cancel">取消</el-button>
    </div>
    <div class="tablediv">
      <el-table
          :data="updateDataShow"
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
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pagesize"
        layout="prev, pager, next, jumper"
        :total="tabletotal">
    </el-pagination>
  </div>
</template>

<script>
import request from "@/utility/request";

export default {
  data(){
    return{
      //当前页
      currentPage: 1,
      //显示的订单数
      pagesize: 10,
      //总订单数
      tabletotal:0,
      //搜索框的内容
      search: "",
      //表格中的数据
      tableData: [],
      //存储原始的时间戳
      tableTime:[]
    }
  },
  methods:{
    //删除订单的信息
    handleDelete(index)
    {
      index=index+(this.currentPage - 1) * this.pagesize
      // 弹出消息提示
      this.$confirm('此操作将永久删除该订单, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //删除商品
        request.post("/deleteorder", {
          "username": this.tableData[index]["username"],
          "goodsid": this.tableData[index]["goodsid"],
          "buytime": this.tableTime[index],
        }).then(result => {
          if(result.code===1)
          {
            this.$message.success("删除成功")
            this.tableData.splice(index, 1); //删除表格的数据
            this.tabletotal-=1
          }
          else {
            this.$message.error(result.msg)
          }
        })
            .catch(error => {
              this.$message.error("删除失败")
              console.log(error)
            })
      }).catch(() => {

      });
    },
    selectallorder(){
      request.post("/selectorder").then(result=>{
        if(result.code===1)
        {
          result.data.forEach(item=>{
            this.tableTime.push(item.buytime)
            // 原始的 UTC 时间戳
            const utcTimestamp = item.buytime;
            // 创建 Date 对象并将 UTC 时间戳传入
            const utcDate = new Date(utcTimestamp);
            // 获取本地时间
            const localDate = new Date(utcDate.getTime());

            // 获取本地时间的字符串表示
            item.buytime = localDate.toLocaleString();
            this.tableData.push(item)
          })
          this.tabletotal=this.tableData.length
        }
        else {
          this.$message.error("用户未登录")
          this.$router.push("/login")
          localStorage.removeItem("managername")
          localStorage.removeItem("managertoken")
        }
      })
          .catch(error=>{
            console.log(error)
          })
    },
    select(){
      request.post("/selectuserorder",{
        "username":this.search
      }).then(result => {
        if(result.code===1)
        {
          this.tableData=[]
          result.data.forEach(item => {
            this.tableTime.push(item.buytime)
            // 原始的 UTC 时间戳
            const utcTimestamp = item.buytime;
            // 创建 Date 对象并将 UTC 时间戳传入
            const utcDate = new Date(utcTimestamp);
            // 获取本地时间
            const localDate = new Date(utcDate.getTime());
            // 获取本地时间的字符串表示
            item.buytime = localDate.toLocaleString();
            this.tableData.push(item)
          })
          this.tabletotal=this.tableData.length
        }
        else {
          this.$message.error("查询失败")
        }
      })
          .catch(error => {
            console.log(error)
          })
    },
    cancel(){
      this.search=""
      this.tableData=[]
      this.selectallorder()
    }
  },
  computed:{
    updateDataShow() {
      // 分页开始的项目索引
      let startPage = (this.currentPage - 1) * this.pagesize
      // 分页结束的项目索引
      let endPage = startPage + this.pagesize
      return  this.tableData.slice(startPage, endPage)
    }
  },
  mounted()
  {
    this.selectallorder()
  }
}

</script>

<style scoped>
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
  margin-top: 20px;
}
</style>