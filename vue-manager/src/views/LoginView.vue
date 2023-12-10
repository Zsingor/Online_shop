<template>
  <div id="background">
    <div id="loginmain">
      <div id="loginleft">
        <img src="@/assets/login.jpg" alt="" style="width: 100%;vertical-align:middle">
      </div>
      <div id="loginright">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 20px">登 录</div>
          <el-form-item prop="managername">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入用户名" v-model="user.managername"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="validcode">
            <div style="display: flex">
              <el-input id="inputcode" prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" placeholder="请输入验证码" v-model="user.validcode"></el-input>
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

<script>
import ValidCode from "@/components/ValidCode.vue";
import request from "@/utility/request";
export default {
  name: "LoginView",
  components:{
    ValidCode
  },
  data()
  {
    return{
      code:"",
      user:{
        validcode:"",
        managername:"",
        password:""
      },
      rules:{
        managername:[
          {required:true,message:"请输入用户名",trigger:"blur"}
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"}
        ],
        validcode: [
          {required:true,message:"请输入验证码",trigger:"blur" }
        ]
      }
    }
  },
  methods:{
    getCode(code){
      this.code=code
    },

    login()
    {
      this.$refs["loginRef"].validate((valid) => {
        // 验证通过
        if (valid)
        {
          if(this.code.toLowerCase()===this.user.validcode.toLowerCase())
          {
            request.post("/managerlogin",{
              "managername":this.user.managername,
              "password":this.user.password
            }).then(result=>{
              if(result.code===1)
              {
                //this.$message.success("登录成功",showClose:true)
                this.$message({
                  title: '成功',
                  message: '用户已登录',
                  type: 'success'
                })
                this.$router.push("/")
                localStorage.setItem("managertoken",JSON.stringify(result.data))//存储用户数据
                localStorage.setItem("managername",JSON.stringify(this.user.managername))
              }
              else {
                this.$refs["loginRef"].resetFields();
                this.$message.error(result.msg);
              }
            })
          }
          else {
            document.getElementById("inputcode").value="";
            this.$message.error("验证码错误");
          }
        }
      });
    }
  }
}
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