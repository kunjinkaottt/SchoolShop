<template>
    <div class="whole">
        <div style="width: 450px; margin: 120px auto; background-color: #fff;border-radius: 10px;padding: 10px">
            <div class="registerFont">
                校内店铺管理系统注册界面
            </div>
            <el-form :rules="rules" ref="form" :model="form">
                <el-form-item>
                    <el-radio-group v-model="radio">
                        <el-radio :label="'business'" style="padding:0 100px 0 60px">商家</el-radio>
                        <el-radio :label="'members'"  >普通用户</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="phone">
                    <el-input prefix-icon="el-icon-phone" v-model="form.phone" placeholder="手机号码注册"
                              clearable></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input prefix-icon="el-icon-unlock" v-model="form.password" placeholder="请输入密码" show-password
                              clearable></el-input>
                </el-form-item>
                <el-form-item prop="confirm">
                    <el-input prefix-icon="el-icon-lock" v-model="form.confirm" placeholder="请确认密码" show-password
                              clearable></el-input>
                </el-form-item>
                <el-form-item prop="truename">
                    <el-input prefix-icon="el-icon-user-solid" v-model="form.truename" placeholder="真实姓名"
                              clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-radio-group v-model="form.gender">
                        <el-radio :label="1" style="padding:0 100px 0 60px">男</el-radio>
                        <el-radio :label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="shopid" v-if="this.radio==='business'">
                    <el-input prefix-icon="el-icon-s-shop" v-model.number="form.shopid" placeholder="请输入想要申请管理的店铺编号"
                              clearable></el-input>
                </el-form-item>
                <el-form-item prop="username" v-if="this.radio==='members'">
                    <el-input prefix-icon="el-icon-user" v-model="form.username" placeholder="输入用户名"
                              clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 95%;margin: 5px" type="primary" @click="register">
                        <i class="el-icon-s-check"></i> 注 册
                    </el-button>
                    <el-button style="width: 95%;margin: 5px" type="primary" @click="this.$router.push('/login')">返回登录
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "Register",
        data() {
            return {
                form: {gender: 1,state: 0},
                radio: 'members',
                ruleForm: {
                    phone: '',
                    password: '',
                    confirm: '',
                    truename: '',
                    shopid: '',
                    username: ''
                },
                rules: {
                    phone: [
                        {required: true, message: '请输入手机号码', trigger: 'blur'},
                        {min: 11, max: 11, message: '请输入长度为11位的号码', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 1, max: 16, message: '密码长度不超过16位不少于1位', trigger: 'blur'}
                    ],
                    confirm: [
                        {required: true, message: '请确认密码', trigger: 'blur'},
                        {min: 1, max: 16, message: '密码长度不超过16位不少于1位', trigger: 'blur'}
                    ],
                    truename: [
                        {required: true, message: '请输入真实姓名', trigger: 'blur'},
                    ],
                    shopid: [
                        {required: true, message: '输入想要申请管理的店铺编号', trigger: 'blur'},
                        {min: 1, message: '请合法输入目前待租店铺编号', type: 'number', trigger: 'blur'}
                    ],
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 1, max: 15, message: '用户名长度不超过15位不少于1位', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            register() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {         //如果输入框的内容合法 才会发送注册的请求

                        if ((/(^[1-9]\d*$)/.test(this.form.phone))) { //手机号码是正整数
                            if (this.form.password !== this.form.confirm) {
                                this.$message.error("两次输入的密码不一致")
                                return
                            }
                            if (this.radio === "business") {    //商家注册
                                request.post("/businessRegister", this.form).then(res => {    //传一个注册填写信息的对象this.form
                                    if (res.code === '0') {
                                        this.$message.success(res.msg)
                                    } else {
                                        this.$message.error(res.msg)
                                    }
                                })
                            }
                            if (this.radio === "members") {     //普通用户注册
                                request.post("/membersRegister", this.form).then(res => {    //传一个注册填写信息的对象this.form
                                    if (res.code === '0') {
                                        this.$message.success("注册成功,可前往登录")
                                    } else {
                                        this.$message.error(res.msg)
                                    }
                                })
                            }
                        } else {//手机号码不是正整数
                            this.$message({type: "error", message: "请正确输入手机号码"})
                        }
                    }
                })
            },
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
    .registerFont{
        background: linear-gradient(45deg,royalblue,#42b983);
        -webkit-background-clip: text;
        color: transparent;
        font-size: 30px; text-align: center;padding: 30px 0;font-weight: bold
    }
</style>