<template>
  <div class="cart">
    <div class="searchinputdiv">
      <el-input class="searchinput" prefix-icon="el-icon-search" v-model="search" placeholder="请输入商品编号">
      </el-input>
      <el-button class="searchbtn" type="primary" @click="select">查询</el-button>
      <el-button class="searchbtn" type="danger" @click="cancel">取消</el-button>
    </div>
    <el-table
        :data="updateDataShow"
        border
        style="width: 100%"
        :row-style="{height: '260px'}"
        :header-cell-style="{backgroundColor:'aliceblue',fontWeight:'blod',color:'#666'}"
    >
      <el-table-column prop="id" label="商品编号" width="100" align="center"></el-table-column>
      <el-table-column prop="mainimg" label="图片" align="center">
        <template slot-scope="scope">
          <el-image
              style="width: 150px; height: 200px"
              :src="$baseUrl+`/download/${scope.row.mainimg}`"
              :fit="cover"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="180" align="center"></el-table-column>
      <el-table-column prop="description" label="描述" width="180" align="center"></el-table-column>
      <el-table-column prop="price" label="价格" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handlechange(scope.row)">修改</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="group_btn">
      <el-button type="success" @click="addgoods">添加商品</el-button>
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

    <!-- 添加新增的内容 -->
    <el-dialog title="添加信息" :visible.sync="addIsDialog" class="addDialog">
      <el-form :model="addData" :rules="rules" ref="addRef">
        <el-form-item label="商品编号" prop="id" :label-width="formLabelWidth">
          <el-input v-model="addData.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name" :label-width="formLabelWidth">
          <el-input v-model="addData.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description" :label-width="formLabelWidth">
          <el-input v-model="addData.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price" :label-width="formLabelWidth">
          <el-input v-model="addData.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" :label-width="formLabelWidth">
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
    <el-dialog title="修改信息" :visible.sync="editIsDialog" class="editDialog">
      <el-form :model="editData" :rules="rules" ref="editRef">
        <el-form-item label="名称" prop="name" :label-width="formLabelWidth">
          <el-input v-model="editData.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description" :label-width="formLabelWidth">
          <el-input v-model="editData.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price" :label-width="formLabelWidth">
          <el-input v-model="editData.price" autocomplete="off"></el-input>
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
        <el-button @click="editIsDialog = false">取 消</el-button>
        <el-button type="primary" @click="editSureBtn">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utility/request";

