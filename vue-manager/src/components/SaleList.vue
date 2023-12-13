<template>
  <div class="background">
    <div class="tablediv">
      <el-table
          :data="updateDataShow"
          border
          style="width: 100%"
          :row-style="{height: '100px'}"
          :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
      >
        <el-table-column prop="saledate" label="日期"  align="center"></el-table-column>
        <el-table-column prop="buynum" label="购买次数"  align="center"></el-table-column>
        <el-table-column prop="goodsnum" label="购买商品总数"  align="center"></el-table-column>
        <el-table-column prop="totalprice" label="购买总价" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagination">
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

    <div class="chartdiv">
      <!-- 用于echart引用这个元素 -->
      <div ref="chart" style="width: 100%; height: 400px;"></div>
    </div>

  </div>
</template>

<script>
import request from "@/utility/request";
import echarts from 'echarts'

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
      tableTime:[],
      chartInstance: null,
      //图表的日期
      chartdate:[],
      //图表的数量
      chartprice:[]
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
        request.post("/deletesale", {
          "saledate": this.tableTime[index],
        }).then(result => {
          if(result.code===1)
          {
            this.$message.success("删除成功")
            this.tabletotal-=1
            this.tableData.splice(index, 1); //删除表格的数据
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
    echartsInit() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(this.$refs.chart);
      myChart.setOption({
        xAxis: {
          type: 'category',
          data: this.chartdate.reverse()
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.chartprice.reverse(),
            type: 'line'
          }
        ]
      })
    },
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
    request.post("/showsale").then(result=>{
      if(result.code===1)
      {
        result.data.forEach(item=>{
          this.tableTime.push(item.saledate)
          // 原始的 UTC 时间戳
          const utcTimestamp = item.saledate;
          // 创建 Date 对象并将 UTC 时间戳传入
          const utcDate = new Date(utcTimestamp);
          // 获取本地时间
          const localDate = new Date(utcDate.getTime());

          // 获取本地时间的字符串表示
          item.saledate = localDate.toLocaleDateString();
          this.tableData.push(item)
          this.chartdate.push(item.saledate)
          this.chartprice.push(item.totalprice)
        })
        this.tabletotal=this.tableData.length
        this.echartsInit()
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
  }
}

</script>

<style scoped>

.pagination{
  text-align: center;
  justify-content: center;
  width: 100%;
}

.el-pagination{
  margin-top: 20px;
}
</style>