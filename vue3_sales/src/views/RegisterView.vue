<template>
  <div id="background">
    <div id="loginmain">
      <div id="loginleft">
        <img src="@/assets/register.jpg" alt="" style="width: 100%;vertical-align:middle">
      </div>
      <div id="loginright">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 20px">注 册</div>
          <el-form-item prop="id">
            <el-input :prefix-icon="User" size="large" placeholder="请输入ID" v-model="user.id"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input :prefix-icon="Lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="comfirmpass">
            <el-input :prefix-icon="Lock" show-password placeholder="请确认密码" v-model="user.comfirmpass"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%;background-color: #5cdeef;border: none" @click="register">注册</el-button>
          </el-form-item>
          <div>
            已有账号?请 <span style="color: #43d5ee;cursor: pointer" @click="$router.push('/login')">登录</span>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>

import {Lock, User,Message} from "@element-plus/icons-vue";
import request from "@/utility/request";
import {ref} from 'vue';
import {ElMessage} from "element-plus";
import { useRouter } from 'vue-router'

const registerRef = ref(null);
const router = useRouter()

var user=ref({
  id:"",
  password:"",
  comfirmpass:""
})

var checkUser = (rule, value, callback) => {
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
}
var checkPwd = (rule, value, callback) => {
  if (value.length<6 || value.length>15) {
    callback(new Error('密码长度为6-15位'));
  } else {
    callback();
  }
}
var checkPass = (rule, value, callback) => {
  if (value !== user.value.password) {
    callback(new Error('两次输入密码不一致'));
  } else {
    callback();
  }
}

var rules=ref({
  id:[
    {required:true,message:"请输入ID",trigger:"blur"},
    {validator: checkUser, trigger: 'blur'}
  ],
  password:[
    {required:true,message:"请输入密码",trigger:"blur"},
    { validator: checkPwd, trigger: 'blur' }
  ],
  comfirmpass: [
    {required:true,message:"请再次确认密码",trigger:"blur"},
    { validator: checkPass, trigger: 'blur' }
  ]
})

var register=()=>{
  registerRef.value.validate((valid) => {
    // 验证通过
    if (valid)
    {
      request.post("/salesregister",{
        "id":user.value.id,
        "password":user.value.password
      }).then(result=>{
        if(result.msg==="success")
        {
          ElMessage({
            message: '注册成功',
            type: 'success',
          })
          router.push("/login")
        }
        else {
          registerRef.value.resetFields();
          ElMessage.error(result.msg)
        }
      })
    }
  });
}

</script>

<style scoped>
#background
{height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #9de8e6
}
#loginmain
{
  display: flex;
  background-color: white;
  width: 50%;
  border-radius: 5px;
  overflow: hidden;
}
#loginleft
{
  flex: 1;
}
#loginright
{
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>