export default {
  data() {
    var checkId = (rule, value, callback) => {
      const regId = /^[0-9]*$/;
      if (!regId.test(value)) {
        callback(new Error('请输入正确的ID格式'));
      } else {
        callback();
      }
    };
    var checkPrice = (rule, value, callback) => {
      const regId = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
      if (!regId.test(value)) {
        callback(new Error('请输入正确的价格'));
      } else {
        callback();
      }
    };
    return {
      managertoken: JSON.parse(localStorage.getItem("managertoken") || '{}'),
      temp:"5fa94baba96c0.jpg",
      //当前页
      currentPage: 1,
      //当前页第一行在tabledata中的下标
      startPage: 0,
      //显示的订单数
      pagesize: 10,
      //总商品数
      tabletotal: 0,
      //搜索框的内容
      search: "",
      //编辑弹窗
      editIsDialog: false,
      //添加弹窗
      addIsDialog: false,
      //存放要修改的数据
      editData: {},
      //存放要添加的数据
      addData: {
        id: "",
        name: "",
        description: "",
        price: "",
        mainimg: ""
      },
      //表格中的数据
      tableData: [],
      rules: {
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
        ]
      }
    };
  },
  methods: {
    //删除商品列表中的商品
    handleDelete(row) {
      const index = this.tableData.findIndex(item => item.id === row["id"]);
      // 弹出消息提示
      this.$confirm('此操作将永久删除该商品, 是否继续?', '警告', {
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
            this.$message.success("删除成功")
            this.tableData.splice(index, 1); //删除表格的数据
            this.tabletotal -= 1
          } else {
            this.$message.error("删除失败")
          }
        })
            .catch(error => {
              this.$message.error("删除失败")
              console.log(error)
            })
      }).catch(() => {

      });
    },
    // 点击弹出添加弹窗
    handlechange(row) {
      this.editIsDialog = true;
      this.editData = row;
    },
    //编辑商品
    editSureBtn() {
      this.$refs["editRef"].validate((valid) => {
        // 验证通过
        if (valid) {
          this.editIsDialog = false
          request.post("/goodschange", {
            "id": this.editData["id"],
            "name": this.editData["name"],
            "description": this.editData["description"],
            "price": this.editData["price"],
            "mainimg": this.editData["mainimg"]
          }).then(result => {
            if (result.code === 1) {
              this.$message.success("修改成功")
              this.editData = {}
            } else {
              this.$message.error("修改失败")
            }
          })
              .catch(error => {
                this.$message.error("修改失败")
                console.log(error)
              })
        }
      });
    },
    addgoods() {
      this.addIsDialog = true
    },
    //添加商品
    addSureBtn() {
      this.$refs["addRef"].validate((valid) => {
        // 验证通过
        if (valid) {
          this.addIsDialog = false;
          request.post("/goodsadd", {
            "id": this.addData["id"],
            "name": this.addData["name"],
            "description": this.addData["description"],
            "price": this.addData["price"],
            "mainimg": this.addData["mainimg"]
          }).then(result => {
            console.log(result)
            if (result.code === 1) {
              this.tableData.push(this.addData);
              this.$message.success("添加成功")
              this.tabletotal += 1
              //初始化添加表单
              this.addData = {
                id: 0,
                name: "",
                description: "",
                price: 0,
                mainimg: ""
              }
            } else {
              this.$message.error("商品编号已存在")
            }
          })
              .catch(error => {
                this.$message.error("添加失败")
                console.log(error)
                //初始化添加表单
                this.addData = {
                  id: 0,
                  name: "",
                  description: "",
                  price: 0,
                  mainimg: ""
                }
              })
        }
      });
    },
    handleAddSuccess(res) {
      this.addData.mainimg = res.data;
    },
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('上传图片只能是 JPG或PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      return (isJPG || isPNG) && isLt2M;
    },
    handleEditSuccess(res) {
      this.editData.mainimg = res.data;
    },
    gettablemsg(){
      request.post("/goodsshow").then(result => {
        if (result.code === 1) {
          result.data.forEach(item => {
            this.tableData.push(item)
          })
          this.tabletotal = this.tableData.length
        }
      })
          .catch(error => {
            console.log(error)
          })
    },
    select(){
      request.post("/goodsselect",{
        "id":this.search
      }).then(result => {
        if(result.code===1)
        {
          this.tableData=[]
          this.tableData.push(result.data)
          this.tabletotal=this.tableData.length
        }
        else {
          this.$message.error(result.msg)
        }
      })
          .catch(error => {
            console.log(error)
          })
    },
    cancel(){
      this.search=""
      this.tableData=[]
      this.gettablemsg()
    }
  },
  computed: {
    updateDataShow() {
      // 分页开始的项目索引
      let startPage = (this.currentPage - 1) * this.pagesize
      // 分页结束的项目索引
      let endPage = startPage + this.pagesize
      return this.tableData.slice(startPage, endPage)
    }
  },
  mounted() {
    request.post("/checklogin").then(result => {
      if (result.code === 1) {
        console.log(result)
      } else {
        this.$message.error("用户未登录")
        this.$router.push("/login")
        localStorage.removeItem("managername")
        localStorage.removeItem("managertoken")
      }
    })
        .catch(error => {
          console.log(error)
        })
    this.gettablemsg()
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

.group_btn {
  margin-top: 20px;
}

.el-pagination {
  margin-top: 30px;
}
</style>