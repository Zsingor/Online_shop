<template>
  <div class="user-profile">
    <el-row>
      <el-col :span="24">
        <el-card shadow="hover">
          <div slot="header" class="user-profile__card-title">个人资料</div>
          <div class="user-profile__info">
            <div class="user-profile__info-item">
              <span class="user-profile__label">用户名：</span>
              <span class="user-profile__text">{{ user.username }}</span>
            </div>
            <div class="user-profile__info-item">
              <span class="user-profile__label">邮箱：</span>
              <span class="user-profile__text">{{ user.email }}</span>
            </div>
            <div class="user-profile__info-item">
              <span class="user-profile__label">姓名：</span>
              <span class="user-profile__text">{{ user.name }}</span>
            </div>
            <div class="user-profile__info-item">
              <span class="user-profile__label">性别：</span>
              <span class="user-profile__text">{{ user.gender }}</span>
            </div>
<!--            <div class="user-profile__info-item">
              <span class="user-profile__label">余额：</span>
              <span class="user-profile__text">{{ user.money }}</span>
            </div>-->
          </div>
          <div class="userbutton">
            <el-button class="user-actionbutton" type="danger" @click="deleteIsDialog = true">注销账号</el-button>
            <el-button class="user-actionbutton" type="primary" @click="editpwdIsDialog = true">修改密码</el-button>
            <el-button class="user-actionbutton" type="primary" @click="editIsDialog = true">修改信息</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 添加编辑的内容 -->
    <el-dialog title="修改信息" :visible.sync="editIsDialog" class="editDialog">
      <el-form :model="editData" :rules="rules" ref="updateRef">
        <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
          <el-input v-model="editData.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
          <el-input v-model="editData.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
          <el-input v-model="editData.gender" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editIsDialog = false">取 消</el-button>
        <el-button type="primary" @click="editsure">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 添加修改密码的内容 -->
    <el-dialog title="修改密码" :visible.sync="editpwdIsDialog" class="editpwdDialog">
      <el-steps :active="pwdactive" finish-status="success" simple style="margin-top: 20px">
        <el-step title="输入当前密码" ></el-step>
        <el-step title="输入新密码" ></el-step>
      </el-steps>
      <el-form :model="editpwd" :rules="rules" ref="changeRef">
        <el-form-item label="当前密码" prop="password" :label-width="formLabelWidth" v-if="pwdactive===0">
          <el-input v-model="editpwd.password" show-password autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newpassword" :label-width="formLabelWidth" v-if="pwdactive===1">
          <el-input v-model="editpwd.newpassword" show-password autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkpassword" :label-width="formLabelWidth" v-if="pwdactive===1">
          <el-input v-model="editpwd.checkpassword" show-password autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="canceledit">取 消</el-button>
        <el-button type="primary" @click="editpwdnext" v-if="pwdactive===0">下一步</el-button>
        <el-button type="primary" @click="editpwdsure" v-if="pwdactive===1">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 添加注销账号的内容 -->
    <el-dialog title="注销账号" :visible.sync="deleteIsDialog" class="deleteDialog">
      <el-form :model="deletepwd" :rules="rules" ref="deleteRef">
        <el-form-item label="当前密码" prop="password" :label-width="formLabelWidth">
          <el-input v-model="deletepwd.password" show-password autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="canceldelete">取 消</el-button>
        <el-button type="primary" @click="deletesure">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utility/request";

