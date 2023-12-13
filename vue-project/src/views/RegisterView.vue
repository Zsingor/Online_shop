<template>
  <div id="background">
    <div id="loginmain">
      <div id="loginleft">
        <img src="@/assets/register.jpg" alt="" style="width: 100%;vertical-align:middle">
      </div>
      <div id="loginright">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 20px">注 册</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入用户名" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input prefix-icon="el-icon-message" size="medium" placeholder="请输入邮箱" v-model="user.email"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="comfirmpass">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请确认密码" v-model="user.comfirmpass"></el-input>
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

<script>
import request from "@/utility/request";
export default {
  name: "RegisterView",
  data()
  {
    var checkUser = (rule, value, callback) => {
      if (value.length<2 || value.length>15) {
        callback(new Error('用户名长度为2-15位'));
      } else {
        callback();
      }
    };
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
    var checkPass = (rule, value, callback) => {
      if (value !== this.user.password) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    };
    return{
      user:{
        username:"",
        email:"",
        password:"",
        comfirmpass:""
      },
      rules:{
        username:[
          {required:true,message:"请输入用户名",trigger:"blur"},
          {validator: checkUser, trigger: 'blur'}
        ],
        email:[
          {required:true,message:"请输入邮箱",trigger:"blur"},
          {validator: checkEmail, trigger: 'blur'}
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"},
          { validator: checkPwd, trigger: 'blur' }
        ],
        comfirmpass: [
          {required:true,message:"请再次确认密码",trigger:"blur"},
          { validator: checkPass, trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    register()
    {
      this.$refs["registerRef"].validate((valid) => {
        // 验证通过
        if (valid)
        {
          request.post("/register",{
            "username":this.user.username,
            "email":this.user.email,
            "password":this.user.password
          }).then(result=>{
            if(result.msg==="success")
            {
              this.$message.success("注册成功")
              this.$router.push("/login")
            }
            else {
              this.$refs["registerRef"].resetFields();
              this.$message.error(result.msg);
            }
          })
        }
      });
    },
    handleWatchEnter(e) {
      var key = window.event ? e.keyCode : e.which;
      console.log(key);
      if (key === 13) {
        // 这里执行相应的行为动作
        this.register()
      }
    }
  },
  mounted()
  {
    document.addEventListener('keydown', this.handleWatchEnter);
  }
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