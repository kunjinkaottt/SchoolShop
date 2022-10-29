<template>
    <div>
        <el-card style="width: 40%; margin: 10px">
            <span  style="color: dodgerblue;">个人信息</span>
            <hr align=center style="margin: 20px 0" color=dodgerblue SIZE=1/>
            <el-form :rules="relus1"
                     ref="form1"
                     :model="businessObject"
                     label-width="80px">
                <el-form-item label="商家编号" style="width:40%">
                    <el-input v-model="businessObject.bid" disabled></el-input>
                </el-form-item>
                <el-form-item label="店铺编号" style="width:40%">
                    <el-input v-model="businessObject.shopid" disabled></el-input>
                </el-form-item>
                <el-form-item label="真实姓名" style="width:40%">
                    <el-input v-model="businessObject.truename" disabled></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="businessObject.gender" disabled>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="phone" label="手机号码">
                     <el-input placeholder="输入要改绑的手机号码" v-model="businessObject.phone"></el-input>
                </el-form-item>
            </el-form>
            <div style="text-align: center">
                <el-button type="primary" @click="updateBusiness" style="background-color: #42b983;border:0">保存修改</el-button>
            </div><div style="text-align: center">
                <el-button type="text" style="color: red;float: right" @click="dialogVisible=true">注销账号</el-button>
            </div>
        </el-card>
<!--        密码-->
        <el-card style="width: 40%; margin: 10px">
            <span style="color:dodgerblue;">修改密码</span>
            <hr align=center style="margin: 20px 0" color=dodgerblue SIZE=1/>   <!-- 分割线 -->
            <el-form ref="form" :model="passwordObject" label-width="80px" :rules="rules">
                <el-form-item label="原密码" prop="password">
                    <el-input v-model="passwordObject.password" show-password></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPass">
                    <el-input v-model="passwordObject.newPass" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPass">
                    <el-input v-model="passwordObject.confirmPass" show-password></el-input>
                </el-form-item>
            </el-form>

            <div style="text-align: center">
                <el-button type="primary" @click="updatePwd" style="background-color: #42b983;border:0">保存密码</el-button>
            </div>
        </el-card>
        <div style="margin: 10px 0">
            <!--      注销账号时弹出的对话框-->
            <el-dialog
                    title="注销账号"
                    v-model="dialogVisible"
                    width="30%">
                <p style="text-align: center;padding-bottom: 50px;color: red">确定注销账号吗？将会清空该账号的所有相关信息！！！</p>
                <span>
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="danger" @click="cancellation">确 定</el-button>
                </span>
            </el-dialog>
        </div>

    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "BusinessEdit",
        data(){
            return{
                relus1:{
                    phone: [
                        {required: true, message: '请输入手机号码', trigger: 'blur'},
                        {min: 11, max: 11, message: '请输入长度为11位的号码', trigger: 'blur'}
                    ]
                },
                rules: {
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                    newPass: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                    ],
                    confirmPass: [
                        {required: true, message: '请输入确认新密码', trigger: 'blur'},
                    ],
                },
                businessObject: {},
                passwordObject: {
                    password: '',
                    newPass: '',
                    confirmPass: ''
                },
                dialogVisible:false
            }
        },
        created() {
            this.load();
        },
        methods:{
            load() {
                this.businessObject = JSON.parse(sessionStorage.getItem("user"))
            },
            updateBusiness(){       //修改 商家 信息
                this.$refs['form1'].validate((valid) => {//有2个表单refs都是form “ref="form"”？
                    if (valid) {
                        if ((/(^[1-9]\d*$)/.test(this.businessObject.phone))) { //手机号码是正整数
                            request.put("/updateBusiness",this.businessObject).then(res=>{
                                if (res.code === '0') {
                                    this.$message.success('手机号码修改成功')
                                    sessionStorage.setItem("user",JSON.stringify(this.businessObject))
                                } else {
                                    this.$message.error(res.msg)
                                }
                            })
                        }else {//手机号码不是正整数
                            this.$message({type: "error", message: "请正确输入手机号码"})
                        }
                    }
                })
            },
            cancellation(){ //注销账号
                request.put("/recoverShop/"+this.businessObject.shopid).then(res1=>{                //收回店铺,商家账号变为2不通过
                    request.delete("/DeleteFailBusiness/"+this.businessObject.bid+"/"+this.businessObject.shopid).then(res2=>{     //根据bid删除商家账号，条件是商家账号状态为2不通过
                        if(res2.code==='0'){
                            this.$message.success("注销商家账号成功")
                            sessionStorage.removeItem("user")
                            this.$router.push("/login")
                        }
                    })
                })
                this.load()
                this.dialogVisible=false
            },
            updatePwd(){        //修改 密码
                this.$refs['form'].validate((valid) => {        //有2个表单refs都是form “ref="form"”？？？？？？？
                    if (valid) {
                        if (this.passwordObject.newPass !== this.passwordObject.confirmPass) {
                            this.$message.error('2次输入新密码必须一致')
                        }else {
                            this.passwordObject.bid = this.businessObject.bid
                            request.put("/updateBusinessPwd", this.passwordObject).then(res => {
                                if (res.code === '0') {
                                    this.$message.success('修改密码成功,请重新登录')
                                    sessionStorage.removeItem("user")
                                    this.$router.push("/login")
                                } else {
                                    this.$message.error(res.msg)
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

</style>