<template>
  <div class="background">
    <div class="maintable">
      <el-table
          :data="updateDataShow"
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
  data() {
    return {
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
  methods: {
    //删除订单的信息
    handleDelete(index) {
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
          if (result.code === 1) {
            this.$message.success("删除成功")
            this.tabletotal-=1
            this.tableData.splice(index, 1); //删除表格的数据
          } else {
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
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 点击分页组件会返回页码， 根据页码更新dataShow数据(显示的项目)
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
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
  mounted() {
    request.post("/selectuserorder",{
      "username":JSON.parse(localStorage.getItem("username"))
    }).then(result => {
      if(result.code===1)
      {
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
        this.$message.error("用户未登录")
        this.$router.push("/login")
        localStorage.removeItem("username")
        localStorage.removeItem("usertoken")
        localStorage.removeItem("goods")
      }
    })
        .catch(error => {
          console.log(error)
        })
  }
}

</script>

<style scoped>

.maintable {
  margin-top: 30px;
  padding-right: 10%;
  padding-left: 10%;
}

.el-pagination{
  margin-bottom: 30px;
  margin-top: 30px;
}
</style>