export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^([a-zA-Z]|[0-9])(\w|-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      if (!regEmail.test(value)) {
        callback(new Error('请输入正确的邮箱格式'));
      } else {
        callback();
      }
    };
    var checkPwd = (rule, value, callback) => {
      if (value.length<6 || value.length>15) {
        callback(new Error('密码长度为6-15位'));
      } else {
        callback();
      }
    };
    var checkPassword = (rule, value, callback) => {
      if (value !== this.editpwd["newpassword"]) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    };
    return {
      //控制编辑信息弹窗显示
      editIsDialog:false,
      //控制修改密码弹窗显示
      editpwdIsDialog:false,
      //控制注销账号弹窗显示
      deleteIsDialog:false,
      //注销验证的密码
      deletepwd:{
        password:""
      },
      //控制修改密码的进度
      pwdactive:0,
      editpwd:{
        password:"",
        newpassword:"",
        checkpassword:""
      },
      editData: {
        name: '',
        email:'',
        gender: ''
      },
      formLabelWidth:'100',
      user: {
        username: '',
        email: '',
        name: '',
        gender: '',
        money: ''
      },
      rules:{
        name:[
          {required:true,message:"请输入名字",trigger:"blur"}
        ],
        email:[
          {required:true,message:"请输入邮箱",trigger:"blur"},
          {validator: checkEmail, trigger: 'blur'}
        ],
        gender:[
          {required:true,message:"请输入性别",trigger:"blur"}
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"},
          {validator: checkPwd, trigger: 'blur'}
        ],
        newpassword:[
          {required:true,message:"请输入新密码",trigger:"blur"},
          {validator: checkPwd, trigger: 'blur'}
        ],
        checkpassword:[
          {required:true,message:"请确认新密码",trigger:"blur"},
          {validator: checkPassword, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getmessage() {
      request.post("/select", {
        "username": JSON.parse(localStorage.getItem("username"))
      }).then(result => {
        if (result.code === 1) {
          this.user["username"] = result.data["username"]
          this.user["name"] = result.data["name"]
          this.user["email"] = result.data["email"]
          this.user["gender"] = result.data["gender"]
          this.user["money"] = result.data["money"]
        }
      })
          .catch(error=>{
            this.$message.error("获取用户信息失败")
            console.log(error)
          })
    },
    editsure()
    {
      this.$refs["updateRef"].validate((valid) => {
        // 验证通过
        if (valid)
        {
          this.editIsDialog=false
          request.post("/update", {
            "username":JSON.parse(localStorage.getItem("username")),
            "email": this.editData["email"],
            "name": this.editData["name"],
            "gender":this.editData["gender"]
          }).then(result => {
            console.log(result)
            if (result.code === 1) {
              this.$message.success("修改成功")
              this.getmessage()
            } else {
              this.$message.error(result.msg)
            }
          })
              .catch(error => {
                this.$message.error("修改失败")
                console.log(error)
              })
        }
      });
    },
    canceledit(){
      this.editpwdIsDialog = false;
      this.editpwd={
        password:"",
        newpassword:"",
        checkpassword:""
      }
    },
    editpwdnext() {
      this.$refs["changeRef"].validate((valid) => {
        // 验证通过
        if (valid)
        {
          request.post("/checkpwd", {
            "username":JSON.parse(localStorage.getItem("username")),
            "password":this.editpwd["password"]
          }).then(result => {
            if (result.code === 1)
            {
              if (this.pwdactive++ > 1) this.pwdactive = 0;
            } else {
              this.$message.error(result.msg)
            }
          })
              .catch(error => {
                this.$message.error("修改失败")
                console.log(error)
              })
        }
      });
    },
    editpwdsure(){
      this.$refs["changeRef"].validate((valid) => {
        // 验证通过
        if (valid)
        {
          request.post("/updatepwd", {
            "username":JSON.parse(localStorage.getItem("username")),
            "password":this.editpwd["newpassword"]
          }).then(result => {
            console.log(result)
            if (result.code === 1)
            {
              this.$message.success("密码更新成功")
              this.pwdactive=0;
              this.editpwdIsDialog=false;
              this.editpwd={
                password:"",
                newpassword:"",
                checkpassword:""
              }
            } else {
              this.$message.error(result.msg)
            }
          })
              .catch(error => {
                this.$message.error("修改失败")
                console.log(error)
              })
        }
      });
    },
    canceldelete(){
      this.deleteIsDialog=false
      this.editpwd={
        password:"",
        newpassword:"",
        checkpassword:""
      }
    },
    deletecount() {
      // 弹出消息提示
      this.$confirm('此操作将永久删除该账号, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.post("/delete", {
          "username": JSON.parse(localStorage.getItem("username"))
        }).then(result => {
          console.log(result)
          if (result.code === 1) {
            this.deleteIsDialog=false
            this.editpwd={
              password:"",
              newpassword:"",
              checkpassword:""
            }
            this.$message.success("注销成功")
            localStorage.removeItem("username")
            localStorage.removeItem("usertoken")
            localStorage.removeItem("goods")
            this.$router.push("/login")
          } else {
            this.$message.error(result.msg)
          }
        })
            .catch(error => {
              this.$message.error("注销失败")
              console.log(error)
            })
      }).catch(() => {

      });
    },
    deletesure(){
      this.$refs["deleteRef"].validate((valid) => {
        // 验证通过
        if (valid)
        {
          request.post("/checkpwd", {
            "username":JSON.parse(localStorage.getItem("username")),
            "password":this.deletepwd["password"]
          }).then(result => {
            if (result.code === 1)
            {
              this.deletecount()
            } else {
              this.$message.error(result.msg)
            }
          })
              .catch(error => {
                this.$message.error("修改失败")
                console.log(error)
              })
        }
      });
    }
  },
  mounted() {
    request.post("/checklogin").then(result=>{
      if(result.code===1)
      {
        this.getmessage()
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
  }
}
</script>

<style scoped>
.user-profile {
  margin: 20px;
  padding-left: 10%;
  padding-right: 10%;
}

.user-profile__header {
  margin-bottom: 30px;
}

.user-profile__title {
  font-size: 24px;
}

.user-profile__card-title {
  font-size: 20px;
}

.user-profile__avatar-container {
  display: flex;
  justify-content: center;
  margin: 20px;
}

.user-profile__info {
  margin: 20px 0;
}

.user-profile__info-item {
  display: flex;
  margin-bottom: 10px;
}

.user-profile__label {
  color: #bbb;
  margin-right: 10px;
}

.user-profile__text {
  font-weight: bold;
}

.userbutton {
  display: flex;
  justify-content: center;
  text-align: center;
  margin-left: 20px;
  margin-right: 20px;
  margin-top: 40px;
}

.user-actionbutton {
  width: 100%;
}

.el-col {
  padding: 10px;
}
</style>