<template>
  <div id="background">
    <div id="loginmain">
      <div id="loginleft">
        <img src="@/assets/login.jpg" alt="" style="width: 100%;vertical-align:middle">
      </div>
      <div id="loginright">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 20px">登 录</div>
          <el-form-item prop="id">
            <el-input :prefix-icon="User" size="large" placeholder="请输入ID" v-model="user.id"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input :prefix-icon="Lock" size="large" show-password type="password" placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="validcode">
            <div style="display: flex">
              <el-input id="inputcode" :prefix-icon="CircleCheck" size="large" style="flex: 1;" placeholder="请输入验证码" v-model="user.validcode"></el-input>
              <div style="flex: 1;height: 36px;text-align: right">
                <valid-code @input="getCode"></valid-code>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%;background-color: #6777ef;border: none" @click="login">登录</el-button>
          </el-form-item>
          <div>
            还没有账号?请 <span style="color: #409eff;cursor: pointer" @click="$router.push('/register')">注册</span>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import ValidCode from "@/components/ValidCode.vue";
import request from "@/utility/request";
import axios from 'axios';
import {ref} from 'vue';
import {ElMessage} from "element-plus";
import { useRouter } from 'vue-router'
import {User, Lock, CircleCheck} from "@element-plus/icons-vue";

const router = useRouter()
const loginRef = ref(null);
const code=ref("");
const user=ref({
  id:"",
  password:"",
  validcode:""
});

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

const rules = ref({
  id:[
    {required:true,message:"请输入ID",trigger:"blur"},
    {validator: checkUser, trigger: 'blur'}
  ],
  password:[
    {required:true,message:"请输入密码",trigger:"blur"}
  ],
  validcode: [
    {required:true,message:"请输入验证码",trigger:"blur" }
  ]
})

const getCode=(data)=>{
  code.value=data;
}

const login = () => {
  loginRef.value.validate((valid) => {
    if (valid) {
      if (code.value.toLowerCase() === user.value.validcode.toLowerCase()) {
        request.post("/saleslogin", {
          "id": parseInt(user.value.id),
          "password": user.value.password
        }).then(result => {
          if (result.code === 1) {
            ElMessage({
              message: '登录成功',
              type: 'success',
            })
            localStorage.setItem("usertoken", JSON.stringify(result.data)) //存储用户数据
            localStorage.setItem("username", JSON.stringify(user.value.id))
            router.push("/")
            //getip()
          } else {
            loginRef.value.resetFields();
            ElMessage.error(result.msg)
          }
        });
      } else {
        document.getElementById("inputcode").value = ""
        ElMessage.error("验证码错误")
      }
    }
  });
};

// const getip=()=> {
//   axios.post("https://ipapi.co/json/").then(result=>{
//     var ip=result.data.ip
//     var cityAndAddress=result.data.country_name+"-"+result.data.city
//     var logs={userid:"",identify:"",operate:"",ip:"",address:""}
//     logs.userid=JSON.parse(localStorage.getItem("username"))
//     logs.identify="销售员"
//     logs.operate="登录"
//     logs.ip=ip
//     logs.address=cityAndAddress
//     request.post("/addlog",logs)
//   })
// }

</script>

<style scoped>
#background
{height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #9da7e8
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