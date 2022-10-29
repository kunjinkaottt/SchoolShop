<template>
    <div class="whole">
<!--        background-color: #fff;border-radius: 10px;padding: 10px-->
        <div style="width: 450px; margin: 150px auto; background-color: #fff;border-radius: 10px;padding: 10px">
            <div class="loginFont">
                欢迎登录校内店铺管理系统
            </div>
            <el-form :rules="rules" ref="form" :model="form">
                <el-form-item>
                    <el-radio-group v-model="radio">
                        <el-radio :label="'admin'" style="padding:0 50px 0 20px">管理员</el-radio>
                        <el-radio :label="'business'" style="padding-right: 50px">商家</el-radio>
                        <el-radio :label="'members'">普通用户</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item v-if="radio==='admin'">
                    <el-input prefix-icon="el-icon-user-solid" v-model="form.phone" placeholder="管理员账号"
                              clearable></el-input>
                </el-form-item>
                <el-form-item v-else prop="phone">
                    <el-input prefix-icon="el-icon-phone" v-model="form.phone" placeholder="请输入手机号码"
                              clearable></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input prefix-icon="el-icon-lock" v-model="form.password" placeholder="请输入密码" show-password
                              clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 95%;margin: 5px" type="primary" @click="login"><i class="el-icon-key"></i>
                        登 录
                    </el-button>
                    <el-button style="width: 95%;margin: 5px" type="primary" @click="this.$router.push('/register')">
                        前往注册
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "Login",
        data() {
            return {
                form: {},
                radio: 'admin',
                rules: {
                    phone: [
                        {required: true, message: '请输入手机号码', trigger: 'blur'},//required: true 必填  trigger: 'blur' 输入框失去焦点时显示message
                        {min: 11, max: 11, message: '请输入长度为11位的号码', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 1, max: 16, message: '密码长度不超过16位不少于1位', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            login() {
                //管理员登录
                if (this.radio === "admin") {
                    request.post("/adminLogin", this.form).then(res => {
                        if (res.code === '0') {
                            this.$message({ type: "success", message: "登录成功" })
                            sessionStorage.setItem("user", JSON.stringify(res.data))
                            this.$router.push("/admin")  //管理员 登录成功后跳转到admin页面
                        } else {
                            this.$message({ type: "error",  message: res.msg  })
                        }
                    })   //传一个手机号码和密码的对象this.form
                }
                this.$refs['form'].validate((valid) => {
                    if (valid) {

                        //商家登录
                        if (this.radio === "business") {
                            request.post("/businessLogin", this.form).then(res => {    //传一个手机号码和密码的对象this.form
                                if (res.code === '0') {
                                    this.$message({ type: "success", message: "登录成功" })
                                    sessionStorage.setItem("user", JSON.stringify(res.data))
                                    this.$router.push("/business")  //商家 登录成功后跳转到business页面
                                } else {
                                    this.$message({ type: "error",  message: res.msg  })
                                }
                            })
                        }
                        //普通用户登录
                        if (this.radio === "members") {
                            request.post("/membersLogin", this.form).then(res => {    //传一个手机号码和密码的对象this.form
                                if (res.code === '0') {
                                    this.$message({ type: "success",  message: "登录成功" })
                                    sessionStorage.setItem("user", JSON.stringify(res.data))
                                    this.$router.push("/members")  //商家 登录成功后跳转到business页面
                                } else {
                                    this.$message({  type: "error", message: res.msg  })
                                }
                            })
                        }

                    }
                })
            }
        }
    }
</script>

<style scoped>
    .whole{
        height: 100vh;
        background-image: linear-gradient(45deg,darkorange,lightskyblue,#42b983);
        overflow: hidden;
        width: 100%;
    }
    .loginFont{
        background: linear-gradient(45deg,royalblue,#42b983);
        -webkit-background-clip: text;
        color: transparent;
        font-size: 30px; text-align: center;padding: 30px 0;font-weight: bold
    }
</style>