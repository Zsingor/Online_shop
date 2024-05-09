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
    <el-dialog title="修改信息" v-model="editIsDialog" class="editDialog">
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
    <el-dialog title="修改密码" v-model="editpwdIsDialog" class="editpwdDialog">
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
    <el-dialog title="注销账号" v-model="deleteIsDialog" class="deleteDialog">
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

<script setup>
import {ref,onMounted} from 'vue'
import request from "@/utility/request";
import {ElMessage,ElMessageBox} from "element-plus";
import {onBeforeRouteLeave, useRouter} from 'vue-router'

onBeforeRouteLeave((to, from, next) => {
  next();
  // 判断是否是从 /person 到 /login 的路由切换
  if (from.path === '/person' && to.path === '/login') {
    window.location.reload();
  }
})

const updateRef = ref(null)
const changeRef=ref(null)
const deleteRef=ref(null)
const router = useRouter()

//控制编辑信息弹窗显示
var editIsDialog=ref(false)
//控制修改密码弹窗显示
var editpwdIsDialog=ref(false)
//控制注销账号弹窗显示
var deleteIsDialog=ref(false)
//注销验证的密码
var deletepwd=ref({
  password:""
})
//控制修改密码的进度
var pwdactive=ref(0)
var editpwd=ref({
  password:"",
  newpassword:"",
  checkpassword:""
})
var editData=ref({
  name: '',
  email:'',
  gender: ''
})
var formLabelWidth=ref(100)
var user=ref({
  username: '',
  email: '',
  name: '',
  gender: '',
  money: ''
})
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
  if (value !== editpwd.value["newpassword"]) {
    callback(new Error('两次输入密码不一致'));
  } else {
    callback();
  }
};

var rules=ref({
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
})

//获取用户的信息
const getmessage=()=>{
  request.post("/select", {
    "username": JSON.parse(localStorage.getItem("username"))
  }).then(result => {
    if (result.code === 1) {
      user.value["username"] = result.data["username"]
      user.value["name"] = result.data["name"]
      user.value["email"] = result.data["email"]
      user.value["gender"] = result.data["gender"]
      user.value["money"] = result.data["money"]
    }
  }).catch(error=>{
        ElMessage.error("获取用户信息失败")
        console.log(error)
      })
}

//确定编辑
const editsure=()=> {
  updateRef.value.validate((valid) => {
    // 验证通过
    if (valid)
    {
      editIsDialog.value=false
      request.post("/update", {
        "username":JSON.parse(localStorage.getItem("username")),
        "email": editData.value["email"],
        "name": editData.value["name"],
        "gender":editData.value["gender"]
      }).then(result => {
        console.log(result)
        if (result.code === 1) {
          ElMessage.success("修改成功")
          getmessage()
        } else {
          ElMessage.error(result.msg)
        }
      }).catch(error => {
            ElMessage.error("修改失败")
            console.log(error)
      })
    }
  });
}

//取消编辑
const canceledit=()=>{
  editpwdIsDialog.value = false;
  editpwd.value={
    password:"",
    newpassword:"",
    checkpassword:""
  }
}

//验证密码
const editpwdnext=()=>{
  changeRef.value.validate((valid) => {
    // 验证通过
    if (valid)
    {
      request.post("/checkpwd", {
        "username":JSON.parse(localStorage.getItem("username")),
        "password":editpwd.value["password"]
      }).then(result => {
        if (result.code === 1)
        {
          if (pwdactive.value++ > 1) pwdactive.value = 0;
        } else {
          ElMessage.error(result.msg)
        }
      }).catch(error => {
            ElMessage.error("修改失败")
            console.log(error)
          })
    }
  });
}

//确认修改密码
const editpwdsure=()=>{
  changeRef.value.validate((valid) => {
    // 验证通过
    if (valid)
    {
      request.post("/updatepwd", {
        "username":JSON.parse(localStorage.getItem("username")),
        "password":editpwd.value["newpassword"]
      }).then(result => {
        console.log(result)
        if (result.code === 1)
        {
          ElMessage.success("密码更新成功")
          pwdactive.value=0;
          editpwdIsDialog.value=false;
          editpwd.value={
            password:"",
            newpassword:"",
            checkpassword:""
          }
        } else {
          ElMessage.error(result.msg)
        }
      }).catch(error => {
            ElMessage.error("修改失败")
            console.log(error)
          })
    }
  });
}

//取消删除
const canceldelete=()=>{
  deleteIsDialog.value=false
  deletepwd.value["password"]=""
}

//注销账号
const deletecount=()=>{
  // 弹出消息提示
  ElMessageBox.confirm(
      '此操作将永久删除该账号, 是否继续?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    request.post("/delete", {
      "username": JSON.parse(localStorage.getItem("username"))
    }).then(result => {
      console.log(result)
      if (result.code === 1) {
        deleteIsDialog.value=false
        editpwd.value={
          password:"",
          newpassword:"",
          checkpassword:""
        }
        ElMessage.success("注销成功")
        localStorage.removeItem("username")
        localStorage.removeItem("usertoken")
        localStorage.removeItem("goods")
        router.push("/login")
      } else {
        ElMessage.error(result.msg)
      }
    })
        .catch(error => {
          ElMessage.error("注销失败")
          console.log(error)
        })
  }).catch(() => {

  });
}

//确认注销
const deletesure=()=>{
  deleteRef.value.validate((valid) => {
    // 验证通过
    if (valid)
    {
      request.post("/checkpwd", {
        "username":JSON.parse(localStorage.getItem("username")),
        "password":deletepwd.value["password"]
      }).then(result => {
        if (result.code === 1)
        {
          deletecount()
        } else {
          ElMessage.error(result.msg)
        }
      }).catch(error => {
            ElMessage.error("修改失败")
            console.log(error)
          })
    }
  });
}

onMounted(()=>{
  request.post("/checklogin").then(result=>{
    if(result.code===1)
    {
      getmessage()
    }
    else {
      ElMessage.error("用户未登录")
      router.push("/login")
      localStorage.removeItem("username")
      localStorage.removeItem("usertoken")
      localStorage.removeItem("goods")
    }
  }).catch(error=>{
        console.log(error)
      })
})
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